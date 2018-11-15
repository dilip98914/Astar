import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class Spot {
	public int i,j;
	public int f=0;
	public int g=0;
	public int h=0;
	public List<Spot> neighbors=new ArrayList<Spot>();
	public int size=Main.size;
	public Spot previous;
	
	public Spot(int i,int j){
		this.i=i;
		this.j=j;
//		previous=new Spot()
	}
	
	public void addNeighBours(){
		if(i>0)
			this.neighbors.add(Main.grid[i-1][j]);
		if(i<Main.cols-1)
			this.neighbors.add(Main.grid[i+1][j]);
		if(j>0)
			this.neighbors.add(Main.grid[i][j-1]);
		if(j<Main.rows-1)
			this.neighbors.add(Main.grid[i][j+1]);
	}
	
	void render(Graphics g,Color color){
		g.setColor(color);
		g.fillRect(this.i*size, this.j*size, size-1, size-1);
		
	}
}
