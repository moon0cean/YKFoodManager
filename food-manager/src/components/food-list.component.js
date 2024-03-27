import React, { Component } from "react";
import FoodDataService from "../services/food.service";

import { FoodTable } from "./food-list-data-table.component"
import { Food, columns } from "./food-list-columns.component"

const foodData: Food[] = []

export default class FoodList extends Component {
  constructor(props) {
    super(props);
    this.fetchAllFood = this.fetchAllFood.bind(this);
    this.refreshList = this.refreshList.bind(this);
    this.setActiveFood = this.setActiveFood.bind(this);

    this.state = {
      FoodList: [],
      currentFood: null,
      currentIndex: -1,
      searchFoodName: ""
    };
  }

  componentDidMount() {
    this.fetchAllFood();
  }

  fetchAllFood() {
    FoodDataService.getAll()
      .then(response => {
        if (response.data) {
            let FoodList = response.data;
            FoodList.map((FoodList, index) => (
              foodData.push({
                name: FoodList.name,
                foodType: FoodList.foodTypeId.description,
                statusId: FoodList.statusId.description,
                createdBy: FoodList.createdBy.userName,
                createdByTimestamp: FoodList.createdTimestamp
              })
          ))
          console.log(foodData);
          this.setState({
            FoodList: foodData
          });
        }
      })
      .catch(e => {
        console.log(e);
      });
  }

  refreshList() {
    this.fetchAllFood();
    this.setState({
      currentFood: null,
      currentIndex: -1
    });
  }

  setActiveFood(Food, index) {
    this.setState({
      currentFood: Food,
      currentIndex: index
    });
  }

  render() {
    const { FoodList } = this.state;

    return (
      <div className="list row">
        <div className="col-md-12">
          <h4>Food List</h4>
          <FoodTable data={FoodList} columns={columns}/>
        </div>
      </div>
    );
  }
}