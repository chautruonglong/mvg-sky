import { HashtagIcon, ChatIcon } from "@heroicons/react/outline";
import Message from "./Message";
import {  useRef, useState } from "react";

export const Chat = () => {
  const inputRef = useRef("");
  const chatRef = useRef(null);
 
  const [messages, setMessages] = useState([
    {
      data: {
        id: 123,
        message:'Hello!!',
        timestamp: '6:08:25 PM',
        name: 'Khánh Toàn',
        email: 'khanhtoan@gmail.com',
        photoURL: 'https://cdn3.vectorstock.com/i/1000x1000/38/17/male-face-avatar-logo-template-pictograph-vector-11333817.jpg'
      }
    },
    {
      data: {
        id: 123,
        message:'Test ui chat.',
        timestamp: '9:08:25 AM',
        name: 'Khánh Toàn',
        email: 'khanhtoan@gmail.com',
        photoURL: 'https://cdn3.vectorstock.com/i/1000x1000/38/17/male-face-avatar-logo-template-pictograph-vector-11333817.jpg'
      }
    },
    {
      data: {
        id: 123,
        message:'Reply message demo @@',
        timestamp: '9:10:25 AM',
        name: 'Phước Quốc',
        email: 'phuocquoc@gmail.com',
        photoURL: 'https://c8.alamy.com/comp/K4AFFG/user-icon-male-avatar-in-business-suit-businessman-flat-icon-man-in-K4AFFG.jpg'
      }
    }
  ])

    const scrollToBottom = () => {
      chatRef.current.scrollIntoView({
        behavior: "smooth",
        block: "start",
      })
    }

    const handleSendMessage = (e) => { 
      e.preventDefault()

      if(inputRef.current.value !== "") {
        setMessages([
          ...messages,
          {
            data: {
              id: 123,
              message: inputRef.current.value ,
              timestamp: '9:08:25 AM',
              name: 'Khánh Toàn',
              email: 'khanhtoan@gmail.com',
              photoURL: 'https://cdn3.vectorstock.com/i/1000x1000/38/17/male-face-avatar-logo-template-pictograph-vector-11333817.jpg'
            }
          }
        ])
      }

      inputRef.current.value = ""
      scrollToBottom()
    }
    
    return (
      <>
        <div className="flex flex-col h-screen">
            <header className="flex items-center justify-between space-x-5 border-b border-gray-800 p-4 -mt-1">
              <div className="flex items-center space-x-1">
                <HashtagIcon className="h-6 text-[#72767d]" />
                <h4 className="text-white font-semibold">Phước Quốc</h4>
              </div>
              <div className="flex space-x-3">
              </div>
            </header>
            <main className="flex-grow overflow-y-scroll scrollbar-hide">
        {messages?.map((doc) => {
          const { message, timestamp, name, photoURL, email } = doc.data;

          return (
            <Message
              key={doc.id}
              id={doc.id}
              message={message}
              timestamp={timestamp}
              name={name}
              email={email}
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
          <button hidden type="submit" onClick={e=> handleSendMessage(e)}>
            Send
          </button>
        </form>
      </div>
          </div>
         
      </>
    )
}