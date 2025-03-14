"use client"

import { FaUser } from "react-icons/fa"
import { Button } from "./button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "./dialog"
import { Input } from "./input"

export const UserSettings = () => {
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
                                Username
                            </label>
                            <Input
                                id="username"
                                placeholder="john_doe"
                                className="col-span-3"
                            />
                        </div>
                        <div className="grid grid-cols-4 items-center gap-4">
                            <label htmlFor="email" className="text-sm font-medium leading-none text-right">
                                Email
                            </label>
                            <Input
                                id="email"
                                placeholder="john.doe@example.com"
                                className="col-span-3"
                            />
                        </div>
                        <div className="grid grid-cols-4 items-center gap-4">
                            <label htmlFor="phone" className="text-sm font-medium leading-none text-right">
                                Telefon
                            </label>
                            <Input
                                id="phone"
                                placeholder="+40712345678"
                                className="col-span-3"
                            />
                        </div>
                    </div>
                    <div className="flex justify-between">
                        <Button onClick={() => { }}>
                            Salvează
                        </Button>
                        <Button
                            onClick={() => { }}
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