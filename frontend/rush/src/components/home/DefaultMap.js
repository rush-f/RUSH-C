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
import postPositionSpreader from "../../util/PostPositionSpreader";

const DefaultMap = withScriptjs(withGoogleMap((props) => {

  const [infoWindowPostId, setInfoWindowPostId] = useState(null);
  const [map, setMap] = useState(null);

  const turnOn = (postId) => {
    if (postId === infoWindowPostId) {
      return;
    }
    setInfoWindowPostId(postId);
  };

  const markers = postPositionSpreader(props.publicMapArticles).map((post, index) => <Marker
      key={index}
      position={{lat: post.latitude, lng: post.longitude}}
      animation={1}
      icon={{
        url: '/footprint.png',
      }}
      optimized={true}
      onClick={() => {
        console.log(post.latitude + " " + post.longitude );
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
          ref={(map) => setMap(map)}
          defaultZoom={16}
          defaultCenter={{lat: 37.63185105917152, lng: 127.07745984005722}}
          defaultOptions={defaultMapOptions}
          onClick={() => {
            setInfoWindowPostId(null);
          }}
      >
        <MarkerClusterer
            averageCenter
            enableRetinaIcons
            gridSize={60}
            minimumClusterSize={4}
            maxZoom={15}
        >
          {markers}
        </MarkerClusterer>
      </GoogleMap>
  );
}));

export default withRouter(DefaultMap);
