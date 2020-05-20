// Magdalena Lipka gr.1 //
// submit numer 6291 //
import java.util.Scanner;

/*
WPROWADZENIE

Jak dla mnie kluczem do zrobienia tego zadania było uświadomienie
sobie, że nie istnieje coś takiego jak odwrócony pociąg - istnieją
za to odwrócone wagony.
Zwykły wagon w zmiennej next przechowuje referencję do następnego
wagonu, a w prev referencję do poprzedniego.

W wagonie odwróconym tak się nie dzieje. W wagonie odwróconym
w zmiennej prev przechowuje się informację o następnym wagonie,
a w next o poprzednim wagonie.
Takie rozróżnienie sprawia, żę można łatwo poruszać się po
pociągu bez zapamiętywania jego kierunku.

Rozumiem, że w komentarzach mogłoby pojawić się dużo nieścisłości
wynikających z używania nazw następny i poprzedni w sytuacji,
gdy te wyrazy nie zawsze faktycznie oznaczają to co stanowi słownik,
dlatego przyjmuję terminologię:
-NASTĘPNY - przechowywany w zmiennej next
-POPRZEDNI - przechowywany w zmiennej prev
-PÓŹNIEJSZY - taki, który w pociągu jest później (to co klasycznie
rozumiemy pod słowem następny)
-WCZEŚNIEJSZY - taki który jest wcześniej w pociągu (klasyczne
rozumienie słowa poprzedni)
Pierwszy i ostatni wagonik to wagoniki wskazywane jako first
i last przez głowę pociągu.

Oczywiście dalej nie można bezpośrednio określić kierunku
konkretnego wagonika. Można to jednak zrobić, wykorzystując fakt,
iż głowa zawsze jest zwykłego kierunku i przeliczając ilość zmian
kierunku między głową a danym wagonikiem.
Kierunek wagonika A jest inny względem kierunku wagonika B (póżniejszego)
w dwóch sytuacjach:
-kiedy A jest zwykły, a B.next wskazuje na A
-kiedy A jest odwrócony i B.prev wskazuje na A
Widać tutaj iż pojęcie odwórcenia wagonika również jest złudne.
Będę przyjmować w takim razie, że wagonik zwykły to taki, do którego
idąć od głowy napotyka się na parzystą ilość zmian kierunku,
a odwrócony to taki między którym a głową występuje nieparzysta ilość
zmian kierunku poruszania się.
W takim przypadku warunek bycia odwróconym spełniałby również
jedyny wagonik w pociągu, jednak dla ułatwienia przyjęłam,
iż taki wagonik uznajemy za zwykły (nieodwrócony).

Wszystkie sprawdzania kierunków w kodzie będą wykorzystywać
takie rozumienie odwrócenia wagonów, jak również będzie na tym
bazować poruszanie się po pociągu w celu wypisania jego zawartości
lub znalezienia konkretnego wagonu.

Dojście do takiego rozwiązania zajęło mi ponad tydzień
i kilkadziesiąt zapisanych kartek i wypisanych flamatrów,
jednak jest to najoptymalniejsze podejście, nie potrzebujące
żadnej dodatkowej pamięci.
*/

class Zajezdnia {

	class Wagon {

