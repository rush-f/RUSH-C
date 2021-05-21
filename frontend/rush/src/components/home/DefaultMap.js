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
import {withRouter} from "react-router-dom";

const DefaultMap = withScriptjs(withGoogleMap((props) => {

  const [infoWindowPostId, setInfoWindowPostId] = useState(null);

  const turnOn = (postId) => {
    if (postId === infoWindowPostId) {
      return;
    }
    setInfoWindowPostId(postId);
  };

  const markers = props.publicMapArticles.map((post, index) => <Marker
      key={index}
      position={{lat: post.latitude, lng: post.longitude}}
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
      <div onClick={() => props.history.push('/articles/' + post.id)}>{post.title}</div>
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

export default withRouter(DefaultMap);
