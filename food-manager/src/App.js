import React, { Component } from "react";
import { TopBarNav } from "./components/topBarNav.js"
import { MainBody } from "./components/mainBody.js"

import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import { ErrorBoundary } from "react-error-boundary";

class App extends Component {
  render() {
    return (
      <div>
        <TopBarNav />
        <ErrorBoundary fallback={<div>Something went wrong loading food dashboard. Please contact support.</div>}>
          <MainBody />
        </ErrorBoundary>
      </div>
    );
  }
}


export default App;
