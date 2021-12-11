
import { ChevronDownIcon, PlusIcon } from "@heroicons/react/outline"
import Channel from "../components/Channel";
import { Chat } from "./Chat";
import { useEffect, useState } from "react";
import { Profile } from  "./Profile"
import { Email } from  "./Email"
import { useHistory } from "react-router";

export const Home = ({status, newMessage}) => {
  const [subChannel, setSubChannel] = useState(1)
  const [channels, setChannels] = useState([])
  const history = useHistory()

  useEffect(()=>{
      if(status === 'chat'){
        setChannels([
          {
            id: 123,
            channelName:'Khánh Toàn'
          },
          {
            id: 234,
            channelName:'Phước QuốC'
          },
          {
            id: 456,
            channelName:'Trường Long'
          },
        ])
      }
    
      if(status === 'profile'){
        setChannels([ 
          {
            id: 1234,
            channelName:'Profile'
          },
          {
            id: 123,
            channelName:'Change Password'
          }
        ])
      } 

      if(status === 'email'){
        setChannels([ 
          {
            id: 1234,
            channelName:'khanhtoanmail'
          },
          {
            id: 123,
            channelName:'testmail'
          }
        ])
      } 
   
  },[status])
  
  const mockData = [ 
    {
      id: 123,
      channelName:'Khánh Toàn'
    },
    {
      id: 234,
      channelName:'Phước QuốC'
    },
    {
      id: 456,
      channelName:'Trường Long'
    },
]

    return (
        <>
        <div className="flex h-screen">
        <div className="flex flex-col space-y-3 bg-[#202225] p-3 min-w-max">
          <div className="server-default hover:bg-app_white">
            <img src="https://scontent.xx.fbcdn.net/v/t1.15752-9/p206x206/259344100_221240166797176_7169315392320620239_n.png?_nc_cat=111&ccb=1-5&_nc_sid=aee45a&_nc_ohc=zqPNQwwlklYAX9X-JZH&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=5530f23c2869d704388b1f8249a048f8&oe=61C022C4" alt="" className="h-12 cursor-pointer rounded-full transition-all duration-100 ease-out hover:rounded-2xl" />
          </div>
          <hr className=" border-gray-700 border w-8 mx-auto" />
          <div className="server-default hover:bg-app_white">
            <img src="https://www.nicepng.com/png/full/128-1284530_chat-icon-png-white-chat-icon-white-png.png" alt="" className="h-7 w-9" onClick={()=> history.push('/channels/chat')}/>
          </div>
          <div className="server-default hover:bg-app_white">
            <img src="https://roeleke.com/wp-content/uploads/2019/06/pngkey.com-email-icon-white-png-9311379.png" alt="" className="h-7 w-9"
             onClick={()=> history.push('/channels/email')} />
          </div>
          <div className="server-default hover:bg-app_white ">
            <img src="https://cdn3.vectorstock.com/i/1000x1000/38/17/male-face-avatar-logo-template-pictograph-vector-11333817.jpg" alt="" className="h-9 w-9 rounded-full" 
            onClick={()=> history.push('/channels/profile')}/>
          </div>
          <div className="server-default hover:bg-app_white ">
            <img src="https://scontent.fdad1-3.fna.fbcdn.net/v/t1.15752-9/263177761_4496565693754875_1252249884928258089_n.png?_nc_cat=104&cb=c578a115-c1c39920&ccb=1-5&_nc_sid=ae9488&_nc_ohc=aUaqbzVrzNwAX9RSqvK&tn=yBHw_zearwumtjmw&_nc_ht=scontent.fdad1-3.fna&oh=7578b3e9ac382e691a599e17dbc4d4e1&oe=61D6F022" alt="" className="h-9 w-9 rounded-full" 
            onClick={()=> history.push('/channels/logout')}/>
          </div>
        </div>
        

        <div className=" w-1/5 bg-[#2f3136] flex flex-col ">
          <h2 className="flex text-white font-bold text-sm items-center justify-between border-b border-gray-800 p-4 hover:bg-[#34373C] cursor-pointer ">
            WORKPLACE MESSENGER
          </h2>
          <div className="text-[#8e9297] flex-grow overflow-y-scroll scrollbar-hide">
            <div className="flex items-center p-2 mb-2">
              <ChevronDownIcon className="h-3  mr-2" />
              <h4 className="font-semibold ">Chats</h4>
              <PlusIcon
                className="h-6 ml-auto cursor-pointer hover:text-white"
              />
            </div>
            <div className="flex flex-col space-y-2 px-2 mb-4">
              {
                channels?.map(channel => (
                  <Channel  
                  id={channel.id}
                  status={status}
                  channelName={channel.channelName}
                  setSubChannel={setSubChannel}
                />
                ))
              }
            </div>
          </div>
          
        </div>
        <div className="bg-[#36393f] flex-grow">
          {
            // subChannel === 1 ? ( <Chat /> ) : (subChannel === 2 ? (<Profile />) : (<Email />) )       
            (<Chat newMessage={newMessage}/>)
            
          }   
        </div>
      </div>
      </>
    )
}