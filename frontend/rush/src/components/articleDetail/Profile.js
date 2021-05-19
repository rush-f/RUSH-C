import styled from "styled-components";

const Profile = styled.div`
  display: inline-block;
  width: 60px;
  height: 60px;
  border-radius: 100px;
  border: 2px solid black;
  background-image: url("${props => props.imageUrl}");
  background-size: contain;
  margin-right: 10px;
`;

export default Profile;
