// Magdalena Lipka gr.1 //
// submit numer 6291 //
import java.util.Scanner;

/*
WPROWADZENIE

Jak dla mnie kluczem do zrobienia tego zadania bylo uswiadomienie
sobie, ze nie istnieje cos takiego jak odwrocony pociag - istnieja
za to odwrocone wagony.
Zwykly wagon w zmiennej next przechowuje referencje do nastepnego
wagonu, a w prev referencje do poprzedniego.

W wagonie odwroconym tak sie nie dzieje. W wagonie odwroconym
w zmiennej prev przechowuje sie informacje o nastepnym wagonie,
a w next o poprzednim wagonie.
Takie rozroznienie sprawia, ze mozna latwo poruszac sie po
pociagu bez zapamietywania jego kierunku.

Rozumiem, ze w komentarzach mogloby pojawic sie duzo niescislosci
wynikajacych z uzywania nazw nastepny i poprzedni w sytuacji,
gdy te wyrazy nie zawsze faktycznie oznaczaja to co stanowi slownik,
dlatego przyjmuje terminologie:
-NASTePNY - przechowywany w zmiennej next
-POPRZEDNI - przechowywany w zmiennej prev
-PozNIEJSZY - taki, ktory w pociagu jest pozniej (to co klasycznie
rozumiemy pod slowem nastepny)
-WCZEsNIEJSZY - taki ktory jest wczesniej w pociagu (klasyczne
rozumienie slowa poprzedni)
Pierwszy i ostatni wagonik to wagoniki wskazywane jako first
i last przez glowe pociagu.

Oczywiscie dalej nie mozna bezposrednio okreslic kierunku
konkretnego wagonika. Mozna to jednak zrobic, wykorzystujac fakt,
iz glowa zawsze jest zwyklego kierunku i przeliczajac ilosc zmian
kierunku miedzy glowa a danym wagonikiem.
Kierunek wagonika A jest inny wzgledem kierunku wagonika B (pozniejszego)
w dwoch sytuacjach:
-kiedy A jest zwykly, a B.next wskazuje na A
-kiedy A jest odwrocony i B.prev wskazuje na A
Widac tutaj iz pojecie odworcenia wagonika rowniez jest zludne.
Bede przyjmowac w takim razie, ze wagonik zwykly to taki, do ktorego
idac od glowy napotyka sie na parzysta ilosc zmian kierunku,
a odwrocony to taki miedzy ktorym a glowa wystepuje nieparzysta ilosc
zmian kierunku poruszania sie.
W takim przypadku warunek bycia odwroconym spelnialby rowniez
jedyny wagonik w pociagu, jednak dla ulatwienia przyjelam,
iz taki wagonik uznajemy za zwykly (nieodwrocony).

Wszystkie sprawdzania kierunkow w kodzie beda wykorzystywac
takie rozumienie odwrocenia wagonow, jak rowniez bedzie na tym
bazowac poruszanie sie po pociagu w celu wypisania jego zawartosci
lub znalezienia konkretnego wagonu.

Dojscie do takiego rozwiazania zajelo mi ponad tydzien
i kilkadziesiat zapisanych kartek i wypisanych flamatrow,
jednak jest to najoptymalniejsze podejscie, nie potrzebujace
zadnej dodatkowej pamieci.
*/

class Zajezdnia {

  class Wagon {

    public String name;
    public Wagon next; // nastepny wagon w pociagu
    public Wagon prev; // poprzedni wagon w pociagu

    /*powyzsze nazwy maja odwrotne znaczenia
		w przypadku wagonow odwroconych, dlatego
		we wprowadzeniu wprowadzilam nowa terminologie*/

    Wagon(String nam) {
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

    Train(String nam) {
      name = nam;
      //juz w konstruktorze pociagu tworze glowe wagonikow
      Wagon Wag = new Wagon("#");
      first = Wag;
      last = Wag;
      Wag.next = Wag;
      Wag.prev = Wag;
    }
  }

  Train HEAD;/* Klasa Zajezdnia przechowuje informacje
	o pierwszym pociagu*/

  Zajezdnia() {
    HEAD = null;
  }

