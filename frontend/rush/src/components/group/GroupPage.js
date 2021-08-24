import React, {useEffect, useState} from 'react';
import WindowSize from "../../util/WindowSize";
import {DisplayBox, Outside} from "../common/Box";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import findGroupApi from "../../api/FindGroupApi";
import {withRouter} from "react-router-dom";
import GroupName from "./GroupName";
import CancelButton from "./CancelButton";
import InvitationCode from "./InvitationCode";
import findGroupMembersApi from "../../api/FindGroupMembersApi";
import GroupMembers from "./members/GroupMembers";
import EditNameModal from "./editNameModal/EditNameModal";

const GroupPage = (props) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const groupId = props.match.params.groupId;

  const [group, setGroup] = useState({
    id: groupId,
    name: "",
    invitationCode: ""
  });
  const [groupMembers, setGroupMembers] = useState([]);

  const [isEditNameModalOpen, setIsEditNameModalOpen] = useState(false);

  useEffect(() => {
    findGroupApi({
      groupId: groupId,
      accessToken: accessToken,
      history: props.history
    }).then(groupPromise => setGroup(groupPromise));

    findGroupMembersApi({
      groupId: groupId,
      accessToken: accessToken,
      history: props.history
    }).then(groupMembersPromise => {
      setGroupMembers(groupMembersPromise)
    });
  }, [groupId, accessToken]);

  return (
    <Outside>
      <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
        <CancelButton/>
        <GroupName
          setIsEditNameModalOpen={setIsEditNameModalOpen}
          groupName={group? group.name : ""}
        />
        <InvitationCode invitationCode={group.invitationCode}/>
        <hr style={{margin: "20px 0 8px 0"}}/>
        <GroupMembers members={groupMembers}/>
        <EditNameModal
          isEditNameModalOpen={isEditNameModalOpen}
          setIsEditNameModalOpen={setIsEditNameModalOpen}
        />
      </DisplayBox>
    </Outside>
  );
};

export default withRouter(GroupPage);