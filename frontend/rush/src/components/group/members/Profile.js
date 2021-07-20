import React from 'react';
import styled from "styled-components";

const ProfileStyle = styled.div`
  display: inline-block;
  margin: 10px 18px;
  width: 50px;
  height: 50px;
  background-image: url("${props => props.userImageUrl}");
  background-size: contain;
  border-radius: 100px;
  border: 2px solid black;
`;

const Profile = ({userImageUrl}) => {
  return(
    <ProfileStyle
      userImageUrl={userImageUrl}
    />
  );
};

export default Profile;
