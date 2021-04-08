import React from 'react';
import styled from "styled-components";

const StyledInput = styled.input`
  display: inline-block;
  font-size: 20px;
  margin: 10px;
  width: 85%;
  background-color: #00000000;
  padding: 10px;
  height: 20px;
`;

const StyledTextarea = styled.textarea`
  display: block;
  font-size: 17px;
  margin: 10px;
  width: 84%;
  height: 75%;
  resize: none;
  background-color: #00000000;
  padding: 10px;
`;

export {StyledInput, StyledTextarea};