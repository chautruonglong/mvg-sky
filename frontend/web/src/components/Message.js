import { TrashIcon } from "@heroicons/react/solid";
import moment from "moment";


function Message({ id, message, timestamp}) {
  let name 
  let photoURL

  if(id === '5d0d018d-bee1-4533-aed8-41a980792ebc'){
    name = 'Khanh Toan'
    photoURL = 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSWbS3I9NbSTEsVOomPr66VVL38-x1RLajLZQ&usqp=CAU'
  } else {
    name = 'Phuoc Quoc'
    photoURL = 'http://api.mvg-sky.com/api/accounts-resources/avatar/5f4e198d-eb70-40e8-80c8-bc4037766c86.jpg'
  }
  return (
    <div className="flex items-center p-1 pl-5 my-5 mr-2 hover:bg-[#32353B] group">
      <img
        src={photoURL}
        alt=""
        className="h-10 rounded-full cursor-pointer mr-3 hover:shadow-2xl"
      />
      <div className="flex flex-col">
        <h4 className="flex items-center space-x-2 font-medium">
          <span className="hover:underline text-white text-sm cursor-pointer">
            {name}
          </span>
          <span className="text-[#72767d] text-xs">
            {timestamp}
          </span>
        </h4>
        <p className="text-sm text-[#dcddde]">{message}</p>
      </div>
      {/* {user?.email === email && (
        <div
          className=" hover:bg-[#ed4245] p-1 ml-auto rounded-sm text-[#ed4245] hover:text-white cursor-pointer"
          onClick={() =>
            db
              .collection("channels")
              .doc(channelId)
              .collection("messages")
              .doc(id)
              .delete()
          }
        >
          <TrashIcon className="h-5 hidden group-hover:inline" />
        </div>
      )} */}
    </div>
  );
}

export default Message;
