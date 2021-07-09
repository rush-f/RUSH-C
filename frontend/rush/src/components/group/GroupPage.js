import React, {useEffect, useState} from 'react';
import WindowSize from "../WindowSize";
import {DisplayBox, Outside} from "../common/Box";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import findGroupApi from "../../api/FindGroupApi";
import {withRouter} from "react-router-dom";
import Name from "./Name";
import CancelButton from "./CancelButton";
import InvitationCode from "./InvitationCode";

const GroupPage = (props) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const groupId = props.match.params.groupId;

  const [group, setGroup] = useState({
    id: groupId,
    name: "",
    invitationCode: ""
  });
  useEffect(() => {
    findGroupApi({
      groupId: groupId,
      accessToken: accessToken,
      history: props.history
    }).then(groupPromise => setGroup(groupPromise));
  }, [groupId, accessToken]);

  return (
    <Outside>
      <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
        <CancelButton/>
        <Name>{group.name}</Name>
        <InvitationCode invitationCode={group.invitationCode}/>
      </DisplayBox>
    </Outside>
  );
};

export default withRouter(GroupPage);