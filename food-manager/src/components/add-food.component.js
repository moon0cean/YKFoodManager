import React, { Component } from "react";
import FoodDataService from "../services/food.service";
import FoodTypeDataService from "../services/food-type.service";
import ContainerDataService from "../services/container.service";
import StatusDataService from "../services/status.service";
import CustomerDataService from "../services/customer.service";
import UomDataService from "../services/uom.service";
import Select from 'react-select'
import Switch from "react-switch";

export default class AddFood extends Component {
  constructor(props) {
    super(props);
    this.onChangeName = this.onChangeName.bind(this);
    this.onChangeFoodType = this.onChangeFoodType.bind(this);
    this.onChangeContainer = this.onChangeContainer.bind(this);
    this.onChangeStatus = this.onChangeStatus.bind(this);
    this.onChangeRequiresCooler = this.onChangeRequiresCooler.bind(this);
    this.onChangeCapacity = this.onChangeCapacity.bind(this);
    this.onChangeCreatedBy = this.onChangeCreatedBy.bind(this);
    this.saveFood = this.saveFood.bind(this);
    this.newFood = this.newFood.bind(this);

    this.state = {
      name: "",
      foodTypeId: "",
      requiresCooler: true,
      capacity: "",
      containerTypeId: "",
      statusId: "",
      createdBy: "",

      foodTypes: [],
      uoms: [],
      containerTypes: [],
      statuses: [],
      customers: [],

      submitted: false
    };
  }

  async getFoodTypes() {
    FoodTypeDataService.getAll().then(
      response => {
        this.setState({
          foodTypes: response.data.map(d => ({
            "value" : d.typeId,
            "label" : d.description
          }))
        });
      }
    ).catch(e => {
      console.error(e);
    });
  }

  async getContainers() {
    ContainerDataService.getAll().then(
      response => {
        this.setState({
          containerTypes: response.data.map(d => ({
             "value" : d.containerTypeId,
             "label" : d.description
           }))
        });
      }
    ).catch(e => {
      console.error(e);
    });
  }

  async getStatuses() {
    StatusDataService.getAll().then(
      response => {
        this.setState({
          statuses: response.data.map(d => ({
            "value" : d.statusId,
            "label" : d.description
          }))
        });
      }
    ).catch(e => {
        console.error(e);
    });
  }

  async getCustomers() {
    CustomerDataService.getAll().then(
      response => {
        this.setState({
          customers: response.data.map(d => ({
            "value" : d.userName,
            "label" : (d.name + " " + d.lastName)
          }))
        });
      }
    ).catch(e => {
      console.error(e);
    });
  }

  async getCapacity() {
      UomDataService.getAll().then(
        response => {
          this.setState({
            uoms: response.data.map(d => ({
              "value" : d.uomId,
              "label" : (d.description)
            }))
          });
        }
      ).catch(e => {
        console.error(e);
      });
  }

  componentDidMount() {
    this.getFoodTypes()
    this.getContainers()
    this.getStatuses()
    this.getCustomers()
    this.getCapacity()
  }

  onChangeName(e) {
    this.setState({
      name: e.target.value
    });
  }

  onChangeFoodType(e) {
    console.log("food type changed: ", e)
    this.setState({
      foodTypeId: e.value
    });
  }

  onChangeContainer(e) {
    this.setState({
      containerTypeId: e.value
    });
  }

  onChangeStatus(e) {
    this.setState({
      statusId: e.value
    });
  }

  onChangeCreatedBy(e) {
    this.setState({
      createdBy:  e.value
    });
  }

  onChangeRequiresCooler(checked) {
    this.setState({
      requiresCooler: checked
    });
  }

  onChangeCapacity(e) {
    this.setState({
      capacity: e.value
    });
  }

  saveFood() {
    var data = {
      name: this.state.name,
      foodTypeId: this.state.foodTypeId,
      requiresCooler: this.state.requiresCooler,
      capacity: this.state.capacity,
      containerTypeId: this.state.containerTypeId,
      statusId: this.state.statusId,
      createdBy: this.state.createdBy
    };

    FoodDataService.create(data)
      .then(response => {
        this.setState({
          id: response.data.id,
          name: response.data.name,
          foodTypeId: response.data.foodTypeId,
          requiresCooler: response.data.requiresCooler,
          capacity: response.data.capacity,
          containerTypeId: response.data.containerTypeId,
          statusId: response.data.statusId,
          createdBy: response.data.createdBy,

          submitted: true
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  newFood() {
    this.setState({
      name: "",
      foodTypeId: "",
      requiresCooler: false,
      capacity: 0,
      capacityUom: 0,
      containerTypeId: "",
      statusId: "",
      createdBy: "",

      submitted: false
    });
  }

  render() {
    return (
      <div className="submit-form">
        {this.state.submitted ? (
          <div>
            <h4>You submitted successfully!</h4>
            <button className="btn btn-success" onClick={this.newFood}>
              Add
            </button>
          </div>
        ) : (
          <div>
            <div className="form-group">
              <label htmlFor="name">Name</label>
              <input
                type="text"
                className="form-control"
                id="name"
                required
                value={this.state.name}
                onChange={this.onChangeName}
                name="name"
              />
            </div>

            <div className="form-group">
              <label htmlFor="foodTypeId">Food Type</label>
              <Select
                id="foodTypeId"
                required
                options={this.state.foodTypes}
                onChange={this.onChangeFoodType}
                name="foodTypeId"
              />
            </div>

            <div className="form-group">
              <label htmlFor="containerTypeId">Container</label>
              <Select
                id="containerTypeId"
                required
                options={this.state.containerTypes}
                onChange={this.onChangeContainer}
                name="containerTypeId"
              />
            </div>

            <div className="form-group">
              <label htmlFor="statusId">Status</label>
              <Select
                id="statusId"
                required
                options={this.state.statuses}
                onChange={this.onChangeStatus}
                name="statusId"
              />
            </div>

            <div className="form-group">
              <label htmlFor="requiresCooler">
                <span>Requires Cooler</span>
                <Switch
                  id="requiresCooler"
                  className=""
                  checked={this.state.requiresCooler}
                  onChange={this.onChangeRequiresCooler}
                  name="requiresCooler"
                />
              </label>
            </div>

            <div className="form-group">
              <label htmlFor="capacity">Capacity</label>
              <Select
                id="capacity"
                required
                options={this.state.uoms}
                onChange={this.onChangeCapacity}
                name="capacity"
              />
            </div>

            <div className="form-group">
              <label htmlFor="createdBy">Created By</label>
              <Select
                id="createdBy"
                required
                options={this.state.customers}
                onChange={this.onChangeCreatedBy}
                name="createdBy"
              />
            </div>

            <button onClick={this.saveFood} className="btn btn-success">
              Create
            </button>
          </div>
        )}
      </div>
    );
  }
}