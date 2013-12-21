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

  	public int[] getCallSequence()
  	{
  		int[] numbers = new int[75];
  		for(int i = 0; i < 75; ++i)
		{
			numbers[i] = i + 1;
		}

    	//randomize the list
    	Random rnd = new Random();
		for (int i = numbers.length - 1; i > 0; i--)
    	{
	     	int index = rnd.nextInt(i + 1);
	     	// Simple swap
	     	int a = numbers[index];
	     	numbers[index] = numbers[i];
	     	numbers[i] = a;
    	}

    	return numbers;	
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