import Map from "./components/Map";
import {CLIENT_ID} from "./constants/GoogleMapAuth";

function App() {
  return (<>
    <Map googleMapURL={CLIENT_ID}
      loadingElement={<div style={{height: `100%`}}/>}
      containerElement={<div style={{height: `700px`}}/>}
      mapElement={<div style={{height: `100%`}}/>}
    />
  </>);
}

export default App;