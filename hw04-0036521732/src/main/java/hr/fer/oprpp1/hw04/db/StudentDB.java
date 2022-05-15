package hr.fer.oprpp1.hw04.db;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDB {

	public static void main(String[] args) {
		
		
		Scanner scanner = new Scanner(System.in);
		String line;
		QueryParser parser;
		List<StudentRecord> students;
		
		StudentDatabase database = null;
		
		try {
			List<String> lines = Files.readAllLines(
					 Paths.get("./bazapodataka.txt"), 
					 StandardCharsets.UTF_8);
			database = new StudentDatabase(lines);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		while(true) {
			try {
				line = scanner.nextLine();
				if(line.equals("exit"))
					break;
				if(!line.startsWith("query"))
					throw new ParserException("Query has to start with keyword 'query'");
				
				parser = new QueryParser(line.replace("query", ""));
				
				students = new ArrayList<>();
				
				if(parser.isDirectQuery()) {
					StudentRecord sr = database.forJMBAG(parser.getQueriedJMBAG());
					if(sr != null)
						students.add(sr);
					else throw new ParserException("Student with jmbag " + parser.getQueriedJMBAG() + " doesn't exist");
				} else {
					for(StudentRecord r: database.filter(new QueryFilter(parser.getQuery()))) {
						if(r != null)
							students.add(r);	
					}		
				}
				
				if(!students.isEmpty())
					printResult(students);
				else System.out.println("No students match the specified conditions");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		System.out.println("Goodbye");
		scanner.close();
	}

	private static void printResult(List<StudentRecord> students) {
		
		int maxFirstNameLength;
		int maxLastNameLength;
		
		maxFirstNameLength = students
								.stream()
								.mapToInt(s -> s.getFirstName().length())
								.max().getAsInt();
		
		maxLastNameLength = students
								.stream()
								.mapToInt(s -> s.getLastName().length())
								.max().getAsInt();

		printDecoration(maxFirstNameLength, maxLastNameLength);
		
		students.forEach(s -> {
			StringBuilder sb = new StringBuilder();
			sb.append("| " + s.getJmbag() + " | " + s.getLastName());
			sb.append(" ".repeat(maxLastNameLength - s.getLastName().length()));
			sb.append("| " + s.getFirstName());
			sb.append(" ".repeat(maxFirstNameLength - s.getFirstName().length()));
			sb.append("| " + s.getFinalGrade() + " |");
			System.out.println(sb.toString());
		});
		
		printDecoration(maxFirstNameLength, maxLastNameLength);
		System.out.println("Records selected: " + students.size());
	}
	
	private static void printDecoration(int maxFirstNameLength, int maxLastNameLength) {
		System.out.printf("+%s+%s+%s+%s+\n", "=".repeat(12), "=".repeat(maxLastNameLength + 2), "=".repeat(maxFirstNameLength + 2), "=".repeat(3));
	}

}
