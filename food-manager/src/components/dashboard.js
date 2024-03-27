import React from 'react';
import { Client } from '@stomp/stompjs';

import {
  Chart,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
} from "chart.js";
import { Line } from 'react-chartjs-2';
import 'chartjs-adapter-luxon';
import StreamingPlugin from "chartjs-plugin-streaming";
import { color } from 'chart.js/helpers';

Chart.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  StreamingPlugin
);

const chartColors = {
  red: "rgb(255, 99, 132)",
  orange: "rgb(255, 159, 64)",
  yellow: "rgb(255, 205, 86)",
  green: "rgb(75, 192, 192)",
  blue: "rgb(54, 162, 235)",
  purple: "rgb(153, 102, 255)",
  grey: "rgb(201, 203, 207)"
};

const data = {
  datasets: [{
      label: "Created",
      backgroundColor: color(chartColors.green)
        .alpha(0.5)
        .rgbString(),
      borderColor: chartColors.green,
      fill: true,
      lineTension: 0,
      borderDash: [8, 4],
      data: []
    }, {
       label: 'Updated',
        backgroundColor: color(chartColors.blue)
           .alpha(0.5)
           .rgbString(),
       borderColor: chartColors.blue,
       cubicInterpolationMode: 'monotone',
       fill: true,
       data: []
    }, {
       label: 'Deleted',
        backgroundColor: color(chartColors.red)
           .alpha(0.5)
           .rgbString(),
       borderColor: chartColors.red,
       cubicInterpolationMode: 'monotone',
       fill: true,
       data: []
    }
  ]
};

const options = {
    animation: false,
    plugins: {
      streaming: {
        frameRate: 15
      }
    },
    elements: {
      line: {
        tension: 0.5
      }
    },
    scales: {
      x: {
          type: "realtime",
          realtime: {
            delay: 2000,
//            refresh: 10000,
            duration: 220000,
          },
          time: {
            unit: "minute"
          }


//          ticks: {
//            displayFormats: 1,
//            maxRotation: 0,
//            minRotation: 0,
//            stepSize: 1,
//            maxTicksLimit: 30,
//            minUnit: "second",
//            source: "auto",
//            autoSkip: true,
//            callback: function(value) {
//             return moment(value, "HH:mm:ss").format("mm:ss");
//            }
//          }
      },
      y: {
        ticks: {
          beginAtZero: true,
          min: 0
        },
        min: 0
      }
    }
};

let brokerHost = window.FOOD_MANAGER_WEBSOCKET_URL;
if (!brokerHost) {
   brokerHost = "ws://localhost:8080";
}
const brokerURL = brokerHost + '/websocket';

class Dashboard extends React.Component {

    constructor(props){
        super(props);

        this.client = new Client({
            brokerURL: brokerURL,
            reconnectDelay: 5000,
            heartbeatIncoming: 0,
            heartbeatOutgoing: 0,
        });

        this.updateDashboard = this.updateDashboard.bind(this);
    }

    updateDashboard = (status, timestamp, datasetData) => {
        let index = datasetData.findIndex(o => o.x === timestamp);
        let value = 1;
        if (datasetData[index]) {
            datasetData[index].y = datasetData[index].y + 1;
            value = datasetData[index].y;
        } else {
            datasetData.push({ x: timestamp, y: 1 })
        }
        console.log("Status: ", status, "Timestamp: ", timestamp, "Value : ", value)
    }

    componentDidMount() {
        let onConnected = () => {
            this.client.subscribe('/dashboard/food', (msg) => {
                let foodChart = Chart.getChart("food-chart")
                if (msg.body) {
                    let jsonBody = JSON.parse(msg.body);
                    if (jsonBody.statusId) {
                        if (jsonBody.statusId === "CREATED") {
                            this.updateDashboard(jsonBody.statusId, jsonBody.creationDate, foodChart.data.datasets[0].data);
                        } else if (jsonBody.statusId === "UPDATED") {
                            this.updateDashboard(jsonBody.statusId, jsonBody.lastUpdateDate, foodChart.data.datasets[1].data);
                        } else if (jsonBody.statusId === "DELETED") {
                            this.updateDashboard(jsonBody.statusId, jsonBody.lastUpdateDate, foodChart.data.datasets[2].data);
                        }
                        foodChart.update('none');
                    }
                }
            });
        }
        let onDisconnected = () => {
            console.log("Disconnected!!")
        }
        this.client.configure({
            onConnect: onConnected,
            onDisconnect: onDisconnected
        });
        this.client.activate();
    }

    render() {
        return (
          <div>
            <Line id="food-chart" data={data} options={options} />
          </div>
        );
    }
}

export default Dashboard;