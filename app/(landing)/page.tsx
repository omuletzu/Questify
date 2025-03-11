"use client"

import { Button } from "@/components/ui/button"
import Typewriter from 'typewriter-effect';

export default function Home() {
  return (
    <div className="h-[400px] flex flex-col">
      <div className="flex-1 flex flex-col items-center justify-center">
        <div className="mb-20 text-5xl font-bold">
          <Typewriter
            onInit={(typewriter) => {
              typewriter
                .typeString('The place where ')
                .typeString('<span style="color: blue;">answers</span>')
                .typeString(' raise ')
                .typeString('<span style="color: blue;">questions</span>')
                .typeString('.')
                .pauseFor(1000)
                .deleteChars(15)
                .deleteChars(9)
                .typeString('<span style="color: blue;">questions</span>')
                .typeString(' raise ')
                .typeString('<span style="color: blue;">answers</span>')
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
        <div className="mt-15 flex flex-col gap-5">
          <Button variant="loginBtn" size="huge2" className="text-lg hover:bg-blue-700">Log in</Button>
          <Button variant="signBtn" className="text-lg hover:bg-gray-800" size="huge2">Sign in</Button>
        </div>
      </div>
    </div>
  )
}
