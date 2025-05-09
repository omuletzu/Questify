"use client";

import { FaArrowUp, FaArrowDown } from "react-icons/fa";
import { Button } from "./button";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { MdDeleteOutline } from "react-icons/md";
import { EditPostButton } from "./EditPostButton";
import toast from "react-hot-toast";
import { safeAxios } from "@/services/axiosInstance";

interface PostProps {
  id: number;
  questionListIndex: number;
  renderViewPostButton: boolean;
  updateQstatus: (qstatus: number) => void;
  updateDeleteQuestion: (index: number) => void;
}

export const Post = ({
  id,
  questionListIndex,
  renderViewPostButton,
  updateQstatus,
  updateDeleteQuestion,
}: PostProps) => {
  const axiosInstance = safeAxios();

  const [authorId, setAuthorId] = useState<number>(0);

  const currentUserId = Number(localStorage.getItem("userId"));

  const [title, setTitle] = useState<string>("");
  const [text, setText] = useState<string>("");
  const [qstatus, setQstatus] = useState<number>(0);
  const [timestamp, setTimestamp] = useState<string>("");

  const [authorScore, setAuthorScore] = useState<number>(0);
  const [score, setScore] = useState<number>(0);
  const [tagList, setTagList] = useState<string[]>([]);
  const [imageList, setImageList] = useState<string[]>([]);
  const [authorOfPost, setAuthorOfPost] = useState<string>("");

  const router = useRouter();

  const handleViewPost = () => {
    router.push(`/home/${id}?questionId=${id}`);
  };

  const redirectToUserPage = () => {
    router.push(`/user/${authorId}`);
  };

  const handleDeletePost = () => {
    const url = "http://localhost:8080/question/deleteById";
    const deleteRequest = { questionId: id };

    axiosInstance.delete(url, { params: deleteRequest }).then(() => {
      toast.success("Question deleted");
      updateDeleteQuestion(questionListIndex);
    });
  };

  const handleEditPost = (
    title: string,
    text: string,
    status: number,
    images: string[],
    tags: string[]
  ) => {
    setTitle(title);
    setText(text);
    setQstatus(status);
    setImageList(images);
    setTagList(tags);
  };

  const voteUp = () => {
    const url = "http://localhost:8080/question/voteUpById";
    const voteRequest = { userId: currentUserId, questionId: id };

    axiosInstance.put(url, voteRequest).then((response) => {
      if (response.data === "Voted up") setScore(score + 1);
      if (response.data === "Vote up removed") setScore(score - 1);
    });
  };

  const voteDown = () => {
    const url = "http://localhost:8080/question/voteDownById";
    const voteRequest = { userId: currentUserId, questionId: id };

    axiosInstance.put(url, voteRequest).then((response) => {
      if (response.data === "Voted down") setScore(score - 1);
      if (response.data === "Vote down removed") setScore(score + 1);
    });
  };

  useEffect(() => {
    const urlQuestionData = "http://localhost:8080/question/getQuestionById";

    axiosInstance
      .get(urlQuestionData, { params: { questionId: id } })
      .then((response) => {
        setAuthorId(response.data.userId);
        setTitle(response.data.title);
        setText(response.data.text);
        setQstatus(response.data.status);
        setTimestamp(response.data.timestamp);

        updateQstatus(response.data.status);
      });

    const urlScore = "http://localhost:8080/question/getQuestionScoreByQuestionId";
    const urlTags = "http://localhost:8080/question/getTagsByQuestionId";
    const urlQuestionImages = "http://localhost:8080/question/getImagesByQuestionId";

    axiosInstance
      .get(urlScore, { params: { questionId: id } })
      .then((response) => setScore(response.data))
      .catch((err) => console.error(err));

    axiosInstance
      .get(urlTags, { params: { questionId: id } })
      .then((response) => setTagList(response.data))
      .catch((err) => console.error(err));

    axiosInstance
      .get(urlQuestionImages, { params: { questionId: id } })
      .then((response) => setImageList(response.data))
      .catch((err) => console.error(err));
  }, [id]);

  useEffect(() => {
    if (authorId !== 0) {
      const urlUsername = "http://localhost:8079/users/getUsernameByUserId";
      const urlAuthorScore = "http://localhost:8079/users/scoreById";

      axiosInstance
        .get(urlUsername, { params: { userId: authorId } })
        .then((response) => setAuthorOfPost(response.data))
        .catch((err) => console.error(err));

      axiosInstance
        .get(urlAuthorScore, { params: { userId: authorId } })
        .then((response) => setAuthorScore(response.data))
        .catch((err) => console.error(err));
    }
  }, [authorId]);

  return (
    <div className="p-4 bg-white shadow-md rounded-lg max-w-2xl mx-auto w-full">
      <div className="flex justify-between text-sm text-gray-600">
        <h1
          className="cursor-pointer hover:underline"
          onClick={redirectToUserPage}
        >
          {authorOfPost}
        </h1>
        <h1>{authorScore}</h1>
        <h1>
          {qstatus === 0
            ? "Sent"
            : qstatus === 1
            ? "In progress"
            : "Solved"}
        </h1>
        <h1 className="z-15">{timestamp}</h1>
      </div>

      <h2 className="font-bold text-2xl mt-2">{title}</h2>

      {imageList.length > 0 && (
        <div className="flex justify-center w-full mx-auto mt-2 space-x-2">
          {imageList.map((item, index) => (
            <img
              key={index}
              src={item}
              alt="image"
              className="h-auto rounded-lg object-cover"
              style={{ width: `${100 / imageList.length / 1.5}%` }}
            />
          ))}
        </div>
      )}

      <p className="mt-4">{text}</p>

      <div className="mt-4">
        {tagList.length > 0 && (
          <h3 className="text-lg font-semibold text-gray-700">Tags:</h3>
        )}
        <div className="flex flex-wrap gap-2 mt-2">
          {tagList.map((item, index) => (
            <span
              key={index}
              className="bg-gray-200 text-gray-800 px-3 py-1 rounded-full text-sm font-medium shadow-md"
            >
              {item}
            </span>
          ))}
        </div>
      </div>

      <div className="flex justify-between items-center mt-4">
        <div className="flex items-center gap-2">
          <button className="text-green-500" onClick={voteUp}>
            <FaArrowUp />
          </button>
          <label className="font-bold">{score}</label>
          <button className="text-red-500" onClick={voteDown}>
            <FaArrowDown />
          </button>
        </div>

        {renderViewPostButton && (
          <Button
            onClick={handleViewPost}
            size="sm"
            className="bg-gray-500 rounded-full"
          >
            View post
          </Button>
        )}

        {(authorId === currentUserId || localStorage.getItem("isAdmin") === "true") && (
          <div className="space-x-5">
            {qstatus !== 2 && (
              <EditPostButton
                question={{
                  id,
                  title,
                  text,
                  qstatus,
                  images: imageList,
                  tags: tagList,
                  handleEditPost,
                }}
              />
            )}

            <Button
              className="bg-red-500 text-white text-xl rounded-full"
              size="sm"
              onClick={handleDeletePost}
            >
              <MdDeleteOutline />
            </Button>
          </div>
        )}
      </div>
    </div>
  );
};