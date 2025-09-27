import React, {useState} from 'react';
import {Check, Edit2, MessageCircle, Plus, Search, Settings, Trash2, X} from 'lucide-react';
import {useDispatch, useSelector} from 'react-redux';
import type {AppDispatch, RootState} from '../../../app/store';
import {
    type Chat,
    clearAllChats,
    createNewChat,
    deleteChat,
    selectChat,
    setSearchQuery,
    updateChatTitle
} from '../../../features/chat/chatSlice';
import './Sidebar.css';

interface SidebarProps {
    isOpen: boolean;
    onToggle: () => void;
}

const Sidebar: React.FC<SidebarProps> = ({isOpen, onToggle}) => {
    const dispatch = useDispatch<AppDispatch>();
    const {chats, currentChatId, searchQuery} = useSelector((state: RootState) => state.chat);
    const [editingChatId, setEditingChatId] = useState<string | null>(null);
    const [editTitle, setEditTitle] = useState('');

    const handleNewChat = () => {
        dispatch(createNewChat());
    };

    const handleSelectChat = (chatId: string) => {
        dispatch(selectChat(chatId));
    };

    const handleClearAll = () => {
        if (window.confirm('Are you sure you want to clear all conversations?')) {
            dispatch(clearAllChats());
        }
    };

    const handleDeleteChat = (chatId: string, e: React.MouseEvent) => {
        e.stopPropagation();
        if (window.confirm('Are you sure you want to delete this conversation?')) {
            dispatch(deleteChat(chatId));
        }
    };

    const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
        dispatch(setSearchQuery(e.target.value));
    };

    const startEditing = (chatId: string, currentTitle: string, e: React.MouseEvent) => {
        e.stopPropagation();
        setEditingChatId(chatId);
        setEditTitle(currentTitle);
    };

    const saveTitle = () => {
        if (editingChatId && editTitle.trim()) {
            dispatch(updateChatTitle({chatId: editingChatId, newTitle: editTitle.trim()}));
        }
        setEditingChatId(null);
        setEditTitle('');
    };

    const cancelEdit = () => {
        setEditingChatId(null);
        setEditTitle('');
    };

    const handleEditKeyDown = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter') {
            saveTitle();
        } else if (e.key === 'Escape') {
            cancelEdit();
        }
    };

    const filteredChats = chats.filter(chat => chat.title.toLowerCase().includes(searchQuery.toLowerCase()));

    const sevenDaysAgo = new Date();
    sevenDaysAgo.setDate(sevenDaysAgo.getDate() - 7);

    const recentChats = filteredChats.filter(chat => new Date(chat.updatedAt) >= sevenDaysAgo);

    const olderChats = filteredChats.filter(chat => new Date(chat.updatedAt) < sevenDaysAgo);

    const renderChatItem = (chat: Chat) => (<div
        key={chat.id}
        className={`chat-item ${currentChatId === chat.id ? 'active' : ''}`}
        onClick={() => handleSelectChat(chat.id)}
    >
        <MessageCircle size={16} className="chat-icon"/>

        {editingChatId === chat.id ? (<div className="edit-title-container">
            <input
                type="text"
                value={editTitle}
                onChange={(e) => setEditTitle(e.target.value)}
                onKeyDown={handleEditKeyDown}
                className="edit-title-input"
                autoFocus
            />
            <button onClick={saveTitle} className="save-title-button">
                <Check size={12}/>
            </button>
            <button onClick={cancelEdit} className="cancel-title-button">
                <X size={12}/>
            </button>
        </div>) : (<>
            <span className="chat-title">{chat.title}</span>
            <div className="chat-actions">
                <button
                    className="edit-chat-button"
                    onClick={(e) => startEditing(chat.id, chat.title, e)}
                >
                    <Edit2 size={12}/>
                </button>
                <button
                    className="delete-chat-button"
                    onClick={(e) => handleDeleteChat(chat.id, e)}
                >
                    <Trash2 size={12}/>
                </button>
            </div>
        </>)}
    </div>);

    return (<>
        {/* Overlay for mobile */}
        {isOpen && <div className="sidebar-overlay" onClick={onToggle}/>}

        <div className={`sidebar ${isOpen ? 'open' : ''}`}>
            {/* Header */}
            <div className="sidebar-header">
                <h2 className="sidebar-title">Mentora AI</h2>
            </div>

            {/* Action Buttons */}
            <div className="action-buttons">
                <button className="new-chat-button" onClick={handleNewChat}>
                    <Plus size={16}/>
                    Create a new chat
                </button>
            </div>

            {/* Search Input */}
            <div className="search-container">
                <div className="search-input-wrapper">
                    <Search size={16} className="search-icon"/>
                    <input
                        type="text"
                        placeholder="Search conversations..."
                        value={searchQuery}
                        onChange={handleSearch}
                        className="search-input"
                    />
                </div>
            </div>

            {/* Conversations Header */}
            <div className="conversations-header">
                <span>Your conversations</span>
                <button onClick={handleClearAll} className="clear-all-button">
                    Clear All
                </button>
            </div>

            {/* Chat List */}
            <div className="chat-list-container">
                <div className="chat-list">
                    {recentChats.length > 0 && (<>
                        {recentChats.map(renderChatItem)}
                    </>)}

                    {olderChats.length > 0 && (<>
                        <div className="chat-section-title">Last 7 Days</div>
                        <div className="older-chats-container">
                            {olderChats.map(renderChatItem)}
                        </div>
                    </>)}

                    {filteredChats.length === 0 && (<div className="no-chats">
                        No conversations found
                    </div>)}
                </div>
            </div>

            {/* Footer */}
            <div className="sidebar-footer">
                <button className="settings-button">
                    <Settings size={16}/>
                    Settings
                </button>

                <div className="user-profile">
                    <div className="user-avatar">
                        <span>J3D</span>
                    </div>
                    <span className="user-name">Just3Devs</span>
                </div>
            </div>
        </div>
    </>);
};

export default Sidebar;