  public void New(String Pociag, String Wagonik) {
    if (findTrain(Pociag) != null) {
      //sprawdzam czy mozna wstwic pociag o podanej nazwie
      System.out.print("Train " + Pociag + " already exists\n");
      return;
    }
    Train Poc = new Train(Pociag); //utworzenie pociagu
    if (HEAD == null) HEAD = Poc;/*gdy nie ma jeszcze
		zadnego pociagu*/ else { //gdy juz istnieja jakies pociagi
      Poc.next = HEAD;
      HEAD = Poc;
    }

    Wagon Wag = new Wagon(Wagonik); //utworzenie pierwszego wagonika
    /*podpinamy odpowiednie referencje tak,
		aby zachowac ciaglosc pociagu*/
    /*zgodnie z zalozeniami, jedyny wagonik traktujemy jak
		normalny wagonik, chcociaz w tym przypadku nie ma to
		znaczenia, poniewaz pozniejszy = wczesniejszy = glowa*/
    Wag.prev = Poc.first;
    Wag.next = Poc.first;
    (Poc.first).next = Wag;
    (Poc.first).prev = Wag;
    Poc.last = Wag;
  }

  public void InsertFirst(String Pociag, String Wagonik) {
    Wagon Wag = new Wagon(Wagonik); //tworze nowy wagonik o nazwie przekazanej argumentem

    Train Poc = findTrain(Pociag); //znajduje referencje o pociagu, do ktorego bede wstawiac wagonik

    boolean kierunek_poczatku = true;

    if (
      ((Poc.first).next).next == Poc.first &&
      (Poc.first).next != (Poc.first).prev
    ) kierunek_poczatku = false; //stwierdzam czy pierwszy wagonik w pociagu (poza glowa) jest odwrocony

    /*poniewaz wstawiam tylko jeden wagonik to dla ulatwienia
		przyjmuje iz wstawiany wagonik bedzie mial taki sam
		kierunek jak wagonik aktualnie pierwszy*/

    if (kierunek_poczatku) {
      //wstawianie kiedy poczatek jest normalny
      /*podpinam referencje tak, aby nowy wagonik
			byl odpowiedniu ustawiony wzgledem glowy i aktualnie
			pierwszego oraz spelnial zalozenia podane we wprowadzeniu*/
      /* dlatego nastepny nowego wagonika to nastepny glowy
			oraz poprzedni nowego to poprzedni aktualnie pierwszego,
			nalezy jeszcze przepiac referencje wychodzace z glowy
			i aktualnie pierwszego tak aby wskazyaly na nowy */
      Wag.next = (Poc.first).next;
      Wag.prev = Poc.first;
      ((Poc.first).next).prev = Wag;
      (Poc.first).next = Wag;
    } else {
      //wstawianie kiedy poczatek jest owdrocony
      /* analogicznie jak dla zwyklego wagonika, jednak
			nalezy pamietac ze dla aktualnie pierwszego wagonika
			informacje o pozniejszym wagoniku przechowujemy
			w nastepnym a o wczesniejszym w poprzednim,
			przepiecie pomiedzy glowa a nowym nastepuje tak samo
			jak dla normalnego, poniewaz glowa ma dalej zwykly kierunek */
      Wag.next = Poc.first;
      Wag.prev = (Poc.first).next;
      ((Poc.first).next).next = Wag;
      (Poc.first).next = Wag;
    }
  }

  public void InsertLast(String Pociag, String Wagonik) {
    Wagon Wag = new Wagon(Wagonik); //tworze nowy wagonik o nazwie zadanej arguentem

    Train Poc = findTrain(Pociag); //znajduje referencje do pociagu do ktorego bede wstawiac wagonik

    boolean kierunek_konca = true;

    if (
      (Poc.last).prev == Poc.first && (Poc.first).next != Poc.last
    ) kierunek_konca = false; //sprawdzam czy ostatni wagonik w pociagu jest odwrocony

    if (kierunek_konca) {
      //jezeli ostatni wagonik pociagu jest normalny
      /*przepinamy wagony tak, zeby zachowujac swoje odwrocenia
			odpowiednio wskazywaly na kolejny wagonik
			sytuacja analogiczna jak w przypadku wstawiania
			na poczatek, jednak tutaj przepinamy referencje
			miedzy ostatnim wagonikiem i zmieniamy poprzedni glowy*/
      Wag.next = Poc.first;
      Wag.prev = Poc.last;
      (Poc.last).next = Wag;
      (Poc.first).prev = Wag;
      Poc.last = Wag;
    } else {
      //jezeli ostatni wagonik jest odwrocony
      //analogicznie jak wyzej
      Wag.next = Poc.last;
      Wag.prev = Poc.first;
      (Poc.last).prev = Wag;
      (Poc.first).prev = Wag;
      Poc.last = Wag;
    }
  }

