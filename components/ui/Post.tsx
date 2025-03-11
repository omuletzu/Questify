"use client"

interface PostProps {
    id: number;
    userId: number;
    title: string;
    author: string;
    status: number; //0="Sent" | 1="In progress" | 2="Solved";
    tag: string;
    score: number;
    image?: string;
}

export const Post = ({ id, userId, title, author, status, tag, score, image }: PostProps) => {
    return (
        <div>
            
            <h2 className="text-bold text-lg">{title}</h2>
            
        </div>
    )
}