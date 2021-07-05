import styled from "styled-components";

const Outside = styled.div`
  max-width: 400px;
  margin: auto;
`;

const DisplayBox = styled.div`
  margin: 10px;
  border: 2px solid rgb(90, 155, 213);
  border-radius: 10px;
  overflow-y: scroll;
`;

const StyledDiv = styled.div`
  display: flex;
  justify: center;
  margin-top: 40px;
`;

const Name = styled.div`
  font-size: 20px;

`;

const Email = styled.div`
  font-size: 20px;

`;

export { Outside, DisplayBox, StyledDiv, Name, Email};
