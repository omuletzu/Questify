"use client"

import { AddPostButton } from "@/components/ui/AddPostButton"
import { Comments } from "@/components/ui/Comments"
import { FilterPostsButton } from "@/components/ui/FilterPostsButton"
import { ScoreLabel } from "@/components/ui/Score"
import { SearchPostsBar } from "@/components/ui/SearchPostsBar"
import { UserSettings } from "@/components/ui/UserSettings"
import { ViewPost } from "@/components/ui/ViewPost"
import { useRouter, useSearchParams } from "next/navigation"
import { useEffect, useState } from "react"
import { AiOutlineSearch } from "react-icons/ai"
import { MdOutlineKeyboardDoubleArrowLeft, MdOutlineKeyboardDoubleArrowRight } from "react-icons/md"
import axios from "axios"

interface PostProps {
    userId: number;
    userScore: number;
}

interface Answer {
    id: number;
    userId: number;
    text: string;
    status: number;
    timestamp: string;
}

interface AnswerProps {
    question: Answer
}

export default function ViewPostPage() {
    const [isOpen, setIsOpen] = useState(true);
    const [mounted, setMounted] = useState(false);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const router = useRouter();

    const searchParams = useSearchParams();

    const questionId = Number(searchParams.get("questionId"));
    const userId = Number(searchParams.get("userId"));
    const text = searchParams.get("text") || "";
    const title = searchParams.get("title") || "";
    const score = Number(searchParams.get("score"));
    const status = Number(searchParams.get("status"));
    const timestamp = searchParams.get("timestamp") || "";

    const [username, setUsername] = useState("");
    const [answerList, setAnswerList] = useState<Answer[]>([]);

    const [searchBarText, setSearchBarText] = useState("");

    const [displayFiltered, setDisplayFiltered] = useState(false);

    const setSearchText = (text: string) => {
        setSearchBarText(text);
    }

    const fetchAnswers = async () => {
        const url = "http://localhost:8080/answers/getByQuestionId";
        const requestData = {
            'questionId': questionId,
        };

        axios.get(url, {
            params: requestData
        })
            .then((response) => {
                const fetchedAnswers = response.data;
                console.log(searchParams.get("questionId"));
                setAnswerList(fetchedAnswers);
            })
            .catch((err) => {

            })
    }

    useEffect(() => {
        setMounted(true)
        setUsername(localStorage.getItem("username")!!);
        fetchAnswers();
    }, [])

    useEffect(() => {
        const handleKeyDown = (event: KeyboardEvent) => {
            if (event.key === "Escape") {
                router.back();
            }
        };

        document.addEventListener("keydown", handleKeyDown);

        return () => {
            document.removeEventListener("keydown", handleKeyDown);
        }
    }, [])

    if (!mounted)
        return null

    return (
        <div>
            <header className="fixed top-0 left-0 right-0 z-50 bg-gray-300 shadow-md p-4 flex items-center justify-between">
                <div className="flex items-center space-x-2">
                    <button
                        className="rounded-full"
                        onClick={() => router.push("/home")}
                    >
                        <img src="/logo.png" alt="Logo" className="w-10 h-10 object-cover" />
                    </button>
                    <h1 className="text-2xl font-extrabold text-blue-900">Questify</h1>
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
                <div className={`flex-1 p-6 overflow-y-auto transition-all duration-300 ease-in-out ${isOpen ? "ml-[300px]" : "ml-[50px]"}`}>
                    {/* postari */}
                    <div className="mt-8">
                        <div className="space-y-6 flex flex-row">
                            <ViewPost id={questionId} userId={userId} text={text} title={title} score={score} author={localStorage.getItem("username")!!} status={status} timestamp={timestamp} />
                            <div>
                                <header
                                    className="font-bold text-2xl"
                                >
                                    Comments
                                </header>

                                <div className="py-2 space-y-4">
                                    <Comments id={1} userId={1} author="raul" questionId={1} text="ce???" timestamp="Now" />
                                    <Comments id={1} userId={1} author="raul" questionId={1} text="ce???" timestamp="Now" />
                                    <Comments id={1} userId={1} author="raul" questionId={1} text="ce???" timestamp="Now" />
                                    <Comments id={1} userId={1} author="raul" questionId={1} text="ce???" timestamp="Now" />
                                    <Comments id={1} userId={1} author="raul" questionId={1} text="ce???" timestamp="Now" />
                                    <Comments id={1} userId={1} author="raul" questionId={1} text="ce???" timestamp="Now" />
                                    <Comments id={1} userId={1} author="raul" questionId={1} text="ce???" timestamp="Now" />
                                    <Comments id={1} userId={1} author="raul" questionId={1} text="ce???" timestamp="Now" />
                                    <Comments id={1} userId={1} author="raul" questionId={1} text="ce???" timestamp="Now" />
                                    {
                                        answerList.map((item) => (
                                            <Comments key={item.id} id={item.id} userId={item.userId} author={localStorage.getItem("username")!!} questionId={questionId} text={item.text} timestamp={item.timestamp} />
                                        ))
                                    }
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}