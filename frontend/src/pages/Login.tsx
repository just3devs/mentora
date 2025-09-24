import React from "react";
import "./Login.css";
import LoginLeft from "../components/LoginLeft.tsx";
import LoginRight from "../components/LoginRight.tsx";


const LoginPage: React.FC = () => {
    return (
        <div className="login-container">
            <div className="area">
                <LoginLeft />
                <LoginRight />
            </div>

        </div>
    );
};

export default LoginPage;
