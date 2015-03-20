import java.util.Random;
import java.util.concurrent.Callable;

public abstract class SearchBase implements Callable<TestInfo>{

	TestInfo fewestMisses = null;
	TestInfo fewestCollisions = null;
	SearchSpace search = new SearchSpace();
	static TestInfo done[][][][] = initializeDone();
	
	final static int MAX = 20;
	
	int size       = (int)Math.pow(2, 19);
	int insertSize = search.getSize();
	
	public abstract TestInfo search();
	
	@Override
	public TestInfo call() 
	{ 
//		done = initializeDone();
		fewestCollisions = testHash(0,0,0,0);
		return search();
	}
	
	public int changeNumber(int num) {
		Random rand = new Random();
		int returnNum = num;
		
		while(num == returnNum) {
			returnNum = rand.nextInt(MAX);
		}
		
		return returnNum;
	}
	
	public void printSearchArea() {
		System.out.println("The combination with the fewest misses:");
		System.out.print(fewestMisses.firstVar + ", " + fewestMisses.secondVar
				+ ", " + fewestMisses.thirdVar + ", " + fewestMisses.fourthVar
				+ ", " + "numMisses = " + fewestMisses.numMisses);
		
		System.out.println();
		
		System.out.println("The combination with the fewest collisions:");
		System.out.print(fewestCollisions.firstVar + ", " + fewestCollisions.secondVar
				+ ", " + fewestCollisions.thirdVar + ", " + fewestCollisions.fourthVar
				+ ", " + "numCollisions = " + fewestCollisions.numCollisions);
		
		System.out.println();
		
	}
	
	public TestInfo testHash(int a, int b, int c, int d) {
		int[] hitList = new int[size];
		
		search.reset();
		
		for(int i = 0; i < insertSize; ++i) {
			int code = search.getNext();
			
			int index = indexFor(hash(a,b,c,d,code), size);
			hitList[index] += 1;
		}
		
		TestInfo TI = new TestInfo(a,b,c,d,hitList);
		
		
		return TI;
	}
	
	public int hash(int a, int b, int c, int d, Object obj) {
		return hash(a,b,c,d,obj.hashCode());
	}
	
	public int hash(int a, int b, int c, int d, int item) {
		item ^= (item >>> a) ^ (item >>> b);
		return item ^ (item >>> c) ^ (item >>> d);
	}
	
	/**
	 * Returns index for hash code h.
	 */
	public int indexFor(int h, int length) {
	    return h & (length-1);
	}
	
	protected TestInfo getTestInfo(int a, int b, int c, int d) {
		if(done[a][b][c][d] == null) {
			TestInfo TI = testHash(a,b,c,d);
			synchronized(done) { //I believe this locks this object
				done[a][b][c][d] = TI;
			}
			
			
			if(TI.hasFewerCollissions(fewestCollisions)){
				System.out.println("Found new best");
				fewestCollisions = TI;
			}
			
			return TI;
		}
		else {
			return done[a][b][c][d];
		}
	}
	
	private static TestInfo[][][][] initializeDone() {
		TestInfo done[][][][] = new TestInfo[MAX][MAX][MAX][MAX];
		
		for(int i = 0; i < MAX; i++) {
			for(int j = 0; j < MAX; j++) {
				for(int k = 0; k < MAX; k++) {
					for(int l = 0; l < MAX; l++) {
						done[i][j][k][l] = null;
					}
				}
			}
		}
		
		return done;
	}
}