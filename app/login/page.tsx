"use client"

import { FollowList } from "@/components/ui/FollowList";
import { Post } from "@/components/ui/Post";
import { useState } from "react";

export default function ForumPage() {
    const [isOpen, setIsOpen] = useState(true);
    const text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
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
                    <button className="px-6 bg-gray-400 rounded-md hover:bg-gray-300">+ Add</button>
                    <input
                        type="text"
                        value="Search Box"
                    />
                    <button className="px-6 bg-gray-400 rounded-md hover:bg-gray-300">Search</button>
                    <button className="px-6 bg-gray-400 rounded-md hover:bg-gray-300">Filter</button>
                </div>

                {/* postari */}
                {/* <h1 className="text-3xl font-bold"></h1> */}
                <div>
                    <Post id={5} userId={3} title="Postarea nr 1" author="User12341" text={text1} status={2} tag="C" timestamp="11 Mar 2025" score={5} />
                </div>
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