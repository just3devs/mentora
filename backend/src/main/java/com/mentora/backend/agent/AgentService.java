package com.mentora.backend.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    private final ChatClient chatClient;

    public AgentService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ChatResponse getResponse(ChatRequest req) {
        var prompt = new Prompt(
                new SystemMessage("You are a concise, helpful assistant for Java/Spring developers."),
                new UserMessage(req.prompt())
        );

        var content = chatClient.prompt(prompt).call().content();

        return new ChatResponse(content);
    }
}
