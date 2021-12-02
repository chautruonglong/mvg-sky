import { HashtagIcon } from "@heroicons/react/outline";
import { useState } from "react";
// import { useDispatch } from "react-redux";
// import { useHistory } from "react-router-dom";
// import { setChannelInfo } from "../features/channelSlice";

function Channel({ id, channelName, setSubChannel }) {
  // const dispatch = useDispatch();
  // const history = useHistory();
  const [channel, setChannel] = useState('')

  const HandleOnClick = () => {
    console.log(Math.random());
    if(id === 1234){
      setSubChannel(2)
    } else {
      setSubChannel(1)
    }
  }
  // const setChannel = () => {
  //   dispatch(
  //     setChannelInfo({
  //       channelId: id,
  //       channelName: channelName,
  //     })
  //   );

  //   history.push(`/channels/${id}`);
  // };

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
