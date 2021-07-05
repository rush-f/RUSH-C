import React, {useEffect, useState} from 'react';
import {Outside, DisplayBox, StyledDiv, Name,Email} from "./Box";
import WindowSize from "../../../../WindowSize";
import CancelButton from "./CancelButton";
import findUserApi from "./findUserApi";
import findMyArticlesApi from "./findMyArticlesApi";
import {ACCESS_TOKEN} from "../../../../../constants/SessionStorage";
import Profile from "./Profile";
import Info from "./Info";
import GroupsMap
  from "../../../../writing/step3/selectionList/groupsMap/GroupsMap";

const MyPage = (props) => {

  const [isGroupOpened, setIsGroupOpened] = useState(false);
  const [user, setUser] = useState(null);
  const [myArticles, setMyArticles] = useState(null);
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);

  useEffect(() => {
    if (!accessToken) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      props.history.push("/login");
    }
    findUserApi(accessToken).then(userPromise => {
    setUser(userPromise)
    });
    findMyArticlesApi(accessToken).then(userPromise => {
      setMyArticles(userPromise)
    });
  }, [accessToken]);

  return (
      <Outside>
        <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
          <CancelButton/>
          <StyledDiv>
            {<Profile userImageUrl={user ? user.imageUrl : ""}/>}
            {<Info user={user ? user : ""}/>}
          </StyledDiv>

        </DisplayBox>
      </Outside>
  );
};

export default MyPage;