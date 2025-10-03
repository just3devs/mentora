import React, {useRef, useState} from "react";
import {Send} from "lucide-react";
import "./InputArea.css";

interface InputAreaProps {
    onSend: (message: string) => void;
}

const InputArea: React.FC<InputAreaProps> = ({onSend}) => {
    const [input, setInput] = useState("");
    const textareaRef = useRef<HTMLTextAreaElement>(null);

    const handleSend = () => {
        if (input.trim()) {
            onSend(input);
            setInput(""); // clear input
            if (textareaRef.current) {
                textareaRef.current.style.height = 'auto';
            }
        }
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setInput(e.target.value);

        // Auto-resize textarea
        if (textareaRef.current) {
            textareaRef.current.style.height = 'auto';
            textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
        }
    };

    const handleKeyDown = (e: React.KeyboardEvent) => {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault();
            handleSend();
        }
    };

    return (<div className="input-area-container">
        <div className="input-area-wrapper">
            <div className={`input-area-box ${input ? 'focused' : ''}`}>
                    <textarea
                        ref={textareaRef}
                        className="input-area-textarea"
                        placeholder="What's on your mind?..."
                        value={input}
                        onChange={handleInputChange}
                        onKeyDown={handleKeyDown}
                        rows={1}
                    />
                <button
                    className={`input-area-button ${input.trim() ? 'active' : 'inactive'}`}
                    onClick={handleSend}
                    disabled={!input.trim()}
                >
                    <Send size={16}/>
                </button>
            </div>
        </div>
    </div>);
};

export default InputArea;