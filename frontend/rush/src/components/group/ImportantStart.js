import React from 'react';

const ImportantStart = ({important}) => {
  const color = important? "yellow" : "#afafaf";

  return (
    <div style={{color: color, fontSize: "20px", marginLeft: "8px"}}>
      ★
    </div>
  );
};

export default ImportantStart;
