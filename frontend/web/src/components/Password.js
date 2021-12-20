import { ArrowRightIcon, HashtagIcon } from "@heroicons/react/outline";
import { useState } from "react";
import { useEffect } from "react/cjs/react.development";
import axios from "axios";
import { toast } from "react-toastify";
import { toastError, toastSuccess } from "./Toast";

export const Password = ({ subChannel, accountId }) => {
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const handleClickUpdate = async () => {
    var data = JSON.stringify({
      "password": "Admin@1234",
      "isActive": true,
      "roles": [
        "EMPLOYEE"
      ]
    });
    
    var config = {
      method: 'patch',
      url: 'http://api.mvg-sky.com/api/accounts/5d0d018d-bee1-4533-aed8-41a980792ebc',
      headers: { 
        'Content-Type': 'application/json'
      },
      data : data
    };

    if(newPassword !== confirmPassword){
      toastError("Confirm password is not correct.");
    } else {
      try {
        await axios(config);
        toastSuccess("Password successfully updated.");
      } catch (error) {
        toastError("Password unsuccessfully updated.");
      }
    }
    
  };

  return (
    <>
      <div className="flex flex-col h-screen">
        <header className="flex items-center justify-between space-x-5 border-b border-gray-800 p-4 -mt-1">
          <div className="flex items-center space-x-1">
            <HashtagIcon className="h-6 text-[#72767d]" />
            <h4 className="text-white font-semibold">CHANGE PASSWORD</h4>
          </div>
        </header>
        <main className="flex-grow overflow-y-scroll scrollbar-hide p-1">
          <div
            className=""
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <img
              src="https://cdn-icons-png.flaticon.com/512/3427/3427189.png"
              alt=""
              className="rounded-full"
              width="150"
              height="150"
            />
          </div>
          <div
            className="p-3"
            style={{
              display: "flex",
              flexDirection: "column",
              justifyContent: "center",
              paddingLeft: "300px",
            }}
          >
            <div style={{ display: "flex", justifyContent: "space-between" }}>
              <h4 className="text-white" style={{ width: "190px" }}>
                New Password:
              </h4>
              <input
                type="password"
                value={newPassword}
                className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                onChange={(e) => setNewPassword(e.target.value)}
              />
            </div>
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                paddingTop: "10px",
              }}
            >
              <h4 className="text-white" style={{ width: "190px" }}>
                Confirm Password:
              </h4>
              <input
                type="password"
                value={confirmPassword}
                className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
            </div>
            <div
              style={{
                display: "flex",
                paddingTop: "10px",
              }}
            >
              <button
                type="button"
                className="text-white"
                style={{ backgroundColor: "#808080", padding: "5px" }}
                onClick={async () => await handleClickUpdate()}
              >
                Update{" "}
              </button>
            </div>
          </div>
        </main>
      </div>
    </>
  );
};
