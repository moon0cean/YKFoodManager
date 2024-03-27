import React, { Component } from "react";
import { Routes, Route } from "react-router-dom";

import AddFood from "./add-food.component";
import Food from "./food.component";
import FoodList from "./food-list.component";

import Dashboard from "./dashboard.js"

class MainBody extends Component {
  render() {
    return (
      <div>
        <div className="container mt-3">
          <Routes>
            <Route path="/" element={<Dashboard/>} />
            <Route path="/foods" element={<FoodList/>} />
            <Route path="/add" element={<AddFood/>} />
            <Route path="/foods/:id" element={<Food/>} />
          </Routes>
        </div>
      </div>
    );
  }
}

export { MainBody };