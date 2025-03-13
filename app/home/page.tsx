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
import { UserSettings } from "@/components/ui/UserSettings";

interface PostProps {
    userId: number;
    userScore: number;
}

export default function ForumPage({ userId, userScore }: PostProps) {
    const [isOpen, setIsOpen] = useState(true);
    const { theme, setTheme } = useTheme()

    const text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."

    const [mounted, setMounted] = useState(false);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);


    useEffect(() => {
        setMounted(true)
    }, [])

    if (!mounted)
        return null

    const toggleDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    }

    return (
        <div className="flex flex-col h-screen">

            {/* <ForumHeader /> */}
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

                    {/* todo */}
                    <span className="text-sm font-medium text-gray-700">User123</span>

                    <UserSettings />


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
                            <Post id={5} userId={3} title="Postarea nr 1" author="User12341" text={text1} status={2} tags=" C" timestamp="11 Mar 2025" score={5} image="/logo.png" />
                            <Post id={6} userId={1} title="Postarea nr 2" author="User001" text="Lorem Ipsum is simply dummy text of the printing and typesetting industry." status={1} tags="JS" timestamp="12 Mar 2025" score={7} />
                            <Post id={7} userId={2} title="Postarea nr 3" author="User002" text="Lorem Ipsum has been the industry's standard dummy text ever since the 1500s." status={0} tags="React" timestamp="12 Mar 2025" score={4} />
                            <Post id={8} userId={4} title="Postarea nr 4" author="User004" text="It has survived not only five centuries, but also the leap into electronic typesetting." image="/img2.jpg" status={2} tags="CSS" timestamp="13 Mar 2025" score={6} />
                            <Post id={9} userId={5} title="Postarea nr 5" author="User005" text="It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages." status={1} tags="HTML" timestamp="14 Mar 2025" score={8} />
                        </div>
                    </div>
                </main>
            </div>
        </div>
    )
}