"use client"

import { FaArrowUp, FaArrowDown } from "react-icons/fa";
import { Button } from "./button";
import { CiMenuKebab } from "react-icons/ci";

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

export const Post = ({ id, userId, title, author, text, status, tags, timestamp, score, image }: PostProps) => {
    const handleViewPost = () => {
        
    }

    return (
        <div className="p-4 bg-white shadow-md rounded-lg max-w-2xl mx-auto w-full">
            <div className="flex justify-between text-sm text-gray-600">
                <h1>{author}</h1>
                <h1>{status === 0 ? "Sent" : status === 1 ? "In progress" : "Solved"}</h1>
                <h1>{timestamp}</h1>
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
                    <button className="text-green-500"><FaArrowUp /></button>
                    <label className="font-bold">{score}</label>
                    <button className="text-red-500"><FaArrowDown /></button>
                </div>
                <Button
                    onClick={() => { }}
                    size="sm"
                    className="bg-gray-500 rounded-full"
                >
                    View post
                </Button>

                <button className="text-gray-500 text-xl rounded-md"><CiMenuKebab /></button>
            </div>

            <button>
                Tags: <span className="bg-gray-500 text-white rounded-full p-1 space-x-6">{tags}</span>
            </button>
        </div>
    )
}