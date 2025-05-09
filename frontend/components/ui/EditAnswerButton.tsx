"use client";

import { CiImageOn } from "react-icons/ci";
import { Button } from "./button";
import { Input } from "./input";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "./dialog";
import { useEffect, useState } from "react";
import toast, { Toaster } from "react-hot-toast";
import { FaRegEdit } from "react-icons/fa";
import { safeAxios } from "@/services/axiosInstance";
import { MdDeleteOutline } from "react-icons/md";

interface Answer {
  id: number;
  text: string;
  answerImages: string[];
  answerListIndex: number;
  handleAnswerEdit: (
    index: number,
    text: string,
    answerImagesList: string[]
  ) => void;
}

interface AnswerProp {
  answer: Answer;
}

export const EditAnswerButton = ({ answer }: AnswerProp) => {
  const axiosInstance = safeAxios();

  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [text, setText] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [answerImagesList, setAnswerImageList] = useState<string[]>([]);

  const handleAddImageToList = () => {
    if (imageUrl.length === 0) {
      toast.error("Empty URL");
      return;
    }

    const newImageList = [...answerImagesList];
    newImageList.push(imageUrl);
    setAnswerImageList(newImageList);
    setImageUrl("");
  };

  const handleImageDelete = (index: number) => {
    const newImageList = [...answerImagesList];
    newImageList.splice(index, 1);
    setAnswerImageList(newImageList);
  };

  const handleEditAnswer = async () => {
    const url = "http://localhost:8080/answers/editById";

    const answerData = {
      answerId: answer.id,
      text: text,
      images: answerImagesList
    };

    axiosInstance
      .put(url, answerData)
      .then((response) => {
        if (response.data === "Success") {
          answer.handleAnswerEdit(
            answer.answerListIndex,
            text,
            answerImagesList
          );
          toast.success("Answer Edited");
          setTimeout(() => setIsDialogOpen(false), 1750);
        }
      });
  };

  useEffect(() => {
    setText(answer.text);
    setAnswerImageList(answer.answerImages);
  }, [answer]);

  return (
    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
      <DialogTrigger asChild>
        <Button
          className="bg-gray-500 text-white text-xl rounded-full"
          size="sm"
        >
          <FaRegEdit />
        </Button>
      </DialogTrigger>
      <DialogContent className="flex flex-col items-start justify-start sm:max-w-[425px] lg:max-w-[1000px] max-h-[80vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle>{"Edit Answer"}</DialogTitle>
        </DialogHeader>
        <div className="grid gap-4 py-4">
          <div className="grid grid-cols-4 items-center gap-4">
            <label
              htmlFor="text"
              className="text-sm font-medium leading-none text-right focus:outline-none"
            >
              Question Description*
            </label>
            <textarea
              id="text"
              value={text}
              onChange={(e) => setText(e.target.value)}
              placeholder="Write a description"
              className="col-span-3 p-4 border border-gray-300 rounded-md resize-none focus:outline-none"
              rows={6}
            />
          </div>
        </div>

        <div className="grid grid-cols-4 items-center gap-4">
          <label
            htmlFor="tags"
            className="text-sm font-medium leading-none text-right"
          >
            Image URL
          </label>
          <Input
            id="imageurl"
            value={imageUrl}
            onChange={(e) => setImageUrl(e.target.value)}
            placeholder="URL"
            className="col-span-3"
          />
        </div>

        <div className="grid grid-cols-4 items-center gap-12">
          <Button
            onClick={handleAddImageToList}
            className="bg-blue-900 text-white px-4 py-2 rounded-md flex items-center space-x-2"
          >
            <span>Add Image</span>
            <CiImageOn className="" />
          </Button>
        </div>

        {answerImagesList &&
          answerImagesList.map((item, index) => (
            <div key={index}>
              <Button
                className="bg-red-500 p-2 text-white text-xl rounded-full"
                size="sm"
                onClick={() => {
                  handleImageDelete(index);
                }}
              >
                <MdDeleteOutline />
              </Button>
              <img src={item} style={{ width: "25%" }} />
            </div>
          ))}

        <div className="h-[200px] flex items-center justify-center space-x-4">
          <Button
            onClick={handleEditAnswer}
            className="text-white px-4 py-2 rounded-md"
          >
            Edit Answer
          </Button>
        </div>

        <Toaster />
      </DialogContent>
    </Dialog>
  );
};
