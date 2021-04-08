import React from 'react';
import DefaultMap from "../DefaultMap";
import {CLIENT_ID} from "../../constants/GoogleMapAuth";
import Menu from "../button/MenuButton";
import LoginButton from "../button/LoginButton";
import WriteButton from "../button/WriteButton";

const DefaultMapPage = () => {
  return (<>
      <DefaultMap googleMapURL={CLIENT_ID}
                  loadingElement={<div style={{height: `100%`}}/>}
                  containerElement={<div style={{height: `800px`}}/>}
                  mapElement={<div style={{height: `100%`}}/>}
      />
      <Menu />
      <LoginButton />
      <WriteButton />
  </>);
};

export default DefaultMapPage;