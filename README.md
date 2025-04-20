# Notes
In `@SpringBootApplication` class, a `RestClient` bean is created.
It uses a free online REST API: `https://jsonplaceholder.typicode.com`

## Endpoints
`/api/posts` [GET]\
Get all post data.

`/posts/{id}` [GET]\
Get post item under given `id`.

## Instructions
Run Spring Boot application.\
Run Docker Desktop.

To call an endpoint, run this command in terminal:\
`curl http://localhost:8080/api/posts` \
`curl http://localhost:8080/api/posts/1`

Or go to the endpoint in a browser:\
API #1
![get all posts](docs/1.jpg)

API #2
![get 1 post by ID](docs/2.jpg)

## Actuator
`curl http://localhost:8080/actuator`
![actuator](docs/3.jpg)

`curl http://localhost:8080/actuator/metrics`
![metrics](docs/4.jpg)

Show all HTTP requests collected
`curl http://localhost:8080/actuator/metrics/http.server.requests`
![http requests](docs/5.jpg)

### Using `@Observed`
```
    @Bean
	@Observed(name = "posts.load-all-posts", contextualName = "post.find-all")
	CommandLineRunner commandLineRunner(JsonPlaceholderService jsonPlaceholderService) {
		return args -> {
			List<Post> posts = jsonPlaceholderService.findAll();
			log.info("All Posts: {}", posts.size());
		};
	}
```
`curl http://localhost:9411/zipkin`
![zipkin](docs/6.jpg)


### With Spring Security
Need to log in for non-actuator endpoints\
e.g. `/api/posts`
![security](docs/7.jpg)

