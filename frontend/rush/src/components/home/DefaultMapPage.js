import React, {useEffect, useState} from 'react';
import DefaultMap from "./DefaultMap";
import {CLIENT_ID} from "../../constants/GoogleMapAuth";
import Menu from "./menu/Menu";
import LoginButton from "./button/LoginButton";
import WriteButton from "./button/WriteButton";
import WindowSize from "../../util/WindowSize";
import findUserImageUrlApi from "../../api/user/FindUserImageUrlApi";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import Profile from "./Profile";
import {
  findGroupedMapArticles,
  findPrivateMapArticles,
  findPublicMapArticles
} from "../../api/article/FindMapArticlesApi";
import {GROUPED, PRIVATE, PUBLIC} from "../../constants/MapType";
import {withRouter} from "react-router-dom";
import CreateGroupModal from "./menu/group/create/CreateGroupModal";
import JoinGroupModal from "./menu/group/join/JoinGroupModal";
import MapType from "./MapTypeStyle";
import findGroupApi from "../../api/group/FindGroupApi";
import MyLocationButton from "./button/MyLocationButton";

const DefaultMapPage = (props) => {
  const LatRangeRatio = 0.561906;
  const LngRangeRatio = 0.70378;
  const [isMenuOpen,setIsMenuOpen] = useState(false);
  const [windowSize, setWindowSize] = useState(WindowSize());
  // 사용자 관련
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const [userImageUrl, setUserImageUrl] = useState(null);
  // 지도 관련
  const [mapType, setMapType] = useState(props.location.state? props.location.state.mapType : PUBLIC);
  const [groupId, setGroupId] = useState(props.location.state? props.location.state.groupId : 0);
  const [groupName, setGroupName] = useState("");
  const [zoom, setZoom] = useState(16);
  const [center, setCenter] = useState({
    lat: () => 37.63185105917152,
    lng: () => 127.07745984005722,
  });
  const [defaultCenter, setDefaultCenter] = useState({
    lat: 37.63185105917152,
    lng: 127.07745984005722,
  });
  const [myLocation, setMyLocation] = useState(null);
  const [articles, setArticles] = useState([]);
  const [latitudeRange, setLatitudeRange] = useState(0.0095);
  const [longitudeRange, setLongitudeRange] = useState(0.025);
  // 메뉴 관련
  const [isCreateGroupModalOpen, setIsCreateGroupModalOpen] = useState(false);
  const [isJoinGroupModalOpen, setIsJoinGroupModalOpen] = useState(false);
  const [isGroupOpened, setIsGroupOpened] = useState(false);

  useEffect(() => {
    navigator.geolocation.getCurrentPosition(
      function success(position) {
        setDefaultCenter({lat: position.coords.latitude, lng: position.coords.longitude});
        setCenter({
          lat: () => props.location.state ? props.location.state.lat : position.coords.latitude,
          lng: () => props.location.state ? props.location.state.lng : position.coords.longitude
        });
      },
      function failure() {
        setCenter({
          lat: () => props.location.state ? props.location.state.lat : defaultCenter.lat,
          lng: () => props.location.state ? props.location.state.lng : defaultCenter.lng
        });
      }
    );
  },[]);

  useEffect(() => {
    setLatitudeRange(
      LatRangeRatio * windowSize.height * Math.pow(0.5, zoom - 2));
    setLongitudeRange(
      LngRangeRatio * windowSize.width * Math.pow(0.5, zoom - 2));
  }, [zoom]);

  useEffect(() => {
    if (mapType === PUBLIC) {
      findPublicMapArticles(center.lat(), latitudeRange,
          center.lng(), longitudeRange)
        .then(mapArticlesPromise => {
        setArticles(mapArticlesPromise)
      })
    }
    else if (mapType === PRIVATE) {
      findPrivateMapArticles(center.lat(), latitudeRange,
        center.lng(), longitudeRange, props.history)
      .then(mapArticlesPromise => {
        setArticles(mapArticlesPromise)
      })
    }
    else if (mapType === GROUPED && groupId > 0) {
        findGroupedMapArticles(groupId, center.lat(), latitudeRange,
          center.lng(), longitudeRange, props.history)
        .then(mapArticlesPromise => {
          setArticles(mapArticlesPromise)
        });
        findGroupApi({
          groupId: groupId,
          accessToken: accessToken,
          history: props.history
        }).then(groupPromise => setGroupName(groupPromise.name))
    }
  }, [zoom, center, mapType, groupId, myLocation]);

  useEffect(() => {
    if (!accessToken) {
      return;
    }
    findUserImageUrlApi(accessToken).then(userImageUrlPromise => {
      setUserImageUrl(userImageUrlPromise)
    })
  }, [accessToken]);

  console.log(center.lat() + " " + center.lng())
  return (<>
    <DefaultMap googleMapURL={CLIENT_ID}
                loadingElement={<div style={{width: `100%`}}/>}
                containerElement={<div style={{height: WindowSize().height}}/>}
                mapElement={<div style={{height: `100%`}}/>}
                mapType={mapType}
                articles={articles}
                defaultCenter={props.location.state ? props.location.state : defaultCenter}
                myLocation={myLocation}
                setZoom={setZoom}
                center={center}
                setCenter={setCenter}
                latitudeRange={latitudeRange}
                longitudeRange={longitudeRange}
                setIsMenuOpen={setIsMenuOpen}
                groupId={groupId}
    />
    <Menu
      isMenuOpen={isMenuOpen}
      setIsMenuOpen={setIsMenuOpen}
      isGroupOpened={isGroupOpened}
      setIsGroupOpened={setIsGroupOpened}
      setMapType={setMapType}
      setGroupId={setGroupId}
      setIsCreateGroupModalOpen={setIsCreateGroupModalOpen}
      setIsJoinGroupModalOpen={setIsJoinGroupModalOpen}
      history={props.history}
      accessToken={accessToken}
    />
    <CreateGroupModal
      isCreateGroupModalOpen={isCreateGroupModalOpen}
      setIsCreateGroupModalOpen={setIsCreateGroupModalOpen}
      setIsGroupOpened={setIsGroupOpened}
    />
    <JoinGroupModal
      isJoinGroupModalOpen={isJoinGroupModalOpen}
      setIsJoinGroupModalOpen={setIsJoinGroupModalOpen}
      setIsGroupOpened={setIsGroupOpened}
    />
    {
      (accessToken === null || userImageUrl === null) ?
        <MapType
          mapType={mapType}
          groupId={groupId}
          groupName={groupName}
          history={props.history}
          rightMargin="100px"
        /> :
        <MapType
          mapType={mapType}
          groupId={groupId}
          groupName={groupName}
          history={props.history}
          rightMargin="60px"
        />
    }
    {
      (accessToken === null || userImageUrl === null) ?
        <LoginButton/> :
        <Profile
          userImageUrl={userImageUrl ? userImageUrl.imageUrl : ""}
          history={props.history}
        />
    }
    <MyLocationButton
      setCenter={setCenter}
      defaultCenter={defaultCenter}
      setDefaultCenter={setDefaultCenter}
      setMyLocation={setMyLocation}
    />
    <WriteButton
      accessToken={accessToken}
      center={center}
      defaultCenter={defaultCenter}
    />
  </>);
};

export default withRouter(DefaultMapPage);