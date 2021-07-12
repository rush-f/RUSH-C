import React from 'react';
import styled from "styled-components";

const ProfileStyle = styled.div`
  display: inline-block;
  position: fixed;
  zIndex: 10;
  top: 0;
  right: 0;
  margin: 10px;
  width: 60px;
  height: 60px;
  background-image: url("${props => props.userImageUrl}");
  background-size: contain;
  border-radius: 100px;
  border: 2px solid black;
  cursor: pointer;
`;

const Profile = ({userImageUrl, history}) => {
  return(
    <ProfileStyle
      userImageUrl={userImageUrl}
      onClick={() => history.push("/mypage")}
    />
  );
};

export default Profile;
