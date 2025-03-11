import { ForumHeader } from "@/components/ui/forumHeader";
import { Header } from "../../components/ui/header";

type Props = {
    children: React.ReactNode;
};

const Layout = ({ children }: Props) => {
    return (
        <div className="bg-gray-200 min-h-screen flex flex-col">
            <ForumHeader />
            <main className="flex-1 flex flex-col items-start justify-items-start">
                {children}
            </main>
        </div>
    )
}

export default Layout;