import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Search {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int numItter = 10;
		
		DFS dfs = new DFS();
		
		ExecutorService service = Executors.newFixedThreadPool(5);
		Future<TestInfo> DFSfuture = service.submit(dfs);
		
		List<Info> list = new ArrayList<Info>();
		
		for(int i = 0; i < numItter; ++i) {
			
			System.out.println();
			System.out.println();
			System.out.println("Began Itteration: " + i);
			System.out.println();
			System.out.println();
			
			SimulatedAnnealing SA = new SimulatedAnnealing();
			Genetic GEN     = new Genetic();
			RandomSearch RS = new RandomSearch();
		
			Future<TestInfo> SAfuture  = service.submit(SA );
			Future<TestInfo> GENfuture = service.submit(GEN);
			Future<TestInfo> RSfuture  = service.submit(RS );

			list.add(new Info(SAfuture .get(), "SA" , i));
			list.add(new Info(GENfuture.get(), "GEN", i));
			list.add(new Info(RSfuture .get(), "RS" , i));
		}
		
		list.add(new Info(DFSfuture.get(), "DFS" , numItter));
		
		for(Info inf : list) {
			inf.printInfo();
		}
		
	}
	
//	public static void main(String[] args) {
//		
//		SearchBase base;
//		
//		if(args.length == 0) {
//			base = new SimulatedAnnealing();
//	    } else if(args[0].equals("DFS")) {
//			base = new DFS();
//		} else if(args[0].equals("GEN")) {
//			base = new Genetic();
//		} else {
//			base = new SimulatedAnnealing();
//		}
//
//		ExecutorService service = Executors.newFixedThreadPool(1);
//		Future<TestInfo> future = service.submit(base);
//		
//		
//		TestInfo TI = null;
//		try {
//			TI = future.get();
//			System.out.println();
//			System.out.println();
//			System.out.println("Best Answer:");
//			TI.printVars();
//			System.out.println(TI.numCollisions);
//			TI.printHitMap();
//		} catch (InterruptedException | ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}

class Info {
	public TestInfo TI;
	public int Itteration;
	public String SearchType;
	
	public Info(TestInfo pTI, String pSearchType, int pItter) {
		TI = pTI;
		SearchType = pSearchType;
		Itteration = pItter;
	}
	
	public void printInfo() {
		System.out.println();
		System.out.println("Type: " + SearchType + " Itter: " + Itteration);
		TI.printVars();
		TI.printHitMap();
		System.out.println();
	}
}