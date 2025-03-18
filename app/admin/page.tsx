"use client";

import AdminUserItem from "@/components/ui/AdminUserItem";
import { safeAxios } from "@/services/axiosInstance";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function BanUnbanUsers() {
  const axiosInstance = safeAxios();

  const router = useRouter();

  const [searchInput, setSearchInput] = useState("");
  const [usersList, setUsersList] = useState<number[]>([]);

  const handleSearch = () => {
    const usersUrl = "http://localhost:8079/users/getIdsBySubusername";

    axiosInstance
      .get(usersUrl, {
        params: { username: searchInput },
      })
      .then((response) => {
        setUsersList(response.data);
      });
  };

  useEffect(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === "Escape") {
        router.back();
      }
    };

    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, []);

  return (
    <div className="flex flex-col items-center justify-start min-h-screen bg-gray-100 p-4">
      <div className="w-full fixed top-0 left-0 bg-gray-100 p-4 z-10">
        <div className="flex gap-2 justify-center">
          <input
            type="text"
            placeholder="Search..."
            value={searchInput}
            onChange={(e) => setSearchInput(e.target.value)}
            className="border border-gray-300 p-2 rounded-lg w-64"
          />
          <button
            onClick={handleSearch}
            className="bg-blue-500 text-white px-4 py-2 rounded-lg"
          >
            Search
          </button>
        </div>
      </div>

      <div
        className="w-full mt-24 max-h-[calc(100vh-96px)] overflow-auto p-4"
        style={{ marginTop: "96px" }}
      >
        {usersList.length > 0 ? (
          usersList.map((item, index) => (
            <AdminUserItem key={index} userId={item} />
          ))
        ) : (
          <p>Users not found :(</p>
        )}
      </div>
    </div>
  );
}
