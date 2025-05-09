"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { safeAxios } from "@/services/axiosInstance";

export const ScoreLabel = () => {
  const axiosInstance = safeAxios();

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
 
    axiosInstance
      .get(url, {
        params: { userId: userId },
      })
      .then((response) => {
        setScore(response.data);
        setLoading(false);
      })

      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
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
      className={`text-lg font-bold ${
        score >= 0 ? "text-green-600" : "text-red-600"
      }`}
    >
      {score >= 0 ? `+${score}` : `-${Math.abs(score)}`}
    </span>
  );
};
