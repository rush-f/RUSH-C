import React, {useEffect, useState} from 'react';
import DefaultMap from "./DefaultMap";
import {CLIENT_ID} from "../../constants/GoogleMapAuth";
import Menu from "./button/MenuButton";
import LoginButton from "./button/LoginButton";
import WriteButton from "./button/WriteButton";
import WindowSize from "../WindowSize";
import findUserImageUrlApi from "./FindUserImageUrlApi";
import {ACCESS_TOKEN} from "../../constants/LocalStorage";
import Profile from "./Profile";

const DefaultMapPage = () => {

  const accessToken = localStorage.getItem(ACCESS_TOKEN);

  const [userImageUrl, setUserImageUrl] = useState(null);

  useEffect(() => {
    if (!accessToken) {
      return;
    }
    findUserImageUrlApi(accessToken).then(userImageUrlPromise => {
      setUserImageUrl(userImageUrlPromise)
    })
  }, [accessToken]);
  return (<>
      <DefaultMap googleMapURL={CLIENT_ID}
                  loadingElement={<div style={{width: `100%`}}/>}
                  containerElement={<div style={{height: WindowSize().height}}/>}
                  mapElement={<div style={{height: `100%`}}/>}
      />
      <Menu />
    {
      (accessToken === null || userImageUrl === null) ?
        <LoginButton/>
          : <Profile/>
    }
      <WriteButton accessToken={accessToken}/>
  </>);
};

export default DefaultMapPage;