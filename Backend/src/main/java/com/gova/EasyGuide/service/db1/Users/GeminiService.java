package com.gova.EasyGuide.service.db1.Users;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;



public interface GeminiService {

    String getChatReposne(ChatClient.Builder builder);
}
