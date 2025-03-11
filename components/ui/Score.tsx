"use client"

import { useEffect, useState } from "react";

export const ScoreLabel = () => {
    const [score, setScore] = useState<number | null>(null);

    useEffect(() => {
        const fetchScore = async () => {
            try {
                const response = await fetch("...");        //api de la backend - TODO
                const data = await response.json();
                setScore(data.score);
            }
            catch (error) {
                console.error("Nu se poate afisa scorul, eroare: ", error);
            }
        };
        fetchScore();
    }, []);

    if (score === null)
        return <span>Loading...</span>

    return (
        <span className={`text-lg font-bold ${score >= 0 ? "text-green-600" : "text-red-600"}`} >
            {score >= 0 ? `+${score}` : `-${Math.abs(score)}`
            }
        </span >
    )
}