import styled from "styled-components";

const LoginPageBox = styled.div`
  position:absolute;
  top: 30%;
  width: 100%;
  text-align: center;
`;

const LoginButton = styled.a`
  display: block;
  margin: 15px auto;
  height: 45px;
  font-size: 20px;
  padding-top: 13px;
  border: 2px solid black;
  max-width: 500px;
  text-decoration: none;
  color: black;
  &:hover {
    color:#00A0C6; 
    text-decoration:none; 
    cursor:pointer;  
  }
`;

const StyledInput = styled.input`
  display: block;
  font-size: 17px;
  margin: 10px auto;
  max-width: 480px;
  width: 80%;
  background-color: #00000000;
  padding: 10px;
  height: 35px;
  border: 1px solid gray;
  cursor: pointer;
  &:focus {
        outline: 2px solid rgb(90, 155, 213);
        border: 1px solid rgb(90, 155, 213);
    }
`;

export {LoginPageBox, LoginButton, StyledInput};