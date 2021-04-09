import React, {useState} from 'react';
import Modal from 'react-modal';
import GroupList from "./GroupList";
import GroupListOpenButton from "./GroupListOpenButton";

const WritingStep3Modal = (props) => {
  const [isGroupOpened, setIsGroupOpened] = useState(false);

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
           <input type="checkbox"/>전체지도
        </div>
        <div>
          그룹지도
          <GroupListOpenButton
              isGroupOpened={isGroupOpened}
              setIsGroupOpened={setIsGroupOpened}
          />
        </div>
        <GroupList isGroupOpened={isGroupOpened}/>
        <div>
           <input type="checkbox"/>개인지도
        </div>
      </Modal>
  );
};

export default WritingStep3Modal;