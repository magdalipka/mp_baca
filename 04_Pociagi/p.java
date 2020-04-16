//Magdalena Lipa gr.1//

import java.util.Scanner;

class Zajezdnia {

	class Wagon {

		public String name;
		public Wagon next;
		public Wagon prev;

		Wagon (String nam) {
			name = nam;
			next = null;
			prev = null;
		}
	}

	class Train {

		public String name;
		public Train next;
		public Wagon first;
		public Wagon last;

		Train (String nam) {
			name = nam;
			Wagon Wag = new Wagon("#");
			first = Wag;
			last = Wag;
			Wag.next = Wag;
			Wag.prev = Wag;
		}
	}

	Train HEAD;

	Zajezdnia() {
		HEAD = null;
	}

	//class Functions {


		public void New (String Pociag, String Wagonik) {

			Train Poc = new Train(Pociag);

			if ( HEAD == null ) HEAD = Poc;
			else {
				Poc.next = HEAD.next;
				HEAD.next = Poc;
			}

			Wagon Wag = new Wagon(Wagonik);
			(Poc.first).next = Wag;
			(Poc.first).prev = Wag;
			Poc.last = Wag;
		}


		public void InsertFirst ( String Pociag, String Wagonik ) {

			Wagon Wag = new Wagon(Wagonik);

			Train Poc = findTrain(Pociag);

			boolean kierunek_poczatku = true;

			if ( ((Poc.first).next).next == Poc.first && (Poc.first).next != (Poc.first).prev ) kierunek_poczatku = false;

			//first zostaje zawsze taki sam bo to glowa

			if ( kierunek_poczatku ) {
				//wstawianie kiedy poczatek jest normalny
				
				Wag.next = (Poc.first).next;
				Wag.prev = Poc.first;
				((Poc.next).first).prev = Wag; //ten ktory wczesniej byl pierwsza nie glowa teraz wskazuje jako poprzedni na nowy dodawany
				(Poc.first).next = Wag;
				

			}
			else {
				//wstawianie kiedy poczatek jest owdrocony

				Wag.next = Poc.first;
				Wag.prev = (Poc.first).next;
				((Poc.first).next).next = Wag; // pierwszy musi jak next (prev) wskazyac na nowy
				(Poc.first).next = Wag;

			}

		}

		public void InsertLast ( String Pociag, String Wagonik ) {


		}

		public void Display ( String Pociag ) {
			Train Poc = findTrain(Pociag);
			
			Wagon Temp = Poc.first;

			if ( Temp.next == Temp.prev ) {
				//to znaczy ze jest jeden wagon tylko
				//ewentualnie nie zadziala to sprawdzic czy Temp.next == Poc.last

				System.out.print(Poc.name + ": " + Temp.name);
			}
			else {
				//wiecej niz jeden wagon

				Temp = Temp.next; //jetesmy na pierwszym wagoniku (nie glowie) niezaleznie od odwrocenia lub nie 

				boolean kierunek = true; //domyslnie poczatek nie jest odwrcony, dopiero to sprawdzimy

				boolean zmiana_kierunku = false;

				while ( !( (Temp.name).equals("#") ) ) {
					

					System.out.print(Temp.name);

					//teraz musimy sprawdzic czy jak pzejdziemy dalej to bedziemy na odwroconej czesci pociagu

					if ( kierunek && (Temp.next).next == Temp && !zmiana_kierunku ) {
						zmiana_kierunku = true;
						kierunek = false; //jestesmy na osatnim wagoniku normalnej czesci
					}
					else zmiana_kierunku = false;

					if ( !kierunek && (Temp.prev).prev == Temp && !zmiana_kierunku ) {
						zmiana_kierunku = true;
						kierunek = true; //jestesmy na ostatnim wagoniku odwroconej czesci
					}
					else zmiana_kierunku = false;


					if ( (kierunek && !zmiana_kierunku) || (!kierunek && zmiana_kierunku)  ) Temp = Temp.next;
					else Temp = Temp.prev;


				}

			}



		}

		public void Trains () {
			Train Temp = HEAD;
			System.out.print("Trains:");
			while ( Temp != null ) {
				System.out.print("  " + Temp.name);
				Temp = Temp.next;
			}
			System.out.print("\n");

		}


		public void Reverse ( String Pociag ) {

			Train Temp = findTrain(Pociag);

			(Temp.first).prev = (Temp.first).next;
			(Temp.first).next = Temp.last;
			Temp.last = (Temp.first).prev;
		
		}


		public void Union ( String P1, String P2 ) {

		}

		public void DelFirst ( String P1, String P2 ) {

		}

		public void DelLast ( String P1, String P2 ) {

		}

		public Train findTrain ( String P ) {

			Train Temp = HEAD;

			while ( Temp != null ) {
				if ( (Temp.name).equals(P) ) return Temp;
				Temp = Temp.next;
			}
			return null;

		}

		public void CLEAR () {

		}

		public boolean ExistT ( String P ) {

		

		}

		public boolean ExistW ( String P, String W ) {

		

		}

	//}

}

/*

	struct Elem {
		string name;
		Elem* next;
		Elem* prev;

	};

	struct Train_List {

		string name;
		Train_Liss *next;
		Elem* first;
		Elem* next;

	};

*/




class Source {
	static Scanner scan =  new Scanner(System.in);
	public static void main(String [] args) {

		int Sets = scan.nextInt();

		while (Sets > 0) {

			int Commands = scan.nextInt();

			Zajezdnia Zaj = new Zajezdnia();

			while (Commands > 0) {





				Commands--;
			}
			
			

			Sets--;
		}


	}
}












