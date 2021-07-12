import React from 'react';
import styled from "styled-components";
import {Email, Name} from "./Box";
import CreateDate from "../../util/CreateDate";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";

const StyledInfo = styled.div`
  padding-Top: 2px;
  margin-left:20px
`;

const Box = styled.div`
  display: flex;
  justify: center;
`;

const StyledButton = styled.button`
  margin-left: 20px;
`;

const Info = (props) => {
  return (
      <StyledInfo>
        <Box>
        <Name>
          {props.user.nickName}
        </Name>
        <StyledButton onClick={()=>{
          if(window.confirm("정말로 로그아웃하실건가요?")){
            sessionStorage.removeItem(ACCESS_TOKEN);
            props.history.push("/");
          }
        }}>로그아웃</StyledButton>
        </Box>
        <Email>
          {props.user.email}
        </Email>
        <CreateDate iso8601format={props.user.visitDate}/>
      </StyledInfo>
  );
};

export default Info;
