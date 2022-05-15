package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Klasa predstavlja zapis studenta u iz baze podataka
 * @author Lovro Glogar
 *
 */
public class StudentRecord {
	
	/**
	 * Jmbag studenta
	 */
	private String jmbag;
	
	/**
	 * Ime studenta
	 */
	private String firstName;
	
	/**
	 * Prezime studenta
	 */
	private String lastName;
	
	/**
	 * Ocjena studenta
	 */
	private int finalGrade;

	/**
	 * Konstruktor
	 * @param jmbag
	 * @param firstName
	 * @param lastName
	 * @param finalGrade
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	
}
