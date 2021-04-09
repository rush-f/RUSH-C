import React, {useState} from 'react';
import Modal from 'react-modal';
import GroupList from "./GroupList";
import GroupListOpenButton from "./GroupListOpenButton";

const WritingStep3Modal = (props) => {
  const groups = [
    {id: 1, name: "그룹A"},
    {id: 3, name: "그룹C"},
    {id: 4, name: "그룹D"},
  ];
  const [isGroupOpened, setIsGroupOpened] = useState(false);

  const [isPublicMapChecked, setIsPublicMapChecked] = useState(false);
  const [isPrivateMapChecked, setIsPrivateMapChecked] = useState(false);
  const [checkedGroups, setCheckedGroups] = useState([]);

  const onGroupCheckboxClicked = (groupId) => {
    if (checkedGroups.includes(groupId)) {
      setCheckedGroups(checkedGroups.filter(e => e !== groupId))
    } else {
      setCheckedGroups([...checkedGroups, groupId]);
    }
  };

  return (
      <Modal            //모달 태그 내부에 옵션을 넣고 태그 사이에 내용을 넣는다
          isOpen={props.step === 3}        //모달의 오픈유무를 bool값으로 정한다.
          shouldCloseOnOverlayClick={false}     //close버튼을 눌러야만 모달이 종료
          //onRequestClose={closeModal}    // 모달이 닫히는 요청을 받으면 무엇을 할껀지
          style={{
            overlay: {
              backgroundColor: '#00112255'
            },
            content: {
              maxWidth: '500px',
              margin: 'auto',
              backgroundColor: 'rgb(222, 235, 247)',
            }
          }}
          contentLabel="Example Modal"       //모달의 라벨
      >
        <div>
          <button onClick={()=>{

            if(isPublicMapChecked || isPrivateMapChecked || checkedGroups.length > 0){
              setCheckedGroups([]);
              setIsPublicMapChecked(false);
              setIsPrivateMapChecked(false);
            }
            else {
              setCheckedGroups(groups.map(group => group.id));
              setIsPublicMapChecked(true)
              setIsPrivateMapChecked(true)
            }
            }} >전체선택</button>
        </div>
        <div>
           <input type="checkbox"
                  onChange={() => setIsPublicMapChecked(!isPublicMapChecked)}
                  checked={isPublicMapChecked}
           />
           전체지도
        </div>
        <div>
          그룹지도
          <GroupListOpenButton
              isGroupOpened={isGroupOpened}
              setIsGroupOpened={setIsGroupOpened}
          />
        </div>
        <GroupList
            isGroupOpened={isGroupOpened}
            groups={groups}
            checkedGroups={checkedGroups}
            onGroupCheckboxClicked={onGroupCheckboxClicked}
        />
        <div>
           <input type="checkbox"
                  onChange={() => setIsPrivateMapChecked(!isPrivateMapChecked)}
                  checked={isPrivateMapChecked}
           />개인지도
        </div>
      </Modal>
  );
};

export default WritingStep3Modal;