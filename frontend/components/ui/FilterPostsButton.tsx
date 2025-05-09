"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "./dialog";
import { Input } from "./input";
import { Checkbox } from "@mui/material";
import { Button } from "./button";
import { useEffect, useState } from "react";
import toast, { Toaster } from "react-hot-toast";
import { safeAxios } from "@/services/axiosInstance";
import { CiFilter } from "react-icons/ci";

interface Question {
  id: number;
  userId: number;
  title: string;
  author: string;
  text: string;
  status: number;
  tags: string[],
  images: string[],
  timestamp: string;
}

interface FilterInterface {
  updateQuestions: (newQuestionList: number[]) => void;
}

export const FilterPostsButton = ({ updateQuestions }: FilterInterface) => {
  const axiosInstance = safeAxios();

  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [isFilterDropdownOpen, setIsFilterDropdownOpen] = useState(false);
  const [tag, setTag] = useState("");
  const [username, setUsername] = useState("");
  const [personalPosts, setPersonalPosts] = useState(false);

  const [filterByTagList, setFilterByTagList] = useState<number[]>([]);
  const [filterByUsernameList, setFilterByUsernameList] = useState<number[]>([]);
  const [filterByPersonalPostsList, setFilterByPersonalPostsList] = useState<number[]>([]);

  useEffect(() => {
    let finalList : number[] = [];

    if (filterByTagList.length > 0) {
        updateQuestions(filterByTagList);
    }

    if(filterByUsernameList.length > 0) {
        updateQuestions(filterByUsernameList);
    }

    if(filterByPersonalPostsList.length > 0) {
        updateQuestions(filterByPersonalPostsList);
    }
  }, [filterByTagList, filterByUsernameList, filterByPersonalPostsList]);

  const toggleFilterDropdown = () => {
    setIsFilterDropdownOpen(!isFilterDropdownOpen);
  };

  const filterPosts = () => {
    setFilterByTagList([]);
    setFilterByUsernameList([]);
    setFilterByPersonalPostsList([]);

    const url = "http://localhost:8080/question/getFiltered";

    if (tag.length > 0) {
      axiosInstance
        .get(url, {
          params: {
            option: 0,
            tag: tag,
          },
        })
        .then((response) => {
          setFilterByTagList(response.data);
        });
    }

    if (username.length > 0) {
      axiosInstance
        .get(url, {
          params: {
            option: 2,
            username: username,
          },
        })
        .then((response) => {
          setFilterByUsernameList(response.data);
        });
    }

    if (personalPosts) {
      axiosInstance
        .get(url, {
          params: {
            option: 3,
            userId: Number(localStorage.getItem("userId")),
          },
        })
        .then((response) => {
          setFilterByPersonalPostsList(response.data);
        });
    }

    toast.success("Filtering");

    setTimeout(() => {
      setIsDialogOpen(false);
    }, 1000);
  };

  return (
    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
      <DialogTrigger asChild>
        <Button
          onClick={toggleFilterDropdown}
          className="px-6 py-2 bg-blue-400 rounded-md hover:bg-blue-300 text-gray-600"
        >
          Filter
          <CiFilter />
        </Button>
      </DialogTrigger>

      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>{"Filter the Posts"}</DialogTitle>
        </DialogHeader>
        <div className="grid gap-4 py-4">
          <div className="grid grid-cols-4 items-center gap-4">
            <label
              htmlFor="tags"
              className="text-sm font-medium leading-none text-right"
            >
              Tags
            </label>
            <Input
              id="tags"
              value={tag}
              onChange={(e) => {
                if(username.length === 0 && !personalPosts) {
                    setTag(e.target.value)
                }
              }}
              placeholder="Search by 1 tag"
              className="col-span-3"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4">
            <label
              htmlFor="searchUser"
              className="text-sm font-medium leading-none text-right"
            >
              Seach by User
            </label>
            <Input
              id="searchUser"
              value={username}
              onChange={(e) => {
                    if(tag.length === 0 && !personalPosts) {
                        setUsername(e.target.value)
                    }
                }
              }
              placeholder="Enter username"
              className="col-span-3"
            />
          </div>
          <div className="grid grid-cols-4 items-center gap-4">
            <label
              htmlFor="searchUser"
              className="text-sm font-medium leading-none text-right"
            >
              Show Only My Posts
            </label>
            <Checkbox
              value={personalPosts}
              checked={tag.length === 0 && username.length === 0 && personalPosts}
              onChange={() => {
                if(tag.length === 0 && username.length === 0) {
                    setPersonalPosts(!personalPosts);
                }
              }}
            />
          </div>
        </div>
        {/* {error && <p className="text-red-500">{error}</p>} */}
        <Button onClick={filterPosts}>Filter</Button>

        <Toaster />
      </DialogContent>
    </Dialog>
  );
};
