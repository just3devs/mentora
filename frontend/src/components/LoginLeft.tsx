import React from "react";
import { FcGoogle } from "react-icons/fc";

const LoginLeft: React.FC = () => {
    return (
        <div className="login-left">
            <div className="login-box">
                <h1 className="login-title">Welcome</h1>
                <p className="login-subtitle">AI Mentor ile öğrenme yolculuğuna başla 🚀</p>

                <button className="google-btn">
                    <FcGoogle size={24}/> Google ile giriş yap
                </button>
            </div>
        </div>
    );
};

export default LoginLeft;