  public void Display(String Pociag) {
    Train Poc = findTrain(Pociag);
    if (Poc == null) { //jezeli pociag nie istnieje
      System.out.print("Train " + Pociag + " does not exist\n");
      return;
    }

    Wagon Temp = Poc.first; //referencja do glowy pociagu

    if (Temp.next == Temp.prev) {
      //to znaczy ze jest jeden wagon tylko
      System.out.print(Poc.name + ": " + (Temp.next).name + "\n");
    } else {
      //wiecej niz jeden wagon
      System.out.print(Poc.name + ":");

      boolean on_next = false; //zmienna pomocnicza
      boolean kierunek = true; //glowa ma zwykly kierunek

      boolean zmiana_kierunku = false;

      while (!((Temp.name).equals("#")) || !on_next) {
        //petla wykonuje sie dopoki nie napotkamy glowy
        /*zmienna on_next sluzy do pominiecia sprawdzania warunku
				w przypadku gdy dopiero wchodzimy do petli*/

        //pomijamy drukowanie nazwy glowy
        if (on_next) System.out.print(" " + Temp.name);

        /*jesli w poprzendnim ruchu okazalo sie, ze nalezy
				zmienic kierunek, to zmieniamy kierunek poruszania sie*/
        if (zmiana_kierunku) kierunek = !kierunek;

        //teraz musimy sprawdzic czy jak pzejdziemy dalej to bedziemy na odwroconej czesci pociagu

        /*ponizsze warunki wynikaja z zalozen podancych
				we wprowadzeniu o roznicach w kierunkach dwoch sasiednich
				pociagow*/
        if (kierunek && (Temp.next).next == Temp) {
          zmiana_kierunku = true;
        } else if (!kierunek && (Temp.prev).prev == Temp) {
          zmiana_kierunku = true;
        } else zmiana_kierunku = false;

        /*przemieszczamy sie na wagonik pozniejszy
				zgodnie z kierunkiem aktualnego wagonika*/
        if (kierunek) Temp = Temp.next; else Temp = Temp.prev;

        on_next = true;
      }
      System.out.print("\n");
    }
  }

  public void Trains() {
    /*tutaj po prostu ide po kolejnych pociagach i wypisuje nazwy */
    Train Temp = HEAD;
    System.out.print("Trains:");
    while (Temp != null) {
      System.out.print(" " + Temp.name);
      Temp = Temp.next;
    }
    System.out.print("\n");
  }

  public void Reverse(String Pociag) {
    Train Temp = findTrain(Pociag); //znajduje referencje do pociagu

    if (Temp == null) { //jesli pociag o danej nazwie nie istnieje
      System.out.print("Train " + Pociag + " does not exist\n");
      return;
    }

    //jesli pociag istnieje
    /* mogloby wydawac sie, ze dzieje sie tutaj cos sprzeczego z zalozeniami - zamianiaja sie nastepny i poprzedni glowy, jednak to wrazenie. tak naprawde ozmienia sie tu kierunek wszystckich wagonow, odwrocone staja sie normalne, a normalne sa teraz odwrocone.
		po prostu dzieje sie to w trzech prostych operacjach, ktorych
		efektem jest manipulacja kierunkiem bez iterowania po wagonikach.
		wystarczy zamienic last Pociagu  wagonik ktory do tej pory byl
		pierwszy, i w glowie 'zamienic kierunek'. jednak jak pisalam
		we wprowadzaeniu - odwrocenie jest zludne i przyjmujemy definicje
		odwrocenia uzaleznionego od glowy, mozemy a nawet musimy dalej
		traktowac glowe jako nieodwrocona */
    (Temp.first).prev = (Temp.first).next;
    (Temp.first).next = Temp.last;
    Temp.last = (Temp.first).prev;
  }

