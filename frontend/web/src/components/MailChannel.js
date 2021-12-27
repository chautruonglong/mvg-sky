import { HashtagIcon } from "@heroicons/react/outline";
import { useState } from "react";
import { useHistory } from "react-router-dom";
import Popup from "reactjs-popup";

function MailChannel({ setEmailContent, mail }) {
  const history = useHistory();
  const [channel, setChannel] = useState("");

  const HandleOnClick = () => {
      setEmailContent(mail)
    history.push(`/channels/email/${mail.id}`);
  };

  return (
    <div
      className="font-medium flex items-center cursor-pointer hover:bg-[#3A3C43] p-1 rounded-md  hover:text-white"
      onClick={() => HandleOnClick()}
    >
      <HashtagIcon className="h-5 mr-2" /> {mail.subject}
    </div>
  );
}

export default MailChannel;
