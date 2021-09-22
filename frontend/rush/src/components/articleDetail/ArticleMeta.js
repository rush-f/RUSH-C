import React from 'react';
import Profile from "./Profile";
import styled from "styled-components";
import CreateDate from "../../util/CreateDate";
import CancelButton from "./CancelButton";

const StyledDiv = styled.div`
  display: flex;
  justify: center;
`;

const Name = styled.div`
  font-size: 20px
`;

const ArticleMeta = (props) => {
  return (<>
    <CancelButton
        markerLat={props.markerLat}
        markerLng={props.markerLng}
        mapType={props.mapType}
    />
    <StyledDiv>
      <Profile imageUrl={props.author? props.author.imageUrl : ""}/>
      <div style={{
        paddingTop: "5px",
      }}>
        <Name>
          {props.author.nickName}
        </Name>
        <CreateDate iso8601format={props.createDate}/>
      </div>
    </StyledDiv>
  </>);
};

export default ArticleMeta;