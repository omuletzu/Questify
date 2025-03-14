"use client"

import { AddPostButton } from "@/components/ui/AddPostButton"
import { Comments } from "@/components/ui/Comments"
import { FilterPostsButton } from "@/components/ui/FilterPostsButton"
import { Post } from "@/components/ui/Post"
import { ScoreLabel } from "@/components/ui/Score"
import { SearchPostsBar } from "@/components/ui/SearchPostsBar"
import { UserSettings } from "@/components/ui/UserSettings"
import { ViewPost } from "@/components/ui/ViewPost"
import { useTheme } from "next-themes"
import { useRouter } from "next/navigation"
import { useEffect, useState } from "react"
import { AiOutlineSearch } from "react-icons/ai"
import { MdOutlineKeyboardDoubleArrowLeft, MdOutlineKeyboardDoubleArrowRight } from "react-icons/md"

export default function ViewPostPage() {
    const { theme, setTheme } = useTheme()
    const [isOpen, setIsOpen] = useState(true);
    const [mounted, setMounted] = useState(false);
    const [username, setUsername] = useState("");
    const router = useRouter();

    const handleLogoClick = () => {
        router.push("/home")
    }

    const handleHomeButton = () => {
        router.push("/home")
    }

    useEffect(() => {
        setMounted(true)
        setUsername(localStorage.getItem("username") || "Username error");

    }, [])

    if (!mounted)
        return null

    return (
        <div>
            <header className="fixed top-0 left-0 right-0 z-50 bg-gray-300 shadow-md p-4 flex items-center justify-between">
                <div className="flex items-center space-x-2">
                    <button
                        className="rounded-full"
                        onClick={handleLogoClick}
                    >
                        <img src="/logo.png" alt="Logo" className="w-10 h-10 object-cover" />
                    </button>
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
                                <li>
                                    <button
                                        className="block w-full p-4 text-left hover:bg-gray-400 rounded-md"
                                        onClick={handleHomeButton}
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
                            <ViewPost id={51251} userId={2} image="/logo.png" text="salut" title="Salutttt" score={0} author="raul" status={0} timestamp="14mar2025" />
                            <header className="font-bold">Comments</header>
                            <Comments author="mihnea" questionId={4} userScore={15} id={1} userId={1} text="Salut" timestamp="14mar2025" image="/img2.jpg" score={10} />
                            <Comments author="mihnea" questionId={4} userScore={15} id={1} userId={1} text="Salut" timestamp="14mar2025" image="/img2.jpg" score={10} />
                        </div>
                    </div>
                </main>
            </div>
        </div>
    )
}