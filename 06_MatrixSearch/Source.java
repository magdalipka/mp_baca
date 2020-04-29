//  Magdalena Lipka gr.1 //
import java.util.Scanner;

class Element {
	int x;
	int y;

	Element(int a, int b) {
		x = b;
		y = a;
	}
}

class Source {
	public static Scanner scan = new Scanner(System.in);
	public static void main ( String[] args ) {

		int Prompts = scan.nextInt();

		for ( int i = 0; i < Prompts; i++ ) {

			int Height = scan.nextInt();
			int Width = scan.nextInt();

			int[][] Matrix = new int[Height][Width];

			for ( int j = 0; j < Height; j++ ) {
				for ( int k = 0; k < Width; k++ ) {
					Matrix[j][k] = scan.nextInt();
				}
			}

			int toFind = scan.nextInt();

			Element solution = new Element(-1, -1);

			solution.x = Width;
			solution.y = Height;
			solution = RekPier(Matrix, Width, Height, toFind, 0, Height - 1, solution);
			if ( solution.x < 0 || solution.x >= Width || solution.y < 0 || solution.y >= Height ) System.out.println( "RekPier: nie ma " + toFind );
			else System.out.println( "RekPier: " + toFind + " w (" + solution.y + "," + solution.x + ")" );

			solution.x = -1;
			solution.y = -1;
			solution = RekOst(Matrix, Width, Height, toFind, 0, Height - 1, solution);
			if ( solution.x < 0 || solution.x >= Width || solution.y < 0 || solution.y >= Height ) System.out.println( "RekOst: nie ma " + toFind );
			else System.out.println( "RekOst: " + toFind + " w (" + solution.y + "," + solution.x + ")" );
			
			solution = IterPier(Matrix, Width, Height, toFind);
			if ( solution.x < 0 || solution.x >= Width || solution.y < 0 || solution.y >= Height ) System.out.println( "IterPier: nie ma " + toFind );
			else System.out.println( "IterPier: " + toFind + " w (" + solution.y + "," + solution.x + ")" );
			
			solution = IterOst(Matrix, Width, Height, toFind);
			if ( solution.x < 0 || solution.x >= Width || solution.y < 0 || solution.y >= Height ) System.out.println( "IterOst: nie ma " + toFind );
			else System.out.println( "IterOst: " + toFind + " w (" + solution.y + "," + solution.x + ")" );

			System.out.println("---");
		}

	}

	static Element RekPier ( int[][] matrix, int width, int height, int toFind, int i, int j, Element bestSoFar ) {
		
		if ( i >= width || j < 0) return bestSoFar;

		if ( matrix[j][i] == toFind ) {
			if ( j < bestSoFar.y ) {
				bestSoFar.x = i;
				bestSoFar.y = j;
			}
			else if ( j == bestSoFar.y && i < bestSoFar.x ) {
				bestSoFar.x = i;
				bestSoFar.y = j;
			}
		}

		if ( matrix[j][i] >= toFind ) j--;
		else i++;

		return RekPier(matrix, width, height, toFind, i, j, bestSoFar);

	}

	static Element RekOst ( int[][] matrix, int width, int height, int toFind, int i, int j, Element bestSoFar ) {
		if ( i >= width || j < 0) return bestSoFar;

		if ( matrix[j][i] == toFind ) {
			if ( j > bestSoFar.y ) {
				bestSoFar.x = i;
				bestSoFar.y = j;
			}
			else if ( j == bestSoFar.y && i > bestSoFar.x ) {
				bestSoFar.x = i;
				bestSoFar.y = j;
			}
		}

		if ( matrix[j][i] > toFind ) j--;
		else i++;

		return RekOst(matrix, width, height, toFind, i, j, bestSoFar);
	}

