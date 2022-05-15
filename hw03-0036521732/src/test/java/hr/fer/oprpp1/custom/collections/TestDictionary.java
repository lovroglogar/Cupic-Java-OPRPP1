package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDictionary {
	
	 @Test
	 public void testPrazniDictionary() {
		 Dictionary<String, Integer> dict = new Dictionary<>();
		 assertEquals(true, dict.isEmpty());
	 }
	 
	 @Test
	 public void testMetodaPutIMetodaGet() {
		 Dictionary<String, Integer> dict = new Dictionary<>();
		 dict.put("Lovro", null);
		 dict.put("Glogar", 1);
		 dict.put("Lovro", 2);
		 assertEquals(2, dict.get("Lovro"));
		 assertEquals(1, dict.get("Glogar"));
	 }
	 
	 @Test
	 public void testMetodaRemove() {
		 Dictionary<String, Integer> dict = new Dictionary<>();
		 dict.put("Lovro", null);
		 dict.put("Glogar", 1);
		 dict.put("Lovro", 2);
		 assertEquals(2, dict.remove("Lovro"));
		 assertEquals(null, dict.remove("Orvol"));
	 }
	 
	 @Test
	 public void testMetodaSize() {
		 Dictionary<String, Integer> dict = new Dictionary<>();
		 assertEquals(0, dict.size());
		 dict.put("Lovro", null);
		 assertEquals(1, dict.size());
	 }
	 
	 @Test
	 public void testMetodaClear() {
		 Dictionary<String, Integer> dict = new Dictionary<>();
		 dict.put("Glogar", 1);
		 dict.put("Lovro", 2);
		 dict.clear();
		 assertEquals(0, dict.size());
	 }

}
