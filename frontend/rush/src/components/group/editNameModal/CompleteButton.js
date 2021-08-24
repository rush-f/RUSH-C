import React from "react";
import {withRouter} from "react-router-dom";
import editGroupNameApi from "../../../api/EditGroupNameApi";

const CompleteButton = ({ isEditNameModalOpen, setIsEditNameModalOpen, newGroupName, group, setGroup, accessToken, history, setNewGroupName }) => {
  return <>
    {isEditNameModalOpen && group.id && <button
      onClick={() => {
        if (!newGroupName) {
          alert("그룹이름을 작성해주세요!");
          return;
        }
        editGroupNameApi({
          groupId: group.id,
          newGroupName: newGroupName,
          accessToken: accessToken,
          history: history
        });
        setNewGroupName("");
        group.name = newGroupName;
        setGroup(group);
        setIsEditNameModalOpen(false);
      }}
      style={{
        backgroundColor: "#00000000",
        width: "60%",
        height: "50px",
        margin: "10px",
        borderRadius: "20px",
        fontSize: "18px",
        fontFamily: "'Gowun Dodum', sans-serif",
        color: "#445566"
      }}
    > 이름 바꾸기 </button>
    }
  </>;
};

export default withRouter(CompleteButton);
