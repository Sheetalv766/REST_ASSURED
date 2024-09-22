package com.crio.webActions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class Activity {

	@Test
	public void getRequest() {
		RestAssured.baseURI = "https://content-qkart-qa-backend.azurewebsites.net";

		// Specify the base Path of RESTful web service using RestAssured.basePath.
		RestAssured.basePath = "/api/v1/products";

		// Declare Request Specification method and assign given specification
		RequestSpecification http = RestAssured.given();

		Response response = http.request(Method.GET);

		int responseStatusCode = response.getStatusCode();

		if (responseStatusCode == 200) {
			System.out.println("The API call was successful");
		} else {
			System.out.println("The API call Failed");
		}
	}

	@Test
	public void getRequestWithParams() {
		RestAssured.baseURI = "https://content-qkart-qa-backend.azurewebsites.net";

		RestAssured.basePath = "/api/v1/products/search";

		RequestSpecification http = RestAssured.given().queryParam("value", "book");

		Response response = http.request(Method.GET);

		int responseStatusCode = response.getStatusCode();

		if (responseStatusCode == 200) {
			System.out.println("The API call was successful");
		} else {
			System.out.println("The API call Failed");
		}
	}

	@Test
	public void basicAuth() {
		RequestSpecification httpRequest = RestAssured.given();

		Response res = httpRequest.get("https://postman-echo.com/basic-auth");
		String rbdy = res.body().asString();
		System.out.println("Data from the GET API- " + rbdy);
	}

	// @Test
	public void bearerToken(String token) {
		String jsonString = "{\"address\":\"My new address - excited about moving in\"}";

		RestAssured.baseURI = "https://content-qkart-qa-backend.azurewebsites.net";

		RestAssured.basePath = "/api/v1/user/addresses";

		RequestSpecification http = RestAssured.given();
		http.contentType(ContentType.JSON);
		http.body(jsonString);
		http.header("Authorization", "Bearer " + token);

		Response response = http.request(Method.POST);
		int responseStatusCode = response.getStatusCode();
		if (responseStatusCode == 200) {
			System.out.println("The API call was successful");
		} else {
			System.out.println("The API call Failed");
		}

		String responseBody = response.getBody().asString();
		System.out.println(responseBody);

	}

	@Test
	public void validateJSONSchema() {
		RestAssured.baseURI = "https://content-qkart-qa-backend.azurewebsites.net/api/v1/products";
		RequestSpecification http = RestAssured.given();
		Response response = http.request(Method.GET);

		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(
				"/home/crio-user/workspace/bytes/me_rest_assured/src/test/resources/schema.json")));
	}

	@Test
	public void validateWithHamcrestProductName() {
		RestAssured.baseURI =
				"https://content-qkart-qa-backend.azurewebsites.net/api/v1/products/34sLtEcMpzabRyfx";
		RequestSpecification http = RestAssured.given();
		Response response = http.request(Method.GET);

		// response.then().body("ParameterFromBody", AssertionFunctions(ValueToBeAsserted));
		response.then().body("name", equalTo("Thinking, Fast and Slow"));
	}
}
