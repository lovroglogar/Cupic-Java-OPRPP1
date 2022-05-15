package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testQueryParser {
	
	@Test
	public void test() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0000000000\" ");
		
		StudentRecord record = new StudentRecord("0000000000", "Ime", "Bosanac", 5);
		
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		

	}
}
