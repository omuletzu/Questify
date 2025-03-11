"use client"

import Image from "next/image"
import { useTheme } from "next-themes"
import { useState, useEffect } from 'react'
import { FaUser } from 'react-icons/fa'
import { Button } from "./button"
import { ScoreLabel } from "./Score"

export const ForumHeader = () => {
    const [mounted, setMounted] = useState(false);
    const { theme, setTheme } = useTheme()
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);

    useEffect(() => {
        setMounted(true)
    }, [])

    if (!mounted)
        return null

    const toggleDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    }

    return (
        <header className="bg-gray-300 h-15 w-full border-b-2 border-slate-200 px-4">
            <div className="lg:max-w-screen-lg mx-auto flex items-center justify-between h-full">
                <div className="pt-8 pl-4 pb-7 flex items-center gap-x-3">
                    <Image src="/logo.png" height={40} width={40} alt="logo" />
                    <h1 className="text-2xl font-extrabold text-blue-900 tracking-wide">
                        Questify
                    </h1>
                </div>

                <div className="flex items-center gap-x-4 ml-auto">
                    <select value={theme} onChange={e => setTheme(e.target.value)}>
                        <option value="system">System</option>
                        <option value="light">Light</option>
                        <option value="dark">Dark</option>
                    </select>

                    <div className="mr-5">
                        <ScoreLabel />
                    </div>
                    
                    <div className="relative">
                        <button
                            onClick={toggleDropdown}
                            className="flex items-center justify-center w-10 h-10 rounded-full border-2 border-gray-400 hover:bg-gray-200 transition-all"
                        >
                            <FaUser size={24} className="text-gray-800" />
                        </button>
                        {isDropdownOpen && (
                            <div className="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded-lg shadow-lg z-10">
                                <ul className="py-2">
                                    <li>
                                        <a href="#" className="block px-4 py-2 text-gray-800 hover:bg-gray-200">
                                            Setări cont
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#" className="block px-4 py-2 text-gray-800 hover:bg-gray-200">
                                            Log out
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </header >
    )
}