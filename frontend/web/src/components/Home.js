import { ServerIcon } from "./ServerIcon"
import { ChevronDownIcon, PlusIcon } from "@heroicons/react/outline"
import Channel from "../components/Channel";
import { Chat } from "./Chat";
// ../../public/logo.png
export const Home = () => {
    return (
        <>
        <div className="flex h-screen">
        <div className="flex flex-col space-y-3 bg-[#202225] p-3 min-w-max">
          <div className="server-default hover:bg-discord_purple">
            <img src="https://scontent.xx.fbcdn.net/v/t1.15752-9/p206x206/259344100_221240166797176_7169315392320620239_n.png?_nc_cat=111&ccb=1-5&_nc_sid=aee45a&_nc_ohc=zqPNQwwlklYAX9X-JZH&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=5530f23c2869d704388b1f8249a048f8&oe=61C022C4" alt="" className="h-12 cursor-pointer rounded-full transition-all duration-100 ease-out hover:rounded-2xl" />
          </div>
          <hr className=" border-gray-700 border w-8 mx-auto" />
          <div className="server-default hover:bg-discord_purple">
            <img src="https://cdn-icons-png.flaticon.com/512/2190/2190510.png" alt="" className="h-9 w-9" />
          </div>
          <div className="server-default hover:bg-discord_purple">
            <img src="https://cdn-icons-png.flaticon.com/512/855/855502.png" alt="" className="h-9 w-9" />
          </div>
        </div>

        <div className=" w-1/4 bg-[#2f3136] flex flex-col ">
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
            <Channel  
                id='123'
                channelName='chanel '
            />
            <Channel  
                id='234'
                channelName='chanel 2'
            />
            </div>
          </div>
          
        </div>
        <div className="bg-[#36393f] flex-grow">
          <Chat />
        </div>
      </div>
      </>
    )
}