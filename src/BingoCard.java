package bingo;

public class BingoCard
{
	private int cardNumber;
	private int[] pattern;

	public BingoCard(){
		cardNumber = 0;
		pattern = new int[]{0};
	}

	public BingoCard(int cardNumber, int[] pattern){
		this.cardNumber = cardNumber;
		this.pattern = pattern;
	}

	public void setCardNumber(int cardNumber){
		this.cardNumber = cardNumber;
	}

	public int getCardNumber(){
		return this.cardNumber;
	}

	public void setPattern(int[] pattern){
		this.pattern = pattern;
	}

	public int[] getPattern(){
		return this.pattern;
	}
}