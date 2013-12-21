package bingo;
import java.util.*;

// Model class
public class Bingo
{
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
    	System.out.println("===============BEGIN=============");
		for (int i = numbers.length - 1; i >= 0; i--)
    	{
	     	int index = rnd.nextInt(i + 1);
	     	// Simple swap
	     	int a = numbers[index];
	     	numbers[index] = numbers[i];
	     	numbers[i] = a;
	     	System.out.println("i:" + numbers[i]);

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

	     	System.out.println("i:" + i + " : " + sequence[i]);
    	}

    	
    	// for(int i = 0; i < 75; ++i)
    	// {
    	// 	System.out.println("i:" + i + " : " + sequence[i]);
    	// }
    	System.out.println("===============END=============");

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
}