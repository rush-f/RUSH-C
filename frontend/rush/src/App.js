import React from "react";
import {BrowserRouter as Router,Switch,Route} from "react-router-dom";
import DefaultMapPage from './components/home/DefaultMapPage';
import LoginPage from './components/login/LoginPage';
import WritingPage from './components/writing/WritingPage';
import PostDetailPage from "./components/postDetail/PostDetailPage";
import OAuth2RedirectHandler from "./components/login/OAuth2RedirectHandler";

function App() {
  return (<Router>
    <Switch>
      <Route path="/" exact component={DefaultMapPage} />
      <Route path="/login" exact component={LoginPage} />
      <Route path="/writing" exact component={WritingPage} />
      <Route path="/posts/:postId" exact component={PostDetailPage} />
      <Route path="/oauth2/redirect" exact component={OAuth2RedirectHandler}/>
    </Switch>
  </Router>);
}

export default App;
