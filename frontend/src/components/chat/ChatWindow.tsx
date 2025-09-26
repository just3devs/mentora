import React, {useEffect, useRef} from "react";
import {useDispatch, useSelector} from "react-redux";
import type {AppDispatch, RootState} from "../../app/store";
import Message from "./Message";
import InputArea from "./InputArea";
import TypingIndicator from "./TypingIndicator";
import {addBotMessage, addUserMessage, setTyping} from "../../features/chat/chatSlice";
import "./ChatWindow.css";

const ChatWindow: React.FC = () => {
    const dispatch = useDispatch<AppDispatch>();
    const {messages, typing} = useSelector((state: RootState) => state.chat);

    const messagesEndRef = useRef<HTMLDivElement | null>(null);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({behavior: "smooth"});
    };

    useEffect(() => {
        scrollToBottom();
    }, [messages, typing]);

    const handleSend = (msg: string) => {
        dispatch(addUserMessage(msg));
        dispatch(setTyping(true));

        // Mock code response
        setTimeout(() => {
            dispatch(setTyping(false));
            dispatch(addBotMessage(`Here's a JavaScript example with syntax highlighting:

\`\`\`javascript
function calculateTotal(items) {
  let total = 0;
  const tax = 0.08;
  
  for (let item of items) {
    total += item.price;
  }
  
  return total * (1 + tax);
}

const products = [
  { name: "Laptop", price: 999.99 },
  { name: "Mouse", price: 29.99 }
];

console.log("Total:", calculateTotal(products));
\`\`\``));
        }, 1200);
    };

    return (<div className="chat-window">
        {/* Header */}
        <div className="chat-header">
            <h1>Hello J3D</h1>
            <p>Start your learning journey with Mentora.</p>
        </div>

        {/* Messages Area */}
        <div className="messages-area">
            {messages.length === 0 && !typing && (<div className="empty-state">
                Hi there! What do you want to learn today?
            </div>)}

            {messages.map((msg) => (<div key={msg.id} className="message-spacing">
                <Message role={msg.role} content={msg.content}/>
            </div>))}
            {typing && <TypingIndicator/>}
            <div ref={messagesEndRef} className="messages-end"/>
        </div>

        {/* Input Area */}
        <InputArea onSend={handleSend}/>
    </div>);
};

export default ChatWindow;