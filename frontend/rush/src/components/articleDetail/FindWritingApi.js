import React from 'react';
import axios from "axios";
import {ADDRESS} from "../../constants/BackendAddress";

const findWritingApi = (articleId) => {
  return axios.get(ADDRESS + "/articles/" + articleId)
    .then(response => response.data);
};

export default findWritingApi;