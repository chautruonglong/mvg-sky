import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { Login } from "./components/login/Login";
import { Home } from "./components/Home";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import React from "react";
import axios from "axios";
import { useState } from "react/cjs/react.development";
import AbstractXHRObject from "sockjs-client/lib/transport/browser/abstract-xhr";

const _start = AbstractXHRObject.prototype._start;

AbstractXHRObject.prototype._start = function (method, url, payload, opts) {
  if (!opts) {
    opts = { noCredentials: true };
  }
  return _start.call(this, method, url, payload, opts);
};

const sockJS = new SockJS("http://api.mvg-sky.com/api/chats/ws");
export const stompClient = Stomp.over(sockJS);
let isConnected = false;
stompClient.connect({}, () => {
  isConnected = true;
});

function App() {
  const [accountId, setAccountId] = useState();
  const [roomId, setRoomId] = useState("e58fe586-a124-4075-ab18-61c907e38725");
  const [newMessage, setnewMessage] = useState();

  const fetchChannels = async () => {
    var config = {
      method: "get",
      url: `http://api.mvg-sky.com/api/rooms?accountId=${accountId}`,
      headers: {},
    };

    const response = await axios(config);
    const rooms = response.data;
    if (isConnected) {
      rooms.forEach((room) => {
        stompClient.subscribe(`/room/${room.id}`, (payload) => {
          setnewMessage(JSON.parse(payload.body).data);
        });
      });
    }
  };

  React.useEffect(() => {
    accountId && fetchChannels();
  }, [accountId]);

  return (
    <Router>
      <Switch>
        <Route exact path="/">
          <Login setAccountId={setAccountId} />
        </Route>

        <Route exact path="/channels">
          <Home accountId={accountId} setnewMessage={setnewMessage}/>
        </Route>

        <Route exact path="/channels/chat">
          <Home
            status="chat"
            newMessage={newMessage}
            accountId={accountId}
            roomId={roomId}
            setRoomId={setRoomId}
            setnewMessage={setnewMessage}
          />
        </Route>
        <Route exact path="/channels/profile">
          <Home status="profile" accountId={accountId} setnewMessage={setnewMessage}/>
        </Route>
        <Route exact path="/channels/email">
          <Home status="email" accountId={accountId} setnewMessage={setnewMessage}/>
        </Route>

        <Route exact path="/channels/chat/:id">
          <Home
            status="chat"
            newMessage={newMessage}
            accountId={accountId}
            roomId={roomId}
            setRoomId={setRoomId}
            setnewMessage={setnewMessage}
          />
        </Route>

        <Route exact path="/channels/profile/:id">
          <Home status="profile" accountId={accountId} setnewMessage={setnewMessage}/>
        </Route>

        <Route exact path="/channels/email/:id">
          <Home status="email" accountId={accountId} setnewMessage={setnewMessage}/>
        </Route>

        <Route exact path="/channels/change-password">
          <Home status="password" accountId={accountId} setnewMessage={setnewMessage}/>
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
