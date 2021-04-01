import React from 'react';
import { slide as BurgerMenu } from "react-burger-menu";
import "./styled.css";

const Menu = () => {
  const showSettings = event => {
    event.preventDefault();
  };

  return (<>
    <BurgerMenu>
      <a id="home" className="menu-item" href="/">Home</a>
      <a id="about" className="menu-item" href="/">About</a>
      <a id="contact" className="menu-item" href="/">Contact</a>
      <a onClick={ showSettings } className="menu-item--small" href="">Settings</a>
    </BurgerMenu>
  </>);
};

export default Menu;
