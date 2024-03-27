import React, { Component } from "react";
import { Link } from "react-router-dom";

class TopBarNav extends Component {
  render() {
    return (
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <a href="/" className="navbar-brand">
            FOOD MANAGER
          </a>
          <div className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/foods"} className="nav-link">
                Foods
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/add"} className="nav-link">
                Add
              </Link>
            </li>
          </div>
        </nav>
      </div>
    );
  }
}

export { TopBarNav };