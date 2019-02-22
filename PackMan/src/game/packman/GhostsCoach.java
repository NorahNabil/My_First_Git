package game.packman;

import java.util.List;
import java.util.Random;

public class GhostsCoach {

	Random random = new Random();
	GameData data = new GameData();
	public int[] decide(GameData data) {//frightened Mode
		// TODO Auto-generated method stub
	    int minValue = 0;
		int[] dirs = new int[4];
		int[] distance = new int[10];		
		for (int i=0; i<4; i++) {
			List<Integer> list = data.getPossibleDirs(data.ghostInfos[i].pos);
			list.remove(new Integer(MoverInfo.REV[data.ghostInfos[i].curDir]));
			dirs[i] = list.get(random.nextInt(list.size()));
		
				       }
		//		    }
					
		//	}
		
		return dirs;
	}
	

	public int demomove(int reqDir, MoverInfo info) {//attempt to implement chase via A* by exhaustive check of every possible move and its distance to the goal (packman) and choose the best direction to go.
		// current position of packman is (row, column)
		 int hdistance = 0;
	//	int count = 0;
		int row = info.pos.row;
		int column = info.pos.column;
		int rows = data.mazes[0].rows;
		int columns = data.mazes[0].columns;
		int nrow = wrap(row, MoverInfo.DROW[reqDir], rows);
		int ncol = wrap(column, MoverInfo.DCOL[reqDir], columns);
		if (data.mazes[0].charAt(nrow, ncol) != '0') {
			final int x1= nrow;
			 final int x2= data.packman.pos.row;
				final int y1= ncol;
				 final int y2= data.packman.pos.column;
				final int dx = Math.abs(x2 - x1);
				final int dy = Math.abs(y2 - y1);
				final double plus= Math.pow(dx, 2)+ Math.pow(dy,2);
				 hdistance = (int) Math.sqrt(plus) ;
			//	 count++;
		}
			//    int minValue = hdistance[0];
			  //  for (int i = 1; i < hdistance.length; i++) {
			    //    if (hdistance[i] < minValue) {
			      //      minValue = hdistance[i];
			        //}
			    //}
				
return hdistance; 
}
	private int wrap(int value, int incre, int max) {
		return (value+max+incre)%max;
	}
	}