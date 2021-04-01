import Map from "./components/Map";
import {CLIENT_ID} from "./constants/GoogleMapAuth";
import Menu from "./components/Menu";
import React from "react";

function App() {
  return (<>
    <Map googleMapURL={CLIENT_ID}
      loadingElement={<div style={{height: `100%`}}/>}
      containerElement={<div style={{height: `700px`}}/>}
      mapElement={<div style={{height: `100%`}}/>}
    />
    <Menu />
  </>);
}

export default App;