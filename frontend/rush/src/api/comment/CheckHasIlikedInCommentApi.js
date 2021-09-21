import React from 'react';
import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const checkHasIlikedInCommentApi = (accessToken, articleId, mapType) => {
  const config = {
    headers: {
      "Authorization": "Bearer " + accessToken
    }
  };
  return axios.get(BACKEND_ADDRESS + "/articles/" + mapType + "/comments/" + articleId + "/like", config)
  .then(response => response.data);
};

export default checkHasIlikedInCommentApi;