package sfera.order_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import sfera.order_service.stubs.InventoryClientStub;

import static org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");
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
		postgreSQLContainer.start();
	}

	@Test
	void shouldPlaceOrder() {
//		ProductRequest productRequest = new ProductRequest("iphone 16 pro", "apple", 800.0);
		String req = """
				{
				"skuCode":"iphone_15_pro",
				"price":1000,
				"quantity":1
				}
				""";
		InventoryClientStub.stubInventoryCall("iphone_15_pro", 1);
		String response = RestAssured.given()
				.contentType("application/json")
				.body(req)
				.when()
				.post("/api/order/placeOrder")
				.then()
				.log().all()
				.statusCode(201)
				.extract().body().asString();

		assertThat(response, Matchers.is("Order placed successfully"));
	}
}
