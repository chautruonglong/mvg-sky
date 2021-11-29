import { EmojiHappyIcon, GiftIcon, HashtagIcon, PlusCircleIcon, SearchIcon } from "@heroicons/react/outline";
import {
  BellIcon,
  ChatIcon,
  UsersIcon,
  InboxIcon,
  QuestionMarkCircleIcon,

} from "@heroicons/react/solid";
import Message from "./Message";
import {  useRef, useState } from "react";

export const Profile = () => {
  const inputRef = useRef("");
  const time = '10/12/2000'
  const chatRef = useRef(null);
  const [user, setuser] = useState(
    {
        id: 123,
        message:'Hello!!',
        timestamp: '6:08:25 PM',
        name: 'Khánh Toàn',
        email: 'khanhtoan@gmail.com',
        photoURL: 'https://cdn3.vectorstock.com/i/1000x1000/38/17/male-face-avatar-logo-template-pictograph-vector-11333817.jpg'
    }
  )
    return (
      <>
        <div className="flex flex-col h-screen">
            <header className="flex items-center justify-between space-x-5 border-b border-gray-800 p-4 -mt-1">
              <div className="flex items-center space-x-1">
                <HashtagIcon className="h-6 text-[#72767d]" />
                <h4 className="text-white font-semibold">PROFILE</h4>
              </div>
            </header>
            <main className="flex-grow overflow-y-scroll scrollbar-hide p-1">
                <div className="" style={{display: "flex", alignItems: "center",justifyContent: "center"}}>
                    <img src="https://cdn3.vectorstock.com/i/1000x1000/38/17/male-face-avatar-logo-template-pictograph-vector-11333817.jpg" alt="" className="rounded-full" width="150" height="150"/> 
                </div>
                <div className="p-3" style={{display: "flex",flexDirection: "column",justifyContent: "center", paddingLeft: "450px"}}>
                    <div style={{ display: "flex", justifyContent: "space-between"}}>
                        <h4 className="text-white ">FullName:</h4> 
                        <input
                        type="text"
                        value="    Huynh Tran Khanh Toan"
                        className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                        />
                    </div>
                    <div style={{ display: "flex", justifyContent: "space-between", paddingTop: "10px"}}>
                        <h4 className="text-white ">Email:</h4> 
                        <input
                        type="text"
                        value="          khanhtoan.dng@gmail.com"
                        className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                        />
                    </div> 
                    <div style={{ display: "flex", justifyContent: "space-between", paddingTop: "10px"}}>
                        <h4 className="text-white">Date of Birth:</h4> 
                        <input
                        type="text"
                        value="  19/01/2000"
                        className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                        />
                    </div> 
                    <div style={{ display: "flex", justifyContent: "space-between", paddingTop: "10px"}}>
                        <h4 className="text-white">Role:</h4> 
                        <input
                        type="text"
                        value="             Employee"
                        className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                        />
                    </div>  
                </div>
            </main>
        </div>
         
      </>
    )
}