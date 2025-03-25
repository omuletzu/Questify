"use client";

import { safeAxios } from "@/services/axiosInstance";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";

interface UserItemProp {
  userId: number;
}

const AdminUserItem: React.FC<UserItemProp> = ({ userId }) => {
  const axiosInstance = safeAxios();

  const [username, setUsername] = useState("");
  const [isBanned, setIsBanned] = useState(false);

  const handleBanUser = () => {
    const urlBan = "http://localhost:8079/users/banUser";
    const data = {
      userId: userId,
    };

    axiosInstance
      .put(urlBan, data)
      .then((response) => {
        toast.success("User banned");
        setIsBanned(true);
      })
      .catch((err) => {
        toast.error("Failed to ban user");
      });
  };

  const handleUnbanUser = () => {
    const urlUnban = "http://localhost:8079/users/unbanUser";
    const data = {
      userId: userId,
    };

    axiosInstance
      .put(urlUnban, data)
      .then((response) => {
        toast.success("User unbanned");
        setIsBanned(false);
      })
      .catch((err) => {
        toast.error("Failed to unban user");
      });
  };

  useEffect(() => {
    const urlUsername = "http://localhost:8079/users/get";

    axiosInstance
      .get(urlUsername, {
        params: { userId: userId },
      })
      .then((response) => {
        setUsername(response.data.username);
        setIsBanned(response.data.banned);
      });
  }, [userId]);

  return (
    <div>
      <div className="flex justify-between items-center p-4 border-b">
        <div>
          <p>User ID: {userId}</p>
          <p>Username: {username}</p>
        </div>
        <button
          onClick={isBanned ? handleUnbanUser : handleBanUser}
          className={`px-4 py-2 rounded ${
            isBanned ? "bg-red-600 text-white" : "bg-green-600 text-white"
          }`}
        >
          {isBanned ? "Unban" : "Ban"}
        </button>
      </div>
    </div>
  );
};

export default AdminUserItem;
