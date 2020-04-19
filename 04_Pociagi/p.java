//Magdalena Lipka gr.1//

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
				Poc.next = HEAD;
				HEAD = Poc;
			}

			Wagon Wag = new Wagon(Wagonik);
			Wag.prev = Poc.first;
			Wag.next = Poc.first;
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
				((Poc.first).next).prev = Wag; //ten ktory wczesniej byl pierwsza nie glowa teraz wskazuje jako poprzedni na nowy dodawany
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

			if ( (Poc.last).prev == Poc.first && (Poc.first).next != Poc.last) kierunek_konca = false;


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

				System.out.print(Poc.name + ": " + (Temp.next).name + "\n");
			}
			else {
				//wiecej niz jeden wagon

				System.out.print(Poc.name + ":");

				//Temp = Temp.next; //jetesmy na pierwszym wagoniku (nie glowie) niezaleznie od odwrocenia lub nie 

				boolean on_next = false;

				boolean kierunek = true; //domyslnie poczatek nie jest odwrcony, dopiero to sprawdzimy

				boolean zmiana_kierunku = false;

				while ( !((Temp.name).equals("#")) || !on_next ) {
					
					if ( on_next ) System.out.print(" " + Temp.name);

					if ( zmiana_kierunku ) kierunek = !kierunek;

					//teraz musimy sprawdzic czy jak pzejdziemy dalej to bedziemy na odwroconej czesci pociagu

					if ( kierunek && (Temp.next).next == Temp ) {
						zmiana_kierunku = true;
						//kierunek = false; //jestesmy na osatnim wagoniku normalnej czesci
					}
					else if ( !kierunek && (Temp.prev).prev == Temp ) {
						zmiana_kierunku = true;
						//kierunek = true; //jestesmy na ostatnim wagoniku odwroconej czesci
					}
					
					else zmiana_kierunku = false;


					if ( kierunek ) Temp = Temp.next;
					else Temp = Temp.prev;

					on_next = true;

				}

				System.out.print("\n");

			}



		}

		public void Trains () {
			Train Temp = HEAD;
			System.out.print("Trains:");
			while ( Temp != null ) {
				System.out.print(" " + Temp.name);
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

			boolean direction_of_t1_end = true;
			boolean direction_of_t2_begin = true;
			boolean direction_of_t2_end = true;
			

			//sprawdzamy odpowiednie kierunki wagonow

			if ( ((T1.first).prev).prev == T1.first && !( (T1.first).next == T1.last ) ) direction_of_t1_end = false;

			if ( (T2.first).next != T2.last ) {
				//czyli t2 ma wiecej niz jeden wagon

				if ( ((T2.first).prev).prev == T2.first ) direction_of_t2_end = false;
				if ( ((T2.first).next).next == T2.first ) direction_of_t2_begin = false;

			}

			//teraz musimy spiac ze soba srodkowe wagoniki

			if ( direction_of_t1_end ) (T1.last).next = (T2.first).next;
			else (T1.last).prev = (T2.first).next;

			if ( direction_of_t2_begin ) ((T2.first).next).prev = T1.last;
			else ((T2.first).next).next = T1.last;

			//laczymy first pierwszeo z lastem ostatniego

			if ( direction_of_t2_end ) (T2.last).next = T1.first;
			else (T2.last).prev = T1.first;

			(T1.first).prev = T2.last;
			 
			//aktalizujemy last
			
			T1.last = T2.last;

			//odczepiamy glowe od pociagu2
			T2.first = null;
			T2.last = null;
			T2.name = null;


			//usuwamy pociag2 z listy pociagow
			//najpierw musimy znalezc poprzedni


			if ( T2 == HEAD ) HEAD = T2.next;
			else {

				Train Temp = HEAD;

				while ( Temp.next != T2 ) Temp = Temp.next;

				Temp.next = T2.next;
				T2.next = null;

			}

		}

		public void DelFirst ( String P1, String P2 ) {

			//sprawdzamy czy dzialanie jest mozliwe

			Train Poc = findTrain(P1);
			if ( Poc == null ) {
				System.out.print( "Train " + P1 + " does not exist\n");
				return;
			}
			
			if ( findTrain(P2) != null ) {
				System.out.print( "Train " + P2 + " already exists\n" );
				return;
			}

			//jezeli dzialanie jest mozliwe wykonujemy co nastepuje

			//tworzymy nowy pociag z wagonikiem i wstawiamy go na poczatek listy

			New(P2, ((Poc.first).next).name);

			//usuwamy pierwszy wagonik z pociagu

			if ( (Poc.first).next == Poc.last ) {
				//jezeli byl to jedyny wagonik to usuwamy caly pociag

				((Poc.first).next).name = null;
				((Poc.first).next).next = null;
				((Poc.first).next).prev = null;
				(Poc.first).next = null;
				(Poc.first).prev = null;
				Poc.first = null;
				Poc.last = null;

				//usuniecie pociagu z listy
				//musimy znalezc poprzedni

				if ( Poc == HEAD) {
					HEAD = Poc.next;
				}
				else {

					Train Temp = HEAD;
					while ( Temp.next != Poc ) Temp = Temp.next;
					
					Temp.next = (Temp.next).next;

				}

			}
			else {
				//jesli nie byl to ostatni wagonik to tylko usuwamy wagonik

				//musimy sprawdzic kierunki wagonikow

				boolean kierunek_usuwanego = true;
				boolean kierunek_nast = true;

				Wagon Usuwany = (Poc.first).next;
				Wagon Nast;

				if ( Usuwany.next == Poc.first ) kierunek_usuwanego = false;

				if ( kierunek_usuwanego ) Nast = Usuwany.next;
				else Nast = Usuwany.prev; 

				if ( kierunek_usuwanego && Nast.next == Usuwany ) kierunek_nast = !kierunek_usuwanego;
				else if ( !kierunek_usuwanego && Nast.prev == Usuwany ) kierunek_nast = !kierunek_usuwanego;
				else kierunek_nast = kierunek_usuwanego;
				//przepiecie pierwszego z nastem tak zeby minac usuwany

				if ( kierunek_usuwanego ) (Poc.first).next = Usuwany.next;
				else (Poc.first).next = Usuwany.prev;

				if ( kierunek_nast ) Nast.prev = Poc.first;
				else Nast.next = Poc.first;

				
			}
			

		}

		public void DelLast ( String P1, String P2 ) {

			//sprawdzamy czy dzialanie jest mozliwe

			Train Poc = findTrain(P1);
			if ( Poc == null ) {
				System.out.print( "Train " + P1 + " does not exist\n");
				return;
			}
			
			if ( findTrain(P2) != null ) {
				System.out.print( "Train " + P2 + " already exists\n" );
				return;
			}

			//jezeli dzialanie jest mozliwe wykonujemy co nastepuje

			//tworzymy nowy pociag z wagonikiem i wstawiamy go na poczatek listy

			New(P2, (Poc.last).name);

			//usuwamy pierwszy wagonik z pociagu

			if ( (Poc.first).next == Poc.last ) {
				//jezeli byl to jedyny wagonik to usuwamy caly pociag

				((Poc.first).next).name = null;
				((Poc.first).next).next = null;
				((Poc.first).next).prev = null;
				(Poc.first).next = null;
				(Poc.first).prev = null;
				Poc.first = null;
				Poc.last = null;

				//usuniecie pociagu z listy
				//musimy znalezc poprzedni

				if ( Poc == HEAD) {
					HEAD = Poc.next;
				}
				else {

					Train Temp = HEAD;
					while ( Temp.next != Poc ) Temp = Temp.next;
					
					Temp.next = (Temp.next).next;

				}

			}
			else {
				//jesli nie byl to ostatni wagonik to tylko usuwamy wagonik

				Wagon Usuwany = Poc.last;
				Wagon Poprz;

				boolean kierunek_usuwanego = true;
				boolean kierunek_poprz;

				if ( Usuwany.prev == Poc.first ) kierunek_usuwanego = false;

				if ( kierunek_usuwanego ) Poprz = Usuwany.prev;
				else Poprz = Usuwany.next;

				if ( kierunek_usuwanego && Poprz.next == Usuwany ) kierunek_poprz = !kierunek_usuwanego;
				else if ( !kierunek_usuwanego && Poprz.prev == Usuwany ) kierunek_poprz = !kierunek_usuwanego;
				else kierunek_poprz = kierunek_usuwanego; 

				if ( kierunek_usuwanego ) (Poc.first).prev = Usuwany.prev;
				else (Poc.first).prev = Usuwany.next;

				if ( kierunek_poprz ) Poprz.next = Poc.first;
				else Poprz.prev = Poc.first;

				Poc.last = Poprz;


			}
		}

		public Train findTrain ( String P ) {

			Train Temp = HEAD;

			while ( Temp != null ) {
				if ( (Temp.name).equals(P) ) return Temp;
				Temp = Temp.next;
			}
			return null;

		}

		public Wagon findWag ( String P, String W ) {

			Train Poc = findTrain(P);
			
			if ( Poc == null ) return null;
			else {

				Wagon Temp = Poc.first;
				if ( Temp == null ) return null;
				else Temp = Temp.next;

//---------------------------------------------------------------


			if ( Temp.next == Temp.prev ) {
				//to znaczy ze jest jeden wagon tylko
				//ewentualnie nie zadziala to sprawdzic czy Temp.next == Poc.last

				System.out.print(Poc.name + ": " + (Temp.next).name + "\n");
			}
			else {
				//wiecej niz jeden wagon

				System.out.print(Poc.name + ":");

				Temp = Temp.next; //jetesmy na pierwszym wagoniku (nie glowie) niezaleznie od odwrocenia lub nie 

				boolean kierunek = true; //domyslnie poczatek nie jest odwrcony, dopiero to sprawdzimy

				boolean zmiana_kierunku = false;

				while ( !( (Temp.name).equals("#") ) ) {
					
					if ( (Temp.name).equals(W) ) break;
					if ( zmiana_kierunku ) kierunek = !kierunek;

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
//--------------------------------------------------------------------

				if ( Temp == Poc.first ) return null;
				else return Temp;

			}
			
		}

		public void CLEAR () {

		}

	//}

}



class Source {
	static Scanner scan =  new Scanner(System.in);
	public static void main(String [] args) {

		int Sets = scan.nextInt();

		while (Sets > 0) {

			int Commands = scan.nextInt();

			Zajezdnia Zaj = new Zajezdnia();

			while (Commands > 0) {

				String Polecenie = scan.next(); 

				String parametr1, parametr2;
				
				switch( Polecenie ) {

					case "New":

					parametr1 = scan.next();
					parametr2 = scan.next();

					Zaj.New(parametr1, parametr2);

					break;
					
					case "InsertFirst":

					parametr1 = scan.next();
					parametr2 = scan.next();

					Zaj.InsertFirst(parametr1, parametr2);

					break;

					case "InsertLast":

					parametr1 = scan.next();
					parametr2 = scan.next();

					Zaj.InsertLast(parametr1, parametr2);

					break;

					case "Display":

					parametr1 = scan.next();
					
					Zaj.Display(parametr1);

					break;
					
					case "Trains":

					Zaj.Trains();

					break;
					
					case "Reverse":

					parametr1 = scan.next();

					Zaj.Reverse(parametr1);

					break;
					
					case "Union":

					parametr1 = scan.next();
					parametr2 = scan.next();

					Zaj.Union(parametr1, parametr2);

					break;
					
					case "DelFirst":

					parametr1 = scan.next();
					parametr2 = scan.next();

					Zaj.DelFirst(parametr1, parametr2);

					break;
					
					case "DelLast":

					parametr1 = scan.next();
					parametr2 = scan.next();

					Zaj.DelLast(parametr1, parametr2);

					break;
					

				}



				Commands--;
			}
			
			

			Sets--;
		}


	}
}

