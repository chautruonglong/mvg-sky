import { HashtagIcon, ChatIcon } from "@heroicons/react/outline";
import Message from "./Message";
import { useEffect, useRef, useState } from "react";
import axios from "axios";
import { stompClient } from "../App";

export const Chat = ({newMessage}) => {
  const inputRef = useRef("");
  const chatRef = useRef(null);
  const idSender = "5d0d018d-bee1-4533-aed8-41a980792ebc";
  const photoURL =
    "https://cdn3.vectorstock.com/i/1000x1000/38/17/male-face-avatar-logo-template-pictograph-vector-11333817.jpg";
  const [messages, setMessages] = useState([]);
  const roomId = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d";

  useEffect(()=> {
    const addMessage = () => {
      if(newMessage){
        setMessages([
          ...messages,
          newMessage
        ])

      }
    }
    addMessage()
  },[newMessage])


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
        url: `http://api.mvg-sky.com/api/messages?roomId=${roomId}&type=TEXT`,
        headers: {},
      };

      const values = await axios(config);
      setMessages(values.data);
    };

    fetchRoom();
  }, []);

  const handleSendMessage = (e) => {
    e.preventDefault();

    if (inputRef.current.value !== "") {
      const chatMessage = {
        accountId: idSender,
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
            <h4 className="text-white font-semibold">Phước Quốc</h4>
          </div>
          <div className="flex space-x-3"></div>
        </header>
        <main className="flex-grow overflow-y-scroll scrollbar-hide">
          {messages?.map((data) => {
            const { content, createdAt, accountId } = data;

            return (
              <Message
                key={Math.random()}
                id={accountId}
                message={content}
                timestamp={createdAt}
                photoURL={photoURL}
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