  public void Union(String P1, String P2) {
    Train T1 = findTrain(P1);
    Train T2 = findTrain(P2);

    if (T1 == null) { //jesli pociag nie istnieje to przerywamy
      System.out.print("Train " + P1 + " does not exist\n");
      return;
    }
    if (T2 == null) { //jesli pociag nie istnieje to przerywamy
      System.out.print("Train " + P2 + " does not exist\n");
      return;
    }

    //inicuje kierunki pociagow
    boolean direction_of_t1_end = true;
    boolean direction_of_t2_begin = true;
    boolean direction_of_t2_end = true;

    //sprawdzam odpowiednie kierunki wagonow
    if (
      ((T1.first).prev).prev == T1.first && !((T1.first).next == T1.last)
    ) direction_of_t1_end = false;

    if ((T2.first).next != T2.last) {
      //czyli t2 ma wiecej niz jeden wagon
      if (((T2.first).prev).prev == T2.first) direction_of_t2_end = false;
      if (((T2.first).next).next == T2.first) direction_of_t2_begin = false;
    }

    /*spinam ze soba srodkowe wagoniki, czyli ostatni pierwszego
		pociagu i pierwszy drugiego pociagu (pierwszy nie liczac glowy),
		trzeba zrobic to tak zeby odpowiednie nexty i prevy wskazywaly
		na rzeczy ktore faktycznie powinny*/

    if (direction_of_t1_end) (T1.last).next = (T2.first).next; else (
      T1.last
    ).prev =
      (T2.first).next;

    if (direction_of_t2_begin) ((T2.first).next).prev = T1.last; else (
      (T2.first).next
    ).next =
      T1.last;

    //llacze glowe pierwszego z ostatnim wagonikiem drugiego
    if (direction_of_t2_end) (T2.last).next = T1.first; else (T2.last).prev =
      T1.first;

    (T1.first).prev = T2.last;

    //aktalizuje last pociagu
    T1.last = T2.last;

    //odczepiam glowe od drugiego pociagu
    T2.first = null;
    T2.last = null;
    T2.name = null;

    //usuwam drugi pociag z listy pociagow
    if (T2 == HEAD) HEAD = T2.next; else {
      Train Temp = HEAD;
      while (Temp.next != T2) Temp = Temp.next;
      Temp.next = T2.next;
      T2.next = null;
    }
  }

  public void DelFirst(String P1, String P2) {
    //sprawdzam czy dzialanie jest mozliwe
    Train Poc = findTrain(P1);
    if (Poc == null) { //jezeli pociag nie istnieje
      System.out.print("Train " + P1 + " does not exist\n");
      return;
    }

    if (findTrain(P2) != null) {/*jezeli pociag ktory mial
			byc utworzony juz istnieje*/
      System.out.print("Train " + P2 + " already exists\n");
      return;
    }

    //jezeli dzialanie jest mozliwe to wykonuje co nastepuje

    //tworze nowy pociag z wagonikiem i wstawiam go na poczatek listy
    New(P2, ((Poc.first).next).name);

    //teraz usuwam pierwszy wagonik z pociagu

    if ((Poc.first).next == Poc.last) {
      //jezeli byl to jedyny wagonik to usuwam caly pociag

      ((Poc.first).next).name = null;
      ((Poc.first).next).next = null;
      ((Poc.first).next).prev = null;
      (Poc.first).next = null;
      (Poc.first).prev = null;
      Poc.first = null;
      Poc.last = null;

      //usuniecie pociagu z listy
      if (Poc == HEAD) {
        HEAD = Poc.next;
      } else {
        Train Temp = HEAD;
        while (Temp.next != Poc) Temp = Temp.next;

        Temp.next = (Temp.next).next;
      }
    } else {
      //jesli nie byl to ostatni wagonik to tylko usuwam wagonik

      //musze sprawdzic kierunki wagonikow
      boolean kierunek_usuwanego = true;
      boolean kierunek_nast = true; //wagonik pozniejszy usuwanego

      Wagon Usuwany = (Poc.first).next;
      Wagon Nast;

      /* wiem, ze pociag ma conajmniej dwa wagoniki, dlatego
            moge sprawdzic tylko czy nastepny usuwanego jego wczesniejszym */
      if (Usuwany.next == Poc.first) kierunek_usuwanego = false;

      //zgodnie z kierunkiem usuwanego wyznaczam wagon pozniejszy
      if (kierunek_usuwanego) Nast = Usuwany.next; else Nast = Usuwany.prev;

      //sprawdzam kierunek wagonu Nast
      if (kierunek_usuwanego && Nast.next == Usuwany) kierunek_nast =
        !kierunek_usuwanego; else if (
        !kierunek_usuwanego && Nast.prev == Usuwany
      ) kierunek_nast = !kierunek_usuwanego; else kierunek_nast =
        kierunek_usuwanego;

      /*przepinam referencje glowy i wagonu Nast tak, zeby
            pominac Usuwany w liscie */
      /* dwie ponizsze linijki moznaby zastapic przez
            (Poc.first).next = Nast
            jednak sa one zapewna pozostaloscia z innego pomyslu
            realizacji tego zadania, niemniej realizuja dokladnie
            taka sama operacje */
      if (kierunek_usuwanego) (Poc.first).next = Usuwany.next; else (
        Poc.first
      ).next =
        Usuwany.prev;

      if (kierunek_nast) Nast.prev = Poc.first; else Nast.next = Poc.first;
    }
  }

