package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class TestSimpleHashtable {
	
	@Test
	public void testMetodaPutIMetodaGet() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);
		hashtable.put("Lovro", 1);
		hashtable.put("Glogar", 2);
		hashtable.put("Glogar", 3);
		hashtable.put("sdfsdfsdghs", 3);
		assertEquals(1, hashtable.get("Lovro"));
		assertEquals(3, hashtable.get("Glogar"));
		assertEquals(3, hashtable.get("sdfsdfsdghs"));
		assertEquals(null, hashtable.get(null));
		assertEquals(null, hashtable.get("ne postoji"));
	}
	
	@Test
	public void testMetodaContainsKey() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);
		hashtable.put("Lovro", 1);
		hashtable.put("Glogar", 2);
		hashtable.put("sdfsdfsdghs", 3);
		assertEquals(true, hashtable.containsKey("Glogar"));
		assertEquals(true, hashtable.containsKey("Lovro"));
		assertEquals(true, hashtable.containsKey("sdfsdfsdghs"));
		assertEquals(false, hashtable.containsKey("lovro"));
	}
	
	@Test
	public void testMetodaContainsValue() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);
		hashtable.put("Lovro", 1);
		hashtable.put("Glogar", 2);
		hashtable.put("sdfsdfsdghs", 3);
		assertEquals(true, hashtable.containsValue(1));
		assertEquals(true, hashtable.containsValue(2));
		assertEquals(true, hashtable.containsValue(3));
		assertEquals(false, hashtable.containsValue(4));
	}
	
	@Test
	public void testMetodaRemove() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);
		hashtable.put("Lovro", 1);
		hashtable.put("Glogar", 2);
		hashtable.put("sdfsdfsdghs", 3);
		assertEquals(1, hashtable.remove("Lovro"));
		assertEquals(2, hashtable.remove("Glogar"));
		assertEquals(null, hashtable.remove("Lovro"));
	}
	
	@Test
	public void testMetodaIsEmpty() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);
		hashtable.put("Lovro", 1);
		hashtable.put("Glogar", 2);
		assertEquals(false, hashtable.isEmpty());
		hashtable = new SimpleHashtable<>(2);
		assertEquals(true, hashtable.isEmpty());
	}
	
	@Test
	public void testIterator() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);
		hashtable.put("Lovro", 1);
		hashtable.put("Glogar", 2);
		hashtable.put("Orvol", 3);
		hashtable.put("Ragolg", 346346);
		hashtable.put("Nista", null);
		SimpleHashtable<String, Integer> hashtable2 = new SimpleHashtable<>(2);
		for(SimpleHashtable.TableEntry<String, Integer> entry: hashtable) {
			hashtable2.put(entry.getKey(), entry.getValue());
		}
		assertEquals(hashtable.toString(), hashtable2.toString());
	}
	
	@Test
	public void testIteratorRemove() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);
		hashtable.put("Lovro", 1);
		hashtable.put("Glogar", 2);
		hashtable.put("Orvol", 3);
		hashtable.put("Ragolg", 346346);
		hashtable.put("Nista", null);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iterator = hashtable.iterator();
		while(iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		assertEquals(0, hashtable.size());
	}
	
	@Test
	public void testModificationIterator() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);
		hashtable.put("Lovro", 1);
		hashtable.put("Glogar", 2);
		hashtable.put("Orvol", 3);
		hashtable.put("Ragolg", 346346);
		hashtable.put("Nista", null);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iterator = hashtable.iterator();
		if(iterator.hasNext()) {
			hashtable.remove("Lovro");
			assertThrows(ConcurrentModificationException.class, () -> {iterator.next();});
		}
	}
	
	@Test
	public void testRemove2TimesIterator() {
		SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);
		hashtable.put("Lovro", 1);
		hashtable.put("Glogar", 2);
		hashtable.put("Orvol", 3);
		hashtable.put("Ragolg", 346346);
		hashtable.put("Nista", null);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iterator = hashtable.iterator();
		if(iterator.hasNext()) {
			iterator.next();
			iterator.remove();;
			assertThrows(ConcurrentModificationException.class, () -> {iterator.remove();});
		}
	}
	
}
