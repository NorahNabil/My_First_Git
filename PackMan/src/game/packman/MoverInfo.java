package game.packman;

public class MoverInfo {

	public MoverInfo(Position pos) {
		this.pos = new Position(pos.row, pos.column);
	}
	
	int curDir;
	int reqDir;
	Position pos;
	
	static final int LEFT=0, UP=1, RIGHT=2, DOWN=3;//indexes of DROW and DCOL arrays
	static final int[] DROW = {0, -1, 0, 1}; //to change the row (left +0 *same row*, up + -1 , right+0  *same row* , down-1)
	static final int[] DCOL = {-1, 0, 1, 0};//to change the column (left + -1 , up + 0 *same column* , right+0  *same column*, down-1)
	static final int[] REV = {RIGHT, DOWN, LEFT, UP};

}
