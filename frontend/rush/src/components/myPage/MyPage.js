import React, {useEffect, useState} from 'react';
import {DisplayBox, Outside, StyledDiv} from "./Box";
import WindowSize from "../WindowSize";
import CancelButton from "./CancelButton";
import findUserApi from "../../api/findUserApi";
import findMyArticlesApi from "../../api/findMyArticlesApi";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import Profile from "./Profile";
import Info from "./Info";
import MyArticles from "./articles/MyArticles";

const MyPage = (props) => {

  const [isOpened, setIsOpened] = useState(false);
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
          <MyArticles
              myArticles={myArticles}
              isOpened={isOpened}
              setIsOpened={setIsOpened}
          />
        </DisplayBox>
      </Outside>
  );
};

export default MyPage;