package api.test;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payloads.User;
import io.restassured.response.Response;

public class UserTestCases {
	Faker faker;
	User userPayLoad;
	
	public Logger logger;
	
	@BeforeClass
	public void setUpData() {
		faker = new Faker();
		userPayLoad = new User();
		
		userPayLoad.setId(faker.idNumber().hashCode());
		userPayLoad.setUsername(faker.name().username());
		userPayLoad.setFirstName(faker.name().firstName());
		userPayLoad.setLastName(faker.name().lastName());
		userPayLoad.setEmail(faker.internet().safeEmailAddress());
		userPayLoad.setPassword(faker.internet().password(5, 10));
		userPayLoad.setPhone(faker.phoneNumber().cellPhone());
		
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test (priority = 1)
	public void testPostUser() {
		
		logger.info("******** Creating user *******");
		Response response = UserEndPoints.createUser(userPayLoad);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("******** User is created *******");
	}
	
	@Test (priority = 2)
	public void testGetUserByName() {
		logger.info("******** Get user *******");
		Response response = UserEndPoints.readUser(this.userPayLoad.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("******** User info is retrieved *******");
	}
	
	@Test (priority = 3)
	public void testUpdateUserByName() {
		logger.info("******** Updating user *******");
		// update data using payload
		userPayLoad.setFirstName(faker.name().firstName());
		userPayLoad.setLastName(faker.name().lastName());
		userPayLoad.setEmail(faker.internet().safeEmailAddress());
		
		
		
		Response response = UserEndPoints.updateUser(this.userPayLoad.getUsername(), userPayLoad);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		// Checking data after update
		Response responseAfterUpdate = UserEndPoints.readUser(this.userPayLoad.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		// ......
		
		logger.info("******** User's info has been updated *******");
		
	}
	
	@Test (priority = 4)
	public void testDeleteUserByName() {
		logger.info("******** Deleting user *******");
		Response response = UserEndPoints.deleteUser(this.userPayLoad.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("******** User has been deleted *******");
	}
	
	
	
}
