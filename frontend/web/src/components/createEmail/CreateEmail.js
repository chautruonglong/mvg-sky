import "./createEmail.css";
import axios from "axios";
import { useState } from "react/cjs/react.development";
import { toastError, toastSuccess } from "../Toast";
import FormData, { getHeaders } from "form-data";

export const CreateEmail = ({ close, accountId }) => {
  const domainIds = "f3411bb7-5f85-489a-b533-8a2be4002277";
  const [to, setTo] = useState();
  const [subject, setSubject] = useState();
  const [body, setBody] = useState();

  const handleBtnClick = async () => {
    var data = new FormData();
    data.append("accountId", accountId);
    data.append("to", to);
    data.append("subject", subject);
    data.append("body", body);

    var config = {
      method: "post",
      url: "http://api.mvg-sky.com/api/mails/send",
      headers: { "Content-type": "multipart/form-data" },
      data: data,
    };

    try {
      await axios(config);
      toastSuccess("Email successfully sended.");
      close();
    } catch (error) {
      console.log("create channel unsuccessfully:", error);
      toastError("Email unsuccessfully created.");
    }
  };

  return (
    <div className="modal">
      <a className="close" onClick={close} style={{ color: "#FFFFFF" }}>
        &times;
      </a>
      <div className="header" style={{ color: "#FFFFFF" }}>
        Create Email
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
            To
          </label>
          <div className="input_field">
            <input
              type="text"
              style={{ width: "300px" }}
              className="input"
              id="input_text"
              onChange={(e) => setTo(e.target.value)}
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
            Subject
          </label>
          <div className="input_field">
            <input
              type="text"
              style={{ width: "300px" }}
              className="input"
              id="input_text"
              onChange={(e) => setSubject(e.target.value)}
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
            body
          </label>
          <div className="input_field">
            {/* <input
              type="text"
              className="input"
              id="input_text"
              onChange={(e) => setBody(e.target.value)}
            /> */}
            <textarea
              type="text"
              rows="5"
              style={{ width: "300px" }}
              className="input"
              onChange={(e) => setBody(e.target.value)}
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
          Send
        </button>
      </div>
    </div>
  );
};
