import "./createChannel.css";
import axios from "axios";
import { useEffect } from "react";
import { useState } from "react/cjs/react.development";
import Select from "react-select";

export const CreateChannel = ({ close, accountId,notify }) => {
  const [channelName, setChannelName] = useState();
  const [userId, setUserId] = useState();
  const [options, setOptions] = useState();
  const domainIds = "f0e9ec3a-efe9-45f4-924b-4718445fd5ac";

  const fetchProfile = async () => {
    var config = {
      method: "get",
      url: `http://api.mvg-sky.com/api/profiles?accountId=${accountId}`,
      headers: {},
    };

    const response = await axios(config);
    return response.data[0].id;
  };

  const fetchContact = async () => {
    const profileId = await fetchProfile();

    var config = {
      method: "get",
      url: `http://api.mvg-sky.com/api/contacts?profileId=${profileId}&domainIds=${domainIds}`,
      headers: {},
    };

    const response = await axios(config);
    const data = response.data;

    const result = data.map((item) => {
      return { value: item.id, label: `${item.firstName} ${item.lastName}` };
    });
    setOptions(result);
  };

  useEffect(() => {
    fetchContact();
  }, []);

  const handleSelect = (value) => {
    setUserId(value);
  };

  const handleBtnClick = async () => {
    var data = JSON.stringify({
      name: channelName,
      description: channelName,
      type: "GROUP",
      accountIds: [accountId, userId.value],
    });

    var config = {
      method: "post",
      url: "http://api.mvg-sky.com/api/rooms",
      headers: {
        "Content-Type": "application/json",
      },
      data: data,
    };

    try {
      await axios(config);
      
    } catch (error) {
      console.log("create channel unsuccessfully");
    }
  };

  return (
    <div className="modal">
      <a className="close" onClick={close} style={{ color: "#FFFFFF" }}>
        &times;
      </a>
      <div className="header" style={{ color: "#FFFFFF" }}>
        Create Channel
      </div>
      <div className="content">
        <div
          className="input_wrap"
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            marginBottom: "20px",
          }}
        >
          <label
            htmlFor="input_text"
            style={{ color: "#FFFFFF", marginRight: "20px", width: "100px" }}
          >
            Contact User
          </label>
          <div className="input_field">
            <Select
              value={userId}
              onChange={(value) => handleSelect(value)}
              options={options}
            />
          </div>
        </div>
        <div
          className="input_wrap"
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            marginBottom: "20px",
          }}
        >
          <label
            htmlFor="input_text"
            style={{ color: "#FFFFFF", marginRight: "20px", width: "100px" }}
          >
            Channel Name
          </label>
          <div className="input_field">
            <input
              type="text"
              className="input"
              id="input_text"
              onChange={(e) => setChannelName(e.target.value)}
            />
          </div>
        </div>
      </div>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          marginBottom: "20px",
        }}
      >
        <button
          className="text-white"
          style={{
            color: "#FFFFFF",
            backgroundColor: "#808080",
            padding: "5px",
          }}
          onClick={async () => await handleBtnClick()}
        >
          Create
        </button>
      </div>
    </div>
  );
};
