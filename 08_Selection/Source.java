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
/*
	while not end of tabay, 
	if heap is empty, 
		place item at root; 
	else, 
		place item at bottom of heap; 
		while (child > parent) 
			swap(parent, child); 
	go to next tabay element; 
end
*/
	void heapify(int i) { 
        //int largest = i; // Initialize largest as root 
        int l = 2*i + 1; // left = 2*i + 1 
        int r = 2*i + 2; // right = 2*i + 2 
  
		if ( l < n && r < n && tab[l] > tab[r]) {
			swap (l, r);
			heapify(l);
			heapify(r);
		}

        if (l < n && tab[l] > tab[i]) {
			swap(i, l);
			heapify(i);
		} 
  
        if (r < n && tab[r] > tab[i]){
			swap(i, l);
			heapify(i);
		} 
    } 

	int find (int x) {
		//heapify(0);
		return tab[x-1];
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
			
			for ( int i = 0; i < Length; i++ ) {
				Tab.heapify(i);
			}

			Tab.print();

			int Prompts = scan.nextInt();

			for ( int p = 0; p < Prompts; p++ ) {
				int x = scan.nextInt();
				System.out.println(x + " " + Tab.find(x));
			}


		}

	}

}