		public String name;
		public Wagon next; // następny wagon w pociągu 
		public Wagon prev; // poprzedni wagon w pociągu
		/*powyższe nazwy mają odwrotne znaczenia
		w przypadku wagonów odwróconych, dlatego
		we wprowadzeniu wprowadziłam nową terminologię*/

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
			//już w konstruktorze pociągu tworzę głowę wagoników
			Wagon Wag = new Wagon("#");
			first = Wag;
			last = Wag;
			Wag.next = Wag;
			Wag.prev = Wag;
		}
	}

	Train HEAD; /* Klasa Zajezdnia przechowuje informację
	o pierwszym pociągu*/

	Zajezdnia() {
		HEAD = null;
	}

	public void New (String Pociag, String Wagonik) {
		
	if ( findTrain(Pociag) != null ) {
		//sprawdzam czy można wstwić pociąg o podanej nazwie
		System.out.print("Train " + Pociag + " already exists\n");
		return;
	}
		Train Poc = new Train(Pociag); //utworzenie pociągu
		if ( HEAD == null ) HEAD = Poc; /*gdy nie ma jeszcze
		żadnego pociągu*/
		else {//gdy już istnieją jakieś pociągi
			Poc.next = HEAD;
			HEAD = Poc;
		}

		Wagon Wag = new Wagon(Wagonik);//utworzenie pierwszego wagonika
		/*podpinamy odpowiednie referencje tak,
		aby zachować ciągłość pociągu*/
		/*zgodnie z założeniami, jedyny wagonik traktujemy jak
		normalny wagonik, chcociaż w tym przypadku nie ma to
		znaczenia, ponieważ późniejszy = wcześniejszy = głowa*/
		Wag.prev = Poc.first;
		Wag.next = Poc.first;
		(Poc.first).next = Wag;
		(Poc.first).prev = Wag;
		Poc.last = Wag;
	}

	public void InsertFirst ( String Pociag, String Wagonik ) {
		
		Wagon Wag = new Wagon(Wagonik); //tworzę nowy wagonik o nazwie przekazanej argumentem

		Train Poc = findTrain(Pociag); //znajduję referencję o pociągu, do którego będę wstawiać wagonik

		boolean kierunek_poczatku = true; 

		if ( ((Poc.first).next).next == Poc.first && (Poc.first).next != (Poc.first).prev ) kierunek_poczatku = false; //stwierdzam czy pierwszy wagonik w pociągu (poza głową) jest odwrócony

		/*ponieważ wstawiam tylko jeden wagonik to dla ułatwienia
		przyjmuję iż wstawiany wagonik będzie miał taki sam
		kierunek jak wagonik aktualnie pierwszy*/

		if ( kierunek_poczatku ) {
			//wstawianie kiedy poczatek jest normalny
			/*podpinam referencje tak, aby nowy wagonik
			był odpowiedniu ustawiony względem głowy i aktualnie
			pierwszego oraz spełniał założenia podane we wprowadzeniu*/
			/* dlatego następny nowego wagonika to następny głowy
			oraz poprzedni nowego to poprzedni aktualnie pierwszego,
			należy jeszcze przepiąć referencje wychodzące z głowy
			i aktualnie pierwszego tak aby wskazyały na nowy */
			Wag.next = (Poc.first).next;
			Wag.prev = Poc.first;
			((Poc.first).next).prev = Wag;
			(Poc.first).next = Wag;
		}
		else {
			//wstawianie kiedy poczatek jest owdrocony
			/* analogicznie jak dla zwykłego wagonika, jednak
			należy pamiętać że dla aktualnie pierwszego wagonika
			informację o późniejszym wagoniku przechowujemy
			w następnym a o wcześniejszym w poprzednim,
			przepięcie pomiędzy głową a nowym następuje tak samo
			jak dla normalnego, ponieważ głowa ma dalej zwykły kierunek */
			Wag.next = Poc.first;
			Wag.prev = (Poc.first).next;
			((Poc.first).next).next = Wag; 
			(Poc.first).next = Wag;
		}
	}

	public void InsertLast ( String Pociag, String Wagonik ) {
		Wagon Wag = new Wagon(Wagonik); //tworzę nowy wagonik o nazwie zadanej arguentem

		Train Poc = findTrain(Pociag); //znajduję referencję do pociągu do którego będę wstawiać wagonik

		boolean kierunek_konca = true;

		if ( (Poc.last).prev == Poc.first && (Poc.first).next != Poc.last) kierunek_konca = false; //sprawdzam czy ostatni wagonik w pociągu jest odwrócony 

		if ( kierunek_konca ) {
			//jeżeli ostatni wagonik pociągu jest normalny
			/*przepinamy wagony tak, żeby zachowując swoje odwrócenia
			odpowiednio wskazywały na kolejny wagonik
			sytuacja analogiczna jak w przypadku wstawiania
			na początek, jednak tutaj przepinamy referencję
			między ostatnim wagonikiem i zmieniamy poprzedni głowy*/
			Wag.next = Poc.first;
			Wag.prev = Poc.last;
			(Poc.last).next = Wag;
			(Poc.first).prev = Wag;
			Poc.last = Wag;
		}
		else {
			//jeżeli ostatni wagonik jest odwrocony
			//analogicznie jak wyżej
			Wag.next = Poc.last;
			Wag.prev = Poc.first;
			(Poc.last).prev = Wag;
			(Poc.first).prev = Wag;
			Poc.last = Wag;
		}
	}

	public void Display ( String Pociag ) {
		Train Poc = findTrain(Pociag);
		if ( Poc == null ) { //jeżeli pociąg nie istnieje
			System.out.print("Train " + Pociag + " does not exist\n");
			return;
		}
			
		Wagon Temp = Poc.first; //referencja do głowy pociągu

		if ( Temp.next == Temp.prev ) {
			//to znaczy ze jest jeden wagon tylko
			System.out.print(Poc.name + ": " + (Temp.next).name + "\n");
		}
		else {
			//więcej niż jeden wagon
			System.out.print(Poc.name + ":");

			boolean on_next = false; //zmienna pomocnicza
			boolean kierunek = true; //głowa ma zwykły kierunek

			boolean zmiana_kierunku = false;

			while ( !((Temp.name).equals("#")) || !on_next ) { 
				//pętla wykonuje się dopóki nie napotkamy głowy
				/*zmienna on_next służy do pominięcia sprawdzania warunku
				w przypadku gdy dopiero wchodzimy do pętli*/

				//pomijamy drukowanie nazwy głowy
				if ( on_next ) System.out.print(" " + Temp.name);

				/*jeśli w poprzendnim ruchu okazało się, że należy
				zmienić kierunek, to zmieniamy kierunek poruszania się*/
				if ( zmiana_kierunku ) kierunek = !kierunek;

				//teraz musimy sprawdzic czy jak pzejdziemy dalej to bedziemy na odwroconej czesci pociagu

				/*poniższe warunki wynikają z założeń podancych
				we wprowadzeniu o różnicach w kierunkach dwóch sąsiednich
				pociągów*/
				if ( kierunek && (Temp.next).next == Temp ) {
						zmiana_kierunku = true;
				}
				else if ( !kierunek && (Temp.prev).prev == Temp ) {
					zmiana_kierunku = true;
				}	
				else zmiana_kierunku = false;

				/*przemieszczamy się na wagonik późniejszy
				zgodnie z kierunkiem aktualnego wagonika*/
				if ( kierunek ) Temp = Temp.next;
				else Temp = Temp.prev;

				on_next = true;
			}
			System.out.print("\n");
		}
	}

	public void Trains () {
		/*tutaj po prostu idę po kolejnych pociągach i wypisuję nazwy */
		Train Temp = HEAD;
		System.out.print("Trains:");
		while ( Temp != null ) {
			System.out.print(" " + Temp.name);
			Temp = Temp.next;
		}
		System.out.print("\n");
	}

	public void Reverse ( String Pociag ) {
		Train Temp = findTrain(Pociag); //znajduję referencję do pociągu

		if ( Temp == null ) { //jeśli pociąg o danej nazwie nie istnieje
			System.out.print("Train " + Pociag + " does not exist\n");
			return;
		}

		//jeśli pociag istnieje
		/* mogłoby wydawać się, że dzieje się tutaj coś sprzeczego z założeniami - zamianiają się następny i poprzedni głowy, jednak to wrażęnie. tak naprawdę ozmienia się tu kierunek wszystckich wagonów, odwrócone stają się normalne, a normalne są teraz odwrócone.
		po prostu dzieje się to w trzech prostych operacjach, których
		efektem jest manipulacja kierunkiem bez iterowania po wagonikach.
		wystarczy zamienić last Pociągu  wagonik który do tej pory był
		pierwszy, i w głowie 'zamienić kierunek'. jednak jak pisałam
		we wprowadzaeniu - odwrócenie jest złudne i przyjmujemy definicję
		odwrócenia uzależnionego od głowy, możemy a nawet musimy dalej
		traktować głowę jako nieodwróconą */
		(Temp.first).prev = (Temp.first).next;
		(Temp.first).next = Temp.last;
		Temp.last = (Temp.first).prev;
	
	}


	public void Union ( String P1, String P2 ) {

		Train T1 = findTrain(P1);
		Train T2 = findTrain(P2);

		if ( T1 == null ) { //jeśli pociąg nie istnieje to przerywamy
			System.out.print("Train " + P1 + " does not exist\n");
			return;
		}
		if ( T2 == null ) { //jeśli pociąg nie istnieje to przerywamy
			System.out.print("Train " + P2 + " does not exist\n");
			return;
		}
		
		//inicuję kierunki pociągów
		boolean direction_of_t1_end = true;
		boolean direction_of_t2_begin = true;
		boolean direction_of_t2_end = true;
		
		//sprawdzam odpowiednie kierunki wagonow
		if ( ((T1.first).prev).prev == T1.first && !( (T1.first).next == T1.last ) ) direction_of_t1_end = false;

		if ( (T2.first).next != T2.last ) {
			//czyli t2 ma wiecej niz jeden wagon
			if ( ((T2.first).prev).prev == T2.first ) direction_of_t2_end = false;
			if ( ((T2.first).next).next == T2.first ) direction_of_t2_begin = false;
		}

		/*spinam ze sobą środkowe wagoniki, czyli ostatni pierwszego
		pociągu i pierwszy drugiego pociągu (pierwszy nie licząc głowy),
		trzeba zrobić to tak żeby odpowiednie nexty i prevy wskazywały
		na rzeczy które faktycznie powinny*/ 

		if ( direction_of_t1_end ) (T1.last).next = (T2.first).next;
		else (T1.last).prev = (T2.first).next;

		if ( direction_of_t2_begin ) ((T2.first).next).prev = T1.last;
		else ((T2.first).next).next = T1.last;

		//lłączę głowę pierwszego z ostatnim wagonikiem drugiego
		if ( direction_of_t2_end ) (T2.last).next = T1.first;
		else (T2.last).prev = T1.first;

		(T1.first).prev = T2.last;
			 
		//aktalizuję last pociągu
		T1.last = T2.last;

		//odczepiam głowę od drugiego pociągu
		T2.first = null;
		T2.last = null;
		T2.name = null;

		//usuwam drugi pociag z listy pociagow
		if ( T2 == HEAD ) HEAD = T2.next;
		else {
			Train Temp = HEAD;
			while ( Temp.next != T2 ) Temp = Temp.next;
			Temp.next = T2.next;
			T2.next = null;
		}
	}

	public void DelFirst ( String P1, String P2 ) {

		//sprawdzam czy dzialanie jest mozliwe
		Train Poc = findTrain(P1);
		if ( Poc == null ) { //jeżeli pociąg nie istnieje
			System.out.print( "Train " + P1 + " does not exist\n");
			return;
		}
			
		if ( findTrain(P2) != null ) { /*jeżeli pociąg który miał
			być utworzony już istnieje*/
			System.out.print( "Train " + P2 + " already exists\n" );
			return;
		}

		//jezeli dzialanie jest możliwe to wykonuję co nastepuje

		//tworzę nowy pociag z wagonikiem i wstawiam go na poczatek listy
		New(P2, ((Poc.first).next).name);

		//teraz usuwam pierwszy wagonik z pociągu

		if ( (Poc.first).next == Poc.last ) {
			//jeżeli był to jedyny wagonik to usuwam cały pociąg

			((Poc.first).next).name = null;
			((Poc.first).next).next = null;
			((Poc.first).next).prev = null;
			(Poc.first).next = null;
			(Poc.first).prev = null;
			Poc.first = null;
			Poc.last = null;

			//usuniecie pociagu z listy
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
			//jeśli nie był to ostatni wagonik to tylko usuwam wagonik

			//muszę sprawdzic kierunki wagonikow
			boolean kierunek_usuwanego = true;
			boolean kierunek_nast = true; //wagonik późniejszy usuwanego

			Wagon Usuwany = (Poc.first).next;
			Wagon Nast;

            /* wiem, że pociąg ma conajmniej dwa wagoniki, dlatego
            mogę sprawdzić tylko czy następny usuwanego jego wcześniejszym */
			if ( Usuwany.next == Poc.first ) kierunek_usuwanego = false;

            //zgodnie z kierunkiem usuwanego wyznaczam wagon późniejszy
			if ( kierunek_usuwanego ) Nast = Usuwany.next;
			else Nast = Usuwany.prev; 

            //sprawdzam kierunek wagonu Nast
			if ( kierunek_usuwanego && Nast.next == Usuwany ) kierunek_nast = !kierunek_usuwanego;
			else if ( !kierunek_usuwanego && Nast.prev == Usuwany ) kierunek_nast = !kierunek_usuwanego;
            else kierunek_nast = kierunek_usuwanego;
            
            /*przepinam referencje głowy i wagonu Nast tak, żeby
            pominąć Usuwany w liście */
            /* dwie poniższe linijki możnaby zastąpić przez
            (Poc.first).next = Nast
            jednak są one zapewna pozostałością z innego pomysłu
            realizacji tego zadania, niemniej realizują dokładnie
            taką samą operację */
			if ( kierunek_usuwanego ) (Poc.first).next = Usuwany.next;
			else (Poc.first).next = Usuwany.prev;

			if ( kierunek_nast ) Nast.prev = Poc.first;
			else Nast.next = Poc.first;	
		}
	}

	public void DelLast ( String P1, String P2 ) {

		//sprawdzam czy dzialanie jest mozliwe
		Train Poc = findTrain(P1);
		if ( Poc == null ) {
			System.out.print( "Train " + P1 + " does not exist\n");
			return;
		}
		if ( findTrain(P2) != null ) {
			System.out.print( "Train " + P2 + " already exists\n" );
			return;
		}

		//jezeli dzialanie jest możliwe to wykonuję co nastepuje

		//tworzę nowy pociag z wagonikiem i wstawiamy go na początek listy
		New(P2, (Poc.last).name);

		//usuwam pierwszy wagonik z pociagu
		if ( (Poc.first).next == Poc.last ) {
			//jeżeli był to jedyny wagonik to usuwam cały pociag
			((Poc.first).next).name = null;
			((Poc.first).next).next = null;
			((Poc.first).next).prev = null;
			(Poc.first).next = null;
			(Poc.first).prev = null;
			Poc.first = null;
			Poc.last = null;

			//usunięcie pociągu z listy
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
			//jeśli nie byl to ostatni wagonik to tylko usuwam wagonik

			Wagon Usuwany = Poc.last;
			Wagon Poprz;

			boolean kierunek_usuwanego = true;
			boolean kierunek_poprz;

            //prawdzam kierunek usuwanego
			if ( Usuwany.prev == Poc.first ) kierunek_usuwanego = false;

            //wyznaczam późniejszy wagon zgodnie z kierunkiem usuwanego
			if ( kierunek_usuwanego ) Poprz = Usuwany.prev;
			else Poprz = Usuwany.next;

            //wyznaczam kierunek wagonu Poprz
			if ( kierunek_usuwanego && Poprz.prev == Usuwany ) kierunek_poprz = !kierunek_usuwanego;
			else if ( !kierunek_usuwanego && Poprz.next == Usuwany ) kierunek_poprz = !kierunek_usuwanego;
			else kierunek_poprz = kierunek_usuwanego; 

            /*przepisanm referencje głowy i Poprz tak aby ominąć
            wagon usuwany*/
            /* tak jak w DelFirts, można by to zrealizować jedną linijką,
            ale są to poniższe dwie linijki są pozostałością po innym
            pomyśle realizacji tego zadania, jednak dokonują dokładnie
            takich samych operacji */
			if ( kierunek_usuwanego ) (Poc.first).prev = Usuwany.prev;
			else (Poc.first).prev = Usuwany.next;

			if ( kierunek_poprz ) Poprz.next = Poc.first;
			else Poprz.prev = Poc.first;

            /*ponieważ usuwam ostatni to trzeba jeszcze
            zaktualizować referencję Last w pociągu*/
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
}



class Source {
	static Scanner scan =  new Scanner(System.in);
	public static void main(String [] args) {

		int Sets = scan.nextInt(); // ilość zestawów poleceń

		while (Sets > 0) {

			int Commands = scan.nextInt(); // ilość poleceń w danym zestawie

			Zajezdnia Zaj = new Zajezdnia(); // utworzenie pustej zajezdni

			while (Commands > 0) {

				String Polecenie = scan.next(); 

				String parametr1, parametr2;
				
				switch( Polecenie ) { // switch obsługujący wykonywanie poleceń podanych z konsoli

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
