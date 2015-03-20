
public class RandomSearch extends SearchBase {
	int numItter;
	
	@Override
	public TestInfo search() {
		int testNums[] = {0,0,0,0};
		
		for(int i = 0; i < numItter; ++i) {
			testNums[i % 4] = changeNumber(testNums[i % 4]);
			getTestInfo(testNums[0], testNums[1], testNums[2], testNums[3]);
		}
		
		// TODO Auto-generated method stub
		return fewestCollisions;
	}
}