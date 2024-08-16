package com.bk.microservice.inventory;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

//@Import(TestcontainersConfiguration.class)
//@SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {


	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private Integer port;


	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}
	@Test
	void inventoryShouldFalse() {



		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/inventory?skuCode=iphone_15&quantity=200")
				.then()
				.log().all()
				.statusCode(200)
				.extract()
				.body().asString();

		assertThat(responseBodyString, Matchers.is("false"));
	}
	@Test
	void inventoryShouldTrue() {



		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/inventory?skuCode=iphone_15&quantity=99")
				.then()
				.log().all()
				.statusCode(200)
				.extract()
				.body().asString();

		assertThat(responseBodyString, Matchers.is("true"));
	}
}
