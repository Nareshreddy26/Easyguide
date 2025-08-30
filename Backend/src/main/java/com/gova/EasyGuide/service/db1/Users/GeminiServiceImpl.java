package com.gova.EasyGuide.service.db1.Users;

import com.fasterxml.classmate.AnnotationOverrides;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
public class GeminiServiceImpl implements  GeminiService{
    @Override
    public String getChatReposne(ChatClient.Builder builder) {

        ChatClient client = builder.build();
        String reposne = client.prompt("say about chakala govardhan sai studing from lpu")
                .call()
                .content();

        return reposne;
    }
}
