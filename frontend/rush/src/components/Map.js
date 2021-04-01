import {
  withScriptjs,
  withGoogleMap,
  GoogleMap,
  Marker
} from "react-google-maps";
import MarkerClusterer
  from "react-google-maps/lib/components/addons/MarkerClusterer";

const Map = withScriptjs(withGoogleMap((props) => {
  const positions = [
    {lat: 37.554722, lng: 126.970833},
    {lat: 37.55, lng: 126.970833},
    {lat: 37.54, lng: 126.970833},
    {lat: 37.53, lng: 126.970833},
    {lat: 37.52, lng: 126.970833},
    {lat: 37.51, lng: 126.970833},
    {lat: 37.50, lng: 126.970833},
    {lat: 37.49, lng: 126.970833},
    {lat: 37.48, lng: 126.970833},
    {lat: 37.47, lng: 126.970833},
    {lat: 37.46, lng: 126.970833},
    {lat: 37.45, lng: 126.970833},
    {lat: 37.554722, lng: 128.970833},
    {lat: 37.60, lng: 128.970833},
    {lat: 37.54, lng: 128.970833},
    {lat: 37.53, lng: 128.970833},
    {lat: 37.52, lng: 128.970833},
    {lat: 37.51, lng: 128.970833},
    {lat: 37.50, lng: 128.970833},
    {lat: 37.49, lng: 128.970833},
    {lat: 37.48, lng: 128.970833},
    {lat: 37.47, lng: 128.970833},
    {lat: 37.46, lng: 128.970833},
    {lat: 37.45, lng: 128.970833},
  ];

  const markers = positions.map((position, index) => <Marker
    key={index}
    position={position}
    animation={1}
    onClick={() => {
      alert(index);
    }}
  />);

  const defaultMapOptions = {
    disableDefaultUI: true
  };

  return (
      <GoogleMap
        defaultZoom={6}
        defaultCenter={{lat: 37.397, lng: 127.644}}
        defaultOptions={defaultMapOptions}
      >
        <MarkerClusterer
          onClick={props.onMarkerClustererClick}
          averageCenter
          enableRetinaIcons
          gridSize={20}
          minimumClusterSize={4}
        >
          {markers}
        </MarkerClusterer>
      </GoogleMap>
  );
}));

export default Map;
