import React from "react";
import {withRouter} from "react-router-dom";

const CompleteButton = ({isEditNameModalOpen, newGroupName}) => {
  return <>
    {isEditNameModalOpen && <button
      onClick={() => {
        if (!newGroupName) {
          alert("그룹이름을 작성해주세요!");
          return;
        }
        // Todo : 그룹이름 바꾸기 api 사용
        // createGroupApi({groupName, history});
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
