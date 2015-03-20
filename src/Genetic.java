import java.util.Random;

public class Genetic extends SearchBase{

	int numItterations;
	int populationSize;
	
	private final double CHANCE_MUTATION = .1;
	
	TestInfo population[];
	
	Genetic()              { this(200000   , 50); }
	Genetic(int pNumItter) { this(pNumItter, 10); }
	
	Genetic(int pNumItter, int pPopulationSize) {
		numItterations = pNumItter;
		if(pPopulationSize % 2 == 0)
			populationSize = pPopulationSize;
		else
			populationSize = pPopulationSize - 1;
	}
	
	@Override
	public TestInfo search() {
		population = new TestInfo[populationSize];
//		int totalHeuristic = 0;
		
		for(int i = 0; i < populationSize; ++i) {
			population[i] = getRandom();
//			totalHeuristic += population[i].numCollisions;
		}
		
		for(int i = 0; i < numItterations; ++i) {
			System.out.println("Generation: " + i);
			double odds[] = getOdds();
			TestInfo newPop[] = new TestInfo[populationSize];
			Random rand = new Random();
			
			for(int j = 0; j < populationSize; j += 2) {
				int first  = choose(odds);
				int second = chooseDifferent(odds, first);
				int offset = rand.nextInt(3) + 1; //If its 0 they wouldn't swap anything
				
				newPop[j]   = reproduce(population[first ], population[second], offset);
				newPop[j+1] = reproduce(population[second], population[first ], offset);
			}
			population = newPop;
		}
		return fewestCollisions;
	}
	
	private TestInfo reproduce(TestInfo first, TestInfo second, int offset) {
//		System.out.println("Reproducing:");
		int set1[] = getVariables(first );
		int set2[] = getVariables(second);
		
		for(int i = offset; i < 4; ++i) {
			set1[i] = set2[i];
		}
		
		double mutation = Math.random();
		
		if(mutation < CHANCE_MUTATION) {
			System.out.println("There was a mutation");
			if(mutation < 1/CHANCE_MUTATION) {
				set1[0] = changeNumber(set1[0]);
			} else if(mutation < 2/CHANCE_MUTATION) {
				set1[1] = changeNumber(set1[1]);
			} else if(mutation < 3/CHANCE_MUTATION) {
				set1[2] = changeNumber(set1[2]);
			} else {
				set1[3] = changeNumber(set1[3]);
			}
		}
		return getTestInfo(set1[0], set1[1], set1[2], set1[3]);
	}
	
	private int[] getVariables(TestInfo TI) {
		int set[] = new int[4];
		
		set[0] = TI.firstVar;
		set[1] = TI.secondVar;
		set[2] = TI.thirdVar;
		set[3] = TI.fourthVar;
		
		return set;
	}
	
	private int choose(double[] odds) { return chooseDifferent(odds, -1); }
	
	private int chooseDifferent(double[] odds, int pLastChosen) {
		int chosen = pLastChosen;
		
		while (chosen == pLastChosen) {
			double choice = Math.random();
			for(int i = 0; i < populationSize; ++i) {
				if(odds[i] > choice) {
					chosen = i;
					break;
				}
			}
		}
		return chosen;
	}
	
	private double[] getOdds() {
		double total = 0;
		for(int i = 0; i < populationSize; ++i) {
			total += (1.0/population[i].numCollisions);
		}
		
		double odds[] = new double[populationSize];
		odds[0] = (1.0/population[0].numCollisions) / total;
		
		for(int i = 1; i < populationSize; ++i) {
			odds[i] = ((1.0/population[i].numCollisions) / total) + odds[i - 1];
		}
		
		return odds;
	}
	
	private TestInfo getRandom() {
		Random rand = new Random();
		int first  = rand.nextInt(MAX);
		int second = rand.nextInt(MAX);
		int third  = rand.nextInt(MAX);
		int fourth = rand.nextInt(MAX);
		
		return getTestInfo(first, second, third, fourth);
	}
	
	
	
	
}
