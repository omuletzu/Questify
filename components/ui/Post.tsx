"use client"

interface PostProps {
    title: string;
    author: string;
    status: "Sent" | "In progress" | "Solved";
    tag: string;
    votes: number;
    image?: string;
}

export const Post = ({}) => {
    return (
        <div>
            Salut
        </div>
    )
}