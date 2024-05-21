package com.example.backend;

import com.example.backend.Member.MemberController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class BackendApplication {
	static MemberController memberController;
	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		SpringApplication.run(BackendApplication.class, args);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI("http://localhost:8080/members/create/club"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.noBody())
				.build();
		client.send(request, HttpResponse.BodyHandlers.ofString());
	}
}
