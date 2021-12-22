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
  const [contact, setContact] = useState(null)


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
            console.log(JSON.parse(payload.body).data)
            const chatMessage = {
              accountId: JSON.parse(payload.body).data.accountId,
              content: JSON.parse(payload.body).data.content,
              threadId: JSON.parse(payload.body).data.threadId,
              type: JSON.parse(payload.body).data.type,
              delay: 0
            }
            setChats(chatMessage)
          }
        );
      })
    }
  }

  const fetchContact = async () => {
    if (user?.domain?.id) {
      const values = await apiRequest.get(`/contacts?domainIds=${user.domain.id}`)
      setContact(values)
    }
  }

  useEffect(() => {
    fetchContact()
  }, [user])

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
        stompClient,
        setChats,
        setMyRooms,
        contact
      }}>
      {children}
    </AuthContext.Provider>
  );
};
