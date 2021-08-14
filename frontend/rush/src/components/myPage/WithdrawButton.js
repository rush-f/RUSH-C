import React from 'react';
import withdrawUserApi from "../../api/WithdrawUserApi";
import styled from "styled-components";

const WithdrawButtonStyle = styled.div`
  color: #777777;
  display: inline-block;
  cursor: pointer;
`;

const WithdrawButton = ({nickName, accessToken, history}) => {
  const onWithdrawButtonClicked = () => {
    withdrawUserApi({accessToken, history})
  };
  return (
      <WithdrawButtonStyle onClick={()=>{
        if (window.confirm("정말 회원탈퇴를 진행하시겠습니까?")){    //확인
          if (window.confirm("회원을 탈퇴하면 작성한 모드 게시글 및 댓글이 삭제됩니다. 계속 진행하시겠습니까?")){
            if(window.prompt("자신의 회원이름을 입력해주세요") === nickName){
              onWithdrawButtonClicked()
            } else { alert("회원이름을 잘못입력하였습니다")}
          }
        }}}>
        삭제
      </WithdrawButtonStyle>
  );
};

export default WithdrawButton;