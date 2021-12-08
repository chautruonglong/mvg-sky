import { HashtagIcon, ArrowCircleRightIcon } from "@heroicons/react/outline";
import Message from "./Message";
import {  useRef, useState } from "react";

export const Email = () => {
  const inputRef = useRef("");
  const chatRef = useRef(null);

    const scrollToBottom = () => {
      chatRef.current.scrollIntoView({
        behavior: "smooth",
        block: "start",
      })
    }
    
    return (
      <>
        <div className="flex flex-col h-screen">
            <header className="flex items-center justify-between space-x-5 border-b border-gray-800 p-4 -mt-1">
              <div className="flex items-center space-x-1">
                <HashtagIcon className="h-6 text-[#72767d]" />
                <h4 className="text-white font-semibold">Subject</h4>
                <input
                    type="text"
                    className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-sm"
                    ref={inputRef}
                />
              </div>
              <div className="flex space-x-3">
              </div>
            </header>
            <div className="flex items-center justify-between space-x-5 border-b border-gray-800 p-4 -mt-1">
              <div className="flex items-center space-x-1">
                <HashtagIcon className="h-6 text-[#72767d]" />
                <h4 className="text-white font-semibold">To</h4>
                <input
                    type="text"
                    className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-sm"
                    ref={inputRef}
                />
              </div>
              <div className="flex space-x-3"></div>
            </div>
            <main className="flex-grow overflow-y-scroll scrollbar-hide">
                <div ref={chatRef} style={{paddingTop: "20px"}} />
                <div className="flex items-center p-2.5 bg-[#40444b] mx-5 mb-7 rounded-lg">
                    {/* <ArrowCircleRightIcon className="icon mr-4" /> */}
                    <form className="flex-grow">
                    <textarea
                        type="text"
                        rows="10"
                        className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-sm"
                        ref={inputRef}
                    />
                    <button hidden type="submit" >
                        Send
                    </button>
                    </form>
                </div>
                <div className="flex items-center p-2.5 bg-[#40444b] mx-5 mb-7 rounded-lg">
                    <button className="text-white font-semibold">Send</button>     
                </div>
            </main>

        </div>
         
      </>
    )
}