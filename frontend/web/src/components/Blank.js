import { HashtagIcon, ArrowCircleRightIcon } from "@heroicons/react/outline";
import Message from "./Message";
import {  useRef, useState } from "react";

export const Blank = () => {
  const inputRef = useRef("");
  const chatRef = useRef(null);
    
    return (
      <>
        <div className="flex flex-col h-screen">
        </div>     
      </>
    )
}