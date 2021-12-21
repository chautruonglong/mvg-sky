import React, { createContext, useState, useEffect } from 'react';
import apiRequest from "../utils/apiRequest"
import Toast from 'react-native-toast-message';
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import AbstractXHRObject from "sockjs-client/lib/transport/browser/abstract-xhr";
export const AuthContext = createContext();
const _start = AbstractXHRObject.prototype._start;

AbstractXHRObject.prototype._start = function (method, url, payload, opts) {
  if (!opts) {
    opts = { noCredentials: true };
  }
  return _start.call(this, method, url, payload, opts);
};
const sockJS = new SockJS('http://api.mvg-sky.com/api/chats/ws');
export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [profile, setProfile] = useState(null);
  const [chats, setChats] = useState(null);
  const [myrooms, setMyRooms] = useState(null);
  const [iduser, setIduser] = useState(null)
  const [isConnnected, setIsConnnected] = useState(false)
  const [stompClient, setStompClient] = useState(Stomp.over(sockJS))


  useEffect(() => {
    stompClient.connect({}, () => {
      setIsConnnected(true);
    });
  }, [])
  const fetchRoom = async () => {
    const rooms = await apiRequest.get(`/rooms?accountId=${user.account.id}`)
    setMyRooms(rooms)
    if (isConnnected) {
      rooms.forEach(room => {
        stompClient.subscribe(
          `/room/${room.id}`,
          (payload) => {
            const chatMessage = {
              accountId: JSON.parse(payload.body).data.accountId,
              content: JSON.parse(payload.body).data.content,
              threadId: null,
              type: "TEXT",
              delay: 0
            }
            setChats(chatMessage)
            if (chatMessage === null) {
            }
            else {
              if (chatMessage.accountId === user?.account?.id) {
                console.log("cung id")
              }
              else {
                Toast.show({
                  type: 'info',
                  text1: title.userName,
                  text2: chatMessage.content,
                });
              }
            }
          }
        );
      })

      // stompClient.connect(
      //   {},
      //   () => {

      //     rooms.forEach(room => {
      //       stompClient.subscribe(
      //         `/room/${room.id}`,
      //         (payload) => {
      //           const chatMessage = {
      //             accountId: JSON.parse(payload.body).data.accountId,
      //             content: JSON.parse(payload.body).data.content,
      //             threadId: null,
      //             type: "TEXT",
      //             delay: 0
      //           }
      //           setChats(chatMessage)
      //         }
      //       );
      //     })
      //   },
      //   (error) => {
      //     console.log(error);
      //   }
      // );
    }
  }

  useEffect(() => {
    fetchRoom()
  }, [user])

  useEffect(() => {
    if (user?.account?.id) {
      handleGetProfile(user?.account?.id)
    }
  }, [user])


  const handleGetProfile = async (userId) => {
    const res = await apiRequest.get(`/profiles`, {
      params: {
        accountId: userId
      },
      // headers: { Authorization: `${user.accessToken}` } 
    })
    setProfile(res[0])
  }

  return (
    <AuthContext.Provider
      value={{
        user,
        setUser,
        myrooms,
        profile,
        setProfile,
        chats,
        stompClient
      }}>
      {children}
    </AuthContext.Provider>
  );
};
