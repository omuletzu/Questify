"use client"

import { FaArrowUp, FaArrowDown, FaRegEdit } from "react-icons/fa";
import { Button } from "./button";
import { CiMenuKebab } from "react-icons/ci";
import { useEffect, useState } from "react";
import axios, { Axios } from "axios";
import { useRouter } from "next/navigation";
import { MdDeleteOutline } from "react-icons/md";

interface PostProps {
    id: number;
    userId: number;
    title: string;
    author: string;
    text: string;
    status: number; //0="Sent" | 1="In progress" | 2="Solved";
    tags?: string;
    timestamp: string;
    score: number;
    image?: string;
}

export const ViewPost = ({ id, userId, title, author, text, status, tags, timestamp, score: initialScore, image }: PostProps) => {

    const [score, setScore] = useState(initialScore);
    const router = useRouter()

    const voteUp = () => {
        const url = "http://localhost:8080/question/voteUpById"
        const voteRequest = {
            'userId': Number(localStorage.getItem("userId")),
            'questionId': Number(id)
        }

        axios.put(url, voteRequest)
            .then((response) => {

                console.log(response.data)

                if (response.data === "Voted up") {
                    setScore(score + 1);
                }

                if (response.data === "Vote up removed") {
                    setScore(score - 1)
                }
            })
    }

    const voteDown = () => {
        const url = "http://localhost:8080/question/voteDownById"
        const voteRequest = {
            'userId': Number(localStorage.getItem("userId")),
            'questionId': Number(id)
        }

        axios.put(url, voteRequest)
            .then((response) => {

                console.log(response.data)

                if (response.data === "Voted down") {
                    setScore(score - 1)
                }

                if (response.data === "Vote down removed") {
                    setScore(score + 1)
                }
            })
    }

    useEffect(() => {
        const url = "http://localhost:8080/question/getQuestionScoreByQuestionId";

        axios.get(url, {
            params: { questionId: id }
        })
            .then((response) => {
                setScore(response.data);
            })
            .catch((err) => {
                console.error(err);
            });

    }, [id]);

    return (
        <div className="p-4 max-h-[calc(100vh-4rem)] overflow-hidden bg-white shadow-md rounded-lg max-w-2xl mx-auto w-[500px]">
            <div className="flex justify-between text-sm text-gray-600">
                <h1>{author}</h1>
                <h1>{status === 0 ? "Sent" : status === 1 ? "In progress" : "Solved"}</h1>
                <h1 className="z-15">{timestamp}</h1>
            </div>

            <h2 className="font-bold text-2xl mt-2">{title}</h2>

            {image && (
                <div className="w-1/2 mx-auto mt-2">
                    <img src={image} alt="image" className="w-full h-auto rounded-lg object-cover  " />
                </div>
            )}

            <p className="mt-4">{text}</p>

            <div className="flex justify-between items-center mt-4">
                <div className="flex items-center gap-2">
                    <button className="text-green-500" onClick={voteUp}><FaArrowUp /></button>
                    <label className="font-bold">{score}</label>
                    <button className="text-red-500" onClick={voteDown}><FaArrowDown /></button>
                </div>

                <div className="space-x-5">
                    <Button
                        className="bg-gray-500 text-white text-xl rounded-full"
                        size="xs"
                    >
                        <FaRegEdit />
                    </Button>
                    <Button
                        className="bg-red-500 text-white text-xl rounded-full"
                        size="xs"
                    >
                        <MdDeleteOutline />
                    </Button>
                </div>
            </div>

            <button>
                Tags: <span className="bg-gray-500 text-white rounded-full p-1 space-x-6">{tags}</span>
            </button>
        </div>
    )
}