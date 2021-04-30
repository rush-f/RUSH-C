import React from 'react';
import axios from "axios";
import {ADDRESS} from "../../../constants/BackendAddress";
import {withRouter} from "react-router-dom";

const createWriting = (props) => {
  const body = {
    title: props.title,
    content: props.content,
    latitude: props.center.lat(),
    longitude: props.center.lng()
  };
  axios.post(ADDRESS + "/articles", body)
    .then(response => {
      if (response.status === 201) {
         const uri = response.headers.location;
         props.history.push(uri);
      }
    });
};

export default createWriting;