package it.unibs.pajc.note.test;

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
		users.add(new User("Dan"));
		assertEquals(1, users.getAll().size());
	}
	
	@Test
	void idAutoIncrementTest() {
		users.add(new User("Hello"));
		users.add(new User("Hello1"));
		users.add(new User(""));
		
		int[] ids = users.getAll().stream().mapToInt(x -> x.getID()).toArray();
		assertArrayEquals(new int[]{0,1} , ids);

	}
	
	@Test
	void idRemove() {
		users.add(new User("Hello"));
		users.add(new User("Hello1"));
		users.add(new User("Hello2"));
		users.remove(u -> u.getName().equals("Hello1"));
		users.add(new User("Hello1"));
		
		int[] ids = users.getAll().stream().mapToInt(x -> x.getID()).toArray();
		assertArrayEquals(new int[]{0,2,3} , ids);
	}
	
	@Test 
	void isPresent() {
		users.add(new User("Daniele"));
		assertTrue(users.getAll().contains(new User("Dani")));
	}
	
	
	@Test
	void authenticate() {
		String username;
		String password;
//		assertTrue(users.authenticate(username, password));
	}
	

}
