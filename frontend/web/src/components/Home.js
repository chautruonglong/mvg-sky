import { ChevronDownIcon, PlusIcon } from "@heroicons/react/outline";
import Channel from "../components/Channel";
import { Chat } from "./Chat";
import { useEffect, useState } from "react";
import { Profile } from "./Profile";
import { Email } from "./Email";
import { Password } from "./Password";
import { useHistory, useParams } from "react-router";
import axios from "axios";
import { Blank } from "./Blank";
import Popup from "reactjs-popup";
import { CreateChannel } from "./createChannel/CreateChannel";
import "./createChannel/createChannel.css";
import { CreateEmail } from "./createEmail/CreateEmail";
import MailChannel from "./MailChannel";

export const Home = ({
  status,
  newMessage,
  roomId,
  setRoomId,
  accountId,
  setnewMessage
}) => {
  const [subChannel, setSubChannel] = useState(1);
  const [channels, setChannels] = useState([]);
  const history = useHistory();
  const { id } = useParams();
  const { channelId, setChannelId } = useState(id);
  const [title, setTitle] = useState();
  const [emailContent, setEmailContent] = useState()
  const [accountList, setAccountList] = useState();

  useEffect(() => {
    const fetchdata = async () => {
      if (status === "chat") {
        const fetchChannels = async () => {
          var config = {
            method: "get",
            url: `http://api.mvg-sky.com/api/rooms?accountId=${accountId}`,
            headers: {},
          };

          const response = await axios(config);
          return response.data;
        };

        const result = await fetchChannels();
        setChannels(result);
      }

      if(status === 'email'){
        const fetchChannels = async () => {
          var config = {
            method: 'get',
            url: `http://api.mvg-sky.com/api/mails?accountId=${accountId}&mailbox=inbox`,
            headers: { }
          };
          

          const response = await axios(config);
          return response.data;
        };

        const result = await fetchChannels();
        setChannels(result);
      }

      if (status === "profile") {
        setChannels([
          {
            id: accountId,
            name: "Profile",
          },
        ]);
      }

      if (status === "password") {
        setChannels([
          {
            id: accountId,
            name: "Change Password",
          },
        ]);
      }
    };

    fetchdata();
  }, [status, id]);

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    history.push("/");
  };

  const HandleChannel = () => {
    if(status !== 'email'){
      return channels?.map((channel) => (
        <Channel
          accountId={accountId}
          id={channel.id}
          status={status}
          channelName={channel.name}
          setSubChannel={setSubChannel}
          setRoomId={setRoomId}
          setTitle={setTitle}
        />
      ))
    } else {
      return channels?.map((channel) => (
        <MailChannel
          setEmailContent={setEmailContent}
          mail={channel}
        />
      ))
    }
       
    } 
  
  return (
    <>
      <div className="flex h-screen">
        <div className="flex flex-col space-y-3 bg-[#202225] p-3 min-w-max">
          <div className="server-default hover:bg-app_white">
            <img
              src="https://scontent.fdad1-3.fna.fbcdn.net/v/t1.15752-9/259344100_221240166797176_7169315392320620239_n.png?_nc_cat=111&ccb=1-5&_nc_sid=ae9488&_nc_ohc=uqWKb3XP9vsAX-pz-2v&_nc_ht=scontent.fdad1-3.fna&oh=03_AVJEzFiTUqOxGEj1C1uBJx8J7D5VIIf2A3KlRzrGJpSmsw&oe=61EC2F47"
              alt=""
              className="h-12 cursor-pointer rounded-full transition-all duration-100 ease-out hover:rounded-2xl"
            />
          </div>
          <hr className=" border-gray-700 border w-8 mx-auto" />
          <div className="server-default hover:bg-app_white">
            <img
              src="https://www.nicepng.com/png/full/128-1284530_chat-icon-png-white-chat-icon-white-png.png"
              alt=""
              className="h-7 w-9"
              onClick={() => history.push("/channels/chat")}
            />
          </div>
          <div className="server-default hover:bg-app_white">
            <img
              src="https://roeleke.com/wp-content/uploads/2019/06/pngkey.com-email-icon-white-png-9311379.png"
              alt=""
              className="h-7 w-9"
              onClick={() => history.push("/channels/email")}
            />
          </div>
          <div className="server-default hover:bg-app_white ">
            <img
              src="https://previews.123rf.com/images/asmati/asmati1706/asmati170606229/80944205-user-avatar-illustration-anonymous-sign-vector-white-icon-with-soft-shadow-on-transparent-background.jpg"
              alt=""
              className="h-9 w-9 rounded-full"
              onClick={() => history.push("/channels/profile")}
            />
          </div>
          <div className="server-default hover:bg-app_white ">
            <img
              src="https://previews.123rf.com/images/fokaspokas/fokaspokas1803/fokaspokas180300213/96827442-key-icon-white-icon-with-shadow-on-transparent-background.jpg"
              alt=""
              className="h-9 w-9 rounded-full"
              onClick={() => history.push("/channels/change-password")}
            />
          </div>
          <div className="server-default hover:bg-app_white ">
            <img
              src="https://us.123rf.com/450wm/fokaspokas/fokaspokas1803/fokaspokas180300425/96962878-shut-down-power-white-icon-with-shadow-on-transparent-background.jpg?ver=6"
              alt=""
              className="h-9 w-9 rounded-full"
              onClick={() => handleLogout()}
            />
          </div>
        </div>

        <div className=" w-1/5 bg-[#2f3136] flex flex-col ">
          <h2 className="flex text-white font-bold text-sm items-center justify-between border-b border-gray-800 p-4 hover:bg-[#34373C] cursor-pointer ">
            WORKPLACE MESSENGER
          </h2>
          <div className="text-[#8e9297] flex-grow overflow-y-scroll scrollbar-hide">
            <div className="flex items-center p-2 mb-2">
              <ChevronDownIcon className="h-3  mr-2" />
              <h4 className="font-semibold ">Channels</h4>

              <Popup
                modal
                trigger={
                  <PlusIcon className="h-6 ml-auto cursor-pointer hover:text-white" />
                }
              >
                {(close) => (
                  status === 'chat' ? (<CreateChannel
                    accountId={accountId}
                    close={close}
                    channels={channels}
                    setChannels={setChannels}
                    setnewMessage={setnewMessage}
                  />) : (status === 'email') ? (<CreateEmail
                    accountId={accountId}
                    close={close}
                    channels={channels}
                    setChannels={setChannels}
                    setnewMessage={setnewMessage}
                  />) : (<></>)
                )}
              </Popup>
            </div>
            <div className="flex flex-col space-y-2 px-2 mb-4">
              {
                HandleChannel()
              // channels?.map((channel) => (
              //   <Channel
              //     accountId={accountId}
              //     id={channel.id}
              //     status={status}
              //     channelName={channel.name}
              //     setSubChannel={setSubChannel}
              //     setRoomId={setRoomId}
              //     setTitle={setTitle}
              //   />
              // ))
            }
            </div>
          </div>
        </div>
        <div className="bg-[#36393f] flex-grow">
          {
            status === "chat" ? (
              <Chat roomId={roomId} newMessage={newMessage} title={title} accountId={accountId} accountList={accountList}/>
            ) : status === "profile" ? (
              <Profile accountId={accountId} />
            ) : status === "email" ? (
              <Email  accountId={accountId} content={emailContent}/>
            ) : status === "password" ? (
              <Password id={channelId} accountId={accountId} />
            ) : (
              <Blank />
            )
          }
        </div>
      </div>
    </>
  );
};
