"use client"

import { useParams } from "next/navigation"

export default function ViewPostPage() {
    const { postId } = useParams();
    return (
        <div>
            Post Id: {postId}
        </div>
    )
}