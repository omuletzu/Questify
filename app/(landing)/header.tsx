"use client"

import { Button } from "@/components/ui/button"
import Image from "next/image"
import { useTheme } from "next-themes"
import { useState, useEffect } from 'react'

export const Header = () => {
    const [mounted, setMounted] = useState(false);
    const { theme, setTheme } = useTheme()

    useEffect(() => {
        setMounted(true)
    }, [])

    if(!mounted)
        return null

    return (
        <header className="bg-gray-300 h-20 w-full border-b-2 border-slate-200 px-4">
            <div className="lg:max-w-screen-lg mx-auto flex items-center justify-between h-full">
                <div className="pt-8 pl-4 pb-7 flex items-center gap-x-3">
                    <Image src="/logo.png" height={40} width={40} alt="logo" />
                    <h1 className="text-2xl font-extrabold text-blue-900 tracking-wide">
                        Questify
                    </h1>
                </div>
                <div>
                    <Button>Log in</Button>
                </div>
                <div>
                    <select value={theme} onChange={e => setTheme(e.target.value)}>
                        <option value="system">System</option>
                        <option value="light">Light</option>
                        <option value="dark">Dark</option>
                    </select>
                </div>
            </div>
        </header>
    )
}