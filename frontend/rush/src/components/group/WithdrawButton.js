import React from 'react';
import styled from "styled-components";
import withdrawGroupApi from "../../api/WithdrawGroupApi";

const WithdrawButtonStyle = styled.div`
  color: #777777;
  margin: 30px 14px;
  cursor: pointer;
`;

const WithdrawButton = ({groupId, accessToken, history}) => {
  const onWithdrawButtonClicked = () => {
    withdrawGroupApi({groupId, accessToken, history})
  };
  return (
      <WithdrawButtonStyle onClick={()=>{
        if (window.confirm("정말 그룹을 탈퇴하시겠습니까?")){    //확인
          onWithdrawButtonClicked()
        }}}>
        그룹 탈퇴
      </WithdrawButtonStyle>
  );
};

export default WithdrawButton;