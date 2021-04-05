import React from "react";
import DefaultMapPage from "./components/page/DefaultMapPage";
import {BrowserRouter as Router,Switch,Route} from "react-router-dom";
import LoginPage from "./components/page/LoginPage";

function App() {
  return (<Router>
    <Switch>
      <Route path="/" exact component={DefaultMapPage} />
      <Route path="/login" exact component={LoginPage} />
    </Switch>
  </Router>);
}

export default App;
