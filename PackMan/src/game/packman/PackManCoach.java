package game.packman;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class PackManCoach {
	public MoverInfo packman;

	Random rand = new Random();
	Maze[] mazes = new Maze[0];
	CopyOnWriteArrayList<Position> powerPills;
	Position pos = new Position();
	public int decide(GameData data) {//deciding direction of the packman randomly.
		// TODO Auto-generated method stub
		//int pacdirs;
		    int dirs;
			List<Integer> list = data.getPossibleDirs(data.packman.pos);// get every possible direction that the packman can move to 		
//			System.out.println(list.size());
			list.remove(new Integer(MoverInfo.REV[data.packman.curDir]));//remove reverse of every direction 
			dirs = list.get(rand.nextInt(list.size()));
            return dirs;
						//return Astar( data);
	
			}

}