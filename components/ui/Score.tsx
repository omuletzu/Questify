"use client";

import { useEffect, useState } from "react";
import axios from "axios";
import { useRouter } from "next/navigation";

export const ScoreLabel = () => {
    const url = "http://localhost:8079/users/scoreById";
    const [score, setScore] = useState<number | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const router = useRouter();

    useEffect(() => {
        const userId = localStorage.getItem("userId");

        if (!userId) {
            console.error("User ID not found in localStorage");
            setLoading(false);
            setError("User ID not found");
            return;
        }

        const fetchData = async () => {
            try {
                const response = await axios.get(url, {
                    params: { userId },
                });

                if (response.data && typeof response.data === "number") {
                    setScore(response.data);
                } else {
                    setError("Invalid score data received");
                }
            } catch (err) {
                console.error("Error fetching score:", err);
                setError("Failed to fetch score");
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    if (loading) {
        return <span>Loading...</span>;
    }

    if (error) {
        return <span className="text-red-500">{error}</span>;
    }

    if (score === null) {
        return <span>No score available</span>;
    }

    return (
        <span
            className={`text-lg font-bold ${score >= 0 ? "text-green-600" : "text-red-600"
                }`}
        >
            {score >= 0 ? `+${score}` : `-${Math.abs(score)}`}
        </span>
    );
};