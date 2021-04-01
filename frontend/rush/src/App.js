import MapWithAMarker from "./components/Map";
import {CLIENT_ID} from "./constants/GoogleMapAuth";

function App() {
  return (<>
    <MapWithAMarker googleMapURL={CLIENT_ID}
      loadingElement={<div style={{height: `100%`}}/>}
      containerElement={<div style={{height: `400px`}}/>}
      mapElement={<div style={{height: `100%`}}/>}
    />
  </>);
}

export default App;