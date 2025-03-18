"use client"

import { useScrollTrigger } from "@mui/material"
import { useState } from "react"
import { AiOutlineSearch } from "react-icons/ai"

interface searchProp {
    setSearchBarText : (text : string) => void
}

export const SearchPostsBar = ({setSearchBarText} : searchProp) => {

    const [input, setInput] = useState("");

    return (
        <div>
            <form className="w-[300px] relative">
                <div className="relative">
                    <input
                        type="search"
                        placeholder="Search Bar"
                        className="w-full p-2 rounded-full bg-blue-400"
                        value={input}
                        onChange={(e) => {
                            setInput(e.target.value);
                            setSearchBarText(e.target.value)
                        }}
                    />
                </div>
            </form >
        </div >
    )
}