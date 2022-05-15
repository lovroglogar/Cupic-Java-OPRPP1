package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;


public class TestStudentDatabase {
	
	private static StudentDatabase database;

	@BeforeAll
	public static void getData() {
		try {
			List<String> studentsString = Files.readAllLines(
					Paths.get("./bazapodataka.txt"),
					StandardCharsets.UTF_8);
			database = new StudentDatabase(studentsString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testForJMBAG() {
		StudentRecord student = database.forJMBAG("0000000001");
		assertEquals("| 0000000001 | Akšamović | Marin | 2 |", student.toString());
	}
	
	@Test
	public void testFilter() {
		List<StudentRecord> list = database.filter(s -> true);
		assertEquals(database.getStudents().size(), list.size());
		
		list = database.filter(s -> false);
		assertEquals(0, list.size());
	}
	
}











