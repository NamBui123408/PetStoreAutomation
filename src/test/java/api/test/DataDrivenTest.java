package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payloads.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTest {
	@Test (priority = 1, dataProvider = "Data", dataProviderClass= DataProviders.class)
	public void testPostUser(String userID, String userName, String fname, String lname, String useremail, String pwd, String ph) {
		User userPayLoad = new User();
		
		userPayLoad.setId(Integer.parseInt(userID));
		userPayLoad.setUsername(userName);
		userPayLoad.setFirstName(fname);
		userPayLoad.setLastName(lname);
		userPayLoad.setEmail(useremail);
		userPayLoad.setPassword(pwd);
		userPayLoad.setPhone(ph);
		
		Response response = UserEndPoints.createUser(userPayLoad);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test (priority = 2, dataProvider = "UserNames", dataProviderClass= DataProviders.class)
	public void testDeleteUserByName (String userName) {
		Response response = UserEndPoints.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
	}
}
