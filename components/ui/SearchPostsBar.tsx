"use client"

export const SearchPostsBar = () => {
    return (
        <div>
            <form className="w-[300px] relative">
                <div className="relative">
                    <input
                        type="search"
                        placeholder="Search Bar"
                        className="w-full p-2 rounded-full bg-gray-400"
                    />
                </div>
            </form >
        </div >
    )
}