"use client"

import { Button } from "@/components/ui/button"
import Image from "next/image"
import Typewriter from 'typewriter-effect';


export const Header = () => {
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
            </div>
            <div className="min-h-screen flex flex-col">
                <div className="flex-1 flex flex-col items-center justify-center">
                    <div className="mb-15 text-5xl font-bold">
                        <Typewriter
                            onInit={(typewriter) => {
                                typewriter
                                    .typeString('The place where ')
                                    .typeString('<span style="color: blue;">answers</span>') // Color for "answers"
                                    .typeString(' raise ')
                                    .typeString('<span style="color: blue;">questions</span>') // Color for "questions"
                                    .typeString('.')
                                    .pauseFor(1000)
                                    .deleteChars(15) // Delete "answers"
                                    .deleteChars(9) // Delete "questions"
                                    .typeString('<span style="color: blue;">questions</span>') // Reverse position
                                    .typeString(' raise ')
                                    .typeString('<span style="color: blue;">answers</span>') // Reverse position
                                    .typeString('.')
                                    .start();
                            }}
                            options={
                                {
                                    autoStart: true,
                                    loop: false,
                                    delay: 75
                                }
                            }
                        />
                    </div>
                    <div className="mt-15 flex gap-5">
                        <Button size="huge">Log in</Button>
                        <Button size="huge">Sign in</Button>
                    </div>
                </div>
            </div>
        </header>
    )
}