package game.packman;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameData { 
	int mazeNo;
	CopyOnWriteArrayList<Position> pills;
	CopyOnWriteArrayList<Position> powerPills;
	public MoverInfo packman;
	public GhostInfo[] ghostInfos = new GhostInfo[4];
	public int score;
	Maze[] mazes;
	boolean dead = false;
	
	public GameData() {
		mazes = new Maze[4];
		// load mazes information
		for (int m=0; m<4; m++) {
			mazes[m] = new Maze(m);
		}
//		mazeNo = 0;
		setMaze(mazeNo);
	}
	
    private void setMaze(int m) {
		packman = new MoverInfo(mazes[m].packmanPos);
		for (int g=0; g<4; g++) {
			ghostInfos[g] = new GhostInfo(mazes[m].ghostPos);
		}
		pills = new CopyOnWriteArrayList((List<Position>)(mazes[m].pills.clone()));
		powerPills = new CopyOnWriteArrayList((List<Position>)(mazes[m].powerPills.clone()));
	}
    
	public void movePackMan(int reqDir) {
		//if (reqDir == this.Astar(null))
		if (move(reqDir, packman)) {
			packman.curDir = reqDir;//update the current direction of packman
		} else {
			move(packman.curDir, packman);
		}
		
	}
	
    public int Astar(GameData data) {//attempt to implement A star algorithm. Failed. 
	int[] FN = null;
	for (int i = 0; i < 100 ; i++) {
	FN[i] = GN( data) + HN(data); 
	}
	 int minValue = FN[0]; // get minimam f(n) function;
	    for (int i = 1; i < FN.length; i++) {
	        if (FN[i] < minValue) {
	            minValue = FN[i];
	        }
	    }
		return minValue;
}
    
    public int GN(GameData data) { //from the starting point of packman in the maze until current position 
	 int gdistance = 0;
   for (int g=0; g< 100; g++) {
	 int x2= packman.pos.row;
	  int x1= mazes[0].packmanPos.row;
		 int y2= packman.pos.column;
		  int y1= mazes[0].packmanPos.column;
		 int dx = Math.abs(x2 - x1);
		 int dy = Math.abs(y2 - y1);
		 int plus= (int) (Math.pow(dx, 2)+ Math.pow(dy,2));
		 gdistance =  (int) Math.sqrt(plus);}
return gdistance;
}   
    public int HN(GameData data) {//from current position of packman until the power pill 
	int hdistance = 0;
	for (int g=0; g< powerPills.size(); g++) {
	final int x1= powerPills.get(g).row;
	 final int x2= packman.pos.row;
		final int y1= powerPills.get(g).column;
		 final int y2= packman.pos.column;
		final int dx = Math.abs(x2 - x1);
		final int dy = Math.abs(y2 - y1);
		final double plus= Math.pow(dx, 2)+ Math.pow(dy,2);
		 hdistance = (int) Math.sqrt(plus);
	}
	return hdistance;
}
    
	private int wrap(int value, int incre, int max) {// make sure that the player doesn't exceed over the maze boundaries. 
		return (value+max+incre)%max;
	}
	
	private boolean move(int reqDir, MoverInfo info) {//Given the required direction and the agent that is to be moved and chance its row and col accordingly. 
		int row = info.pos.row; //current row of the movable agent
		int column = info.pos.column; // current column
		int rows = mazes[mazeNo].rows; //Maximum number of rows in the maze
		int columns = mazes[mazeNo].columns;// maximum columns
		int nrow = wrap(row, MoverInfo.DROW[reqDir], rows);//current row , incremental value (up= row-1 , down = row+1)
		int ncol = wrap(column, MoverInfo.DCOL[reqDir], columns);//current column , incremental value (left= row-1 , right = row+1)
		if (mazes[mazeNo].charAt(nrow, ncol) != '0') {// check if the new coordination is not on an obstacle defined n the maze code file. 
			info.pos.row = nrow;
			info.pos.column = ncol;
			return true;
		}
		return false;
	}
	
	public void update() {//keep track of every change during the game
		if (pills.contains(packman.pos)) {//eating the pills
			pills.remove(packman.pos);
			score += 5;
		} else if (powerPills.contains(packman.pos)) {//eating powerPill
			powerPills.remove(packman.pos);
			score += 50;
			for (GhostInfo g:ghostInfos) {
				g.edibleCountDown = 500;//starting the time count down of edible mode
			}
		}
		for (GhostInfo g:ghostInfos) {
			if (g.edibleCountDown > 0) {//when the ghost are edible packman can eat them 
				if (touching(g.pos, packman.pos)) {//if packman ate a ghost. 
					// eat the ghost and reset
					score += 100;
					g.curDir = g.reqDir = MoverInfo.LEFT;
					g.pos.row = mazes[mazeNo].ghostPos.row;
					g.pos.column = mazes[mazeNo].ghostPos.column;
					g.edibleCountDown = 0;//only for the eaten ghost
				} 
				g.edibleCountDown--;
			} else {
				if (touching(g.pos, packman.pos)) {//when ghosts are not edible they will eat packman 
					dead = true;
				}
			}
		}
		// level is cleared
		if (pills.isEmpty() && powerPills.isEmpty()) {
			mazeNo++;
			if (mazeNo < 4) {//change the level
				setMaze(mazeNo);
			} else {
				// game over
				dead = true;
			}
		}
	}
	
    private boolean touching(Position a, Position b) {
		return Math.abs(a.row-b.row) + Math.abs(a.column-b.column) < 3;
	}
    
    public void moveGhosts(int[] reqDirs) {
	//	for (GhostInfo info:ghostInfos) {
      this.randomMode(reqDirs);
       //this.ChaseMode(reqDirs);//Dumb Attempt. But Working.
       //this.ScatterMode();//Also dumb attempt.

        }
    
	public void randomMode(int[] reqDirs) {//Frightened mode. Working.
		for(int i=0; i<4; i++) {
			GhostInfo info = ghostInfos[i];
			info.reqDir = reqDirs[i];
			if (move(info.reqDir, info)) {
				info.curDir = info.reqDir;
			} else {
				move(info.curDir, info);
			}
		}
	}
	
	public void ChaseMode(int[] reqDirs) {// More of a Jump than a Chase
		int i;
		for (i=0 ; i<4; i++) {
				GhostInfo info = ghostInfos[i];	
        if(i==0) {//redGost
		info.pos.row=packman.pos.row ;
		info.pos.column= packman.pos.column;
		}
		if(i== 1) {
			info.pos.row=packman.pos.row;
			info.pos.column= packman.pos.column;
		}
		if(i ==2 ) {
			info.pos.row=packman.pos.row ;
			info.pos.column= packman.pos.column;
			
		}
		if(i ==3) {
			info.pos.row=packman.pos.row ;
			info.pos.column= packman.pos.column;
		}
		}
	}
	
	public void ScatterMode() {
		int i;
		for (i=0 ; i<4; i++) {
				GhostInfo info = ghostInfos[i];	
        if(i==0) {//redGost
        	info.pos.row=this.mazes[this.mazeNo].redscater.row;
        	info.pos.column=this.mazes[this.mazeNo].redscater.column;
		}
		if(i== 1) {
        	info.pos.row=this.mazes[this.mazeNo].pinkscater.row;
        	info.pos.column=this.mazes[this.mazeNo].pinkscater.column;
		}
		if(i ==2 ) {
        	info.pos.row=this.mazes[this.mazeNo].bluescater.row;
        	info.pos.column=this.mazes[this.mazeNo].bluescater.column;
		}
		if(i ==3) {
        	info.pos.row=this.mazes[this.mazeNo].orngscater.row;
        	info.pos.column=this.mazes[this.mazeNo].orngscater.column;
		}
		}

	}
	
	public int getWidth() {
		return mazes[mazeNo].width;
	}
	
	public int getHeight() {
		return mazes[mazeNo].height;
	}
	
    public List<Integer> getPossibleDirs(Position pos) {//returns the possible direction of the agent; up,right.down,up depend of the surrounding obstacles.
		List<Integer> list = new ArrayList<Integer>();
		for (int d=0; d<4; d++) {
			Position npos = getNextPositionInDir(pos, d);
			if (mazes[mazeNo].charAt(npos.row, npos.column) != '0') {
				list.add(d);
			}
		}
		return list;
	}
    
	private Position getNextPositionInDir(Position pos, int d) {
		int nrow = wrap(pos.row, MoverInfo.DROW[d], mazes[mazeNo].rows);
		int ncol = wrap(pos.column, MoverInfo.DCOL[d], mazes[mazeNo].columns);
		return new Position(nrow, ncol);
	}
}