import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';

import AboutPage from './pages/AboutPage';
import NotFoundPage from './pages/NotFoundPage';
import LoginPage from "./pages/Login";
import ChatPage from "./pages/ChatPage";

const AppRoutes: React.FC = () => (<Router>
    <Routes>
        <Route path="/" element={<LoginPage/>}/>
        <Route path="/about" element={<AboutPage/>}/>
        <Route path="/chat" element={<ChatPage/>}/>
        <Route path="*" element={<NotFoundPage/>}/>
    </Routes>
</Router>);

export default AppRoutes;