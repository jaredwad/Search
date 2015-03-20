
public class SimulatedAnnealing extends SearchBase {

	int numItterations;
	
	SimulatedAnnealing() { this(10000); }
	
	SimulatedAnnealing(int pNumItter) {
		numItterations = pNumItter;
	}
	
	@Override
	public TestInfo search() {
		TestInfo current = testHash(0,0,0,0);
		
		for(int i = 0; i < numItterations; ++i) {
			
			if(i % 1000 == 0) {
				System.out.println(i);
			}
			
			double temperature = 1 - ((double)i / numItterations);
			
			TestInfo next = getNeighbor(current);
			
			if(next.hasFewerCollissions(current)) {
				current = next;
			} else {
				double chance = Math.random();
				if(chance <= temperature) {
					current = next;
				}
			}
		}
		return fewestCollisions;
	}
		
	private TestInfo getNeighbor(TestInfo previous) {
		int first  = previous.firstVar;
		int second = previous.secondVar;
		int third  = previous.thirdVar;
		int fourth = previous.fourthVar;

		double change = Math.random();
		if(change < .25) {
			first  = changeNumber(first);
		} else if (change < .50) {
			second = changeNumber(second);
		} else if (change < .75) {
			third  = changeNumber(third);
		} else {
			fourth = changeNumber(fourth);
		}
		
		return getTestInfo(first, second, third, fourth);
	}
}
