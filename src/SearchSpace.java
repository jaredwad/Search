import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchSpace {
	private int size;
	private int numbers[];
	private int currentNumber;
	
	public SearchSpace() {
		fillNumbers();
		size = numbers.length;
		currentNumber = 0;
	}
	
	public int getSize() { return size; }
	
	public int getNext() {
		int num = numbers[currentNumber];
		currentNumber++;
		
		if(currentNumber >= size) { currentNumber = 0; }
		
		return num;
	}
	
	public void reset() { currentNumber = 0; }
	
	private void fillNumbers() {
		List<Integer> hashCodes = new ArrayList<Integer>();
		System.out.println("Reading in file...");
		
		URL url = getClass().getResource("/Words.txt");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                hashCodes.add(line.hashCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		numbers = new int[hashCodes.size()];
		int i = 0;
		for(int code : hashCodes) {
			numbers[i++] = code;
		}
				
	}
}
