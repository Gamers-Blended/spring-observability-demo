package com.blended.observability_demo;

import com.blended.observability_demo.post.JsonPlaceholderService;
import com.blended.observability_demo.post.Post;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@SpringBootApplication
public class ObservabilityDemoApplication {

	private static final Logger log = LoggerFactory.getLogger(ObservabilityDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ObservabilityDemoApplication.class, args);
	}

	@Bean
	JsonPlaceholderService jsonPlaceholderService() {
		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(JsonPlaceholderService.class);
	}

	// runs after application context started
	@Bean
	@Observed(name = "posts.load-all-posts", contextualName = "post.find-all")
	CommandLineRunner commandLineRunner(JsonPlaceholderService jsonPlaceholderService) {
		return args -> {
			List<Post> posts = jsonPlaceholderService.findAll();
			log.info("All Posts: {}", posts.size());
		};
	}

}
