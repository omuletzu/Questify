"use client"

import { FaArrowUp, FaArrowDown } from "react-icons/fa";
import { Button } from "./button";

interface PostProps {
    id: number;
    userId: number;
    title: string;
    author: string;
    text: string;
    status: number; //0="Sent" | 1="In progress" | 2="Solved";
    tag: string;
    timestamp: string;
    score: number;
    image?: string;
}

export const Post = ({ id, userId, title, author, text, status, tag, timestamp, score, image }: PostProps) => {
    return (
        <div className="p-4 bg-white shadow-md rounded-lg">
            <div className="flex justify-between">
                <h1>{author}</h1>
                {status === 0 && (
                    <h1>Sent</h1>
                )}
                {status === 1 && (
                    <h1>In progress</h1>
                )}
                {status === 2 && (
                    <h1>Solved</h1>
                )}
                <h1>{timestamp}</h1>
            </div>
            <h2 className="text-bold text-3xl">{title}</h2>
            <p>{text}</p>
            <div className="flex justify-between">
                <div className="p-5">
                    <button><FaArrowUp /></button>
                    <label>{score}</label>
                    <button><FaArrowDown /></button>
                </div>
                <Button>Comments</Button>
                <button>⠇</button>
            </div>
        </div>
    )
}