"use client"

import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import Typewriter from 'typewriter-effect';
import axios from 'axios';

export default function Home() {
  const [isAuthModalOpen, setIsAuthModalOpen] = useState(false);
  const [isSignUp, setIsSignUp] = useState(false); //true pt signin si false pt login
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [error, setError] = useState("");

  const handleAuth = async () => {
    try {
      const url = "http://localhost:8079/auth/connect";
      const userData = {
        'action': isSignUp ? "register" : "login",
        'username': username,
        'password': password,
        'email': isSignUp ? email : "",
        'phone': isSignUp ? phone : ""
      };

      console.log(url);

      const response = axios.post(url, userData, {
        headers: {
          'Content-Type': 'application/json'
        }
      });

      const [jwtToken, userReponse] = (await response).data;

      //TODO mesaj in functie de logat cu succes sau nu

    } catch (eroare: any) {
      setError(eroare.message || "An error occurred");
    }
  }

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

          {/* buton de login */}
          <Dialog open={isAuthModalOpen} onOpenChange={setIsAuthModalOpen}>
            <DialogTrigger asChild>
              <Button
                variant="loginBtn"
                size="huge2"
                className="text-lg hover:bg-blue-700"
                onClick={() => setIsSignUp(false)}
              >
                Log in
              </Button>
            </DialogTrigger>

            {/* buton de signin */}
            <DialogTrigger asChild>
              <Button
                variant="signBtn"
                className="text-lg hover:bg-gray-800"
                size="huge2"
                onClick={() => setIsSignUp(true)} // Setăm modul de Sign In
              >
                Sign in
              </Button>
            </DialogTrigger>

            <DialogContent className="sm:max-w-[425px]">
              <DialogHeader>
                <DialogTitle>{isSignUp ? "Sign in" : "Log in"}</DialogTitle>
                <DialogDescription>
                  {isSignUp ? "Completați formularul pentru a vă înregistra." : "Introduceți username-ul și parola pentru a vă autentifica."}
                </DialogDescription>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div className="grid grid-cols-4 items-center gap-4">
                  <label htmlFor="username" className="text-sm font-medium leading-none text-right">
                    Username
                  </label>
                  <Input
                    id="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="john_doe"
                    className="col-span-3"
                  />
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <label htmlFor="password" className="text-sm font-medium leading-none text-right">
                    Parola
                  </label>
                  <Input
                    id="password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="********"
                    className="col-span-3"
                  />
                </div>
                {isSignUp && (
                  <>
                    <div className="grid grid-cols-4 items-center gap-4">
                      <label htmlFor="email" className="text-sm font-medium leading-none text-right">
                        Email
                      </label>
                      <Input
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
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
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                        placeholder="+40712345678"
                        className="col-span-3"
                      />
                    </div>
                  </>
                )}
              </div>
              {error && <p className="text-red-500">{error}</p>}
              <Button onClick={handleAuth}>
                {isSignUp ? "Înregistrare" : "Autentificare"}
              </Button>
            </DialogContent>
          </Dialog>

        </div>
      </div>
    </div >
  )
}