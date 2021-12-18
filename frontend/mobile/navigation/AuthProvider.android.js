import React, { createContext, useState, useEffect } from 'react';
import apiRequest from "../utils/apiRequest"
import Toast from 'react-native-toast-message';
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [profile, setProfile] = useState(null);
  const [chats, setChats] = useState(null);
  const [myrooms, setMyRooms] = useState(null);
  const [iduser, setIduser] = useState(null)


  const sockJS = new SockJS('http://api.mvg-sky.com/api/chats/ws');
  const [stompClient, setStompClient] = useState(Stomp.over(sockJS))

  const fetchRoom = async () => {
    const rooms = await apiRequest.get(`/rooms?accountId=${user.account.id}`)
    setMyRooms(rooms)
    stompClient.connect(
      {},
      () => {

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
            }
          );
        })
      },
      (error) => {
        console.log(error);
      }
    );
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
