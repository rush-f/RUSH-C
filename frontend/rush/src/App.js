import React from "react";
import {BrowserRouter as Router,Switch,Route} from "react-router-dom";
import DefaultMapPage from './components/home/DefaultMapPage';
import LoginPage from './components/login/LoginPage';
import WritingStep1Page from './components/writing/step1/WritingStep1Page';

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
