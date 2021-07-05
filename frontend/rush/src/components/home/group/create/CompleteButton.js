import React from "react";
import createGroupApi from "./CreateGroupApi";
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
        position: "absolute",
        bottom: 0,
        right: 0,
        backgroundColor: "#00000000",
        width: "100px",
        height: "50px",
        margin: "10px",
      }}
    > 다음 </button>
    }
  </>;
};

export default withRouter(CompleteButton);