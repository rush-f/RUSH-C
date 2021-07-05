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
        backgroundColor: "#00000000",
        width: "100px",
        height: "50px",
        margin: "10px",
      }}
    > 그룹만들기 </button>
    }
  </>;
};

export default withRouter(CompleteButton);