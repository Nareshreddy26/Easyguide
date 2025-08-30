package com.gova.EasyGuide;

import com.gova.EasyGuide.service.db1.Users.EmailSenderImpl;
import com.gova.EasyGuide.service.db1.Users.GeminiService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EasyGuideApplicationTests {

	@Autowired
	private GeminiService geminiService;


	@Autowired
	private EmailSenderImpl emailSender;

	@Test
	void contextLoads() {
	}

	@Test
    void geminreposne(ChatClient.Builder builder)
	{
		String response = geminiService.getChatReposne(builder);
		System.out.println(response);
	}

	@Test
	void EmailSender()
	{
		emailSender.sendEmail("sgovardhan300@gmail.com","Testing mail","this mail has bidy of the test");
	}



}
