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
    const [isFilterDropdownOpen, setIsFilterDropdownOpen] = useState(false);
    const [isAddDropdownOpen, setIsAddDropdownOpen] = useState(false);


    useEffect(() => {
        setMounted(true)
    }, [])

    if (!mounted)
        return null

    const toggleDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    }

    const toggleFilterDropdown = () => {
        setIsFilterDropdownOpen(!isFilterDropdownOpen)
    }

    const toggleAddDropdown = () => {
        setIsAddDropdownOpen(!isAddDropdownOpen)
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

                    <div className="w-8 h-8 bg-gray-400 rounded-full flex items-center justify-center">
                        <button
                            onClick={toggleDropdown}
                            className="flex items-center justify-center w-10 h-10 rounded-full border-2 border-gray-400 hover:bg-gray-200 transition-all"
                        >
                            <FaUser size={24} className="text-gray-800" />
                        </button>
                        {isDropdownOpen && (
                            <div className="absolute top-full right-0 mt-2 w-48 bg-white border border-gray-200 rounded-lg shadow-lg z-10">
                                <ul className="py-2">
                                    <li>
                                        <a href="#" className="block px-4 py-2 text-gray-800 hover:bg-gray-200">
                                            Setări cont
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#" className="block px-4 py-2 text-gray-800 hover:bg-gray-200">
                                            Log out
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        )}
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
                    {/* meniu add, search, filter*/}
                    <div className="flex justify-center space-x-6 p-6">
                        <Dialog>
                            <DialogTrigger asChild>
                                <button
                                    onClick={toggleAddDropdown}
                                    className="px-6 bg-gray-400 rounded-md hover:bg-gray-300"
                                >
                                    + Add
                                </button>
                            </DialogTrigger>

                            <DialogContent className="flex flex-col items-start justify-start sm:max-w-[425px] lg:max-w-[1000px] lg:h-[500px] ">
                                <DialogHeader>
                                    <DialogTitle>{"Add a new Question"}</DialogTitle>
                                </DialogHeader>
                                <div className="grid gap-4 py-4">
                                    <div className="grid grid-cols-4 items-center gap-4">
                                        <label htmlFor="title" className="text-sm font-medium leading-none text-right">
                                            Title*
                                        </label>
                                        <Input
                                            id="title"
                                            // value={username}
                                            // onChange={(e) => setUsername(e.target.value)}
                                            placeholder="Question Title"
                                            className="col-span-3 focus:outline-none"
                                        />
                                    </div>
                                    <div className="grid grid-cols-4 items-center gap-4">
                                        <label htmlFor="text" className="text-sm font-medium leading-none text-right focus:outline-none">
                                            Question Description*
                                        </label>
                                        <textarea
                                            id="text"
                                            // value={password}
                                            // onChange={(e) => setPassword(e.target.value)}
                                            placeholder="Write a description"
                                            className="col-span-3 p-4 border border-gray-300 rounded-md resize-none focus:outline-none"
                                            rows={6}
                                        />
                                    </div>
                                    <div className="grid grid-cols-4 items-center gap-4">
                                        <label htmlFor="tags" className="text-sm font-medium leading-none text-right">
                                            Tags separated by commas
                                        </label>
                                        <Input
                                            id="tags"
                                            // value={password}
                                            // onChange={(e) => setPassword(e.target.value)}
                                            placeholder="Put 1 or more tags"
                                            className="col-span-3"
                                        />
                                    </div>

                                </div>
                                {/* {error && <p className="text-red-500">{error}</p>} */}
                                <div className="grid grid-cols-4 items-center gap-12">
                                    <Button
                                        onClick={() => { }}
                                        className="bg-blue-900 text-white px-4 py-2 rounded-md flex items-center space-x-2"
                                    >
                                        <span>Add Image</span>
                                        <CiImageOn
                                            className=""
                                        />
                                    </Button>
                                </div>

                                <div className="h-[200px] flex items-center justify-center">
                                    <Button
                                        onClick={() => { }}
                                        className="text-white px-4 py-2 rounded-md"
                                    >
                                        Add Question
                                    </Button>
                                </div>

                            </DialogContent>
                        </Dialog>

                        <form className="w-[400px] relative">
                            <div className="relative">
                                <input
                                    type="search"
                                    placeholder="Search Bar"
                                    className="w-full p-4 rounded-full bg-gray-400"
                                />
                                <button className="absolute right-1 top-1/2 -translate-y-1/2 p-4 bg-gray-500 rounded-full">
                                    <AiOutlineSearch />
                                </button>
                            </div>
                        </form>

                        <Dialog>
                            <DialogTrigger asChild>
                                <button
                                    onClick={toggleFilterDropdown}
                                    className="px-6 bg-gray-400 rounded-md hover:bg-gray-300"
                                >
                                    Filter
                                </button>
                            </DialogTrigger>

                            <DialogContent className="sm:max-w-[425px]">
                                <DialogHeader>
                                    <DialogTitle>{"Filter the Posts"}</DialogTitle>
                                </DialogHeader>
                                <div className="grid gap-4 py-4">
                                    <div className="grid grid-cols-4 items-center gap-4">
                                        <label htmlFor="tags" className="text-sm font-medium leading-none text-right">
                                            Tags
                                        </label>
                                        <Input
                                            id="tags"
                                            // value={username}
                                            // onChange={(e) => setUsername(e.target.value)}
                                            placeholder="Search by 1 tag"
                                            className="col-span-3"
                                        />
                                    </div>
                                    <div className="grid grid-cols-4 items-center gap-4">
                                        <label htmlFor="searchUser" className="text-sm font-medium leading-none text-right">
                                            Seach by User
                                        </label>
                                        <Input
                                            id="searchUser"
                                            // value={password}
                                            // onChange={(e) => setPassword(e.target.value)}
                                            placeholder="Enter username"
                                            className="col-span-3"
                                        />
                                    </div>
                                    <div className="grid grid-cols-4 items-center gap-4">
                                        <label htmlFor="searchUser" className="text-sm font-medium leading-none text-right">
                                            Show Only My Posts
                                        </label>
                                        <Checkbox />
                                    </div>

                                </div>
                                {/* {error && <p className="text-red-500">{error}</p>} */}
                                <Button onClick={() => { }}>
                                    Filter
                                </Button>
                            </DialogContent>
                        </Dialog>
                    </div>

                    {/* postari */}
                    {/* <h1 className="text-3xl font-bold"></h1> */}
                    <div className="space-y-6">
                        <Post id={5} userId={3} title="Postarea nr 1" author="User12341" text={text1} status={2} tags=" C" timestamp="11 Mar 2025" score={5} image="/logo.png" />
                        <Post id={6} userId={1} title="Postarea nr 2" author="User001" text="Lorem Ipsum is simply dummy text of the printing and typesetting industry." status={1} tags="JS" timestamp="12 Mar 2025" score={7} />
                        <Post id={7} userId={2} title="Postarea nr 3" author="User002" text="Lorem Ipsum has been the industry's standard dummy text ever since the 1500s." status={0} tags="React" timestamp="12 Mar 2025" score={4} />
                        <Post id={8} userId={4} title="Postarea nr 4" author="User004" text="It has survived not only five centuries, but also the leap into electronic typesetting." image="/img2.jpg" status={2} tags="CSS" timestamp="13 Mar 2025" score={6} />
                        <Post id={9} userId={5} title="Postarea nr 5" author="User005" text="It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages." status={1} tags="HTML" timestamp="14 Mar 2025" score={8} />
                    </div>
                </main>
            </div>
        </div>

    )
}