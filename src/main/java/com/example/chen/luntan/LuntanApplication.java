package com.example.chen.luntan;

import com.example.chen.luntan.socket.ServerSocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LuntanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuntanApplication.class, args);
		ServerSocketConfig serverSocketConfig = new ServerSocketConfig();
		serverSocketConfig.socketCreate();
	}

}
