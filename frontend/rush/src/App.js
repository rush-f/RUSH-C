import React from "react";
import DefaultMapPage from "./components/page/DefaultMapPage";
import {BrowserRouter as Router,Switch,Route} from "react-router-dom";
import LoginPage from "./components/page/LoginPage";
import WritingStep1Page from "./components/page/writingPage/WritingStep1Page";

function App() {
  return (<Router>
    <Switch>
      <Route path="/" exact component={DefaultMapPage} />
      <Route path="/login" exact component={LoginPage} />
      <Route path="/writing/step1" exact component={WritingStep1Page} />
    </Switch>
  </Router>);
}

export default App;
