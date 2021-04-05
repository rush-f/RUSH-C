import {
  withScriptjs,
  withGoogleMap,
  GoogleMap,
  Marker,
  InfoWindow
} from "react-google-maps";
import MarkerClusterer
  from "react-google-maps/lib/components/addons/MarkerClusterer";
import React, {useState} from "react";

const Map = withScriptjs(withGoogleMap((props) => {

  const [infoWindowPostId, setInfoWindowPostId] = useState(null);

  const turnOn = (postId) => {
    if (postId === infoWindowPostId) {
      return;
    }
    setInfoWindowPostId(postId);
  };

  const posts = [
    {lat: 37.554722, lng: 126.970833, id: 1},
    {lat: 37.55, lng: 126.970833, id: 2},
    {lat: 37.54, lng: 126.970833, id: 3},
    {lat: 37.53, lng: 126.970833, id: 4},
    {lat: 37.52, lng: 126.970833, id: 5},
    {lat: 37.51, lng: 126.970833, id: 6},
    {lat: 37.50, lng: 126.970833, id: 7},
    {lat: 37.49, lng: 126.970833, id: 8},
    {lat: 37.48, lng: 126.970833, id: 9},
    {lat: 37.47, lng: 126.970833, id: 10},
    {lat: 37.46, lng: 126.970833, id: 11},
    {lat: 37.45, lng: 126.970833, id: 12},
    {lat: 37.554722, lng: 128.970833, id: 13},
    {lat: 37.60, lng: 128.970833, id: 14},
    {lat: 37.54, lng: 128.970833, id: 15},
    {lat: 37.53, lng: 128.970833, id: 16},
    {lat: 37.52, lng: 128.970833, id: 17},
    {lat: 37.51, lng: 128.970833, id: 18},
    {lat: 37.50, lng: 128.970833, id: 19},
    {lat: 37.49, lng: 128.970833, id: 20},
    {lat: 37.48, lng: 128.970833, id: 21},
    {lat: 37.47, lng: 128.970833, id: 22},
    {lat: 37.46, lng: 128.970833, id: 23},
    {lat: 37.45, lng: 128.970833, id: 24},
  ];

  const markers = posts.map((post, index) => <Marker
      key={index}
      position={{lat: post.lat, lng: post.lng}}
      animation={1}
      icon={{
        url: '/footprint.png',
      }}
      onClick={() => {
        turnOn(post.id)
      }}
  >
    {(infoWindowPostId === post.id) && <InfoWindow
        defaultOptions={false}
        onCloseClick={() => {
          setInfoWindowPostId(null);
        }}
    >
      <div> 하이하이</div>
    </InfoWindow>}
  </Marker>);

  const defaultMapOptions = {
    disableDefaultUI: true
  };

  return (
      <GoogleMap
          defaultZoom={6}
          defaultCenter={{lat: 37.397, lng: 127.644}}
          defaultOptions={defaultMapOptions}
          onClick={() => setInfoWindowPostId(null)}
      >
        <MarkerClusterer
            averageCenter
            enableRetinaIcons
            gridSize={60}
            minimumClusterSize={4}
        >
          {markers}
        </MarkerClusterer>
      </GoogleMap>
  );
}));

export default Map;
