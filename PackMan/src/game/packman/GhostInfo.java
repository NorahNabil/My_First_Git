package game.packman;

public class GhostInfo extends MoverInfo {//keep track of infos of every ghost of the four. 

	public int edibleCountDown;
	public GhostInfo(Position pos) {
		super(pos);
	}
}
