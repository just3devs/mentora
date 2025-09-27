import React, {useEffect, useState} from "react";
import ChatWindow from "../components/chat/ChatWindow";

const ChatPage: React.FC = () => {
    const [userName, setUserName] = useState(null);

    useEffect(() => {
        fetch("/api/user")
            .then(response => response.json())
            .then(data => setUserName(data.name));
    }, []);

    const handleLogout = () => {
        window.location.href = "http://localhost:8080/logout";
    };

    return (
        <div>
            <div style={{ display: "flex", justifyContent: "space-between", padding: "10px" }}>
                <h1>Welcome, {userName}</h1>
                <button onClick={handleLogout}>Logout</button>
            </div>
            <ChatWindow />
        </div>
    );
};

export default ChatPage;