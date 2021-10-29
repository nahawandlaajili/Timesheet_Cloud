
package tn.esprit.spring.service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.services.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

	@Autowired
	IUserService us;

	private static final Logger Log = (Logger) LogManager.getLogger(UserServiceImplTest.class);

	@Test
	public void testRetrieveAllUsers() {
		List<User> users = us.retrieveAllUsers();
		// if there are 7 users in DB :
		Assert.assertEquals(5, users.size());
		//Log.info("retrieve all users:" + users);
	}

	@Test
	public void testRetrieveUser() {
		User user = us.retrieveUser("4");
		assertNotNull(user.getDateNaissance());
		Log.info("retrieveUser : " + user);
	}

	@Test
	public void testAddUser() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d = dateFormat.parse("2015-03-23");
		User u = new User("Mehdi", "ben Chaabene", d, Role.ADMINISTRATEUR);
		User userAdded = us.addUser(u);
		Assert.assertEquals(u.getLastName(), userAdded.getLastName());
	}
	
	@Test
	public void testUpdateUser() throws ParseException   {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d = dateFormat.parse("2019-08-23");
		User u = new User(5, "Nahawand", "Laajili", d, Role.CHEF_DEPARTEMENT);
	//	assertTrue(u.getRole().equals(Role.CHEF_DEPARTEMENT));
		User userUpdated  = us.updateUser(u); 
		Assert.assertEquals(u.getLastName(), userUpdated.getLastName());
	}
	
	@Test
	public void testDeleteUser()  {
		us.deleteUser("3");
		Assert.assertNull(us.retrieveUser("3"));
	}

	// 5 tests unitaires

}
