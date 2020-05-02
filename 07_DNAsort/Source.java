import java.util.Scanner;

import javax.xml.crypto.Data;

class Codon {

	String Data;

	Codon(String inn) {
		Data = inn;
	}

	boolean Cut () {

		String Pom = "";

		boolean started = false;
		boolean ended = false;

		for ( int i = 0; i < Data.length(); i++ ) {

			//sprawdzenie poprawnosci znakow
			if ( Data.toCharArray()[i] != 'A' && Data.toCharArray()[i] != 'G' && Data.toCharArray()[i] != 'T' && Data.toCharArray()[i] != 'C') {
				System.out.println("Wrong character in DNA sequence.");
				return false;
			}

			//wykrycie startu
			if ( i+2 < Data.length() && Data.toCharArray()[i] == 'A' && Data.toCharArray()[i+1] == 'T' && Data.toCharArray()[i+2] == 'G' ) {
				if ( !started ) {
					started = true;
					i += 2;
					continue;
				}
				else {
					System.out.println("More than one START/STOP codon");
					return false;
				}
			}
			
			//wykrycie konca
			if ( i+2 < Data.length() && Data.toCharArray()[i] == 'T' && ( (Data.toCharArray()[i+1] == 'A' && Data.toCharArray()[i+2] == 'A') || (Data.toCharArray()[i+1] == 'A' && Data.toCharArray()[i+2] == 'G') || (Data.toCharArray()[i+1] == 'G' && Data.toCharArray()[i+2] == 'A') ) ) {
				if ( started && !ended ) {
					ended = true;
					i += 2;
					continue;
				}
				else {
					System.out.println("More than one START/STOP codon");
					return false;
				}
			}
			//sprawdzenie czy jestesmy miedzy startem a koncem, jesli tak to dodanie znakow
			if ( started && !ended ) {
				Pom += Data.toCharArray()[i];
			}

		}

		if ( started && ended ) {
			Data = Pom;
			System.out.println(Data);
			return true;
		}
		else {
			System.out.println("Wrong DNA sequence.");
			return false;
		}

	}

	boolean Verify () {

		if ( !Cut() ) return false;

		if ( Data.length()%3 != 0 ) {
			System.out.println("Wrong DNA sequence.");
			return false;
		}

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