	static Element IterPier ( int[][] matrix, int width, int height, int toFind ) {
		Element bestSoFar = new Element(height, width);

		int i = 0;
		int j = height - 1;

		while ( i < width && j >= 0 ) {

			if ( matrix[j][i] == toFind ) {
				if ( j < bestSoFar.y ) {
					bestSoFar.x = i;
					bestSoFar.y = j;
				}
				else if ( j == bestSoFar.y && i < bestSoFar.x ) {
					bestSoFar.x = i;
					bestSoFar.y = j;
				}
			}

			if ( matrix[j][i] >= toFind ) j--;
			else i++;

		}
		return bestSoFar;
	}

	static Element IterOst ( int[][] matrix, int width, int height, int toFind ) {
		Element bestSoFar = new Element(-1, -1);

		int i = 0;
		int j = height - 1;

		while ( i < width && j >= 0 ) {

			if ( matrix[j][i] == toFind ) {
				if ( j > bestSoFar.y ) {
					bestSoFar.x = i;
					bestSoFar.y = j;
				}
				else if ( j == bestSoFar.y && i > bestSoFar.x ) {
					bestSoFar.x = i;
					bestSoFar.y = j;
				}
			}

			if ( matrix[j][i] > toFind ) j--;
			else i++;

		}

		return bestSoFar;
	}

}

/* TESTY

1.)
INPUT
1
6 7
0 0 1 2 3 4 4
1 2 2 3 3 4 4
2 2 4 4 4 5 6
3 3 4 4 4 6 6
7 7 7 7 7 7 8
8 8 8 9 10 11 11
4

OUTPUT
RekPier: 4 w (0,5)
RekOst: 4 w (3,4)
IterPier: 4 w (0,5)
IterOst: 4 w (3,4)
---

2.)
INPUT
1
6 7
0 0 1 2 3 4 4
1 2 2 3 3 4 4
2 2 4 4 4 5 6
3 3 4 4 4 6 6
7 7 7 7 7 7 8
8 8 8 9 10 11 11
7

OUTPUT
RekPier: 7 w (4,0)
RekOst: 7 w (4,5)
IterPier: 7 w (4,0)
IterOst: 7 w (4,5)
---

3.)
INPUT
1
6 7
0 0 1 2 3 4 4
1 2 2 3 3 4 4
2 2 4 4 4 5 6
3 3 4 4 4 6 6
7 7 7 7 7 7 8
8 8 8 9 10 11 11
1

OUTPUT
RekPier: 1 w (0,2)
RekOst: 1 w (1,0)
IterPier: 1 w (0,2)
IterOst: 1 w (1,0)
---

4.)
INPUT
1
6 7
0 0 1 2 3 4 4
1 2 2 3 3 4 4
2 2 4 4 4 5 6
3 3 4 4 4 6 6
7 7 7 7 7 7 8
8 8 8 9 10 11 11
11

OUTPUT
RekPier: 11 w (5,5)
RekOst: 11 w (5,6)
IterPier: 11 w (5,5)
IterOst: 11 w (5,6)
---

5.)
INPUT
1
6 7
0 0 1 2 3 4 4
1 2 2 3 3 4 4
2 2 4 4 4 5 6
3 3 4 4 4 6 6
7 7 7 7 7 7 8
9 9 9 9 10 11 11
8

OUTPUT
RekPier: 8 w (4,6)
RekOst: 8 w (4,6)
IterPier: 8 w (4,6)
IterOst: 8 w (4,6)
---

6.)
INPUT
1
3 7
0 0 1 2 3 4 4
1 2 2 3 3 4 4
2 2 4 6 6 6 6
4

OUTPUT
RekPier: 4 w (0,5)
RekOst: 4 w (2,2)
IterPier: 4 w (0,5)
IterOst: 4 w (2,2)
---

7.)
INPUT
1
3 7
0 0 1 1 4 4 4
1 2 2 4 4 4 4
2 2 4 6 6 6 6
3

OUTPUT
RekPier: nie ma 3
RekOst: nie ma 3
IterPier: nie ma 3
IterOst: nie ma 3
---

*/