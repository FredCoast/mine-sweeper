import java.util.*;

public class BombSquare extends GameSquare
{
	//Initialises the variables
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;
	private GameBoard board;
	private int x,y;
	private Boolean checked = false;
	private int squareScore;
	/**
	 * Creates a bombsquare at the specified x,y location
	 * Returns nothing
	 * 
	 * @param x The x value at which the bomb would be created
	 * @param y The y value at which the bomb would be created
	 * @param board The board/window the bomb square should be drawn onto
	 */
	public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png", board);
		this.x=x;
		this.y=y;
		this.board = board;
		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);

	}
	/**
	 * When the square has been clicked it checks if the square contains a bomb 
	 * If the square contains a bomb it sets the image of the square to a bomb
	 * If there is not a bomb it sets it to the number of squares around it that contain a bomb
	 * If there are no bombs in adjacent squares it then "clicks" all the other blank squares around it
	 * Returns nothing
	 */
	public void clicked() {
		//Says that this square already has been "clicked"
		checked = true;
		//If the square contains a bomb it sets the picture to the bomb picture
		if (thisSquareHasBomb) {
			setImage("images/bomb.png");
		} else {
			//If the square does not contain a bomb it checks how many bombs are around it
			squareScore = checkAround(x, y);
			//It then sets the image to the quantity of bombs aound it
			setImage("images/" + squareScore + ".png");
			//If there are no bombs around it it starts unveiling other blank squares
			if(squareScore == 0){
				revealBlank();
			}
		}
	}
	/**
	 * Reveals all the squares around the current blank one
	 * If another square is also blank it reveals all of those blank ones recursively
	 * Returns nothing
	 */
	private void revealBlank(){
		//Checks all of the squares around the current blank one
		for(int i = -1;i<2;i++){
			for(int j = -1;j<2;j++){
				//Checks if one of the adjacent sqaures are blank
				try{
					//Gets the sqaure to see if it is blank
					BombSquare sqr = (BombSquare) board.getSquareAt(x+i, y+j);
					// If the square is blank and the square has not already been checked it clicks the square
					if(sqr.squareScore == 0 &&!sqr.checked) sqr.clicked();
				}catch(Exception e){

				}
			}
		}
	}
	/**
	 * Gets the total amount of bombs in the 8 squares around the desired location
	 * 
	 * @param checkX The x coordinate of the square that needs to be checked around
	 * @param checkY The y coordinate of the square that needs to be checked around
	 * @return The amount of bombs around the square that is being checked
	 */
	//Checks all the adjacent squares to see if they have a bomb
	public int checkAround(int checkX,int checkY){
		//Creates a score of how many bombs are around the desired square
		int score = 0;
		//Creates a loop to check all the bombs
        for(int i = -1;i<2;i++){
			//Adds by rows the amount of squares which have bombs in them
			score+=getAdjacent(checkX+i, checkY)+getAdjacent(checkX+i, checkY+1)+getAdjacent(checkX+i, checkY-1);
        }
		//Returns the amount of bombs that are around
		return score;
    }
	/**
	 * Checks a square to see if it contains a bomb
	 * Also checks if the square is inside the boards boundaries
	 * 
	 * @param rX The location that is being checked x coordinate
	 * @param rY The location that is being checked y coordinate
	 * @return 1 if the square contains a bomb 0 if the square does not
	 */
	private int getAdjacent(int rX, int rY){
		//Checks if is inside the boundaries
		try{
			//Returns 1 if this square has a bomb 0 elsewise
			return ((BombSquare) board.getSquareAt(rX, rY)).thisSquareHasBomb ?  1 :  0;
		}catch(Exception e){
			//Returns 0 is the square is outside
			return 0;
		}

	}
}
