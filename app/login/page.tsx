"use client"

import { FollowList } from "@/components/ui/FollowList";
import { useState } from "react";

export default function ForumPage() {
    const [isOpen, setIsOpen] = useState(true);
    return (
        <div className="flex">

            {/* sidebar */}
            <aside className={`fixed left-0 h-full bg-gray-300 transition-all duration-300 ease-in-out ${isOpen ? "w-[300px]" : "w-[50px]"}`}>
                <button
                    onClick={() => setIsOpen(!isOpen)}
                    className="absolute top-4 right-4"
                >
                    {isOpen ? "◀" : "▶"}
                </button>

                {isOpen && (
                    <div>
                        <h2 className="text-xl font-bold">Sidebar</h2>
                        <ul className="mt-4 space-y-2">
                            <li><a href="#" className="block p-2 hover:bg-gray-700">btn1</a></li>
                            <li><a href="#" className="block p-2 hover:bg-gray-700">btn2</a></li>
                            <li><a href="#" className="block p-2 hover:bg-gray-700">btn3</a></li>
                        </ul>
                        {/* <FollowList /> */}
                    </div>
                )}
            </aside>


            <main className={`transition-all duration-300 ease-in-out flex-1 p-6 ${isOpen ? "ml-[300px]" : "ml-[50px]"}`}>
                {/* meniu add, search, filter*/}
                <div className={`flex justify-around space-x-6 transition-all duration-300 ease-in-out flex-1 p-6 ${isOpen ? "ml-[300px]" : "ml-[50px]"}`}>
                    <button className="px-6 bg-gray-400 rounded-md">+ Add</button>
                    <button className="px-6 bg-gray-400 rounded-md">Search</button>
                    <button className="px-6 bg-gray-400 rounded-md">Filter</button>
                </div>

                {/* postari */}
                {/* <h1 className="text-3xl font-bold"></h1> */}
                <div className="mt-4 space-y-4">
                    <div className="p-4 bg-white shadow-md rounded-lg">
                        <h2 className="text-xl font-semibold">Postare 1</h2>
                        <p>Acesta este un exemplu de postare.</p>
                    </div>
                    <div className="p-4 bg-white shadow-md rounded-lg">
                        <h2 className="text-xl font-semibold">Postare 2</h2>
                        <p>Acesta este un alt exemplu de postare.</p>
                    </div>
                </div>
            </main>

        </div>

    )
}