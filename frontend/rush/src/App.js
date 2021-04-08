import React from "react";
import {BrowserRouter as Router,Switch,Route} from "react-router-dom";
import DefaultMapPage from './components/home/DefaultMapPage';
import LoginPage from './components/login/LoginPage';
import WritingPage from './components/writing/WritingPage';

function App() {
  return (<Router>
    <Switch>
      <Route path="/" exact component={DefaultMapPage} />
      <Route path="/login" exact component={LoginPage} />
      <Route path="/writing/step1" exact component={WritingPage} />
    </Switch>
  </Router>);
}

export default App;
