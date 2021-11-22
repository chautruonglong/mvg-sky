import { EmojiHappyIcon, GiftIcon, HashtagIcon, PlusCircleIcon, SearchIcon } from "@heroicons/react/outline";
import {
  BellIcon,
  ChatIcon,
  UsersIcon,
  InboxIcon,
  QuestionMarkCircleIcon,

} from "@heroicons/react/solid";
import Message from "./Message";
import {  useRef } from "react";

export const Chat = () => {
  const inputRef = useRef("");
  const chatRef = useRef(null);
    return (
      <>
        <div className="flex flex-col h-screen">
            <header className="flex items-center justify-between space-x-5 border-b border-gray-800 p-4 -mt-1">
              <div className="flex items-center space-x-1">
                <HashtagIcon className="h-6 text-[#72767d]" />
                <h4 className="text-white font-semibold">CHAT</h4>
              </div>
              <div className="flex space-x-3">
                <BellIcon className="icon" />
                <ChatIcon className="icon" />
                <UsersIcon className="icon" />
                <InboxIcon className="icon" />
                <QuestionMarkCircleIcon className="icon" />
              </div>
            </header>
            <main className="flex-grow overflow-y-scroll scrollbar-hide">
        {/* {messages?.docs.map((doc) => {
          const { message, timestamp, name, photoURL, email } = doc.data();

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
        })} */}
        {/* <div ref={chatRef} className="pb-16" /> */}
      </main>
      <div className="flex items-center p-2.5 bg-[#40444b] mx-5 mb-7 rounded-lg">
        <PlusCircleIcon className="icon mr-4" />
        <form className="flex-grow">
          <input
            type="text"
            // disabled={!channelId}
            // placeholder={
            //   channelId ? `Message #${channelName}` : "Select a channel"
            // }
            className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-sm"
            ref={inputRef}
          />
          <button hidden type="submit" >
            Send
          </button>
        </form>
        <GiftIcon className="icon mr-2" />
        <EmojiHappyIcon className="icon" />
      </div>
          </div>
         
      </>
    )
}