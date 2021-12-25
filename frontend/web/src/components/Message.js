import axios from "axios";

function Message({ id, message, timestamp, type }) {
  let name;
  function getText(html) {
    var divContainer = document.createElement("div");
    divContainer.innerHTML = html;
    return divContainer.textContent || divContainer.innerText || "";
  }

  if(id == '097647f6-8b4e-4d4c-9ef3-2f037c8f8e4c'){
    name = 'Toan Khanh Huynh'
  } else {
    name = 'Quoc Nguyen'
  }

  const showMessage = () => {
    if(type === 'TEXT'){
      return ( <p className="text-sm text-[#dcddde]">{getText(message)}</p>)
    }

    const detectType = message.indexOf('.txt') !== -1
    if(detectType){
      const nameFile = decodeURI(message.split('/')[message.split('/').length - 1])
      return (<a href={`http://api.mvg-sky.com${message}`} className="text-white">{nameFile}</a>)
    }  else {
      return ( <img src={`http://api.mvg-sky.com${message}`} alt="" />)
    }

  }
  
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
        {
          showMessage()
        }
      </div>
    </div>
  );
}

export default Message;
