import React from 'react';
import WindowSize from "../WindowSize";
import {DisplayBox, Outside} from "../common/Box";

const GroupPage = () => {
  return (
    <Outside>
      <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
      </DisplayBox>
    </Outside>
  );
};

export default GroupPage;