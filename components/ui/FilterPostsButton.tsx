"use client"

import { AiOutlineSearch } from "react-icons/ai"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "./dialog"
import { Input } from "./input"
import { Checkbox } from "@mui/material"
import { Button } from "./button"
import { useState } from "react"

export const FilterPostsButton = () => {
    const [isFilterDropdownOpen, setIsFilterDropdownOpen] = useState(false);

    const toggleFilterDropdown = () => {
        setIsFilterDropdownOpen(!isFilterDropdownOpen)
    }

    return (
        <Dialog>
            <DialogTrigger asChild>
                <button
                    onClick={toggleFilterDropdown}
                    className="px-6 py-2 bg-gray-400 rounded-md hover:bg-gray-300"
                >
                    Filter
                </button>
            </DialogTrigger>

            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>{"Filter the Posts"}</DialogTitle>
                </DialogHeader>
                <div className="grid gap-4 py-4">
                    <div className="grid grid-cols-4 items-center gap-4">
                        <label htmlFor="tags" className="text-sm font-medium leading-none text-right">
                            Tags
                        </label>
                        <Input
                            id="tags"
                            // value={username}
                            // onChange={(e) => setUsername(e.target.value)}
                            placeholder="Search by 1 tag"
                            className="col-span-3"
                        />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                        <label htmlFor="searchUser" className="text-sm font-medium leading-none text-right">
                            Seach by User
                        </label>
                        <Input
                            id="searchUser"
                            // value={password}
                            // onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter username"
                            className="col-span-3"
                        />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                        <label htmlFor="searchUser" className="text-sm font-medium leading-none text-right">
                            Show Only My Posts
                        </label>
                        <Checkbox />
                    </div>

                </div>
                {/* {error && <p className="text-red-500">{error}</p>} */}
                <Button onClick={() => { }}>
                    Filter
                </Button>
            </DialogContent>
        </Dialog>
    )
}





