import React from 'react';
import Profile from "./Profile";
import Nickname from "./NickName";

const Member = ({userId, nickname, userImageUrl}) => {
  return (
    <div style={{
      display: "flex",
      flexDirection: "row",
      alignItems: "center"
    }}>
      <Profile userImageUrl={userImageUrl}/>
      <Nickname nickname={nickname}/>
    </div>
  );
};

export default Member;