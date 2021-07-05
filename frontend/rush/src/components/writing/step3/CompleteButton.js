import React from 'react';
import createWritingApi from "../../../api/CreateWritingApi";
import {withRouter} from "react-router-dom";

const CompleteButton = (props) => {
  return <>
    {(props.step === 3) && <button
        onClick={() => {
          createWritingApi(props);
        }}
        style={{
          position: "absolute",
          bottom: 0,
          right: 0,
          backgroundColor: "#00000000",
          width: "100px",
          height: "50px",
          margin: "10px",
        }}
    > 완료 </button>
    }
  </>;
};

export default withRouter(CompleteButton);