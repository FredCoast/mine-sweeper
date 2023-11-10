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
		checked = true;
		if (thisSquareHasBomb) {
			setImage("images/bomb.png");
		} else {
			squareScore = checkAround(x, y);
			setImage("images/" + squareScore + ".png");
			if(squareScore == 0){
				for(int i = -1;i<2;i++){
					for(int j = -1;j<2;j++){
						try{
							BombSquare sqr = (BombSquare) board.getSquareAt(x+i, y+j);
							if(sqr.squareScore == 0 &&!sqr.checked) sqr.clicked();
						}catch(Exception e){

						}
					}
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
	public int checkAround(int checkX,int checkY){
		int score =0;
        for(int i = -1;i<2;i++){
			score+=getAdjacent(checkX+i, checkY)+getAdjacent(checkX+i, checkY+1)+getAdjacent(checkX+i, checkY-1);
        }
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
		try{
			return ((BombSquare) board.getSquareAt(rX, rY)).thisSquareHasBomb ?  1 :  0;
		}catch(Exception e){
			return 0;
		}

	}
}
