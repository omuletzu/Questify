"use client"

import { useEffect, useState } from "react";

export const ScoreLabel = () => {
    const [score, setScore] = useState<number | null>(null);

    useEffect(() => {
        const fetchScore = async () => {
            try {
                const userId = localStorage.getItem("userId");
                if (!userId) {
                    console.error("User ID is missing");
                    return;
                }
                const response = await fetch(`http://localhost:3000/users/scoreById?id=${userId}`);
                if (!response.ok) throw new Error("Server error");

                //api de la backend - TODO
                const data = await response.json();
                setScore(data);
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