import React, {useEffect, useState} from 'react';
import {Outside, DisplayBox, StyledDiv, Info, Name,Email} from "./Box";
import WindowSize from "../../../../WindowSize";
import CancelButton from "./CancelButton";
import findUserApi from "./findUserApi";
import {ACCESS_TOKEN} from "../../../../../constants/SessionStorage";
import Profile from "./Profile";
import CreateDate from "../../../../../util/CreateDate";

const MyPage = (props) => {

  const [user, setUser] = useState(null);
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  useEffect(() => {
    if (!accessToken) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      props.history.push("/login");
    }
    findUserApi(accessToken).then(userPromise => {
    setUser(userPromise)
    })
  }, [accessToken]);

  return (
      <Outside>
        <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
          <CancelButton/>
          <StyledDiv>
            {<Profile userImageUrl={user ? user.imageUrl : ""}/>}
            <Info>
              <Name>
                {user.nickName}
              </Name>
              <Email>
                {user.email}
              </Email>
              <CreateDate iso8601format={user.visitDate}/>
            </Info>
          </StyledDiv>

        </DisplayBox>
      </Outside>
  );
};

export default MyPage;