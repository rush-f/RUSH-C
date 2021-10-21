import React from 'react';

const CommentCreateDate = (props) => {
  const week = ["일", "월", "화", "수", "목", "금", "토"];
  const dateObj = new Date(props.iso8601format);

  const year = dateObj.getFullYear();
  const month = (dateObj.getMonth() + 1).toString().padStart(2, '0');
  const date = dateObj.getDate().toString().padStart(2, '0');
  const day = week[dateObj.getDay()];
  const hours = dateObj.getHours();
  const minutes = dateObj.getMinutes();

  return (
      <div style={{
        fontSize: "12px",
        color: "#666666"
      }}>
        {year}.{month}.{date} {day} {hours}:{minutes}
      </div>
  );
};

export default CommentCreateDate;