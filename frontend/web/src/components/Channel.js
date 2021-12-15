import { HashtagIcon } from "@heroicons/react/outline";
import { useState } from "react";
import { useHistory } from "react-router-dom";

function Channel({ id, status, channelName, setSubChannel, setRoomId }) {
  const history = useHistory();
  const [channel, setChannel] = useState("");

  const HandleOnClick = () => {
    if (status === "profile") {
      setSubChannel(2);
    }

    if (status === "chat") {
      setSubChannel(1);
      setRoomId(id);
      
    }

    if (status === "email") {
      setSubChannel(3);
    }

    history.push(`/channels/${status}/${id}`);
  };

  return (
    <div
      className="font-medium flex items-center cursor-pointer hover:bg-[#3A3C43] p-1 rounded-md  hover:text-white"
      onClick={() => HandleOnClick()}
    >
      <HashtagIcon className="h-5 mr-2" /> {channelName}
    </div>
  );
}

export default Channel;
