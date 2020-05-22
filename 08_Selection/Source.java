//Magdalena Lipka gr. 1
import java.util.Scanner;

class Tabelka {
	int[] tab;
	int n;

	Tabelka( int ilosc ) {
		tab = new int[ilosc];
		n = ilosc;
	}

	void builtmaxheap( int lvl ) {
		for ( int i = 0; i < lvl; i++ ) {

		}
	}

	void swap( int i, int j ) {
		tab[i] = tab[i] + tab[j];
		tab[j] = tab[i] - tab[j];
		tab[i] = tab[i] - tab[j];
	}

	void heapify(int i, int lim) { 
        int topop = i; 
        int l = 2*i + 1; // left = 2*i + 1 
        int r = 2*i + 2; // right = 2*i + 2 

        if (l < n && l < lim && tab[l] > tab[topop]) topop = l;
  
		if (r < n && r < lim && tab[r] > tab[topop]) topop = r;
		
		if ( topop != i ) {
			swap(topop, i);
			heapify(topop, lim);
		}
    }

	int find (int x) {
		for ( int i = x-1; i >=0 ; i-- ) {
			heapify (i, x);
		}

		for ( int i = x; i < n; i++ ) {
			if ( tab[i] < tab[0] ) {
				swap(i, 0);
				heapify(0, x);
			}
		}

		return tab[0];
	}

	void print() {
		System.out.println("---");
		for ( int i = 0; i < n; i++ ) {
			System.out.print(tab[i]);
		}
		System.out.println("\n---");
	}
	
	
}

class Source {
	public static Scanner scan = new Scanner (System.in);
	public static void main ( String[] args ) {

		int Quantity = scan.nextInt();

		for ( int q = 0; q < Quantity; q++ ) {

			int Length = scan.nextInt();

			Tabelka Tab = new Tabelka(Length);
			
			for ( int i = 0; i<Length; i++ ) {
				Tab.tab[i] = scan.nextInt();
				//Tab.heapify(i, i);
				//Tab.print();
			}
			
			//for ( int i = 0; i < Length; i++ ) {
				//Tab.heapify(i);
			//}

			int Prompts = scan.nextInt();

			for ( int p = 0; p < Prompts; p++ ) {
				int x = scan.nextInt();
				if (x < 1 || x > Length
				) System.out.println(x + " brak");
				else System.out.println(x + " " + Tab.find(x));
			}

		}

	}

}