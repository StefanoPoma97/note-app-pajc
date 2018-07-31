package it.unibs.pajc.note.test1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.model.User;

class UserArchiveTests {
	
	UserArchive users;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		users = new UserArchive();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void addNewUser() {
		users.add(new User("Dan","pwd"));
		assertEquals(1, users.all().size());
	}
	
	@Test
	void idAutoIncrementTest() {
		users.add(new User("Hello","pwd"));
		users.add(new User("Hello1","pwd"));
		users.add(new User("",""));
		
		int[] ids = users.all().stream().mapToInt(x -> x.getID()).toArray();
		assertArrayEquals(new int[]{0,1} , ids);

	}
	
	@Test
	void idRemove() {
		users.add(new User("Hello","pwd"));
		users.add(new User("Hello1","pwd"));
		users.add(new User("Hello2","pwd"));
		users.remove(u -> u.getName().equals("Hello1"));
		users.add(new User("Hello1","pwd"));
		
		int[] ids = users.all().stream().mapToInt(x -> x.getID()).toArray();
		assertArrayEquals(new int[]{0,2,3} , ids);
	}
	
	@Test 
	void isPresent() {
		String uname = "Stefano";
		User u = new User(uname ,"pwd");
		users.add(u);
		assertTrue(users.all().contains(u));
	}
	
	
	@Test
	void simpleAuth() {
		String username = "Dan";
		String password = "pwd123!";
		
		users.add(new User(username,password));
		
		assertTrue(users.authenticate(username, password));
	}
	
	@Test
	void noDuplicateUsernames() {
		users.add(new User("Dan", "pollo"));
		users.add(new User("Dan", "pollo123"));
		assertFalse(users.authenticate("Dan", "pollo123"));
	}
	
	@Test
	void emptyPwd() {
		users.add(new User("Dan", ""));
		assertEquals(users.all().size(), 0);
	}
	

}
