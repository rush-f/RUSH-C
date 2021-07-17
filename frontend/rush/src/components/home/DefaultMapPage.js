import React, {useEffect, useState} from 'react';
import DefaultMap from "./DefaultMap";
import {CLIENT_ID} from "../../constants/GoogleMapAuth";
import Menu from "./button/MenuButton";
import LoginButton from "./button/LoginButton";
import WriteButton from "./button/WriteButton";
import WindowSize from "../WindowSize";
import findUserImageUrlApi from "../../api/FindUserImageUrlApi";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import Profile from "./Profile";
import {
  findGroupedMapArticles,
  findPrivateMapArticles,
  findPublicMapArticles
} from "../../api/FindMapArticlesApi";
import {GROUPED, PRIVATE, PUBLIC} from "../../constants/MapType";
import {withRouter} from "react-router-dom";
import CreateGroupModal from "./group/create/CreateGroupModal";
import JoinGroupModal from "./group/join/JoinGroupModal";
import MapType from "./MapTypeStyle";
import findGroupApi from "../../api/FindGroupApi";

const DefaultMapPage = (props) => {
  const LatRangeRatio = 0.561906;
  const LngRangeRatio = 0.70378;

  const [windowSize, setWindowSize] = useState(WindowSize());
  // 사용자 관련
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const [userImageUrl, setUserImageUrl] = useState(null);
  // 지도 관련
  const [mapType, setMapType] = useState(PUBLIC);
  const [groupId, setGroupId] = useState(0);
  const [groupName, setGroupName] = useState("");
  const [zoom, setZoom] = useState(16);
  const [center, setCenter] = useState({
    lat: () => 37.63185105917152,
    lng: () => 127.07745984005722,
  });
  const [articles, setArticles] = useState([]);
  const [latitudeRange, setLatitudeRange] = useState(0.0095);
  const [longitudeRange, setLongitudeRange] = useState(0.025);
  // 메뉴 관련
  const [isCreateGroupModalOpen, setIsCreateGroupModalOpen] = useState(false);
  const [isJoinGroupModalOpen, setIsJoinGroupModalOpen] = useState(false);
  const [isGroupOpened, setIsGroupOpened] = useState(false);

  useEffect(() => {
    setLatitudeRange(
      LatRangeRatio * windowSize.height * Math.pow(0.5, zoom - 1));
    setLongitudeRange(
      LngRangeRatio * windowSize.width * Math.pow(0.5, zoom - 1));
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
  }, [zoom, center, mapType, groupId]);

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
    <Menu
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
    <WriteButton accessToken={accessToken}/>
  </>);
};

export default withRouter(DefaultMapPage);