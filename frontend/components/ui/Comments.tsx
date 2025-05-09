"use client";

import { FaArrowDown, FaArrowUp } from "react-icons/fa";
import { useEffect, useState } from "react";
import { MdDeleteOutline } from "react-icons/md";
import { EditAnswerButton } from "./EditAnswerButton";
import { Button } from "./button";
import { safeAxios } from "@/services/axiosInstance";

interface AnswerProps {
  id: number;
  answerListIndex: number;
  qstatus: number;
  updateDeleteAnswerCallback: (index: number) => void;
}

export const Comments = ({
  id,
  answerListIndex,
  qstatus,
  updateDeleteAnswerCallback,
}: AnswerProps) => {
  const axiosInstance = safeAxios();

  const [userId, setUserId] = useState(0);
  const [text, setText] = useState("");
  const [timestamp, setTimestamp] = useState("");

  const [authorScore, setAuthorScore] = useState(0);
  const [answerScore, setAnswerScore] = useState(0);
  const [authorOfAnswer, setAuthorOfAnswer] = useState("");
  const [answerImageList, setAnswerImageList] = useState<string[]>([]);

  const voteUpAnswer = () => {
    const url = "http://localhost:8080/answers/voteUpById";
    const voteRequest = {
      userId: Number(localStorage.getItem("userId")),
      answerId: Number(id),
    };

    axiosInstance.put(url, voteRequest).then((response) => {
      if (response.data === "Voted up") {
        setAnswerScore(answerScore + 1);
      }

      if (response.data === "Vote up removed") {
        setAnswerScore(answerScore - 1);
      }
    });
  };

  const voteDownAnswer = () => {
    const url = "http://localhost:8080/answers/voteDownById";
    const voteRequest = {
      userId: Number(localStorage.getItem("userId")),
      answerId: Number(id),
    };

    axiosInstance.put(url, voteRequest).then((response) => {
      console.log(response.data);

      if (response.data === "Voted down") {
        setAnswerScore(answerScore - 1);
      }

      if (response.data === "Vote down removed") {
        setAnswerScore(answerScore + 1);
      }
    });
  };

  const handleAnswerEdit = (
    index: number,
    text: string,
    imageList: string[]
  ) => {
    setText(text);
    setAnswerImageList(imageList);
  };

  const handleAnswerDelete = () => {
    const url = "http://localhost:8080/answers/deleteById";

    axiosInstance
      .delete(url, {
        params: {
          answerId: id,
        },
      })
      .then(() => {
        updateDeleteAnswerCallback(answerListIndex);
      });
  };

  useEffect(() => {
    if (userId != 0) {
      const urlUsername = "http://localhost:8079/users/getUsernameByUserId";
      const urlAuthorScore = "http://localhost:8079/users/scoreById";

      axiosInstance
        .get(urlUsername, {
          //username
          params: { userId: userId },
        })
        .then((response) => {
          console.log(response.data + "USERNAME");
          setAuthorOfAnswer(response.data);
        })
        .catch((err) => {
          console.error(err);
        });

      axiosInstance
        .get(urlAuthorScore, {
          params: { userId: userId },
        })
        .then((response) => {
          setAuthorScore(response.data);
        });
    }
  }, [userId]);

  const fetchAnswerData = () => {
    const urlAnswerGeneralData = "http://localhost:8080/answers/getByAnswerId";

    axiosInstance
      .get(urlAnswerGeneralData, {
        params: { answerId: id },
      })
      .then((response) => {
        setUserId(response.data.userId);
        setText(response.data.text);
        setTimestamp(response.data.timestamp);
      });

    const urlScore = "http://localhost:8080/answers/getAnswerScoreByAnswerId";
    const urlAnswerImages = "http://localhost:8080/answers/getAnswerImagesById";

    axiosInstance
      .get(urlScore, {
        //score
        params: { answerId: id },
      })
      .then((response) => {
        setAnswerScore(response.data);
      });

    axiosInstance
      .get(urlAnswerImages, {
        params: { answerId: id },
      })
      .then((response) => {
        setAnswerImageList(response.data);
      });
  };

  useEffect(() => {
    fetchAnswerData();
  }, [id]);

  return (
    <div className="p-4 bg-gray-100 rounded-lg max-w-2xl mx-auto w-[600px]">
      <div className="flex justify-between text-sm text-gray-600">
        <header className="flex flex-row items-center">
          <h1>{authorOfAnswer}</h1>
          <span className="ml-2">{authorScore}</span>
        </header>
        <h1>{timestamp}</h1>
      </div>

      {answerImageList.length > 0 && (
        <div className="flex justify-center w-full center mx-auto mt-2">
          {answerImageList.length > 0 ? (
            answerImageList.map((item, index) => (
              <img
                key={index}
                src={item}
                alt="image"
                className="h-auto rounded-lg object-cover"
                style={{
                  width: `${100 / answerImageList.length / 1.5}%`,
                }}
              />
            ))
          ) : (
            <p>No images available.</p>
          )}
        </div>
      )}

      <p className="mt-4">{text}</p>

      <div className="flex justify-between items-center mt-4">
        <div className="flex items-center gap-2">
          <button className="text-green-500" onClick={voteUpAnswer}>
            <FaArrowUp />
          </button>
          <label className="font-bold">{answerScore}</label>
          <button className="text-red-500" onClick={voteDownAnswer}>
            <FaArrowDown />
          </button>
        </div>

        {((Number(localStorage.getItem("userId")) === userId &&
          qstatus !== 2) ||
          localStorage.getItem("isAdmin") === "true") && (
          <div className="space-x-4">
            <EditAnswerButton
              answer={{
                id: id,
                text: text,
                answerImages: answerImageList,
                answerListIndex: answerListIndex,
                handleAnswerEdit: handleAnswerEdit,
              }}
            />

            <Button
              className="bg-red-500 p-2 text-white text-xl rounded-full"
              size="sm"
              onClick={handleAnswerDelete}
            >
              <MdDeleteOutline />
            </Button>
          </div>
        )}
      </div>
    </div>
  );
};
