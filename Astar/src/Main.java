import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Main extends Canvas {
	static int width = 1000;
	static int height = 1000;
	static int size = 20;
	static int cols = width / size;
	static int rows = height / size;

	static Spot[][] grid = new Spot[cols][rows];
	// spots decalration must ahead of it
	ArrayList<Spot> openSet = new ArrayList<Spot>();
	ArrayList<Spot> closedSet = new ArrayList<Spot>();
	private boolean running = true;

	Spot start, end;
	ArrayList<Spot> path;

	public Main() {
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setFocusable(true);
		init();
	}

	private void init() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				this.grid[i][j] = new Spot(i, j);
			}
		}

		// adding neightbours which eventually idea of openSet
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				this.grid[i][j].addNeighBours();
			}
		}

		start = grid[0][0];
		end = grid[17][17];
		openSet.add(start);// staring node to be added

	}

	public void run() {
		while (running) {
			tick();
			render();
		}
	}
	
	int HEURISTIC(Spot start,Spot end){
		return (end.j-start.j)+(end.i-start.i);
	}

	private void tick() {
		if (openSet.size() > 0) {
			// Spot current=openSet
			int winner = 0;
			for (int i = 0; i < openSet.size(); i++) {
				if (openSet.get(i).f < openSet.get(winner).f) {
					winner = i;
				}
			}
			Spot current = openSet.get(winner);

			if (openSet.get(winner) == end) {
				
				path=new ArrayList<Spot>();
				Spot temp=current;
				path.add(temp);
				while(temp.previous!=null){
					path.add(temp.previous);
					temp=temp.previous;
				}
				
				
				System.out.println("DONE!");
				return;
			}

			closedSet.add(current);
			openSet.remove(current);
			List<Spot> neighbors=current.neighbors;
			
			for(int i=0;i<neighbors.size();i++){
				Spot neighbor=neighbors.get(i);
				if(!closedSet.contains(neighbor)){
					int tempG=current.g+1; 
					if(openSet.contains(neighbor)){
						if(tempG<neighbor.g){
							neighbor.g=tempG;
						}
					}else{
						neighbor.g=tempG;
						openSet.add(neighbor);
					}
					neighbor.h=HEURISTIC(neighbor,end);
					neighbor.f=neighbor.h+neighbor.g;
					neighbor.previous=current;
				}
			}
			
			// what to add to openset

		} else {
			System.out.println("no solution!");
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j].render(g, Color.white);
			}
		}

		for (int i = 0; i < openSet.size(); i++) {
			openSet.get(i).render(g, Color.green);
		}

		for (int i = 0; i < closedSet.size(); i++) {
			closedSet.get(i).render(g, Color.red);
		}
		
		if(path!=null){
			for (int i = 0; i < path.size(); i++) {
				path.get(i).render(g, Color.magenta);
			}
			
		}
		
		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("ASTAR");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Main program = new Main();
		frame.add(program);
		frame.pack();
		frame.setLocationRelativeTo(null);
		program.run();

	}
}
