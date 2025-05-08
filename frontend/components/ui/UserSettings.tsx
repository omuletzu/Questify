"use client"

import { FaUser } from "react-icons/fa"
import { Button } from "./button"
import { Dialog, DialogContent, DialogTrigger } from "./dialog"
import { useRouter } from "next/navigation"

export const UserSettings = () => {

    const router = useRouter();

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
                    <div className="flex flex-col">
                        <Button
                            onClick={() => {
                                localStorage.setItem("jwtToken", "");
                                localStorage.setItem("username", "");
                                router.push("/")
                            }}
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