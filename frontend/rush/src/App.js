import Map from "./components/Map";
import {CLIENT_ID} from "./constants/GoogleMapAuth";
import Menu from "./components/button/MenuButton";
import WriteButton from "./components/button/WriteButton";
import React from "react";
import LoginButton from "./components/button/LoginButton";

function App() {
  return (<>
    <Map googleMapURL={CLIENT_ID}
      loadingElement={<div style={{height: `100%`}}/>}
      containerElement={<div style={{height: `700px`}}/>}
      mapElement={<div style={{height: `100%`}}/>}
    />
    <Menu />
    <LoginButton />
    <WriteButton />
  </>);
}

export default App;
