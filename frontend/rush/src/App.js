import React from "react";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import DefaultMapPage from './components/home/DefaultMapPage';
import LoginPage from './components/login/LoginPage';
import WritingPage from './components/writing/WritingPage';
import ArticleDetailPage from "./components/articleDetail/ArticleDetailPage";
import OAuth2RedirectHandler from "./components/login/OAuth2RedirectHandler";
import GroupPage from "./components/group/GroupPage";
import MyPage from "./components/myPage/MyPage";
import EmailLoginPage from "./components/login/EmailLoginPage";
import SignUpPage from "./components/login/SignUpPage";

function App() {
  return (<Router>
    <Switch>
      <Route path="/" exact component={DefaultMapPage} />
      <Route path="/login" exact component={LoginPage} />
      <Route path="/login/email" exact component={EmailLoginPage} />
      <Route path="/login/signUp" exact component={SignUpPage} />
      <Route path="/writing" exact component={WritingPage} />
      <Route path="/articles/:mapType/:articleId" exact component={ArticleDetailPage} />
      <Route path="/oauth2/redirect" exact component={OAuth2RedirectHandler}/>
      <Route path="/groups/:groupId" exact component={GroupPage}/>
      <Route path="/mypage" exact component={MyPage}/>
    </Switch>
  </Router>);
}

export default App;
