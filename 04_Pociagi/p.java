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

			//jak nie zadziala to sprawdzic poc.first.next == poc.last

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

			Wagon Wag = new Wagon(Wagonik);

			Train Poc = findTrain(Pociag);

			boolean kierunek_konca = true;

			if ( ((Poc.last).prev).prev == Poc.last && ! ( (Poc.first).next == Poc.last ) ) kierunek_konca = false;

			//jak nie zadziala to sprawdzic poc.first.next == poc.first.prev

			if ( kierunek_konca ) {
				//jezeli koniec pociagu jest normalny

				Wag.next = Poc.first;
				Wag.prev = Poc.last;
				(Poc.last).next = Wag;
				(Poc.first).prev = Wag;
				Poc.last = Wag;

			}
			else {
				//jezeli koniec pociagu jest odwrocony

				Wag.next = Poc.last;
				Wag.prev = Poc.first;
				(Poc.last).prev = Wag;
				(Poc.first).prev = Wag;
				Poc.last = Wag;

			}

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
					
					if ( zmiana_kierunku ) kierunek = !kierunek;

					System.out.print(Temp.name);

					//teraz musimy sprawdzic czy jak pzejdziemy dalej to bedziemy na odwroconej czesci pociagu

					if ( kierunek && (Temp.next).next == Temp && !zmiana_kierunku ) {
						zmiana_kierunku = true;
						//kierunek = false; //jestesmy na osatnim wagoniku normalnej czesci
					}
					else zmiana_kierunku = false;

					if ( !kierunek && (Temp.prev).prev == Temp && !zmiana_kierunku ) {
						zmiana_kierunku = true;
						//kierunek = true; //jestesmy na ostatnim wagoniku odwroconej czesci
					}
					else zmiana_kierunku = false;


					if ( kierunek ) Temp = Temp.next;
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

			Train T1 = findTrain(P1);
			Train T2 = findTrain(P2);

			//sprawdzamy czy pociagi sa odwrocone

			boolean kierunek1 = true;
			boolean kierunek2 = true;

			if ( ((T1.first).next).next == T1.first && (T1.first).next != T1.last ) kierunek1 = false;

			if ( ((T2.first).next).next == T2.first && (T2.first).next != T2.last ) kierunek2 = false;
			
			//last pierwszego pociagu musi mieć linki do pierwszego wagonika drugiego pociagu

			if ( kierunek1 && kierunek2 ) {
				//laczenie dwoch normalnych

				//sklejamy srodek
				(T1.last).next = (T2.first).next;
				((T2.first).next).prev = T1.last;

				//sklejamy boki
				(T1.first).prev = T2.last;
				(T2.last).prev = T1.first;

				//updatujemy T1.last
				T1.last = T2.last;
			}
			else if ( kierunek1 && !kierunek2 ) {
				//laczenie normalnego z odwroconym


			}
			else if ( !kierunek1 && kierunek2 ) {
				//laczenie odwroconego z normalnym

			}
			else if ( !kierunek1 && !kierunek2 ) {
				//laczenie dwoch odwroconych

			}


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












