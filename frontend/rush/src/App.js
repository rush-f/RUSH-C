import React from "react";
import DefaultMapPage from "./components/page/DefaultMapPage";
import {BrowserRouter as Router,Switch,Route} from "react-router-dom";

function App() {
  return (<Router>
    <Switch>
      <Route path="/" component={DefaultMapPage} />
    </Switch>
  </Router>);
}

export default App;
