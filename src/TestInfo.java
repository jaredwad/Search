import java.util.LinkedList;
import java.util.List;

public class TestInfo {
	public List<Integer> numHits; //A list of the number of nodes with index collisions
	public int numMisses;
	public int numCollisions;
	
	public int firstVar;
	public int secondVar;
	public int thirdVar;
	public int fourthVar;
	
	public TestInfo(int a, int b, int c, int d, int[] hitList) {
		numHits = new LinkedList<Integer>();
		
		firstVar  = a;
		secondVar = b;
		thirdVar  = c;
		fourthVar = d;
		
		
		for(int i = 0; i < hitList.length; ++i) {
			addHit(hitList[i]);
		}
		
		numMisses = numHits.get(0);
		numCollisions = getCollisions();
	}
	
	//This has fewer collisions than the passed in variable
	public boolean hasFewerCollissions(TestInfo ti) {
		return (numCollisions < ti.numCollisions);
	}
	
	//This has fewer misses than the passed in variable
	public boolean hasFewerMisses(TestInfo ti) {
		return (numMisses < ti.numMisses);
	}
	
	public void printHitMap() {
		for(int i = 0; i < numHits.size(); ++i) {
			int hits = numHits.get(i);
			if(hits > 0) {
				System.out.println("i = " + i + " num hits: " + hits);
			}
		}
	}
	
	public void printVars() {
		System.out.println("The variables are:");
		System.out.println("A: " + firstVar + " B: " + secondVar
						+ " C: " + thirdVar + " D: " + fourthVar);
	}
	
	private int getCollisions() {
		int collisions = 0;
		
		//start at 2 since 1 means no collisions
		for(int i = 2; i < numHits.size(); ++i) {
			collisions += numHits.get(i) * (i - 1); //There are i-1 collisions for index i > 1
		}
		
		return collisions;
	}
	
	/*
	 * 
	 */
	private void addHit(int i) {
		while(numHits.size() < i + 1) {
			numHits.add(0);
		}
		
		numHits.set(i, numHits.get(i) + 1);
	}
	
	
}
