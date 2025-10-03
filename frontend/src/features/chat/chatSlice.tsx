import {createSlice, type PayloadAction} from "@reduxjs/toolkit";

export interface ChatMessage {
    id: number;
    role: "user" | "bot";
    content: string;
    timestamp: string;
}

export interface Chat {
    id: string;
    title: string;
    messages: ChatMessage[];
    createdAt: string;
    updatedAt: string;
}

interface ChatState {
    chats: Chat[];
    currentChatId: string | null;
    typing: boolean;
    searchQuery: string;
    sidebarOpen: boolean;
    idCounter: number;
}

const generateChatId = () => {
    return Date.now().toString() + Math.random().toString(36).substring(2, 9);
};

const generateTitle = (firstMessage: string) => {
    const words = firstMessage.trim().split(/\s+/).slice(0, 3);
    return words.join(' ');
};

// Mock Chats
const sampleChats: Chat[] = [{
    id: "1",
    title: "Create Html Game Environment...",
    messages: [],
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString()
}, {
    id: "2",
    title: "Apply To Leave For Emergency",
    messages: [],
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString()
}, {
    id: "3",
    title: "What Is UI UX Design?",
    messages: [],
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString()
}, {
    id: "4",
    title: "Create POS System",
    messages: [],
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString()
}, {
    id: "5",
    title: "What Is UX Audit?",
    messages: [],
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString()
}, {
    id: "6",
    title: "Create Chatbot GPT...",
    messages: [],
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString()
}, {
    id: "7",
    title: "How Chat GPT Work?",
    messages: [],
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString()
}];

// Mock Older Chats
const olderDate = new Date();
olderDate.setDate(olderDate.getDate() - 10);

const olderChats: Chat[] = [{
    id: "8",
    title: "Crypto Landing App Name",
    messages: [],
    createdAt: olderDate.toISOString(),
    updatedAt: olderDate.toISOString()
}, {
    id: "9",
    title: "Operator Grammar Types",
    messages: [],
    createdAt: olderDate.toISOString(),
    updatedAt: olderDate.toISOString()
}];

const initialState: ChatState = {
    chats: [...sampleChats, ...olderChats],
    currentChatId: null,
    typing: false,
    searchQuery: "",
    sidebarOpen: false,
    idCounter: 1,
};

const chatSlice = createSlice({
    name: "chat", initialState, reducers: {
        createNewChat: (state) => {
            const newChatId = generateChatId();
            const newChat: Chat = {
                id: newChatId,
                title: "New Chat",
                messages: [],
                createdAt: new Date().toISOString(),
                updatedAt: new Date().toISOString()
            };
            state.chats.unshift(newChat);
            state.currentChatId = newChatId;
        },

        selectChat: (state, action: PayloadAction<string>) => {
            state.currentChatId = action.payload;
        },

        addUserMessage: (state, action: PayloadAction<string>) => {
            const currentChat = state.chats.find(chat => chat.id === state.currentChatId);
            if (currentChat) {
                const newMessage: ChatMessage = {
                    id: state.idCounter++, role: "user", content: action.payload, timestamp: new Date().toISOString()
                };
                currentChat.messages.push(newMessage);

                if (currentChat.messages.length === 1) {
                    currentChat.title = generateTitle(action.payload);
                }

                currentChat.updatedAt = new Date().toISOString();
            }
        },

        addBotMessage: (state, action: PayloadAction<string>) => {
            const currentChat = state.chats.find(chat => chat.id === state.currentChatId);
            if (currentChat) {
                const newMessage: ChatMessage = {
                    id: state.idCounter++, role: "bot", content: action.payload, timestamp: new Date().toISOString()
                };
                currentChat.messages.push(newMessage);
                currentChat.updatedAt = new Date().toISOString();
            }
        },

        setTyping: (state, action: PayloadAction<boolean>) => {
            state.typing = action.payload;
        },

        clearAllChats: (state) => {
            state.chats = [];
            state.currentChatId = null;
        },

        deleteChat: (state, action: PayloadAction<string>) => {
            state.chats = state.chats.filter(chat => chat.id !== action.payload);
            if (state.currentChatId === action.payload) {
                state.currentChatId = state.chats.length > 0 ? state.chats[0].id : null;
            }
        },

        setSearchQuery: (state, action: PayloadAction<string>) => {
            state.searchQuery = action.payload;
        },

        updateChatTitle: (state, action: PayloadAction<{ chatId: string, newTitle: string }>) => {
            const {chatId, newTitle} = action.payload;
            const chatIndex = state.chats.findIndex(chat => chat.id === chatId);
            if (chatIndex !== -1) {
                const chat = state.chats[chatIndex];
                chat.title = newTitle;
                chat.updatedAt = new Date().toISOString();

                state.chats.splice(chatIndex, 1);
                state.chats.unshift(chat);
            }
        },

        toggleSidebar: (state) => {
            state.sidebarOpen = !state.sidebarOpen;
        },

    },
});

export const {
    createNewChat,
    selectChat,
    addUserMessage,
    addBotMessage,
    setTyping,
    clearAllChats,
    deleteChat,
    setSearchQuery,
    updateChatTitle,
    toggleSidebar,
} = chatSlice.actions;

export default chatSlice.reducer;