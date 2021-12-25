import axios from "axios";

function Message({ id, message, timestamp, type, accountList }) {
  let name;
  function getText(html) {
    var divContainer = document.createElement("div");
    divContainer.innerHTML = html;
    return divContainer.textContent || divContainer.innerText || "";
  }

  name = accountList?.find(ele => ele.accountId === id)
  
  return (
    <div className="flex items-center p-1 pl-5 my-5 mr-2 hover:bg-[#32353B] group">
      <img
        src={`http://api.mvg-sky.com/api/accounts-resources/avatar/${id}`}
        alt=""
        className="h-10 rounded-full cursor-pointer mr-3 hover:shadow-2xl"
      />
      <div className="flex flex-col">
        <h4 className="flex items-center space-x-2 font-medium">
          <span className="hover:underline text-white text-sm cursor-pointer">
            {name}
          </span>
          <span className="text-[#72767d] text-xs">{timestamp}</span>
        </h4>
        {type === "TEXT" ? (
          <p className="text-sm text-[#dcddde]">{getText(message)}</p>
        ) : (
          <img src={`http://api.mvg-sky.com${message}`} alt="" />
        )}
      </div>
    </div>
  );
}

export default Message;
