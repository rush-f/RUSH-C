import React from "react";
import joinGroupApi from "../../../../api/JoinGroupApi";

const CompleteButton = ({isCreateGroupModalOpen, invitationCode, history}) => {
  return <>
    {isCreateGroupModalOpen && <button
      onClick={() => {
        if (!invitationCode) {
          alert("초대코드를 입력해주세요!");
          return;
        }
        joinGroupApi({invitationCode, history});
      }}
      style={{
        backgroundColor: "#00000000",
        width: "60%",
        height: "50px",
        margin: "10px",
        borderRadius: "20px",
        fontSize: "18px",
        color: "#445566"
      }}
    >가입</button>
    }
  </>;
};

export default CompleteButton;