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

	void heapify( int i ) { 
		
		int largest = i; // Initialize largest as root 
		int l = 2*i + 1; // left = 2*i + 1 
		int r = 2*i + 2; // right = 2*i + 2 
  
		// If left child is larger than root 
		if (l < n && tab[l] < tab[largest]) 
			largest = l; 
  
		// If right child is larger than largest so far 
		if (r < n && tab[r] < tab[largest]) 
			largest = r; 
  
		// If largest is not root 
		if (largest != i) 
		{ 
			int swap = tab[i]; 
			tab[i] = tab[largest]; 
			tab[largest] = swap; 
  
			// Recursively heapify the affected sub-tree 
			heapify( largest ); 
		} 

		
		/*
		if ( i == 0 ) return;

		int parent = (i-1)/2;

		if ( tab[i] > tab[parent] ) {
			swap (i, parent);
			heapify(parent);
		}
		*/
	} 

	int find (int poz) {
		for ( int i = 0; i < poz; i++ ) {
			heapify(i);
		}
		print();
		for ( int i = poz; i < n; i++ ) {
	 		if ( tab[i] < tab[0] ) {
				int temp = tab[0];
				tab[0] = tab[i];   // Now A[i] the new root
				tab[i] = temp;
		 		heapify(i);
			  }
			}
		print();
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
			}
			

			int Prompts = scan.nextInt();

			for ( int p = 0; p < Prompts; p++ ) {
				int x = scan.nextInt();
				System.out.println(x + " " + Tab.find(x));
			}


		}

	}

}