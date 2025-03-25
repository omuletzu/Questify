"use client";

import { AddPostButton } from "@/components/ui/AddPostButton";
import { Comments } from "@/components/ui/Comments";
import { FilterPostsButton } from "@/components/ui/FilterPostsButton";
import { ScoreLabel } from "@/components/ui/Score";
import { SearchPostsBar } from "@/components/ui/SearchPostsBar";
import { UserSettings } from "@/components/ui/UserSettings";
import { ViewPost } from "@/components/ui/ViewPost";
import { useRouter, useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";
import { AiOutlineSearch } from "react-icons/ai";
import { IoIosAddCircle } from "react-icons/io";
import {
  MdOutlineKeyboardDoubleArrowLeft,
  MdOutlineKeyboardDoubleArrowRight,
} from "react-icons/md";
import axios from "axios";
import { Post } from "@/components/ui/Post";
import { Button } from "@mui/material";
import { AddAnswerButton } from "@/components/ui/AddAnswerButton";
import { safeAxios } from "@/services/axiosInstance";

interface Answer {
  id: number;
}

export default function ViewPostPage() {
  const axiosInstance = safeAxios();

  const [isOpen, setIsOpen] = useState(true);
  const [mounted, setMounted] = useState(false);
  const router = useRouter();

  const searchParams = useSearchParams();

  const questionId = Number(searchParams.get("questionId"));

  const [qstatus, setQstatus] = useState(0);

  const [username, setUsername] = useState("");
  const [answerList, setAnswerList] = useState<number[]>([]);

  const updateQstatus = (qstatus: number) => {
    setQstatus(qstatus);
  };

  const updateDeleteQuestion = (index: number) => {
    router.back();
  };

  const fetchAnswers = async () => {
    const url = "http://localhost:8080/answers/getByQuestionId";
    const requestData = {
      questionId: questionId,
    };

    axiosInstance
      .get(url, {
        params: requestData,
      })
      .then((response) => {
        const fetchedAnswers = response.data;
        setAnswerList(fetchedAnswers);
      })
      .catch((err) => {});
  };

  const updateAddAnswerCallback = (answerId: number) => {
    const newAnswerList = [...answerList];
    newAnswerList.push(answerId);
    setAnswerList(newAnswerList);
  };

  const updateDeleteAnswerCallback = (index: number) => {
    const newAnswerList = [...answerList];
    newAnswerList.splice(index, 1);
    setAnswerList(newAnswerList);
  };

  useEffect(() => {
    setMounted(true);
    setUsername(localStorage.getItem("username")!!);
    fetchAnswers();
  }, []);

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

  if (!mounted) return null;

  return (
    <div>
      <header className="fixed top-0 left-0 right-0 z-50 bg-blue-300 shadow-md p-4 flex items-center justify-between">
        <div className="flex items-center space-x-2">
          <h1 className="text-2xl font-extrabold text-blue-900">Questify</h1>
        </div>
        <div className="flex items-center space-x-4">
          <div className="flex items-center space-x-1">
            <ScoreLabel />
          </div>

          <span className="text-sm font-medium text-gray-700">{username}</span>

          <div className="w-8 h-8 bg-gray-500 rounded-full flex items-center justify-center">
            <UserSettings />
          </div>
        </div>
      </header>

      <div className="flex flex-1 mt-16">
        {/* sidebar */}
        <aside
          className={`fixed left-0 h-full bg-gray-300 transition-all duration-300 ease-in-out ${
            isOpen ? "w-[150px]" : "w-[50px]"
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
                  <button
                    className="block w-full p-4 text-left hover:bg-gray-400 rounded-md"
                    onClick={() => router.push("/home")}
                  >
                    Home
                  </button>
                </li>
              </ul>
            </div>
          )}
        </aside>
        <div
          className={`flex-1 p-6 overflow-y-auto transition-all duration-300 ease-in-out ${
            isOpen ? "ml-[300px]" : "ml-[50px]"
          }`}
        >
          {/* postari */}
          <div className="mt-8">
            <div className="space-y-6 flex flex-row">
              <Post
                id={questionId}
                questionListIndex={0}
                renderViewPostButton={false}
                updateQstatus={updateQstatus}
                updateDeleteQuestion={updateDeleteQuestion}
              />
              <div className="ml-20">
                {qstatus !== 2 && (
                  <div className="flex justify-between items-center w-full space-x-6">
                    <header className="font-bold text-2xl">Comments</header>
                    <AddAnswerButton
                      addAnswer={{
                        questionId: questionId,
                        updateAddCommentCallback: updateAddAnswerCallback,
                      }}
                    />
                  </div>
                )}

                <div className="py-2 space-y-4">
                  {answerList.map((item, index) => (
                    <Comments
                      key={index}
                      id={item}
                      qstatus={qstatus}
                      answerListIndex={index}
                      updateDeleteAnswerCallback={updateDeleteAnswerCallback}
                    />
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
