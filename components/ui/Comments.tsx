"use client"

import { FaArrowDown, FaArrowUp } from "react-icons/fa";
import { CiMenuKebab } from "react-icons/ci";
import axios from "axios";

interface AnswerProps {
    id: number;
    userId: number;
    questionId: number;
    author: string;
    userScore: number
    text: string;
    timestamp: string;
    score: number;
    image?: string;
}



export const Comments = ({ id, userId, questionId, author, userScore, text, timestamp, image, score }: AnswerProps) => {

    const voteUpAnswer = () => {
        //     const url = "http://localhost:8080/question/voteUpById"
        //     const voteRequest = {
        //         'userId': Number(localStorage.getItem("userId")),
        //         'questionId': Number(id)
        //     }

        //     axios.put(url, voteRequest)
        //         .then((response) => {

        //             console.log(response.data)

        //             if (response.data === "Voted up") {
        //                 setAnswerScore(score + 1);
        //             }

        //             if (response.data === "Vote up removed") {
        //                 setAnswerScore(score - 1)
        //             }
        //         })
    }

    const voteDownAnswer = () => {
        //     const url = "http://localhost:8080/question/voteDownById"
        //     const voteRequest = {
        //         'userId': Number(localStorage.getItem("userId")),
        //         'questionId': Number(id)
        //     }

        //     axios.put(url, voteRequest)
        //         .then((response) => {

        //             console.log(response.data)

        //             if (response.data === "Voted down") {
        //                 setAnswerScore(score - 1)
        //             }

        //             if (response.data === "Vote down removed") {
        //                 setAnswerScore(score + 1)
        //             }
        //         })
    }
    return (
        <div className="p-4 bg-white shadow-md rounded-lg max-w-2xl mx-auto w-full">
            <div className="flex justify-between text-sm text-gray-600">
                <header className="flex flex-row items-center">
                    <h1>{author}</h1>
                    <span className="ml-2">{userScore}</span>
                </header>
                <h1>{timestamp}</h1>
            </div>

            {image && (
                <div className="w-1/2 mx-auto mt-2">
                    <img src={image} alt="image" className="w-full h-auto rounded-lg object-cover  " />
                </div>
            )}

            <p className="mt-4">{text}</p>

            <div className="flex justify-between items-center mt-4">
                <div className="flex items-center gap-2">
                    <button className="text-green-500" onClick={voteUpAnswer}><FaArrowUp /></button>
                    <label className="font-bold">{score}</label>
                    <button className="text-red-500" onClick={voteDownAnswer}><FaArrowDown /></button>
                </div>

                <button className="text-gray-500 text-xl rounded-md"><CiMenuKebab /></button>
            </div>

        </div>
    )
}