import React from 'react';
import changeGroupImportant from "../../api/ChangeGroupImportant";

const ImportantStart = ({group, setGroup, accessToken, history}) => {
  const color = (group && group.important)? "#ffd400" : "#afafaf";

  return (
    <div
      style={{color: color, fontSize: "20px", marginLeft: "8px"}}
      onClick={() => {
        if (group) {
          changeGroupImportant({
            groupId: group.id,
            accessToken,
            history
          });
          setGroup({
            id: group.id,
            name: group.name,
            invitationCode: group.invitationCode,
            important: !group.important,
            createDate: group.createDate
          })
        }
      }}
    >
      ★
    </div>
  );
};

export default ImportantStart;
