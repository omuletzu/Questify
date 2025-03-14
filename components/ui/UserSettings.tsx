"use client"

import { FaUser } from "react-icons/fa"
import { Button } from "./button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "./dialog"
import { useTheme } from "next-themes"
import { useEffect, useState } from "react"
import { useRouter } from "next/navigation"

export const UserSettings = () => {

    const { theme, setTheme } = useTheme()
    const [selectedTheme, setSelectedTheme] = useState(theme || "system");
    const router = useRouter();

    useEffect(() => {
        setSelectedTheme(theme || "system");
    }, [theme]);

    const handleThemeChange = (newTheme: string) => {
        setSelectedTheme(newTheme);
        setTheme(newTheme);
    };


    return (
        <div className="w-8 h-8 bg-gray-400 rounded-full flex items-center justify-center">
            <Dialog>
                <DialogTrigger asChild>
                    <button
                        className="flex items-center justify-center w-10 h-10 rounded-full hover:bg-gray-200 transition-all"
                    >
                        <FaUser size={24} className="text-gray-800" />
                    </button>
                </DialogTrigger>

                <DialogContent className="sm:max-w-[425px]">
                    <DialogHeader>
                        <DialogTitle>{"Setări Cont"}</DialogTitle>
                    </DialogHeader>
                    <div className="grid gap-4 py-4">
                        <div className="grid grid-cols-4 items-center gap-4">
                            <label htmlFor="username" className="text-sm font-medium leading-none text-right">
                                Theme
                            </label>
                            <div className="col-span-3 flex flex-col gap-2">
                                <label className="flex items-center gap-2 cursor-pointer">
                                    <input
                                        type="checkbox"
                                        checked={selectedTheme === "system"}
                                        onChange={() => handleThemeChange("system")}
                                        className="w-5 h-5"
                                    />
                                    System
                                </label>
                                <label className="flex items-center gap-2 cursor-pointer">
                                    <input
                                        type="checkbox"
                                        checked={selectedTheme === "light"}
                                        onChange={() => handleThemeChange("light")}
                                        className="w-5 h-5"
                                    />
                                    Light
                                </label>
                                <label className="flex items-center gap-2 cursor-pointer">
                                    <input
                                        type="checkbox"
                                        checked={selectedTheme === "dark"}
                                        onChange={() => handleThemeChange("dark")}
                                        className="w-5 h-5"
                                    />
                                    Dark
                                </label>
                            </div>
                        </div>
                    </div>
                    <div className="flex flex-col">
                        <Button
                            onClick={() => router.push("/")}
                            className="bg-red-500 text-white"
                        >
                            Log out
                        </Button>
                    </div>
                </DialogContent>
            </Dialog>
        </div>
    )
}