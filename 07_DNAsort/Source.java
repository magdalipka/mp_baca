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

	boolean Cut () {

		boolean started = false;
		boolean ended = false;
		int licznik = 0;
		int i = 0;
		while ( i < Data.length() ) {
			//wykrycie startu
			if ( i+2 < Data.length() && Data.toCharArray()[i] == 'A' && Data.toCharArray()[i+1] == 'T' && Data.toCharArray()[i+2] == 'G' ) {

				if ( !started ) {
					started = true;
					i += 3;
					continue;
				}
				else {
					System.out.println("More than one START/STOP codon.");
					return false;
				}

			}

			if ( !started ) {
				i++;
				continue;
			}

			//wykrycie konca
			if ( i+2 < Data.length() && Data.toCharArray()[i] == 'T' && ( ( Data.toCharArray()[i+1] == 'G' && Data.toCharArray()[i+2] == 'A' ) || ( Data.toCharArray()[i+1] == 'A' && Data.toCharArray()[i+2] == 'A' ) || (Data.toCharArray()[i+1] == 'G' && Data.toCharArray()[i+2] == 'G' ) ) ) {

				if ( !started ) {
					System.out.println("Wrong DNA sequence.");
					return false;
				}
				else if ( ended ) {
					System.out.println("More tha one START/STOP codon.");
					return false;
				}
				else {
					ended = true;
					i += 3;
					continue;
				}

			}

			//jesli jestesmy miedzy startem a stopem to dodajemy element do tablicy
			if ( i+2 < Data.length() && started && !ended ) {
				String Pom = "";
				Pom += Data.toCharArray()[i] + Data.toCharArray()[i+1] + Data.toCharArray()[i+2];
				Codons[quantity] = Pom;
				quantity++;
			}

		}

		if ( started && ended ) {
			return true;
		}
		else {
			System.out.println("Wrong DNA sequence.");
			return false;
		}

	}

	boolean Verify () {

		toUpper();

		if ( !Cut() ) return false;

		for ( int i = 0; i < Data.length(); i++ ) {

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

			if ( KODON.Verify() ) {



			}
			

		}

	}

}