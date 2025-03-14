"use client"

import { Button } from "@/components/ui/button";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Post } from "@/components/ui/Post";
import { ScoreLabel } from "@/components/ui/Score";
import { useTheme } from "next-themes";
import { useEffect, useState } from "react";
import { AiOutlineSearch } from "react-icons/ai";
import { FaUser } from "react-icons/fa";
import { MdOutlineKeyboardDoubleArrowLeft, MdOutlineKeyboardDoubleArrowRight } from "react-icons/md";
import { Checkbox, FormControlLabel } from '@mui/material';
import { CiImageOn } from "react-icons/ci";
import { AddPostButton } from "@/components/ui/AddPostButton";
import { SearchPostsBar } from "@/components/ui/SearchPostsBar";
import { FilterPostsButton } from "@/components/ui/FilterPostsButton";
import axios from "axios";
import { title } from "process";
import { UserSettings } from "@/components/ui/UserSettings";
import { useRouter } from "next/navigation";

interface PostProps {
    userId: number;
    userScore: number;
}

interface Question {
    id: number;
    userId: number;
    title: string;
    author: string;
    text: string;
    status: number;
    timestamp: string;
}

export default function ForumPage({ userId, userScore }: PostProps) {
    const [isOpen, setIsOpen] = useState(true);
    const [mounted, setMounted] = useState(false);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const router = useRouter();

    const [username, setUsername] = useState("");
    const [pageIndex, setPageIndex] = useState(0);
    const [questionListFiltered, setQuestionListFiltered] = useState<Question[]>([]);
    const [questionListUnfiltered, setQuestionListUnfiltered] = useState<Question[]>([]);

    const [searchBarText, setSearchBarText] = useState("");

    const [displayFiltered, setDisplayFiltered] = useState(false);

    const setSearchText = (text: string) => {
        setSearchBarText(text);
    }

    const updateQuestionList = (list: Question[]) => {
        setQuestionListFiltered(list);
        setDisplayFiltered(true)
    }

    const fetchPosts = async () => {
        const url = "http://localhost:8080/question/getRecent";
        const requestData = {
            'limit': 10,
            'pageNumber': pageIndex
        };

        axios.get(url, {
            params: requestData
        })
            .then((response) => {
                const fetchedQuestions = response.data;
                setQuestionListUnfiltered(questionListUnfiltered.concat(fetchedQuestions));
            })
            .catch((err) => {

            })
    }

    const filterByTitle = () => {
        const url = "http://localhost:8080/question/getFiltered";
        const requestData = {
            'option': 1,
            'title': searchBarText
        };

        axios.get(url, {
            params: requestData
        })
            .then((response) => {
                const fetchedQuestions = response.data;
                setQuestionListFiltered(fetchedQuestions);
                setDisplayFiltered(true);
            })
            .catch((err) => {

            })
    }

    const toggleDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    }

    useEffect(() => {
        setMounted(true)
        setUsername(localStorage.getItem("username") || "Username error");

        fetchPosts();
    }, [])

    useEffect(() => {
        const handleKeyDown = (event: KeyboardEvent) => {
            if (event.key === "Escape") {
                setDisplayFiltered(false);
            }
        };

        document.addEventListener("keydown", handleKeyDown);

        return () => {
            document.removeEventListener("keydown", handleKeyDown);
        }
    }, [questionListUnfiltered])

    if (!mounted)
        return null

    return (
        <div className="flex flex-col h-screen">

            {/* header */}
            <header className="fixed top-0 left-0 right-0 z-50 bg-gray-300 shadow-md p-4 flex items-center justify-between">
                <div className="flex items-center space-x-2">
                    <img src="/logo.png" alt="Logo" className="w-10 h-10 object-cover" />
                    <h1 className="text-2xl font-extrabold text-blue-900">Questify</h1>
                </div>

                <div className="flex justify-center space-x-6 p-1">
                    <AddPostButton />
                    <SearchPostsBar setSearchBarText={setSearchText} />
                    <button
                        onClick={() => {
                            filterByTitle();
                        }}>
                        <AiOutlineSearch size={20} />
                    </button>

                    <FilterPostsButton updateQuestions={updateQuestionList} />
                </div>

                <div className="flex items-center space-x-4">
                    <div className="flex items-center space-x-1">
                        <ScoreLabel />
                    </div>

                    <span className="text-sm font-medium text-gray-700">{username}</span>

                    <div className="w-8 h-8 bg-gray-400 rounded-full flex items-center justify-center">
                        <UserSettings />
                    </div>
                </div>
            </header>

            <div className="flex flex-1 mt-16">
                {/* sidebar */}
                <aside className={`fixed left-0 h-full bg-gray-300 transition-all duration-300 ease-in-out ${isOpen ? "w-[150px]" : "w-[50px]"} overflow-y-auto`}>
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
                                        Home</button>
                                </li>

                            </ul>
                        </div>
                    )}
                </aside>

                <main className={`flex-1 p-6 overflow-y-auto transition-all duration-300 ease-in-out ${isOpen ? "ml-[300px]" : "ml-[50px]"}`}>
                    {/* postari */}
                    <div className="mt-8">
                        <div className="space-y-6">
                            {
                                displayFiltered
                                    ? questionListFiltered.map((item) => (
                                        <Post
                                            key={item.id}
                                            id={item.id}
                                            userId={item.userId}
                                            title={item.title}
                                            author={item.author}
                                            text={item.text}
                                            status={item.status}
                                            timestamp={item.timestamp}
                                        />
                                    ))
                                    : questionListUnfiltered.map((item) => (
                                        <Post
                                            key={item.id}
                                            id={item.id}
                                            userId={item.userId}
                                            title={item.title}
                                            author={item.author}
                                            text={item.text}
                                            status={item.status}
                                            timestamp={item.timestamp}
                                        />
                                    ))
                            }
                        </div>
                    </div>
                </main>
            </div>
        </div>
    )
}