import { ArrowRightIcon, HashtagIcon } from "@heroicons/react/outline";
import { useState } from "react";
import { useEffect } from "react/cjs/react.development";
import axios from "axios";
import { toast } from "react-toastify";
import { toastError, toastSuccess } from "./Toast";

export const Profile = ({ subChannel, accountId }) => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [title, setTitle] = useState("");
  const [mobile, setMobile] = useState("");
  const [birthday, setBirthday] = useState("");
  const [location, setLocation] = useState("");
  const [profileId, setProfileId] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      var config = {
        method: "get",
        url: `http://api.mvg-sky.com/api/profiles?accountId=${accountId}`,
        headers: {},
      };

      const values = await axios(config);
      const result = values.data[0];
      setFirstName(result.firstName);
      setLastName(result.lastName);
      setTitle(result.title);
      setMobile(result.mobile);
      setBirthday(result.birthday);
      setLocation(result.location);
      setProfileId(result.id);
    };

    fetchData();
  }, []);

  const handleClickUpdate = async () => {

    var data = JSON.stringify({
      firstName,
      lastName,
      title,
      mobile,
      birthday,
      location,
    });

    var config = {
      method: "patch",
      url: `http://api.mvg-sky.com/api/profiles/${profileId}`,
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
      },
      data: data,
    };

    try {
      await axios(config);
      toastSuccess("Profile successfully updated.")
    } catch (error) {
      toastError("Profile unsuccessfully updated.")
    }
  };

  return (
    <>
      <div className="flex flex-col h-screen">
        <header className="flex items-center justify-between space-x-5 border-b border-gray-800 p-4 -mt-1">
          <div className="flex items-center space-x-1">
            <HashtagIcon className="h-6 text-[#72767d]" />
            <h4 className="text-white font-semibold">PROFILE</h4>
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
              src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSWbS3I9NbSTEsVOomPr66VVL38-x1RLajLZQ&usqp=CAU"
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
              <h4 className="text-white" style={{ width: "170px" }}>
                First Name:
              </h4>
              <input
                type="text"
                value={firstName}
                className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                onChange={(e) => setFirstName(e.target.value)}
              />
            </div>
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                paddingTop: "10px",
              }}
            >
              <h4 className="text-white" style={{ width: "170px" }}>
                Last Name:
              </h4>
              <input
                type="text"
                value={lastName}
                className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                onChange={(e) => setLastName(e.target.value)}
              />
            </div>
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                paddingTop: "10px",
              }}
            >
              <h4 className="text-white" style={{ width: "170px" }}>
                Title:
              </h4>
              <input
                type="text"
                value={title}
                className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                onChange={(e) => setTitle(e.target.value)}
              />
            </div>
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                paddingTop: "10px",
              }}
            >
              <h4 className="text-white" style={{ width: "170px" }}>
                Date of Birth:
              </h4>
              <input
                type="text"
                value={birthday}
                className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                onChange={(e) => setBirthday(e.target.value)}
              />
            </div>
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                paddingTop: "10px",
              }}
            >
              <h4 className="text-white" style={{ width: "170px" }}>
                Mobile:
              </h4>
              <input
                type="text"
                value={mobile}
                className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                onChange={(e) => setMobile(e.target.value)}
              />
            </div>
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                paddingTop: "10px",
              }}
            >
              <h4 className="text-white" style={{ width: "170px" }}>
                Location:
              </h4>
              <input
                type="text"
                value={location}
                className="bg-transparent focus:outline-none text-[#dcddde] w-full placeholder-[#72767d] text-base"
                onChange={(e) => setLocation(e.target.value)}
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
