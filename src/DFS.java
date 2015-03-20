
public class DFS extends SearchBase{

	@Override
	public TestInfo search() {
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		
		for(a = 0; a < MAX; ++a) {
			for(b = 0; b < MAX; ++b) {
				System.out.println("Completed " + (int) Math.pow(MAX, 2) * b + " Tests");
				for(c = 0; c < MAX; ++c) {
					for(d = 0; d < MAX; ++d) {
						TestInfo TI = testHash(a,b,c,d);
						if( fewestCollisions == null || TI.hasFewerCollissions(fewestCollisions)) {
							fewestCollisions = TI;
						}
						
						if( fewestMisses == null || TI.hasFewerMisses(fewestMisses)) {
							fewestMisses = TI;
						}
					}
				}
			}
		}
		
		return fewestCollisions;
	}
}
