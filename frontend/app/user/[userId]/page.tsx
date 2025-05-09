"use client";

import { Post } from "@/components/ui/Post";
import { ScoreLabel } from "@/components/ui/Score";
import { safeAxios } from "@/services/axiosInstance";
import { Users } from "lucide-react";
import { useParams, useRouter, useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";
import { FaUser } from "react-icons/fa";
import {
  MdOutlineKeyboardDoubleArrowLeft,
  MdOutlineKeyboardDoubleArrowRight,
} from "react-icons/md";

export default function UserProfilePage() {
  const axiosInstance = safeAxios();

  const [username, setUsername] = useState("");
  const [mounted, setMounted] = useState(false);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [displayFiltered, setDisplayFiltered] = useState(false);
  const [questionListFiltered, setQuestionListFiltered] = useState<number[]>(
    []
  );
  const [isOpen, setIsOpen] = useState(true);
  const [questionListUnfiltered, setQuestionListUnfiltered] = useState<
    number[]
  >([]);
  const [isAdmin, setIsAdmin] = useState(false);
  const router = useRouter();
  const searchParams = useParams();
  const authorId = Number(searchParams.userId)

  useEffect(() => {
    setMounted(true);
    setUsername(localStorage.getItem("username") || "Username error");
    setIsAdmin(localStorage.getItem("isAdmin") === "true")
    fetchQuestionsByUserId();
  }, []);

  const fetchQuestionsByUserId = () => {
    const url = "http://localhost:8080/question/getQuestionsByUserId";
    const requestData = {
      userId: authorId,
    };

    axiosInstance
      .get(url, {
        params: requestData,
      })
      .then((response) => {
        const fetchedQuestions = response.data;

        setQuestionListUnfiltered(
          questionListUnfiltered.concat(fetchedQuestions)
        );
      })
      .catch((err) => { });
  };

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const updateQstatus = (qstatus: number) => { };
  const updateDeleteQuestion = (index: number) => {
    if (displayFiltered) {
      const newQuestionList = [...questionListFiltered];
      newQuestionList.splice(index, 1);
      setQuestionListFiltered(newQuestionList);
    } else {
      const newQuestionList = [...questionListUnfiltered];
      newQuestionList.splice(index, 1);
      setQuestionListUnfiltered(newQuestionList);
    }
  };

  return (
    <div className="flex flex-col h-screen">
      <header className="fixed top-0 left-0 right-0 z-50 bg-blue-300 shadow-md p-4 flex items-center justify-between">
        <div className="flex items-center space-x-2">
          <h1 className="text-2xl font-extrabold text-blue-900">Questify</h1>
        </div>

        <div className="flex items-center space-x-4">
          <div className="flex items-center space-x-1">
            <ScoreLabel />
          </div>

          <span className="text-sm font-medium text-gray-700">{username}</span>

          <div className="w-8 h-8 bg-gray-400 rounded-full flex items-center justify-center">
            <button
              onClick={toggleDropdown}
              className="flex items-center justify-center w-10 h-10 rounded-full hover:bg-gray-200 transition-all"
            >
              <FaUser size={24} className="text-gray-800" />
            </button>

            {isDropdownOpen && (
              <div className="absolute top-full right-0 mt-2 w-48 bg-white border border-gray-200 rounded-lg shadow-lg z-10">
                <ul className="py-2">
                  <li>
                    <a
                      href="#"
                      className="block px-4 py-2 text-gray-800 hover:bg-gray-200"
                      onClick={() => {
                        localStorage.setItem("jwtToken", "");
                        localStorage.setItem("username", "");
                        router.replace("/");
                      }}
                    >
                      Log out
                    </a>
                  </li>
                </ul>
              </div>
            )}
          </div>
        </div>
      </header>

      <div className="flex flex-1 mt-16">
        <aside
          className={`fixed left-0 h-full bg-gray-200 transition-all duration-300 ease-in-out ${isOpen ? "w-[300px]" : "w-[50px]"
            } overflow-y-auto`}
        >
          <button
            onClick={() => setIsOpen(!isOpen)}
            className="absolute top-4 right-4"
          >
            {isOpen && (
              <MdOutlineKeyboardDoubleArrowLeft className="text-2xl" />
            )}
            {!isOpen && (
              <MdOutlineKeyboardDoubleArrowRight className="text-2xl" />
            )}
          </button>

          {isOpen && (
            <div className="p-4">
              <h2 className="text-xl font-bold">Sidebar</h2>
              <ul className="mt-4 space-y-2">
                <li>
                  <a
                    href="#"
                    className="block p-2 hover:bg-gray-700"
                    onClick={() => {
                      //router.push("/home");
                    }}
                  >
                    Home
                  </a>
                </li>

                {isAdmin && (
                  <li>
                    <a
                      href="#"
                      className="block p-2 hover:bg-gray-700"
                      onClick={() => {
                        router.push("/admin");
                      }}
                    >
                      Ban / Unban users
                    </a>
                  </li>
                )}
              </ul>
            </div>
          )}
        </aside>

        <main
          className={`flex-1 p-6 overflow-y-auto transition-all duration-300 ease-in-out ${isOpen ? "ml-[300px]" : "ml-[50px]"
            }`}
        >
          <h1 className="font-bold text-2xl">Questions: </h1>
          <div className="mt-8">
            <div className="space-y-6">
              {displayFiltered
                ? questionListFiltered.map((item, index) => (
                  <Post
                    key={item}
                    id={item}
                    renderViewPostButton={true}
                    questionListIndex={index}
                    updateQstatus={updateQstatus}
                    updateDeleteQuestion={updateDeleteQuestion}
                  />
                ))
                : questionListUnfiltered.map((item, index) => (
                  <Post
                    key={item}
                    id={item}
                    renderViewPostButton={true}
                    questionListIndex={index}
                    updateQstatus={updateQstatus}
                    updateDeleteQuestion={updateDeleteQuestion}
                  />
                ))}
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}
