import { HashtagIcon, ChatIcon } from "@heroicons/react/outline";
import Message from "./Message";
import { useEffect, useRef, useState } from "react";
import axios from "axios";
import { stompClient } from "../App";

export const Chat = ({ id, newMessage, roomId, setRoomId, title, accountId, accountList }) => {
  const inputRef = useRef("");
  const chatRef = useRef(null);
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    const addMessage = () => {
      if (newMessage) {
        console.log("send message");
        setMessages([...messages, newMessage]);
      }
    };
    addMessage();
  }, [newMessage]);

  const scrollToBottom = () => {
    chatRef.current.scrollIntoView({
      behavior: "smooth",
      block: "start",
    });
  };
  useEffect(() => {
    const fetchRoom = async () => {
      var config = {
        method: "get",
        url: `http://api.mvg-sky.com/api/messages?roomId=${roomId}&limit=30`,
        headers: {},
      };

      const values = await axios(config);
      values.data.reverse();
      setMessages(values.data);
    };

    fetchRoom();
    scrollToBottom();
  }, [roomId]);

  const handleSendMessage = (e) => {
    e.preventDefault();

    if (inputRef.current.value !== "") {
      console.log('xxx----===', accountId)
      const chatMessage = {
        accountId: accountId,
        content: inputRef.current.value,
        threadId: null,
        type: "TEXT",
        delay: 0,
      };
      stompClient.send(
        `/chat/send-message/${roomId}`,
        {},
        JSON.stringify(chatMessage)
      );
    }

    inputRef.current.value = "";
    scrollToBottom();
  };

  return (
    <>
      <div className="flex flex-col h-screen">
        <header className="flex items-center justify-between space-x-5 border-b border-gray-800 p-4 -mt-1">
          <div className="flex items-center space-x-1">
            <HashtagIcon className="h-6 text-[#72767d]" />
            <h4 className="text-white font-semibold">{title}</h4>
          </div>
          <div className="flex space-x-3"></div>
        </header>
        <main className="flex-grow overflow-y-scroll scrollbar-hide">
          {messages?.map((data) => {
            const { content, createdAt, accountId, type } = data;
            return (
              <Message
                key={Math.random()}
                id={accountId}
                message={content}
                timestamp={createdAt}
                type={type}
                // photoURL={photoURL}
              />
            );
          })}
          <div ref={chatRef} className="pb-16" />
        </main>
        <div className="flex items-center p-2.5 bg-[#40444b] mx-5 mb-7 rounded-lg">
          <ChatIcon className="icon mr-4" />
          <form className="flex-grow">
            <input
              type="text"
              className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-sm"
              ref={inputRef}
            />
            <button hidden type="submit" onClick={(e) => handleSendMessage(e)}>
              Send
            </button>
          </form>
        </div>
      </div>
    </>
  );
};
