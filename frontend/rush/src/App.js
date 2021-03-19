import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";

function App() {
    const [message, setMessage] = useState("");
    useEffect(() => {
        fetch("http://localhost:8080/test")
            .then(response => {
                console.log(response)
                return response.text()
            })
            .then(message => {
                setMessage(message);
            });
    },[]);
    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo"/>
                <h1 className="App-title">{message}</h1>
            </header>
            <p className="App-intro">
                To get started, edit <code>src/App.js</code> and save to reload.
            </p>
        </div>
    )
}



export default App;
