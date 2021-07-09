import React from 'react';
import styled from "styled-components";

const InvitationCodeStyle = styled.div`
  padding: 0 15px;
  font-size: 18px;
`;

const CopyButton = styled.button`
  margin: 0 12px;
  padding: 0 20px;
  font-size: 18px;
  border-radius: 20px;
`;

const InvitationCode = ({invitationCode}) => {
  const onCopyButtonClicked = (e) => {
    e.preventDefault();
    alert("아직 개발중입니다!");
  }
  return (
    <InvitationCodeStyle>
      {"초대코드 : " + invitationCode}
      <CopyButton onClick={onCopyButtonClicked}>복사</CopyButton>
    </InvitationCodeStyle>
  );
};

export default InvitationCode;