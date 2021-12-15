import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { Login } from "./components/login/Login";
import { Home } from "./components/Home";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import React from "react";
import { useState } from "react/cjs/react.development";

const sockJS = new SockJS("http://api.mvg-sky.com/api/chats/ws");
export const stompClient = Stomp.over(sockJS);
let oldRoom

function App() {
  const [token, setToken] = useState();
  const [roomId, setRoomId] = useState("e58fe586-a124-4075-ab18-61c907e38725")
  const [newMessage, setnewMessage] = useState();

  

  React.useEffect(() => {
    console.log('reconnectxxxxxxxxxxxxxxx')
    stompClient.connect(
      {},
      () => {
        console.log('dmmmmmmmmmmmmmmmmmmmmmmmmmm');
        stompClient.subscribe(`/room/${roomId}`, (payload) => {
          setnewMessage(JSON.parse(payload.body).data);
        });
      },
      {}
    );
  }, [roomId]);

  // if(!token){
  //   return <Login setToken={setToken}/>
  // }

  return (
    <Router>
      <Switch>
        <Route exact path="/">
          <Login />
        </Route>

        <Route exact path="/channels">
          <Home newMessage={newMessage}/>
        </Route>

        <Route exact path="/channels/chat">
          <Home status="chat" newMessage={newMessage} roomId={roomId} setRoomId={setRoomId}/>
        </Route>
        <Route exact path="/channels/profile">
          <Home status="profile" />
        </Route>
        <Route exact path="/channels/email">
          <Home status="email" />
        </Route>

        <Route exact path="/channels/chat/:id">
          <Home status="chat" newMessage={newMessage}  roomId={roomId} setRoomId={setRoomId}/>
        </Route>
        
        <Route exact path="/channels/profile/:id">
          <Home status="profile" />
        </Route>

        <Route exact path="/channels/email/:id">
          <Home status="email" />
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
