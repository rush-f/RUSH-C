import React from "react";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import DefaultMapPage from './components/home/DefaultMapPage';
import LoginPage from './components/login/LoginPage';
import WritingPage from './components/writing/WritingPage';
import ArticleDetailPage from "./components/articleDetail/ArticleDetailPage";
import OAuth2RedirectHandler from "./components/login/OAuth2RedirectHandler";

function App() {
  return (<Router>
    <Switch>
      <Route path="/" exact component={DefaultMapPage} />
      <Route path="/login" exact component={LoginPage} />
      <Route path="/writing" exact component={WritingPage} />
      <Route path="/articles/public/:articleId" exact component={ArticleDetailPage} />
      <Route path="/articles/private/:articleId" exact component={ArticleDetailPage} />
      <Route path="/articles/grouped/:articleId" exact component={ArticleDetailPage} />
      <Route path="/oauth2/redirect" exact component={OAuth2RedirectHandler}/>
    </Switch>
  </Router>);
}

export default App;
