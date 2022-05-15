package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Predstavlja bazu podataka studenata
 * @author Lovro Glogar
 *
 */
public class StudentDatabase {

	/**
	 * Svi studenti
	 */
	private List<StudentRecord> students;
	
	/**
	 * Index koji mapira studentov jmbag na studenta
	 */
	private Map<String, StudentRecord> index;

	/**
	 * Konstruktor; prima listu stringova koji predstavljaju svaki po jednog studenta te svaki string pretvara u StudentRecord
	 * @param studentsString
	 */
	public StudentDatabase(List<String> studentsString) {
		students = new ArrayList<>();
		index = new HashMap<>();
		studentsString.forEach(s -> {
			String[] student = s.split("\t");
			int ocjena = Integer.parseInt(student[3]);
			if(index.get(student[0]) != null)
				throw new IllegalArgumentException("Student with jmbag '" + student[0] + "' already exists");
			if(ocjena < 1 || ocjena > 5)
				throw new IllegalArgumentException("Student grade must be between 1 and 5");
			StudentRecord studentRecord = new StudentRecord(student[0], student[2], student[1], Integer.parseInt(student[3]));
			students.add(studentRecord);
			index.put(student[0], studentRecord);
		});
	}
	
	/**
	 * Vraca StudentRecord kojemu je pridruzen jmbag
	 * @param jmbag jmbag trazenog studenta
	 * @return studenta koji ima jmbag <b>jmbag</b>
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if(jmbag == null)
			throw new NullPointerException();
		return this.index.get(jmbag);
	}
	
	/**
	 * Vraa listu studenata koji zadovoljavaju uvjet poslan u filteru
	 * @param filter
	 * @return lista studenata koja zadovoljava uvjet filtera
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> returnList = new ArrayList<>();
		students.forEach(s -> {
			if(filter.accepts(s))
				returnList.add(s);
		});
		return returnList;
	}
	
	public List<StudentRecord> getStudents() {
		return students;
	}

	@Override
	public String toString() {
		return this.students.toString();
	}
	
}
