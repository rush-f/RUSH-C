import React, {useEffect, useState} from 'react';
import {DisplayBox, Outside} from "../common/Box";
import WindowSize from "../../util/WindowSize";
import CancelButton from "./CancelButton";
import findUserApi from "../../api/user/findUserApi";
import findMyArticlesApi from "../../api/article/findMyArticlesApi";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import Profile from "./Profile";
import Info from "./Info";
import MyArticles from "./articles/MyArticles";
import MyGroups from "./groups/MyGroups";
import findMyGroupsApi from "../../api/group/FindMyGroupsApi";
import styled from "styled-components";
import {withRouter} from "react-router-dom";
import WithdrawButton from "./WithdrawButton";

const StyledDiv = styled.div`
  display: flex;
  justify: center;
  margin-top: 40px;
`;

const MyPage = (props) => {

  const [isGroupsOpened, setIsGroupsOpened] = useState(false);
  const [isArticlesOpened, setIsArticlesOpened] = useState(false);
  const [user, setUser] = useState(null);
  const [myArticles, setMyArticles] = useState(null);
  const [myGroups, setMyGroups] = useState(null);
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);

  useEffect(() => {
    if (!accessToken) {
      alert("로그인이 필요한 서비스입니다.");
      props.history.push("/login");
      return;
    }
    findMyGroupsApi(props.history).then(groupsPromise=>{
      setMyGroups(groupsPromise)
    })
    findUserApi(accessToken).then(userPromise => {
    setUser(userPromise)
    });
    findMyArticlesApi(accessToken).then(articlesPromise => {
      setMyArticles(articlesPromise)
    });
  }, [accessToken]);

  return (
      <Outside>
        <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
          <CancelButton/>
          <StyledDiv>
            {<Profile userImageUrl={user ? user.imageUrl : ""}/>}
            {<Info
                user={user ? user : {visitDate:""}}
                history={props.history}
            />}
          </StyledDiv>
          <MyArticles
              myArticles={myArticles}
              isOpened={isArticlesOpened}
              setIsOpened={setIsArticlesOpened}
          />
          <MyGroups
              myGroups={myGroups}
              isOpened={isGroupsOpened}
              setIsOpened={setIsGroupsOpened}
          />
          <WithdrawButton
              nickName={user ? user.nickName : ""}
              accessToken={accessToken}
              history={props.history}
          />
        </DisplayBox>
      </Outside>
  );
};

export default withRouter(MyPage);