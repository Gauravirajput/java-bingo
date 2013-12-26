package bingo;
import java.util.*;

// Model class
public class Bingo
{
	public int[] winningPattern = new int[]{1};
	public int[][] getFullRandomizedSet()
  	{
  		// storing the complete list of randomized card set
  		int[][] numbers = new int[5][15];
  		for(int i = 0; i < 5; ++i)
		{
			for(int j = 0; j < 15; ++j)
			{
				numbers[i][j] = (i * 15) + j + 1;
			}
		}

    	//loop through B,I,N,G,O and randomize each column
    	Random rnd = new Random();
    	for(int j = 0; j < 5; ++j)
    	{
    		//randomize the column
    		for (int i = numbers[j].length - 1; i > 0; i--)
	    	{
		     	int index = rnd.nextInt(i + 1);
		     	// Simple swap
		     	int a = numbers[j][index];
		     	numbers[j][index] = numbers[j][i];
		     	numbers[j][i] = a;
	    	}
    	}

    	return numbers;	
  	}

  	public String[] getCallSequence()
  	{
  		int[] numbers = new int[75];
  		String[] sequence = new String[75];

  		for(int i = 0; i < 75; ++i)
		{
			numbers[i] = i + 1;
		}

    	//randomize the list
    	Random rnd = new Random();
		for (int i = numbers.length - 1; i >= 0; i--)
    	{
	     	int index = rnd.nextInt(i + 1);
	     	// Simple swap
	     	int a = numbers[index];
	     	numbers[index] = numbers[i];
	     	numbers[i] = a;

	     	//assign to String data type with alphabets
	     	String prefix;
	     	if(numbers[i] >= 1 && numbers[i] <= 15)
	     	{
	     		prefix = "B";
	     	}
	     	else if(numbers[i] >= 16 && numbers[i] <= 30)
	     	{
	     		prefix = "I";
	     	}
	     	else if(numbers[i] >= 31 && numbers[i] <= 45)
	     	{
	     		prefix = "N";
	     	}
	     	else if(numbers[i] >= 46 && numbers[i] <= 60)
	     	{
	     		prefix = "G";
	     	}
	     	else if(numbers[i] >= 61 && numbers[i] <= 75)
	     	{
	     		prefix = "O";
	     	}
	     	else
	     	{
	     		prefix = "";
	     	}
     		sequence[i] = prefix + numbers[i];
    	}

    	return sequence;	
  	}

	public int[] generateCardSet()
	{
		//full set from 1 to 75
		int[][] fullSet = getFullRandomizedSet();
  		//storing the cards to be distributed to client
  		int[] cardSet = new int[25];

		for(int i = 0; i < 5; ++i)
    	{
    		for(int j = 0; j < 5; ++j)
    		{
    			//transpose the generated randomized array 
    			//to display the array correctly
    			cardSet[(5 * i) + j] = fullSet[j][i];
    		}
    	}

		return cardSet;
	}

	public boolean checkPattern(int[] pattern, int[] sequence)
	{
		boolean win = false;
		//check winning pattern 1: horizontal lines
		//i = row, j = column
		for(int i = 0; i < 5; ++i)
		{
			int count = 0;
			for(int j = 0; j < 5; ++j)
			{
				if(pattern[(i * 5) + j] == 1)
				{
					count++;
				}
			}

			if(count == 5){
				win = true;
				break;
			}
		}

		//check winning pattern 2: vertical lines
		if(!win)
		{
			for(int i = 0; i < 5; ++i)
			{
				int count = 0;
				for(int j = 0; j < 5; ++j)
				{
					if(pattern[(j * 5) + i] == 1)
					{
						count++;
					}
				}

				if(count == 5){
					win = true;
				break;
				}
			}
		}

		//check winning pattern 3: diagonals
		if(!win)
		{
			int[][] interimPattern = new int[][]{{pattern[0],pattern[6],pattern[12],pattern[18],pattern[24]},
												 {pattern[4],pattern[8],pattern[12],pattern[16],pattern[20]}
												};

			if(interimPattern[0] == winningPattern) win = true;
			else if(interimPattern[1] == winningPattern) win = true;
			else win = false;
		}

		//check winning pattern 4: corners
		if(!win)
		{
			win = true;
			int[] interimPattern = new int[]{pattern[0],pattern[4],pattern[20],pattern[24]};
			for(int element : interimPattern)
			{
				if(element != 1) win = false;
			}
		}

		return win;
	}
}