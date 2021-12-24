// import Stomp from "stompjs";
// import SockJS from "sockjs-client";
// import AbstractXHRObject from "sockjs-client/lib/transport/browser/abstract-xhr";
// import { useEffect } from "react";

// const _start = AbstractXHRObject.prototype._start;

// AbstractXHRObject.prototype._start = function (method, url, payload, opts) {
//   if (!opts) {
//     opts = { noCredentials: true };
//   }
//   return _start.call(this, method, url, payload, opts);
// };

// function App() {
//   const roomId = [
//     "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
//     "0e989b43-94b0-4dc8-ba4c-181d31509b12",
//     "e4984186-80a0-4c4b-9bff-1b52d02f5914",
//   ];
//   useEffect(() => {
//     const socket = new SockJS("http://api.mvg-sky.com/api/chats/ws");
//     const stompClient = Stomp.over(socket);

//     stompClient.connect(
//       {},
//       () => {
//         roomId.forEach((room) =>
//           stompClient.subscribe(`/room/${room}`, (payload) => {
//             console.log("Receivinnnnnnnnnnnnnnnnnnnnnnnnng");
//             console.log(payload.body);
//           })
//         );

//         const chatMessage = {
//           accountId: "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
//           content: "Hello anh emmmmmmmmmmmmmmmmmmmm",
//           threadId: null,
//           type: "TEXT",
//           delay: 0,
//         };

//         stompClient.send(
//           "/room/9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
//           {},
//           chatMessage
//         );
//       },
//       (error) => {
//         console.log(error);
//       }
//     );
//   }, []);
//   return <div>Demo</div>;
// }

// export default App;

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
          <Home accountId={accountId}/>
        </Route>

        <Route exact path="/channels/chat">
          <Home
            status="chat"
            newMessage={newMessage}
            accountId={accountId}
            roomId={roomId}
            setRoomId={setRoomId}
          />
        </Route>
        <Route exact path="/channels/profile">
          <Home status="profile" accountId={accountId} />
        </Route>
        <Route exact path="/channels/email">
          <Home status="email" accountId={accountId} />
        </Route>

        <Route exact path="/channels/chat/:id">
          <Home
            status="chat"
            newMessage={newMessage}
            accountId={accountId}
            roomId={roomId}
            setRoomId={setRoomId}
          />
        </Route>

        <Route exact path="/channels/profile/:id">
          <Home status="profile" accountId={accountId} />
        </Route>

        <Route exact path="/channels/email/:id">
          <Home status="email" accountId={accountId} />
        </Route>
      </Switch>
    </Router>
  );
}

export default App;

// import SockJS from "sockjs-client";
// import Stomp from "stompjs";
// import React, { useState, useEffect } from "react";
// import axios from "axios";
// import AbstractXHRObject from "sockjs-client/lib/transport/browser/abstract-xhr";

// const _start = AbstractXHRObject.prototype._start;

// AbstractXHRObject.prototype._start = function (method, url, payload, opts) {
//   if (!opts) {
//     opts = { noCredentials: true };
//   }
//   return _start.call(this, method, url, payload, opts);
// };

// const sockJS = new SockJS("http://api.mvg-sky.com/api/chats/ws");
// export const stompClient = Stomp.over(sockJS);

// function App() {
//   const [accountId, setAccountId] = useState(
//     "5d0d018d-bee1-4533-aed8-41a980792ebc"
//   );

//   useEffect(() => {
//     const fetchChannels = async () => {
//       var config = {
//         method: "get",
//         url: `http://api.mvg-sky.com/api/rooms?accountId=${accountId}`,
//         headers: {},
//       };

//       const response = await axios(config);
//       const rooms = response.data;
//       console.log("xxxx", rooms);
//       stompClient.connect(
//         {},
//         () => {
//           console.log("===========");
//           rooms.forEach((room) => {
//             stompClient.subscribe(`/room/${room.id}`, (payload) => {
//               console.log(payload);
//             });
//           });
//         },
//         {}
//       );
//     };

//     fetchChannels();
//   }, [accountId]);

//   return <div>DEMO</div>;
// }

// export default App;