  public void DelLast(String P1, String P2) {
    //sprawdzam czy dzialanie jest mozliwe
    Train Poc = findTrain(P1);
    if (Poc == null) {
      System.out.print("Train " + P1 + " does not exist\n");
      return;
    }
    if (findTrain(P2) != null) {
      System.out.print("Train " + P2 + " already exists\n");
      return;
    }

    //jezeli dzialanie jest mozliwe to wykonuje co nastepuje

    //tworze nowy pociag z wagonikiem i wstawiamy go na poczatek listy
    New(P2, (Poc.last).name);

    //usuwam pierwszy wagonik z pociagu
    if ((Poc.first).next == Poc.last) {
      //jezeli byl to jedyny wagonik to usuwam caly pociag
      ((Poc.first).next).name = null;
      ((Poc.first).next).next = null;
      ((Poc.first).next).prev = null;
      (Poc.first).next = null;
      (Poc.first).prev = null;
      Poc.first = null;
      Poc.last = null;

      //usuniecie pociagu z listy
      if (Poc == HEAD) {
        HEAD = Poc.next;
      } else {
        Train Temp = HEAD;
        while (Temp.next != Poc) Temp = Temp.next;
        Temp.next = (Temp.next).next;
      }
    } else {
      //jesli nie byl to ostatni wagonik to tylko usuwam wagonik

      Wagon Usuwany = Poc.last;
      Wagon Poprz;

      boolean kierunek_usuwanego = true;
      boolean kierunek_poprz;

      //prawdzam kierunek usuwanego
      if (Usuwany.prev == Poc.first) kierunek_usuwanego = false;

      //wyznaczam pozniejszy wagon zgodnie z kierunkiem usuwanego
      if (kierunek_usuwanego) Poprz = Usuwany.prev; else Poprz = Usuwany.next;

      //wyznaczam kierunek wagonu Poprz
      if (kierunek_usuwanego && Poprz.prev == Usuwany) kierunek_poprz =
        !kierunek_usuwanego; else if (
        !kierunek_usuwanego && Poprz.next == Usuwany
      ) kierunek_poprz = !kierunek_usuwanego; else kierunek_poprz =
        kierunek_usuwanego;

      /*przepisanm referencje glowy i Poprz tak aby ominac
            wagon usuwany*/
      /* tak jak w DelFirts, mozna by to zrealizowac jedna linijka,
            ale sa to ponizsze dwie linijki sa pozostaloscia po innym
            pomysle realizacji tego zadania, jednak dokonuja dokladnie
            takich samych operacji */
      if (kierunek_usuwanego) (Poc.first).prev = Usuwany.prev; else (
        Poc.first
      ).prev =
        Usuwany.next;

      if (kierunek_poprz) Poprz.next = Poc.first; else Poprz.prev = Poc.first;

      /*poniewaz usuwam ostatni to trzeba jeszcze
            zaktualizowac referencje Last w pociagu*/
      Poc.last = Poprz;
    }
  }

  public Train findTrain(String P) {
    //funkcja znajdujaca referencje do pociagu o zadanej nazwie
    Train Temp = HEAD;
    while (Temp != null) {
      if ((Temp.name).equals(P)) return Temp;
      Temp = Temp.next;
    }
    return null;
  }
  /*public Wagon findWag ( String P, String W ) {
        //funkcja znajdujaca referencje do wagonu o zadanej
        //nazwie w pociagu o zadanej nazwie
        //okazuje sie, ze nie uzywam jej nigdzie w kodzie
        //mimo, ze widocznie myslalam, ze sie przyda
        //bardzo mozliwe tez, ze nie dziala, dlatego ja wykomentowalam
		Train Poc = findTrain(P);
		if ( Poc == null ) return null;
		else {

			Wagon Temp = Poc.first;
			if ( Temp == null ) return null;
            else Temp = Temp.next;
            
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
			if ( Temp == Poc.first ) return null;
			else return Temp;
		}	
	}*/
}

class Source {

  static Scanner scan = new Scanner(System.in);

  public static void main(String[] args) {
    int Sets = scan.nextInt(); // ilosc zestawow polecen

    while (Sets > 0) {
      int Commands = scan.nextInt(); // ilosc polecen w danym zestawie

      Zajezdnia Zaj = new Zajezdnia(); // utworzenie pustej zajezdni

      while (Commands > 0) {
        String Polecenie = scan.next();

        String parametr1, parametr2;

        switch (Polecenie) { // switch obslugujacy wykonywanie polecen podanych z konsoli
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
