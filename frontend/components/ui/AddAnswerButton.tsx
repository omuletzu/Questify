"use client";

import { CiImageOn } from "react-icons/ci";
import { IoIosAddCircle } from "react-icons/io";
import { Button } from "./button";
import { Input } from "./input";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "./dialog";
import { useState } from "react";
import toast, { Toaster } from "react-hot-toast";
import { safeAxios } from "@/services/axiosInstance";
import { MdDeleteOutline } from "react-icons/md";

interface AddAnswer {
  questionId: number;
  updateAddCommentCallback: (
    answerId: number,
    text: string,
    answerImageList: string[],
    timestamp: string
  ) => void;
}

interface AddAnswerProps {
  addAnswer: AddAnswer;
}

export const AddAnswerButton = ({ addAnswer }: AddAnswerProps) => {
  const axiosInstance = safeAxios();

  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [isAddDropdownOpen, setIsAddDropdownOpen] = useState(false);
  const [text, setText] = useState("");

  const [imageList, setImageList] = useState<string[]>([]);
  const [imageUrl, setImageUrl] = useState("");

  const handleAddImageToList = () => {
    if (imageUrl.length === 0) {
      toast.error("Empty URL");
      return;
    }

    const newImageList = [...imageList];
    newImageList.push(imageUrl);
    setImageList(newImageList);
    setImageUrl("");
  };

  const handleImageDelete = (index: number) => {
    const newImageList = [...imageList];
    newImageList.splice(index, 1);
    setImageList(newImageList);
  };

  const toggleAddDropdown = () => {
    setIsAddDropdownOpen(!isAddDropdownOpen);
  };

  const handleAddComment = async () => {
    if (text.length === 0) {
      toast.error("Empty text");
      return;
    }

    const url = "http://localhost:8080/answers/addForQuestionId";

    const answerData = {
      userId: Number(localStorage.getItem("userId")),
      questionId: addAnswer.questionId,
      text: text,
      images: imageList,
    };

    axiosInstance
      .post(url, answerData, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        addAnswer.updateAddCommentCallback(
          response.data.id,
          response.data.text,
          imageList,
          response.data.timestamp
        );
        toast.success("Success");
        setTimeout(() => setIsDialogOpen(false), 1750);
      })
      .catch((err) => {
        toast.error(err.response.data);
      });
  };

  return (
    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
      <DialogTrigger asChild>
        <Button
          onClick={toggleAddDropdown}
          className="px-6 bg-blue-400 rounded-md hover:bg-blue-300 text-gray-700"
        >
          Add Comment
          <IoIosAddCircle />
        </Button>
      </DialogTrigger>
      <DialogContent className="flex flex-col items-start justify-start sm:max-w-[425px] lg:max-w-[1000px] max-h-[80vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle>{"Add a new Comment"}</DialogTitle>
        </DialogHeader>
        <div className="grid gap-4 py-4">
          <div className="grid grid-cols-4 items-center gap-4">
            <label
              htmlFor="text"
              className="text-sm font-medium leading-none text-right focus:outline-none"
            >
              Comment text*
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
        </div>
        {/* {error && <p className="text-red-500">{error}</p>} */}
        <div className="grid grid-cols-4 items-center gap-12">
          <Button
            onClick={handleAddImageToList}
            className="bg-blue-900 text-white px-4 py-2 rounded-md flex items-center space-x-2"
          >
            <span>Add Images</span>
            <CiImageOn className="" />
          </Button>
        </div>

        {imageList.map((item, index) => (
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

        <div className="h-[200px] flex items-center justify-center">
          <Button
            onClick={handleAddComment}
            className="text-white px-4 py-2 rounded-md"
          >
            Add Comment
          </Button>
        </div>

        <Toaster />
      </DialogContent>
    </Dialog>
  );
};
