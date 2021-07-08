import React from "react";
import createGroupApi from "../../../../api/CreateGroupApi";
import {withRouter} from "react-router-dom";

const CompleteButton = ({isCreateGroupModalOpen, groupName, history}) => {
  return <>
    {isCreateGroupModalOpen && <button
      onClick={() => {
        if (!groupName) {
          alert("그룹이름을 작성해주세요!");
          return;
        }
        createGroupApi({groupName, history});
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
    > 그룹 만들기 </button>
    }
  </>;
};

export default withRouter(CompleteButton);