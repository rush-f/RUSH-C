import React from 'react';
import Member from "./Member";

const GroupMembers = ({members}) => {
  return (
    <>
      {
        members.map(member => <Member
          userId={member.id}
          nickname={member.nickname}
          userImageUrl={member.imageUrl}
        />)
      }
    </>
  );
};

export default GroupMembers;
