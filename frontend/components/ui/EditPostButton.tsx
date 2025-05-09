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

interface Question {
  id: number;
  title: string;
  text: string;
  qstatus: number;
  tags: string[];
  images: string[];
  handleEditPost: (
    title: string,
    text: string,
    qstatus: number,
    tagList: string[],
    imagesList: string[]
  ) => void;
}

interface QuestionProp {
  question: Question;
}

export const EditPostButton = ({ question }: QuestionProp) => {
  const axiosInstance = safeAxios();

  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [isAddDropdownOpen, setIsAddDropdownOpen] = useState(false);
  const [title, setTitle] = useState("");
  const [text, setText] = useState("");
  const [tags, setTags] = useState("");
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

  const handleCloseQuestion = () => {
    const tagList = tags.split(/[ ,;]+/).filter((tag) => tag.trim() !== "");

    const url = "http://localhost:8080/question/closeQuestionById";

    axiosInstance
      .put(url, { id: question.id })
      .then(() => {
        setTimeout(() => {
          toast.success("Question Closed");
          question.handleEditPost(
            title,
            text,
            2,
            imageList,
            tagList
          );
          setIsDialogOpen(false);
        }, 1750);
      })
      .catch((err) => {
        toast.error(err.response.data);
      });
  };

  const handleEditQuestion = async () => {
    const tagList = tags.split(/[ ,;]+/).filter((tag) => tag.trim() !== "");

    const url = "http://localhost:8080/question/editById";

    const questionData = {
      id: question.id,
      title: title,
      text: text,
      tags: tagList,
      images: imageList,
    };

    axiosInstance
      .put(url, questionData)
      .then((response) => {
        if (response.data === "Success") {
          question.handleEditPost(
            title,
            text,
            question.qstatus,
            imageList,
            tagList
          );
          toast.success("Question Edited");
          setTimeout(() => setIsDialogOpen(false), 1750);
        }
      });
  };

  useEffect(() => {
    setTitle(question.title);
    setText(question.text);
    setTags(question.tags.join(" "));
    setImageList(question.images)
  }, [question]);

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
          <DialogTitle>{"Edit Question"}</DialogTitle>
        </DialogHeader>
        <div className="grid gap-4 py-4">
          <div className="grid grid-cols-4 items-center gap-4">
            <label
              htmlFor="title"
              className="text-sm font-medium leading-none text-right"
            >
              Title*
            </label>
            <Input
              id="title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="Question Title"
              className="col-span-3 focus:outline-none"
            />
          </div>
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
          <div className="grid grid-cols-4 items-center gap-4">
            <label
              htmlFor="tags"
              className="text-sm font-medium leading-none text-right"
            >
              Tags separated by spaces
            </label>
            <Input
              id="tags"
              value={tags}
              onChange={(e) => setTags(e.target.value)}
              placeholder="Put 1 or more tags"
              className="col-span-3"
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
            <span>Add Image</span>
            <CiImageOn className="" />
          </Button>
        </div>

        {imageList && imageList.map((item, index) => (
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
            onClick={handleCloseQuestion}
            className="bg-red-500 text-white px-4 py-2 rounded-md"
          >
            Close Question
          </Button>

          <Button
            onClick={handleEditQuestion}
            className="text-white px-4 py-2 rounded-md"
          >
            Edit Question
          </Button>
        </div>

        <Toaster />
      </DialogContent>
    </Dialog>
  );
};
