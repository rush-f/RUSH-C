import React, {useEffect, useState} from 'react';
import DefaultMap from "./DefaultMap";
import {CLIENT_ID} from "../../constants/GoogleMapAuth";
import Menu from "./button/MenuButton";
import LoginButton from "./button/LoginButton";
import WriteButton from "./button/WriteButton";
import WindowSize from "../WindowSize";
import findUserImageUrlApi from "./FindUserImageUrlApi";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import Profile from "./Profile";
import findMapArticles from "./FindPublicMapArticlesApi";
import {PUBLIC} from "../../constants/MapType";

const DefaultMapPage = (props) => {
  const LatRangeRatio = 0.561906;
  const LngRangeRatio = 0.70378;

  const [windowSize, setWindowSize] = useState(WindowSize());

  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const [mapType, setMapType] = useState(PUBLIC);
  const [zoom, setZoom] = useState(16);
  const [center, setCenter] = useState({
    lat: () => 37.63185105917152,
    lng: () => 127.07745984005722,
  });
  const [userImageUrl, setUserImageUrl] = useState(null);
  const [articles, setArticles] = useState([]);
  const [latitudeRange, setLatitudeRange] = useState(0.0095);
  const [longitudeRange, setLongitudeRange] = useState(0.025);

  useEffect(() => {
    setLatitudeRange(
      LatRangeRatio * windowSize.height * Math.pow(0.5, zoom - 1));
    setLongitudeRange(
      LngRangeRatio * windowSize.width * Math.pow(0.5, zoom - 1));
  }, [zoom]);

  useEffect(() => {
    findMapArticles(center.lat(), latitudeRange,
        center.lng(), longitudeRange, mapType)
      .then(mapArticlesPromise => {
      setArticles(mapArticlesPromise)
    })
  }, [zoom, center, mapType]);

  useEffect(() => {
    if (!accessToken) {
      return;
    }
    findUserImageUrlApi(accessToken).then(userImageUrlPromise => {
      setUserImageUrl(userImageUrlPromise)
    })
  }, [accessToken]);

  return (<>
    <DefaultMap googleMapURL={CLIENT_ID}
                loadingElement={<div style={{width: `100%`}}/>}
                containerElement={<div style={{height: WindowSize().height}}/>}
                mapElement={<div style={{height: `100%`}}/>}
                mapType={mapType}
                articles={articles}
                markerCenter={props.location.state ? props.location.state
                  : {lat: 37.63185105917152, lng: 127.07745984005722}}
                setZoom={setZoom}
                center={center}
                setCenter={setCenter}
                latitudeRange={latitudeRange}
                longitudeRange={longitudeRange}
    />
    <Menu/>
    {
      (accessToken === null || userImageUrl === null) ?
        <LoginButton/>
        : <Profile userImageUrl={userImageUrl ? userImageUrl.imageUrl : ""}/>
    }
    <WriteButton accessToken={accessToken}/>
  </>);
};

export default DefaultMapPage;