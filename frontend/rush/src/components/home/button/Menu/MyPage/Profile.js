import styled from "styled-components";

const Profile = styled.div`
  display: inline-block;
  width: 80px;
  height: 80px;
  border-radius: 100px;
  border: 2px solid black;
  background-image: url("${props => props.userImageUrl}");
  background-size: contain;
  margin-left: 20px;
`;

export default Profile;
