"use client"

export const Sidebar = () => {
    return (
        <aside className="w-[300px] bg-gray-500">
            <h2 className="text-xl font-bold">Sidebar</h2>
            <ul className="mt-4 space-y-2">
                <li><a href="#" className="block p-2 hover:bg-gray-700">btn1</a></li>
                <li><a href="#" className="block p-2 hover:bg-gray-700">btn2</a></li>
                <li><a href="#" className="block p-2 hover:bg-gray-700">btn3</a></li>
            </ul>
        </aside>
    )
}