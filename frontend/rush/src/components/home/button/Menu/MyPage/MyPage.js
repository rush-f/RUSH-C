import React, {useEffect, useState} from 'react';
import {Outside,DisplayBox} from "./Box";
import WindowSize from "../../../../WindowSize";
import CancelButton from "./CancelButton";
import findUserApi from "./findUserApi";
import {ACCESS_TOKEN} from "../../../../../constants/SessionStorage";


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
          <CancelButton></CancelButton>

        </DisplayBox>
      </Outside>
  );
};

export default MyPage;