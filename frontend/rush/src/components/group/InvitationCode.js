import React, {useRef} from 'react';
import styled from "styled-components";

const InvitationCodeStyle = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0 15px;
  font-size: 18px;
`;

const CopyButton = styled.button`
  margin: 0 12px;
  padding: 4px 30px;
  font-size: 18px;
  border-radius: 20px;
`;

const InvitationCode = ({invitationCode}) => {
  const tempTextInput = useRef();

  const onCopyButtonClicked = (e) => {
    e.preventDefault();
    const tempInput = tempTextInput.current;
    console.log(tempInput)
    tempInput.select();
    document.execCommand("copy");
    alert("초대코드가 복사되었습니다! 초대할 상대에게 공유해주세요!");
  }
  return (
    <InvitationCodeStyle>
      <div style={{
        flexDirection: "row",
        margin: "5px"
      }}>
        초대코드
        <CopyButton onClick={onCopyButtonClicked}>복사</CopyButton>
      </div>
      <input
        type="text"
        ref={tempTextInput}
        style={{
          background: "skyblue",
          padding: "8px",
          textAlign: "center",
          borderRadius: "20px",
          marginTop: "5px",
          borderWidth: 0,
          fontSize: "18px",
          marginLeft: "5px",
          overflow: "hidden",
          outline: "none"
        }}
        value={invitationCode}
        readOnly
      />
    </InvitationCodeStyle>
  );
};

export default InvitationCode;