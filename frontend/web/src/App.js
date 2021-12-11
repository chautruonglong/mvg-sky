import './App.css';
import {BrowserRouter as Router, Switch, Route} from "react-router-dom"
import { Login } from './components/login/Login';
import { Home  } from './components/Home';
import SockJS from 'sockjs-client';
import Stomp from "stompjs"
import React from 'react';
import { useState } from 'react/cjs/react.development';

const sockJS = new SockJS('http://api.mvg-sky.com/api/chats/ws');
export const stompClient = Stomp.over(sockJS);
const roomId = '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d'

function App() {
  const [newMessage, setnewMessage] = useState()

stompClient.connect({}, () => {
  stompClient.subscribe(`/room/${roomId}`,(payload)=>{
    setnewMessage(JSON.parse(payload.body).data)
    // console.log('===', JSON.parse(payload.body).data);
  });
},{});

  React.useEffect(() => {
    console.log(12312312312321321,newMessage)
  }, [newMessage]);

  return (
    <Router>
      <Switch>
        <Route exact path ="/">
          <Login/>
        </Route>

        <Route exact path ="/channels">
          <Home newMessage={newMessage}/> 
        </Route>

        <Route exact path ="/channels/chat">
          <Home status="chat"/>
        </Route>
        <Route exact path ="/channels/profile">
          <Home status="profile"/>
        </Route>
        <Route exact path ="/channels/email">
          <Home status="email"/>
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
