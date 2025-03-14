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
import { UserSettings } from "@/components/ui/UserSettings";

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
    tags: string;
    timestamp: string;
    score: number;
    image: string;
}

export default function ForumPage({ userId, userScore }: PostProps) {
    const [isOpen, setIsOpen] = useState(true);
    const { theme, setTheme } = useTheme()

    const text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."

    const [mounted, setMounted] = useState(false);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);

    const [username, setUsername] = useState("");
    const [pageIndex, setPageIndex] = useState(0);
    const [questionList, setQuestionList] = useState<Question[]>([]);

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
                setQuestionList(questionList.concat(fetchedQuestions));
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
                    <SearchPostsBar />
                    <button
                        onClick={() => { }}>
                        <AiOutlineSearch size={20} />
                    </button>

                    <FilterPostsButton />
                </div>

                <div className="flex items-center space-x-4">
                    <select value={theme} onChange={e => setTheme(e.target.value)}>
                        <option value="system">System</option>
                        <option value="light">Light</option>
                        <option value="dark">Dark</option>
                    </select>


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
                <aside className={`fixed left-0 h-full bg-gray-300 transition-all duration-300 ease-in-out ${isOpen ? "w-[300px]" : "w-[50px]"} overflow-y-auto`}>
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
                                <li><a href="#" className="block p-2 hover:bg-gray-700">btn1</a></li>
                                <li><a href="#" className="block p-2 hover:bg-gray-700">btn2</a></li>
                                <li><a href="#" className="block p-2 hover:bg-gray-700">btn3</a></li>
                            </ul>
                            {/* <FollowList /> */}
                        </div>
                    )}
                </aside>

                <main className={`flex-1 p-6 overflow-y-auto transition-all duration-300 ease-in-out ${isOpen ? "ml-[300px]" : "ml-[50px]"}`}>
                    {/* postari */}
                    <div className="mt-8">
                        <div className="space-y-6">
                            <Post id={51251} userId={2} image="/logo.png" text="salut" title="Salutttt" score={0} author="raul" status={0} timestamp="14mar2025" />
                            {
                                questionList.map((item) => (
                                    <Post
                                        key={item.id}  // Make sure to provide a unique key
                                        id={item.id}
                                        userId={item.userId}
                                        title={item.title}
                                        author={item.author}
                                        text={item.text}
                                        status={item.status}
                                        tags={item.tags}
                                        timestamp={item.timestamp}
                                        score={item.score}
                                        image={item.image}
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