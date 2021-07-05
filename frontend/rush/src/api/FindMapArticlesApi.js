import axios from "axios";
import {BACKEND_ADDRESS} from "../constants/ADDRESS";
import {GROUPED, PRIVATE, PUBLIC} from "../constants/MapType";
import {ACCESS_TOKEN} from "../constants/SessionStorage";

export const findPublicMapArticles = (latitude, latitudeRange, longitude, longitudeRange) => {
  return axios.get(BACKEND_ADDRESS + "/articles/" + PUBLIC
    + "?latitude=" + latitude
    + "&latitudeRange=" + latitudeRange
    + "&longitude=" + longitude
    + "&longitudeRange=" + longitudeRange)
  .then(response => response.data);
};

export const findPrivateMapArticles = (latitude, latitudeRange, longitude, longitudeRange, history) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);

  if (!accessToken) {
    alert("로그인이 필요한 서비스입니다.")
    history.push('/login');
    return Promise.reject("토큰이 없음");
  }
  const config = {
    headers: {
      "Authorization": "Bearer " + accessToken
    }
  };
  return axios.get(BACKEND_ADDRESS + "/articles/" + PRIVATE
    + "?latitude=" + latitude
    + "&latitudeRange=" + latitudeRange
    + "&longitude=" + longitude
    + "&longitudeRange=" + longitudeRange, config)
  .then(response => response.data)
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 403) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      history.push("/login");
      return;
    }
    alert("이유가 뭔지 모르겠지만 개인지도 글 가져오기에 실패했음...");
  });
};

export const findGroupedMapArticles = (groupId, latitude, latitudeRange, longitude, longitudeRange, history) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const config = {
    headers: {
      "Authorization": "Bearer " + accessToken
    }
  };
  return axios.get(BACKEND_ADDRESS + "/articles/" + GROUPED
    + "?groupId=" + groupId
    + "&latitude=" + latitude
    + "&latitudeRange=" + latitudeRange
    + "&longitude=" + longitude
    + "&longitudeRange=" + longitudeRange, config)
  .then(response => response.data)
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 403) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      history.push("/login");
      return;
    }
    alert("이유가 뭔지 모르겠지만 개인지도 글 가져오기에 실패했음...");
  });
};
