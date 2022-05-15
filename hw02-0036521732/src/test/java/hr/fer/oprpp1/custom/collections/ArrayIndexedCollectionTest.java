package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	@Test
	public void defaultConstructorSizeTest() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection();
		assertEquals(0, aic.size());
	}
	
	@Test
	public void constructorWithCollectionArgumentTest() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(2));
		ArrayIndexedCollection aic2 = new ArrayIndexedCollection(aic);
		assertArrayEquals(aic.toArray(), aic2.toArray());
	}
	
	@Test
	public void constructorWithCollectionArgumentThatIsNull() {
		assertThrows(NullPointerException.class,() -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void constructorWithInitialCapcityThatIsLessThan1() {
		assertThrows(IllegalArgumentException.class,() -> new ArrayIndexedCollection(-1));
	}
	
	@Test
	public void constructorWithInisitalCapacityAndCollectionCorrectlyPassed() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(2));
		ArrayIndexedCollection aic2 = new ArrayIndexedCollection(aic, 1);
		assertArrayEquals(aic.toArray(), aic2.toArray());
	}
	
	@Test
	public void collectionSizeMethodTest() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(2));
		aic.add(Integer.valueOf(3));
		assertEquals(3, aic.size());
	}
	
	@Test
	public void isEmptyMethodTest() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		assertEquals(true, aic.isEmpty());
	}
	
	@Test
	public void addObjectInCollectionAndCheckElements() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(1);
		aic.add(Integer.valueOf(25));
		aic.add(Integer.valueOf(46));
		aic.add(Integer.valueOf(12));
		Object[] comparrisonArray = new Object[3];
		comparrisonArray[0] = Integer.valueOf(25);
		comparrisonArray[1] = Integer.valueOf(46);
		comparrisonArray[2] = Integer.valueOf(12);
		assertArrayEquals(comparrisonArray, aic.toArray());
	}
	
	@Test
	public void toArrayMethodTest() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(1);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(2));
		aic.add(Integer.valueOf(3));
		Object[] arrayHelper = new Object[3];
		arrayHelper[0] = Integer.valueOf(1);
		arrayHelper[1] = Integer.valueOf(2);
		arrayHelper[2] = Integer.valueOf(3);
		assertArrayEquals(arrayHelper, aic.toArray());
	}
	
	@Test
	public void toArrayMethodEmptyCollectionTest() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(1);
		assertThrows(UnsupportedOperationException.class, () -> aic.toArray());
	}
	
	@Test
	public void addObjectInCollectionAndCheckSize() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(1);
		aic.add(Integer.valueOf(25));
		aic.add(Integer.valueOf(46));
		aic.add(Integer.valueOf(12));
		assertEquals(3, aic.size());
	}
	
	@Test
	public void containsMethodTrue() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(2));
		aic.add(Integer.valueOf(3));
		assertEquals(true, aic.contains(Integer.valueOf(3)));
	}
	
	@Test
	public void containsMethodFalse() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(2));
		aic.add(Integer.valueOf(3));
		assertEquals(false, aic.contains(Integer.valueOf(4)));
	}
	
	@Test
	public void removeMethodWithObjectArgumentTest() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(3));
		ArrayIndexedCollection aic2 = new ArrayIndexedCollection(2);
		aic2.add(Integer.valueOf(1));
		aic2.add(Integer.valueOf(2));
		aic2.add(Integer.valueOf(3));
		aic2.remove(Integer.valueOf(2));
		assertArrayEquals(aic.toArray(), aic2.toArray());
	}
	
	@Test
	public void forEachMethodTest() {
		ArrayIndexedCollection aicForProcess = new ArrayIndexedCollection(2);
		class TestProcessor implements Processor{
			@Override
			public void process(Object value) {
				aicForProcess.add(value.hashCode());
			}
		}
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(3));
		Object[] arrayCorrect = new Object[2];
		arrayCorrect[0] = Integer.valueOf(1).hashCode();
		arrayCorrect[1] = Integer.valueOf(3).hashCode();
		aic.forEach(new TestProcessor());
		assertArrayEquals(arrayCorrect, aicForProcess.toArray());
	}
	
	@Test
	public void clearMethodElementsTest() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(3));
		aic.clear();
		assertEquals(true, aic.isEmpty());
	}
	
	@Test
	public void getMethodLegalIndexTest() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(3));
		assertEquals(Integer.valueOf(1), aic.get(0));
	}
	
	@Test
	public void getMethodIllegalIndexTest() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(3));
		assertThrows(IndexOutOfBoundsException.class, () -> aic.get(3));
	}
	
	@Test
	public void insertMethodIllegalPositionTest(){
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		assertThrows(IndexOutOfBoundsException.class, () -> aic.insert(Integer.valueOf(4), 5));
	}
	
	@Test
	public void insertMethodNullObjectValueTest(){
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		assertThrows(NullPointerException.class, () -> aic.insert(null, 1));
	}
	
	@Test
	public void insertMethodTest(){
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(3));
		aic.insert(Integer.valueOf(4), 2);
		Object[] helperArray = new Object[3];
		helperArray[0] = Integer.valueOf(1);
		helperArray[1] = Integer.valueOf(3);
		helperArray[2] = Integer.valueOf(4);
		assertArrayEquals(helperArray, aic.toArray());
	}
	
	@Test
	public void indexOfMethodTest() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(Integer.valueOf(1));
		aic.add(Integer.valueOf(3));
		assertEquals(1, aic.indexOf(Integer.valueOf(3)));
	}
	
	@Test
	public void removeMethodWithIndexArgumentIllegalIndexTest() {
		ArrayIndexedCollection aic2 = new ArrayIndexedCollection(2);
		aic2.add(Integer.valueOf(1));
		aic2.add(Integer.valueOf(2));
		aic2.add(Integer.valueOf(3));
		assertThrows(IndexOutOfBoundsException.class, () -> aic2.remove(3));
	}
	
	@Test
	public void removeMethodWithIndexArgumentTest() {
		Object[] helperArray = new Object[2];
		helperArray[0] = Integer.valueOf(1);
		helperArray[1] = Integer.valueOf(3);
		ArrayIndexedCollection aic2 = new ArrayIndexedCollection(2);
		aic2.add(Integer.valueOf(1));
		aic2.add(Integer.valueOf(2));
		aic2.add(Integer.valueOf(3));
		aic2.remove(1);
		assertArrayEquals(helperArray, aic2.toArray());
	}
	
}