import React from 'react';
import { Redirect } from 'react-router-dom'
import {ACCESS_TOKEN} from "../../constants/LocalStorage";

const OAuth2RedirectHandler = (props) => {
  const getUrlParameter = (name) => {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');

    const results = regex.exec(props.location ? props.location.search : "");
    return results === null ? ''
        : decodeURIComponent(results[1].replace(/\+/g, ' '));
  };

  const token = getUrlParameter('token');
  const error = getUrlParameter('error');

  if(token) {
    localStorage.setItem(ACCESS_TOKEN, token);
    return <Redirect to={{
      pathname: "/",
      state: { from: props.location ? props.location.state : "" }
    }}/>;
  } else {
    return <Redirect to={{
      pathname: "/login",
      state: {
        from: props.location ? props.location.state : "",
        error: error
      }
    }}/>;
  }
};

export default OAuth2RedirectHandler;