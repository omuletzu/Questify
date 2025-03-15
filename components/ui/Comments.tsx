"use client"

import { FaArrowDown, FaArrowUp, FaRegEdit } from "react-icons/fa";
import { CiMenuKebab } from "react-icons/ci";
import axios from "axios";
import { useScrollTrigger } from "@mui/material";
import { useEffect, useState } from "react";
import { Button } from "./button";
import { MdDeleteOutline } from "react-icons/md";

interface AnswerProps {
    id: number;
    userId: number;
    questionId: number;
    author: string;
    text: string;
    timestamp: string;
}

export const Comments = ({ id, userId, questionId, author, text, timestamp }: AnswerProps) => {

    const [answerScore, setAnswerScore] = useState(0);

    const voteUpAnswer = () => {
        const url = "http://localhost:8080/answers/voteUpById"
        const voteRequest = {
            userId: Number(localStorage.getItem("userId")),
            answerId: Number(id)
        }

        axios.put(url, voteRequest)
            .then((response) => {

                if (response.data === "Voted up") {
                    setAnswerScore(answerScore + 1);
                }

                if (response.data === "Vote up removed") {
                    setAnswerScore(answerScore - 1)
                }
            })
    }

    const voteDownAnswer = () => {
        const url = "http://localhost:8080/answers/voteDownById"
        const voteRequest = {
            userId: Number(localStorage.getItem("userId")),
            answerId: Number(id)
        }

        axios.put(url, voteRequest)
            .then((response) => {

                console.log(response.data)

                if (response.data === "Voted down") {
                    setAnswerScore(answerScore - 1)
                }

                if (response.data === "Vote down removed") {
                    setAnswerScore(answerScore + 1)
                }
            })
    }

    const fetchAnswerData = () => {
        const url = "http://localhost:8080/answers/getAnswerScoreByAnswerId"
        const voteRequest = {
            answerId: Number(id)
        }

        axios.get(url, {
            params: voteRequest
        })
            .then((response) => {
                console.log(response.data);
                setAnswerScore(response.data)
            })
    }

    useEffect(() => {
        fetchAnswerData();
    }, [id]);

    return (
        <div className="p-4 bg-gray-100 rounded-lg max-w-2xl mx-auto w-[500px]">
            <div className="flex justify-between text-sm text-gray-600">
                <header className="flex flex-row items-center">
                    <h1>{author}</h1>
                    <span className="ml-2">{answerScore}</span>
                </header>
                <h1>{timestamp}</h1>
            </div>

            {/* {image && (
                <div className="w-1/2 mx-auto mt-2">
                    <img src={image} alt="image" className="w-full h-auto rounded-lg object-cover  " />
                </div>
            )} */}

            <p className="mt-4">{text}</p>

            <div className="flex justify-between items-center mt-4">
                <div className="flex items-center gap-2">
                    <button className="text-green-500" onClick={voteUpAnswer}><FaArrowUp /></button>
                    <label className="font-bold">{answerScore}</label>
                    <button className="text-red-500" onClick={voteDownAnswer}><FaArrowDown /></button>
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

        </div>
    )
}