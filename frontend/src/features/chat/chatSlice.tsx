import {createSlice, type PayloadAction} from "@reduxjs/toolkit";

export interface ChatMessage {
    id: number;
    role: "user" | "bot";
    content: string;
}

interface ChatState {
    messages: ChatMessage[];
    typing: boolean;
    idCounter: number;
}

const initialState: ChatState = {
    messages: [], typing: false, idCounter: 1,
};

const chatSlice = createSlice({
    name: "chat", initialState, reducers: {
        addUserMessage: (state, action: PayloadAction<string>) => {
            state.messages.push({
                id: state.idCounter++, role: "user", content: action.payload,
            });
        }, addBotMessage: (state, action: PayloadAction<string>) => {
            state.messages.push({
                id: state.idCounter++, role: "bot", content: action.payload,
            });
        }, setTyping: (state, action: PayloadAction<boolean>) => {
            state.typing = action.payload;
        }, resetChat: () => initialState,
    },
});

export const {addUserMessage, addBotMessage, setTyping, resetChat} = chatSlice.actions;
export default chatSlice.reducer;