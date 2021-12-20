import { TrashIcon } from "@heroicons/react/solid";
import moment from "moment";


function Message({ id, message, timestamp}) {
  let name 
  let photoURL
  function getText(html){
    var divContainer= document.createElement("div");
    divContainer.innerHTML = html;
    return divContainer.textContent || divContainer.innerText || "";
}


  if(id === '5d0d018d-bee1-4533-aed8-41a980792ebc'){
    name = 'Toan'
    photoURL = 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSWbS3I9NbSTEsVOomPr66VVL38-x1RLajLZQ&usqp=CAU'
  } else {
    name = 'quoc'
    photoURL = 'https://scontent.fdad1-3.fna.fbcdn.net/v/t39.30808-6/217967515_1940851459422042_4184009242946268958_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=AS3ygPEi-eoAX_Yfrcr&tn=yBHw_zearwumtjmw&_nc_ht=scontent.fdad1-3.fna&oh=00_AT_h5ARWCgjA9zFG6ouVGYVEZVDxmQt8QAB2TFKk89NxyQ&oe=61C3ACA0'
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
        <p className="text-sm text-[#dcddde]">{getText(message)}</p>
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
