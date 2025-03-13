"use client"

import { CiImageOn } from "react-icons/ci"
import { Button } from "./button"
import { Input } from "./input"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "./dialog"
import { useState } from "react"

export const AddPostButton = () => {
    const [isAddDropdownOpen, setIsAddDropdownOpen] = useState(false);
    const [title, setTitle] = useState("")
    const [text, setText] = useState("")
    const [tags, setTags] = useState("")


    const toggleAddDropdown = () => {
        setIsAddDropdownOpen(!isAddDropdownOpen)
    }

    const handleAddQuestion = () => {
        const tagList = tags.split(/[ ,;]+/);

        const url = "hhtps://localhost:8080/question/add"
        const questionData = {
            'userId': localStorage.get("userId"),
            'title': title,
            'text': text,

        }
    }

    return (
        <Dialog>
            <DialogTrigger asChild>
                <button
                    onClick={toggleAddDropdown}
                    className="px-6 bg-gray-400 rounded-md hover:bg-gray-300"
                >
                    + Add
                </button>
            </DialogTrigger>
            <DialogContent className="flex flex-col items-start justify-start sm:max-w-[425px] lg:max-w-[1000px] lg:h-[500px] ">
                <DialogHeader>
                    <DialogTitle>{"Add a new Question"}</DialogTitle>
                </DialogHeader>
                <div className="grid gap-4 py-4">
                    <div className="grid grid-cols-4 items-center gap-4">
                        <label htmlFor="title" className="text-sm font-medium leading-none text-right">
                            Title*
                        </label>
                        <Input
                            id="title"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            placeholder="Question Title"
                            className="col-span-3 focus:outline-none"
                        />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                        <label htmlFor="text" className="text-sm font-medium leading-none text-right focus:outline-none">
                            Question Description*
                        </label>
                        <textarea
                            id="text"
                            value={text}
                            onChange={(e) => setText(e.target.value)}
                            placeholder="Write a description"
                            className="col-span-3 p-4 border border-gray-300 rounded-md resize-none focus:outline-none"
                            rows={6}
                        />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                        <label htmlFor="tags" className="text-sm font-medium leading-none text-right">
                            Tags separated by commas
                        </label>
                        <Input
                            id="tags"
                            value={tags}
                            onChange={(e) => setTags(e.target.value)}
                            placeholder="Put 1 or more tags"
                            className="col-span-3"
                        />
                    </div>

                </div>
                {/* {error && <p className="text-red-500">{error}</p>} */}
                <div className="grid grid-cols-4 items-center gap-12">
                    <Button
                        onClick={() => { }}
                        className="bg-blue-900 text-white px-4 py-2 rounded-md flex items-center space-x-2"
                    >
                        <span>Add Image</span>
                        <CiImageOn
                            className=""
                        />
                    </Button>
                </div>

                <div className="h-[200px] flex items-center justify-center">
                    <Button
                        onClick={handleAddQuestion}
                        className="text-white px-4 py-2 rounded-md"
                    >
                        Add Question
                    </Button>
                </div>

            </DialogContent>
        </Dialog>
    )
}