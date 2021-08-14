import React from 'react';
import withdraUserwApi from "../../api/WithdraUserwApi";
import styled from "styled-components";

const WithdrawButtonStyle = styled.div`
  color: #777777;
  display: inline-block;
  cursor: pointer;
`;

const WithdrawButton = ({accessToken, history}) => {
  const onWithdrawButtonClicked = () => {
    withdraUserwApi({accessToken, history})
  };
  return (
      <WithdrawButtonStyle onClick={
        onWithdrawButtonClicked}>
        삭제
      </WithdrawButtonStyle>
  );
};

export default WithdrawButton;