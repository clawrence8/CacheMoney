import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class RemoveUserTest {
//Chez's J-units
	private User user1;
	private User user2;
	private USer user3;
	private static Map<String, User> usersList;
	public static final int TIMEOUT = 200;

	  @Before
    public void setup() {
        usersList = new HashMap<>();
        user1 = new User( "bob", "bBob", "bob@gmail.com", "ob", "CS",
                "Male", "Marvel");
        user2 = new User( "sally", "", "", "", "",
                "", "");
        user3 = null;
    }

    @Test(timeout = TIMEOUT)
    public void testValidUser() {

    	assertFalse(usersList.addUser(user3));
    	assertTrue(usersList.addUser(user2));
    	assertFalse(usersList.contains(user1));

    }

}
