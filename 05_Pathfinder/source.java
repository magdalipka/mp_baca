import java.util.Scanner;
import java.util.LinkedList;

class node {
	int x;//szerokosc
	int y;//wysokosc
	node Parent;
	char dirfromParent;
	node N;
	node E;
	node S;
	node W;

	node ( int a, int b, node par ) {
		x = b;
		y = a;
		Parent = par;
	}
}

class Map {

	int width;
	int height;
	int[][] map;
	int[][] visited;

	Map ( int a, int b ) {
		width = a;
		height = b;
		map = new int[b][a];
		visited = new int[b][a]; 
	}

	void Print() {
		for ( int i = height-1; i >= 0; i-- ) {
			for ( int j = 0; j < width; j++ ) {
				System.out.print(map[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}


class RecursivePathfinder {

	void findPath(int startX, int startY, int destX, int destY, Map Mapka) {
		node current = new node(startY, startX, null);
		node sol = find (current, destX, destY, Mapka);
		if ( sol == null ) System.out.print("r X");
		else {
			//System.out.println("FOUND");
			String Answer ="r";
			while ( sol != null ) {
				//System.out.print("(" + sol.y + ", " + sol.x + "), ");
				Answer += sol.dirfromParent + " ";
				sol = sol.Parent;
			}
			System.out.print("r");
			for ( int i = Answer.length()-3; i > 0; i--) {
				System.out.print(Answer.toCharArray()[i]);
			}
		} 
	}

	node find (node current, int destX, int destY, Map Mapka) {

		//System.out.println("curr = (" + current.y + ", " + current.x + ")");
		//System.out.println("dest = (" + destY + ", " + destX + ")");
		

		if ( current.x == destX && current.y == destY ) return current;

		Mapka.visited[current.y][current.x] = 1;

		//konstrukcja sasiadow
		current.N = current.E = current.S = current.W = null;
		if ( current.x+1<Mapka.width && Mapka.map[current.y][current.x+1] != 1 && Mapka.visited[current.y][current.x+1] != 1) {
			node temp = new node(current.y, current.x+1, current);
			temp.dirfromParent = 'E';
			current.E = temp;
			//System.out.println("E added");
		}
		if ( current.x-1>=0 && Mapka.map[current.y][current.x-1] != 1 && Mapka.visited[current.y][current.x-1] != 1 ) {
			node temp = new node(current.y, current.x-1, current);
			temp.dirfromParent = 'W';
			current.W = temp;
			//System.out.println("W added");
		}
		if ( current.y+1<Mapka.height && Mapka.map[current.y+1][current.x] != 1 && Mapka.visited[current.y+1][current.x] != 1 ) {
			node temp = new node(current.y+1, current.x, current);
			temp.dirfromParent = 'N';
			current.N = temp;
			//System.out.println("N added");
		}
		if ( current.y-1>=0 && Mapka.map[current.y-1][current.x] != 1 && Mapka.visited[current.y-1][current.x] != 1 ) {
			node temp = new node(current.y-1, current.x, current);
			temp.dirfromParent = 'S';
			current.S = temp;
			//System.out.println("S added");
		}
		//koniec konstrucji sasiadow

		//jezeli dany element nie ma sasaidow to wychodzimy
		if ( current.N == null && current.E == null && current.S == null && current.W == null ) return null;

		node temp = null;

		if ( current.N != null ) temp = find ( current.N, destX, destY, Mapka ); 
		if ( temp != null ) return temp;
		if ( current.E != null ) temp = find ( current.E, destX, destY, Mapka );
		if ( temp != null ) return temp;
		if ( current.S != null ) temp = find ( current.S, destX, destY, Mapka );
		if ( temp != null ) return temp;
		if ( current.W != null ) temp = find ( current.W, destX, destY, Mapka );
		return temp;
	}


}

class IterativePathfinder {

	
/*
	node findPath(int startX, int startY, int destX, int destY, Map Mapka) {

		LinkedList Lista = new LinkedList();
	

		Lista.push(current);

		while ( !Lista.isEmpty() ) {

			if ( current.x == destX && current.y == destY ) return current;

			//konstrukcja sasiadow

			if ( current.x+1<Mapka.width && Mapka.map[current.y][current.x+1] != 1 && Mapka.visited[current.y][current.x+1] != 1) {
				node temp = new node(current.y, current.x+1, current);
				current.E = temp;
			}
			if ( current.x-1>=0 && Mapka.map[current.y][current.x-1] != 1 && Mapka.visited[current.y][current.x-1] != 1 ) {
				node temp = new node(current.y, current.x-1, current);
				current.W = temp;
			}
			if ( current.y+1<Mapka.height && Mapka.map[current.y+1][current.x] != 1 && Mapka.visited[current.y+1][current.x] != 1 ) {
				node temp = new node(current.y+1, current.x, current);
				current.N = temp;
			}
			if ( current.x-1>=0 && Mapka.map[current.y-1][current.x] != 1 && Mapka.visited[current.y-1][current.x] != 1 ) {
				node temp = new node(current.y-1, current.x, current);
				current.S = temp;
			}

			//---------------------------

			Lista.pop(); //current
			Mapka.visited[current.y][current.x] = 1;



 


		}

		return null; //nie istnieje
	}
*/
}



class Source {
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		int Width = scan.nextInt();
		int Height = scan.nextInt();

		Map Mapka = new Map(Width, Height);

		for ( int i = Height-1; i >= 0; i-- ) {
			for ( int j = 0; j < Width; j++ ) {
				Mapka.map[i][j] = scan.nextInt();
			}
		}
		
		int Prompts = scan.nextInt();

		for ( int i = 0; i < Prompts; i++ ) {
			char typ = scan.next().charAt(0);
			int startx  = scan.nextInt();
			int starty = scan.nextInt();
			int destx = scan.nextInt();
			int desty = scan.nextInt();

			if ( typ == 'r' || typ == 'R' ) {
				RecursivePathfinder Solution = new RecursivePathfinder();
				Solution.findPath(startx, starty, destx, desty, Mapka);
			}
			else {
				IterativePathfinder Solution = new IterativePathfinder();
				//Solution.findPath(startx, starty, destx, desty, Mapka);

			}
		}

	}
}