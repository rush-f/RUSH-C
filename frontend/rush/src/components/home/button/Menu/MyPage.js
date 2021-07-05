import React from 'react';
import {Outside,DisplayBox} from "./Box";
import WindowSize from "../../../WindowSize";
import CancelButton from "./CancelButton";

const MyPage = () => {
  return (
      <Outside>
        <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
          <CancelButton></CancelButton>
        </DisplayBox>
      </Outside>
  );
};

export default MyPage;