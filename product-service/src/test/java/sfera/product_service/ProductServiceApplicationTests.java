package sfera.product_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:latest");
	@LocalServerPort
	private Integer port;
//	private final MongoDBContainer mongoDBContainer;

	@BeforeEach
	void setup(){
//		mongoDBContainer.start();
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDbContainer.start();
	}

	@Test
	void shouldCreateProduct() {
//		ProductRequest productRequest = new ProductRequest("iphone 16 pro", "apple", 800.0);
		String req = """
				{
				"name": "iphone 16 pro",
				"description": "apple",
				"price": 800
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(req)
				.when()
				.post("/api/product/create")
				.then()
				.statusCode(201)
				.body("name", Matchers.equalTo("iphone 16 pro"))
				.body("description", Matchers.equalTo("apple"))
				.body("price", Matchers.equalTo(800.0F));
	}
}
