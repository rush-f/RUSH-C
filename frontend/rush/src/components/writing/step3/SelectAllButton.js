import React from 'react';

const SelectAllButton = (props) => {
  const onClick = ()=>{
    if(props.isPublicMapChecked || props.isPrivateMapChecked || props.checkedGroups.length > 0){
      props.setCheckedGroups([]);
      props.setIsPublicMapChecked(false);
      props.setIsPrivateMapChecked(false);
    }
    else {
      props.setCheckedGroups(props.groups.map(group => group.id));
      props.setIsPublicMapChecked(true)
      props.setIsPrivateMapChecked(true)
    }
  };

  return (
      <div>
        <button onClick={onClick} >전체선택</button>
      </div>
  );
};

export default SelectAllButton;