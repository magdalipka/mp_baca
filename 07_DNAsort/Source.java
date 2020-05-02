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
		if ( koniec == start + 2 ) {
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

}


class Source {
	public static Scanner scan = new Scanner(System.in);
	public static void main ( String[] args  ) {

		//int Prompts = scan.nextInt();

		for ( int i = 0; i < 1; i++ ) {

			String Input = scan.next();

			Codon KODON = new Codon(Input);

			if ( KODON.Validate() ) {



			}
			

		}

	}

}