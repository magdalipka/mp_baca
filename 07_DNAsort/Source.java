//Lipka Magdalena gr.1
import java.util.Scanner;

class Codon {

	String Data;
	String[] Codons;
	int quantity;

	Codon(String inn) {
		Data = inn;
		quantity = Data.length()/3;
		Codons = new String[quantity];
		quantity = 0;
	}

	void toUpper() {
		Data = Data.toUpperCase();
	}

	boolean isStop(int i) {
		if ( Data.toCharArray()[i] == 'T' && ( (Data.toCharArray()[i+1] == 'A' && Data.toCharArray()[i+2] == 'A') || (Data.toCharArray()[i+1] == 'A' && Data.toCharArray()[i+2] == 'G') || (Data.toCharArray()[i+1] == 'G' && Data.toCharArray()[i+2] == 'A') ) ) return true;
		return false;
	}

	boolean isStart(int i) {
		if (Data.toCharArray()[i] == 'A' && Data.toCharArray()[i+1] == 'T' && Data.toCharArray()[i+2] == 'G') return true;
		return false; 
	}

	boolean isBigger (String a, String b) {
		if ( a.toCharArray()[0] > b.toCharArray()[0] ) return true;
		else if ( a.toCharArray()[0] == b.toCharArray()[0] ) {
			if ( a.toCharArray()[1] > b.toCharArray()[1] ) return true;
			else if ( a.toCharArray()[1] == b.toCharArray()[1] ) {
				if ( a.toCharArray()[2] > b.toCharArray()[2] ) return true;
				else if ( a.toCharArray()[2] == b.toCharArray()[2] ) {
					if ( a.toCharArray()[3] > b.toCharArray()[3] ) return true;
					else return false;
				}
				else return false;
			}
			else return false;
		}  
		else return false;
	}

	boolean isSomewhatBigger ( String a, char b, int pos ) {
		if (a.toCharArray()[pos] > b) return true;
		else return false;
	}

	boolean Validate () {

		toUpper();

		//sprawdzenie czy nie ma nieprawidlowych znaczkow
		for ( int i = 0; i < Data.length(); i++ ) {
			if ( Data.toCharArray()[i] != 'A' &&  Data.toCharArray()[i] != 'C' &&  Data.toCharArray()[i] != 'T' &&  Data.toCharArray()[i] != 'G' ) {
				System.out.println("Wrong character in DNA sequence.");
				return false;
			}
		}

		//pierwsza pozycja startu
		int start = -1;
		for ( int i = 0; i < Data.length()-2; i++ ) {
			if ( isStart(i) ) {
				start = i;
				break;
			}
		}
		if ( start == -1 ) {
			System.out.println("Wrong DNA sequence.");
			return false;
		}

		//ostatnia pozycja konca
		int koniec = -1;
		for ( int i = start + 3; i < Data.length()-2; i += 3 ) {
			if ( isStop(i) ) {
				koniec = i;
			}
		}
		if ( koniec == -1 ) {
			System.out.println("Wrong DNA sequence.");
			return false;
		}
		if ( koniec == start + 3 ) {
			System.out.println("Wrong DNA sequence.");
			return false;
		}

		//sprawdzenie powtorzen startu
		for ( int i = start+3; i < koniec; i += 3 ) {
			if ( isStart(i) ) {
				System.out.println("More than one START/STOP codon.");
				return false;
			}
		}

		//sprawdzenie powtorzen konca 
		for ( int i = koniec-3; i > start; i -= 3 ) {
			if ( isStop(i) ) {
				System.out.println("More than one START/STOP codon.");
				return false;
			}
		}


		//przepiasnie poprawnych danych do tabelki
		for ( int i = start + 3; i < koniec; i += 3 ) {
			String Pom = Character.toString(Data.toCharArray()[i]);
			Pom += Character.toString(Data.toCharArray()[i+1]);
			Pom += Character.toString(Data.toCharArray()[i+2]);
			Codons[quantity] = Pom;
			quantity++;
		}
		
		return true;
	
	}

	void swap (int m, int n) {
		String pom = Codons[m];
		Codons[m] = Codons[n];
		Codons[n] = pom;
	}

	int partition (char klucz, int left, int right, int depth ) {
	
		int i = left - 1;

		for ( int j = left; j <= right; j++ ) {
			if ( !isSomewhatBigger(Codons[j], klucz, depth) ) {
				i++;
				swap(i, j);
			}
		}
		return i;
	}

	char genKey(int i) {
		if ( i%4 == 0 ) return 'A';
		else if ( i%4 == 1 ) return 'C';
		else if ( i%4 == 2 ) return 'G';
		else return 'T'; 
	}

	int findBorder ( int start, int depth ) {
		for ( int i = start; i < quantity; i++ ) {
			if ( i < quantity-1 && Codons[i].toCharArray()[depth] != Codons[i+1].toCharArray()[depth]  || depth > 0 && i < quantity-1 && Codons[i].toCharArray()[depth-1] != Codons[i+1].toCharArray()[depth-1] ) return i;
		}
		return quantity - 1;
	}

	void sort () {
 
		int left = 0;
		int right = quantity-1;
		int Border = -1;
		char key = 'T';
		int licznik = 0;

		//przesortowanie ze wzgledu na pierwszy znak
		while ( left < right ) {
			key = genKey(licznik);
			int temp = partition(key, left, right, 0);
			if ( temp > left ) left = temp;
			licznik++;
		}
		//sortowanie ze wzgledu na pozostale znaki
		for ( int depth = 1; depth < 3; depth++ ) {
			left = 0;
			licznik = 3;
			key = genKey(licznik);
			while( left < right ) {
				if ( key == 'T' ) Border = findBorder(left, depth-1);
				licznik++;
				key = genKey(licznik);
				int temp = partition(key, left, Border, depth);
				if ( temp > left ) left = temp;
				if ( key == 'T' ) left = Border+1;
			}			
		}
		
	}

	void print() {
		for ( int i = 0; i < quantity; i++ ) {
			System.out.print(Codons[i]);
		}
		System.out.print("\n");
	}



}


class Source {
	public static Scanner scan = new Scanner(System.in);
	public static void main ( String[] args  ) {

		int Prompts = scan.nextInt();

		for ( int i = 0; i < Prompts; i++ ) {

			String Input = scan.next();

			Codon KODON = new Codon(Input);

			if ( KODON.Validate() ) {

				KODON.sort();
				KODON.print();


			}
			

		}

	}

}