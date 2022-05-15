package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestFieldValueGetters {

	@Test
	public void testFirstNameGetter() {
		StudentRecord record = new StudentRecord("0000000000", "Test", "Test", 5);
		assertEquals("0000000000", FieldValueGetters.JMBAG.get(record));
		assertEquals("Test", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Test", FieldValueGetters.LAST_NAME.get(record));
	}
	
}
