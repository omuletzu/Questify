"use client";

import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Post } from "@/components/ui/Post";
import { ScoreLabel } from "@/components/ui/Score";
import { useTheme } from "next-themes";
import { useEffect, useState } from "react";
import { AiOutlineSearch } from "react-icons/ai";
import { FaUser } from "react-icons/fa";
import {
  MdOutlineKeyboardDoubleArrowLeft,
  MdOutlineKeyboardDoubleArrowRight,
} from "react-icons/md";
import { Checkbox, FormControlLabel } from "@mui/material";
import { CiImageOn } from "react-icons/ci";
import { AddPostButton } from "@/components/ui/AddPostButton";
import { SearchPostsBar } from "@/components/ui/SearchPostsBar";
import { FilterPostsButton } from "@/components/ui/FilterPostsButton";
import axios from "axios";
import { title } from "process";
import { safeAxios } from "@/services/axiosInstance";
import { useRouter } from "next/navigation";

interface PostProps {
  userId: number;
  userScore: number;
}

export default function ForumPage({ userId, userScore }: PostProps) {
  const axiosInstance = safeAxios();

  const router = useRouter();

  const [isOpen, setIsOpen] = useState(true);
  const { theme, setTheme } = useTheme();

  const [mounted, setMounted] = useState(false);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const [username, setUsername] = useState("");
  const [pageIndex, setPageIndex] = useState(0);
  const [questionListFiltered, setQuestionListFiltered] = useState<number[]>(
    []
  );
  const [questionListUnfiltered, setQuestionListUnfiltered] = useState<
    number[]
  >([]);

  const [searchBarText, setSearchBarText] = useState("");

  const [displayFiltered, setDisplayFiltered] = useState(false);

  const setSearchText = (text: string) => {
    setSearchBarText(text);
  };

  const updateQstatus = (qstatus: number) => {};

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

  const updateQuestionList = (list: number[]) => {
    setQuestionListFiltered(list);
    setDisplayFiltered(true);
  };

  const fetchPosts = async () => {
    const url = "http://localhost:8080/question/getRecent";
    const requestData = {
      limit: 10,
      pageNumber: pageIndex,
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
      .catch((err) => {});
  };

  const filterByTitle = () => {
    const url = "http://localhost:8080/question/getFiltered";
    const requestData = {
      option: 1,
      title: searchBarText,
    };

    axiosInstance
      .get(url, {
        params: requestData,
      })
      .then((response) => {
        const fetchedQuestions = response.data;
        setQuestionListFiltered(fetchedQuestions);
        setDisplayFiltered(true);
      })
      .catch((err) => {});
  };

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  useEffect(() => {
    setMounted(true);
    setUsername(localStorage.getItem("username") || "Username error");

    fetchPosts();
  }, []);

  useEffect(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === "Escape") {
        setDisplayFiltered(false);
      }
    };

    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, [questionListUnfiltered]);

  if (!mounted) return null;

  return (
    <div className="flex flex-col h-screen">
      {/* <ForumHeader /> */}
      {/* header */}
      <header className="fixed top-0 left-0 right-0 z-50 bg-blue-300 shadow-md p-4 flex items-center justify-between">
        <div className="flex items-center space-x-2">
          <h1 className="text-2xl font-extrabold text-blue-900">Questify</h1>
        </div>

        <div className="flex justify-center space-x-6 p-1">
          <AddPostButton />
          <SearchPostsBar setSearchBarText={setSearchText} />
          <button
            onClick={() => {
              filterByTitle();
            }}
          >
            <AiOutlineSearch size={20} />
          </button>

          <FilterPostsButton updateQuestions={updateQuestionList} />
        </div>

        <div className="flex items-center space-x-4">
          <select value={theme} onChange={(e) => setTheme(e.target.value)}>
            <option value="system">System</option>
            <option value="light">Light</option>
            <option value="dark">Dark</option>
          </select>

          <div className="flex items-center space-x-1">
            <ScoreLabel />
          </div>

          {/* todo */}
          <span className="text-sm font-medium text-gray-700">{username}</span>

          <div className="w-8 h-8 bg-gray-400 rounded-full flex items-center justify-center">
            <button
              onClick={toggleDropdown}
              className="flex items-center justify-center w-10 h-10 rounded-full hover:bg-gray-200 transition-all"
            >
              <FaUser size={24} className="text-gray-800" />
            </button>

            {/* todo: modal,nu dropdown pt user settings */}
            {isDropdownOpen && (
              <div className="absolute top-full right-0 mt-2 w-48 bg-white border border-gray-200 rounded-lg shadow-lg z-10">
                <ul className="py-2">
                  <li>
                    <a
                      href="#"
                      className="block px-4 py-2 text-gray-800 hover:bg-gray-200"
                    >
                      Setări cont
                    </a>
                  </li>
                  <li>
                    <a
                      href="#"
                      className="block px-4 py-2 text-gray-800 hover:bg-gray-200"
                      onClick={() => {
                        localStorage.setItem("jwtToken", "");
                        localStorage.setItem("username", "")
                        router.replace("/")
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
        {/* sidebar */}
        <aside
          className={`fixed left-0 h-full bg-gray-200 transition-all duration-300 ease-in-out ${
            isOpen ? "w-[300px]" : "w-[50px]"
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

                {localStorage.getItem("isAdmin") === "true" && (
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
          className={`flex-1 p-6 overflow-y-auto transition-all duration-300 ease-in-out ${
            isOpen ? "ml-[300px]" : "ml-[50px]"
          }`}
        >
          {/* postari */}
          <div className="mt-8">
            <div className="space-y-6">
              {displayFiltered
                ? questionListFiltered.map((item, index) => (
                    <Post
                      key={index}
                      id={item}
                      renderViewPostButton={true}
                      questionListIndex={index}
                      updateQstatus={updateQstatus}
                      updateDeleteQuestion={updateDeleteQuestion}
                    />
                  ))
                : questionListUnfiltered.map((item, index) => (
                    <Post
                      key={index}
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
