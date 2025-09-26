import React from "react";

const TypingIndicator: React.FC = () => {
    return (<div className="flex justify-start">
        <div className="bg-gray-200 text-gray-600 rounded-2xl rounded-bl-none px-4 py-2 inline-flex space-x-1">
            <span className="animate-bounce">.</span>
            <span className="animate-bounce delay-150">.</span>
            <span className="animate-bounce delay-300">.</span>
        </div>
    </div>);
};

export default TypingIndicator;