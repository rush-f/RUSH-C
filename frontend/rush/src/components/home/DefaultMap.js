import {
  GoogleMap,
  InfoWindow,
  Marker,
  withGoogleMap,
  withScriptjs
} from "react-google-maps";
import {withRouter} from "react-router-dom";
import MarkerClusterer
  from "react-google-maps/lib/components/addons/MarkerClusterer";
import React, {useRef, useState} from "react";
import postPositionSpreader from "../../util/PostPositionSpreader";

const DefaultMap = withScriptjs(withGoogleMap((props) => {

  const mapRef = useRef(null)
  const [infoWindowPostId, setInfoWindowPostId] = useState(null);

  const turnOn = (postId) => {
    if (postId === infoWindowPostId) {
      return;
    }
    setInfoWindowPostId(postId);
  };

  const markers = postPositionSpreader(props.articles).map((post, index) => <Marker
      key={index}
      position={{lat: post.latitude, lng: post.longitude}}
      animation={1}
      icon={{
        url: '/footprint.png',
      }}
      optimized={true}
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
      <div onClick={() => props.history.push('/articles/' + props.mapType + '/' + post.id)}>{post.title}</div>
    </InfoWindow>}
  </Marker>);

  return (
      <GoogleMap
          ref={mapRef}
          defaultZoom={16}
          center={props.defaultCenter}
          defaultOptions={{
            disableDefaultUI:true,
            maxZoom:21,
            minZoom:3,
            restriction: {
              latLngBounds: {
                north: 85,
                south: -85,
                west: -180,
                east: 180,
              },
            },
          }}
          onClick={() => {
            props.setIsMenuOpen(false);
            setInfoWindowPostId(null);
          }}
          onZoomChanged={()=>{
            props.setZoom(mapRef.current.getZoom());
            props.setCenter(mapRef.current.getCenter());
          }}
          onCenterChanged={() =>{
            let latChangeRange= Math.abs(props.center.lat()-mapRef.current.getCenter().lat())>(5*props.latitudeRange/12);
            let lngChangeRange= Math.abs(props.center.lng()-mapRef.current.getCenter().lng())>(5*props.longitudeRange/12);
            let dummy= (latChangeRange || lngChangeRange)?props.setCenter(mapRef.current.getCenter()): "";
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
