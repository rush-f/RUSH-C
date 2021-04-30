import React from "react";
import {BrowserRouter as Router,Switch,Route} from "react-router-dom";
import DefaultMapPage from './components/home/DefaultMapPage';
import LoginPage from './components/login/LoginPage';
import WritingPage from './components/writing/WritingPage';
import ArticleDetailPage from "./components/articleDetail/ArticleDetailPage";

function App() {
  return (<Router>
    <Switch>
      <Route path="/" exact component={DefaultMapPage} />
      <Route path="/login" exact component={LoginPage} />
      <Route path="/writing" exact component={WritingPage} />
      <Route path="/articles/:articleId" exact component={ArticleDetailPage} />
    </Switch>
  </Router>);
}

export default App;
