package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	@Test
	public void defaultConstructorSizeTest() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		assertEquals(0, llic.size());
	}
	
	@Test
	public void constructorWithCollectionArgumentTest() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(2));
		LinkedListIndexedCollection llic2 = new LinkedListIndexedCollection(llic);
		assertArrayEquals(llic.toArray(), llic2.toArray());
	}
	
	@Test
	public void constructorWithCollectionArgumentThatIsNull() {
		assertThrows(NullPointerException.class,() -> new LinkedListIndexedCollection(null));
	}
	
	@Test
	public void collectionSizeMethodTest() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(2));
		llic.add(Integer.valueOf(3));
		assertEquals(3, llic.size());
	}
	
	@Test
	public void isEmptyMethodTest() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		assertEquals(true, llic.isEmpty());
	}
	
	@Test
	public void addObjectInCollectionAndCheckElements() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(25));
		llic.add(Integer.valueOf(46));
		llic.add(Integer.valueOf(12));
		Object[] comparrisonArray = new Object[3];
		comparrisonArray[0] = Integer.valueOf(25);
		comparrisonArray[1] = Integer.valueOf(46);
		comparrisonArray[2] = Integer.valueOf(12);
		assertArrayEquals(comparrisonArray, llic.toArray());
	}
	
	@Test
	public void toArrayMethodTest() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(2));
		llic.add(Integer.valueOf(3));
		Object[] arrayHelper = new Object[3];
		arrayHelper[0] = Integer.valueOf(1);
		arrayHelper[1] = Integer.valueOf(2);
		arrayHelper[2] = Integer.valueOf(3);
		assertArrayEquals(arrayHelper, llic.toArray());
	}
	
	@Test
	public void toArrayMethodEmptyCollectionTest() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		assertThrows(UnsupportedOperationException.class, () -> llic.toArray());
	}
	
	@Test
	public void addObjectInCollectionAndCheckSize() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(25));
		llic.add(Integer.valueOf(46));
		llic.add(Integer.valueOf(12));
		assertEquals(3, llic.size());
	}
	
	@Test
	public void containsMethodTrue() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(2));
		llic.add(Integer.valueOf(3));
		assertEquals(true, llic.contains(Integer.valueOf(3)));
	}
	
	@Test
	public void containsMethodFalse() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(2));
		assertEquals(false, llic.contains(Integer.valueOf(4)));
	}
	
	@Test
	public void removeMethodWithObjectArgumentTest() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(3));
		LinkedListIndexedCollection llic2 = new LinkedListIndexedCollection();
		llic2.add(Integer.valueOf(1));
		llic2.add(Integer.valueOf(2));
		llic2.add(Integer.valueOf(3));
		llic2.remove(Integer.valueOf(2));
		assertArrayEquals(llic.toArray(), llic2.toArray());
	}
	
	@Test
	public void forEachMethodTest() {
		LinkedListIndexedCollection llicForProcessor = new LinkedListIndexedCollection();
		class TestProcessor extends Processor{
			@Override
			public void process(Object value) {
				llicForProcessor.add(value.hashCode());
			}
		}
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(3));
		Object[] arrayCorrect = new Object[2];
		arrayCorrect[0] = Integer.valueOf(1).hashCode();
		arrayCorrect[1] = Integer.valueOf(3).hashCode();
		llic.forEach(new TestProcessor());
		assertArrayEquals(arrayCorrect, llicForProcessor.toArray());
	}
	
	@Test
	public void clearMethodElementsTest() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(3));
		llic.clear();
		assertEquals(true, llic.isEmpty());
	}
	
	@Test
	public void getMethodLegalIndexTest() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(3));
		assertEquals(Integer.valueOf(1), llic.get(0));
	}
	
	@Test
	public void getMethodIllegalIndexTest() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(3));
		assertThrows(IndexOutOfBoundsException.class, () -> llic.get(3));
	}
	
	@Test
	public void insertMethodIllegalPositionTest(){
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		assertThrows(IndexOutOfBoundsException.class, () -> llic.insert(Integer.valueOf(4), 5));
	}
	
	@Test
	public void insertMethodNullObjectValueTest(){
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		assertThrows(NullPointerException.class, () -> llic.insert(null, 1));
	}
	
	@Test
	public void insertMethodTest(){
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(3));
		llic.add(Integer.valueOf(2));
		llic.insert(Integer.valueOf(4), 2);
		Object[] helperArray = new Object[4];
		helperArray[0] = Integer.valueOf(1);
		helperArray[1] = Integer.valueOf(3);
		helperArray[2] = Integer.valueOf(4);
		helperArray[3] = Integer.valueOf(2);
		assertArrayEquals(helperArray, llic.toArray());
	}
	
	@Test
	public void indexOfMethodTest() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(Integer.valueOf(1));
		llic.add(Integer.valueOf(3));
		assertEquals(1, llic.indexOf(Integer.valueOf(3)));
	}
	
	@Test
	public void removeMethodWithIndexArgumentIllegalIndexTest() {
		LinkedListIndexedCollection llic2 = new LinkedListIndexedCollection();
		llic2.add(Integer.valueOf(1));
		llic2.add(Integer.valueOf(2));
		llic2.add(Integer.valueOf(3));
		assertThrows(IndexOutOfBoundsException.class, () -> llic2.remove(3));
	}
	
	@Test
	public void removeMethodWithIndexArgumentTest() {
		Object[] helperArray = new Object[2];
		helperArray[0] = Integer.valueOf(1);
		helperArray[1] = Integer.valueOf(3);
		LinkedListIndexedCollection llic2 = new LinkedListIndexedCollection();
		llic2.add(Integer.valueOf(1));
		llic2.add(Integer.valueOf(2));
		llic2.add(Integer.valueOf(3));
		llic2.remove(1);
		assertArrayEquals(helperArray, llic2.toArray());
	}
	
}