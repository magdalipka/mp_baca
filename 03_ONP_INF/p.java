//Magdalena Lipka gr.1//
// submit numer 3268 //
import java.util.Scanner;

/*
Ogólną ideą programu jes czytanie danyh z wejścia i w odpowiedni sposób
układanie ich na stosach.
W przypadku konwersji ONP->INF na początek na stos kładzione są litery.
Przykładowo dla wyrażenia abc+* po odczytaniu pierwszych trzech znaków
stos będzie wyglądał następująco: a|b|c. Następnym znakiem jest opeator,
więc ze strosu zostaje ściągnięta odpowiednia ilość argumentów, które
podlegają operatorowi (powstaje wyrażenie b+c), które nasępnie zostaje
położone na stos (a|b+c). Postępujemy tak ze wszystkimi znakami z wejścia,
po czym ostateczne wyrażenie jest wyjściową formą (zakładając poprawność
wyrażenia wejciowego, ale od tego jest inna funkcja).
W przypadku konwersji INF->ONP 
*/

class Stack {

	//Klasa Stack służąca do obsługi stosu w konwersji inf->onp

	char[] Elems;
	int maxSize = 256;
	int top;

	public Stack() {
		Elems = new char[maxSize];
		top = 0; 
		Elems[0]='#'; //przechowuję # w pierwszym elemencie, ponieważ pierwotnie miałam zamiar inaczej zorganizować zdejmowanie ze stosu, później wyszło z tego kilka błędóœ, w efekcie których czasem używam funkcji isEMPTY, a czasem korzystam z tego właśnie hasha w pierwszej komórce
	}

	public char TOP() { 
		//zwraca element na górze stosu
		return Elems[top];
	}
	public char POP() {
		//usuwa i zwraca element z góry stosu
		if(Elems[top] == '#') return '#';
		char elem = Elems[top];
		Elems[top] = 0;
		top--;
		return elem;
	}
	public void PUSH(char elem) {
		//kładzie element na górę stosu
		top++;
		Elems[top] = elem;   
	}
	public boolean isEMPTY() {
		//sprawdza czy stos jest pusty
		if (top == 0) return true;
		return false;
	}

}

class DoubleStack {

	//Klasa służąca do obsługi stosu z dwoma polami w konwersji onp->inf

	String[] Elems; //przechowuje wyważenie
	char[] Priors; //przechowuje najbardziej znaczący operator z wyrażenia
	int maxSize = 256;
	int top;

	public DoubleStack() {
		Elems = new String[maxSize];
		Priors = new char[maxSize];
		top = 0;
		Elems[0]="#"; //hash analagocznie jak w klasie Stack
	}
	public String TOP() {
		//zwraca element z góry stosu
		return Elems[top];
	}
	public char TOPprior() {
		//zwraca operator (priorytet) z góry stosu
		return Priors[top];
	}
	public char POP() { 
		//zdejmuje elementy z góry stosu
		if(Elems[top]=="#") return '#';
		Elems[top] = null;
		Priors[top] = 0;
		top--;
		return 1;
	}
	public void PUSH(String elem, char prior) {
		//kładzie element na górę stosu
		top++;
		Elems[top] = elem;
		Priors[top] = prior;   
	}
	public boolean isEMPTY() {
		//sprawdza czy stos jest pusty
		if (top == 0) return true;
		return false;
	}

}

class Source {
	static Scanner scan =  new Scanner(System.in);
	public static void main(String [] args) {
	 
		int Quantity = Integer.parseInt(scan.nextLine()); //ilość wyrażeń
		//scan.nextLine();

		for(int q = 0; q<Quantity; q++) {

			String MayBeExpression = scan.nextLine(); //zczytanie wyrażenia

			String Expression = "";
				
			if(MayBeExpression.toCharArray()[0]=='I') { //jeżeli wyrażenie jest w postaci INF
				for( char read : MayBeExpression.toCharArray()) {
					//usuwanei zbeęnych znaków//
					if ( Acceptable( MayBeExpression, read, 1 ) ) {
						Expression += read;
					}
				}

				if ( Automat(Expression, 1) ) { //sprawdzam czy wyrażenie jest poprawne
						
					Stack theStack = new Stack(); //utworzenie pustego stosu na operatory

					System.out.print("ONP: ");

					for(char c : Expression.toCharArray()) { //iteruję po wszystkich znakach z wyrażenia

						if (c >= 'a' && c <= 'z') { //jeżeli znak jest literką to wypisuję ją na ekran
							System.out.print(c);
						}
						else if ( c == '(') { //jeżeli znak jest nawiasem otwierającym to należy go odłożyć na stos operatorów
							theStack.PUSH(c);
						} else if (c == '<' || c == '>' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%' ) { //operator lewostronnie łączny
							//zdejmuję ze stosu operatoryo priortecie niemniejszym niż priorytek aktualnego znaku
							while (Priority(theStack.TOP()) >= Priority(c)) {
								System.out.print(theStack.POP());
							}
							theStack.PUSH(c); //aktualny znak kładę na stos
						} else if ( c == '=' || c == '^' || c == '~' ) { //operator prawostronnie łączny
							//zdejmuję ze stosu operatory o priorytecie wiekszym niż priorytet aktualnego
							while (Priority(theStack.TOP()) > Priority(c)) {
								System.out.print(theStack.POP());
							}
							theStack.PUSH(c); //kładę na stos aktualny operator
						} else if ( c == ')' ) {
							//zdejmuję ze stosu wszystkie operatory aż do nawiasu otwierającego
							while( theStack.TOP() != '(' ) {
								System.out.print(theStack.POP());
							}
							theStack.POP(); //zdejmuję nawias otwierający, bez drukowania go do konsoli
						}
					}
					//zdejmowanie pozostałości ze stosu//
					while ( theStack.TOP() != '#' ) {
						System.out.print(theStack.POP());
					}
					System.out.print("\n");

				} else System.out.println("ONP: error");
				
			}
			else { //jeżeli wyrażenie jest w postaci ONP
				
				for( char read : MayBeExpression.toCharArray()) {
					//usuwane zbednych znakow//
					if ( Acceptable( MayBeExpression, read, 0 ) ) {
						Expression += read;
					}
				}

				if ( Automat(Expression, 0) ) { //sprawdzam czy wyrażenie jest poprawne

					DoubleStack theDoubleStack = new DoubleStack(); //utworzenie pustego stosu na "podwyrażenia"

					for ( char c : Expression.toCharArray() ) { //iteruję po każdym znaku wyrażenia
						if ( c >= 'a' && c <= 'z' ) { //jeżeli aktualny znak jest literką to odkładam go na stos
							String xxx = "";
							xxx += c;
							theDoubleStack.PUSH(xxx, c);
						}
						else if ( c == '='  || c == '<' || c == '>' || c == '-' || c == '+' || c == '%' || c == '/' || c == '*' || c == '^' ) { //jeżeli wyrażenie jest operaorem to trzeba ściągnąć dwa ostatnie wyrażenia ze stosu i je połączyć
							String Result = "";
								
							String Right = theDoubleStack.TOP(); //ściągam prawe wyrażenie ze stosu
							char right = theDoubleStack.TOPprior(); //zczytuję najistotniejszy operator z prawego wyrażenia
							theDoubleStack.POP(); //ściągam prawe wyrażenie ze stosu
								
							String Left = theDoubleStack.TOP(); // pobieram ze stosu prawe wyrażenie
							char left =theDoubleStack.TOPprior(); //pobieram ze stosu najistotniejszy operator podwyrażenia
							theDoubleStack.POP(); // usuwam podwyrażenie ze stosu

							//teraz następuje połączenie wyrażeń z użyciem aktualnego znaku-operatora z minimalną ilością nawiasów
							if (Priority(c) > Priority(left) || (Priority(c) == Priority(left) && (c == '=' || c == '^' || c == '~') )) { //nawiasu na lewym wyrażeniu potrzebujemy jeśli priorytet aktualnego operatora jest więkzy niż proprytet lewego wyrażenia lub priorytety są równe, ale aktualny operator i tak wymaga nawiasu z racji prawołączności
								Result += '(' + Left + ')';
							} else Result += Left;

							Result += c;

							if (Priority(c) > Priority(right) || (Priority(c) == Priority(right) && (c == '<' || c == '>' || c == '-' || c == '+' || c == '*' || c == '%' || c == '/') )) {//nawiasu na prawym wyrażeniu potrzebujemy jeśli priorytet aktualnego operatora jest więkzy niż proprytet prawego wyrażenia lub priorytety są równe, ale aktualny operator i tak wymaga nawiasu z racji lewołączności
								Result += '(' + Right + ')';
							} else Result += Right;

							theDoubleStack.PUSH(Result, c); // na stos kładziemy utworzone w taki sposów wyrażenie, z aktualnym wyrażeniem jako najistotoniejszym
						}
						else if ( c == '~' ) { // tyldę należy obsłużyć soobno ponieważ jest operatorem jednoargumentowym
							String Result = "";
							Result += '~';
							//poniżej sprawdzamy czy ściągane wyrażenie wymaga nawiasu
							//nawias jest wymagany gdy podwyrażenie składa się z więcej niż jednego znaku i najistotniejszy operator takiego podwyrażenia nie jest tyldą
							if(theDoubleStack.TOP().length() != 1 && theDoubleStack.TOPprior() != '~') Result += '(';
							Result += theDoubleStack.TOP();
							if(theDoubleStack.TOP().length() != 1  && theDoubleStack.TOPprior() != '~') Result += ')';
							theDoubleStack.POP();
							theDoubleStack.PUSH(Result, c);
						}
							
					}
						
					System.out.println("INF: " + theDoubleStack.TOP());

				} else System.out.println("INF: error");

			}

		}
	}

	static boolean Acceptable( String T, int symbol, int type) {
		//funkcja do sprawdzania czy znak 'symbol' jest dopuszczalny

		String AcceptedONP = "qwertyuiopasdfghjklzxcvbnm=<>+-*%^~/";
		String AcceptedINF = "qwertyuiopasdfghjklzxcvbnm=<>+-*%^~/()";

		if ( type == 1 ) {
			for(char c : AcceptedINF.toCharArray()) {
				if( c == symbol ) return true;
			}
		} else {
			for(char c : AcceptedONP.toCharArray()) {
				if( c == symbol ) return true;
			}
		}
		return false;
	}

	static boolean Automat(String Exp, int type) {
		//funkcja sprawdzajaca czy wyrazenie jest obliczalne
		if(type==1) { //sprawdzanie wyrażenia w postaci inf 
			int state = 0; //rozpoczytamy działanie "maszyny" w stanie zero
			int opened = 0; //ilość otworzynych nawiasów
			for(char c : Exp.toCharArray()) {
				switch(state) {
					case 0:
					//w stanie 0 poprawne oczekujemy jakiegoś wyrażenia rozpoczynającego, a więc nawiasów, tylky lub litery
						if ( c == '(' ) { opened++; state = 0; }
						else if( c == '~' ) state = 2;
						else if(c >= 'a' && c <= 'z') state = 1;
						else return false;
					break;
					case 1:
					//w stanie 1 oczekujemy wyrażenia zamykającego lub kontynuującego, a więc nawiasów zamyjających bądź też operatorów
						if ( c == ')' ) { opened--; state = 1; }
						else if ( c == '^' || c == '*' || c == '/' || c == '%' || c == '+' || c == '-' || c == '<' || c == '>' || c == '=' ) state = 0;
						else return false;
					break;
					case 2:
					//w stanie 2 (mimo, że wygląda pozornie tak samo jak stan 0) wyrażenia kontynuującego
						if ( c == '(' ) { opened++; state = 0; }
						else if ( c == '~' ) state = 2;
						else if( c >= 'a' && c <= 'z' ) state = 1;
						else return false;
					break;
				}
				if ( opened < 0 ) return false; //jeśli w jakimkolwiek momencie jest więcej zamykających niż otwierających nawiasów to wychodzimy
			}
			if ( state != 1 ) return false; 
			if ( opened != 0 ) return false; //jeśli nie zostały zamknięte wszystkie nawiasy to wychodzimy 
		}
		else { //sprawdzanie wyrażenia w postaci onp
			int mode = 0; //ilość wymaganych aktualnie literek
			for(char c : Exp.toCharArray()) {
				if( c >= 'a' && c <= 'z' ) mode++;
				else if( c == '^' || c == '*' || c == '/' || c == '%' || c == '+' || c == '-' || c == '<' || c == '>' || c == '=' ) mode--; //każdy operator zmniejsza ilość wymaganych do poprawności literek
				if ( mode <= 0 ) return false; //jeśli w jakimkolwiek momencie liczba wymaganych literek jest mniejsza od zera to wychodzimy 
			}
			if ( mode != 1 ) return false; //jeśli okazuje się że zostało podanych za dużo literek to również wychodzimy
		}
		return true;
	}

	static int Priority(char symbol) {
		//funkcja snadająca priorytety operatorom
		switch(symbol) {
			case '~':
				return 6;
			case '^':
				return 5;
			case '*':
				return 4;
			case '/':
				return 4;
			case '%':
				return 4;
			case '+':
				return 3;
			case '-':
				return 3;
			case '<':
				return 2;
			case '>':
				return 2;
			case '=':
				return 1;
		}
		if ( symbol >= 'a' && symbol <= 'z' ) return 6;
		else return -1;
	}
}


//TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_//
//TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_//

//Wejscie//
//20
//INF: a+b*(c/d)+g^r-p/(m+n)
//INF: b=y+h*c-y/g^k
//INF: a~+b*c
//INF: ()+b
//INF: (a +,b..*c)/(b/c+a*d)
//INF: a=b<c+y*t<(c+d)
//INF: l^(a*c+y*(m+o))
//INF: (a+b)*(c+d)/y(
//INF: a^b^m*(c+d)
//INF: ~(~(~((~a+~~b)/~c)*~d)^~e)
//ONP: abc-*ab^c/+
//ONP: a~~~~
//ONP: ab+~
//ONP: ab+~~
//ONP: ab+c/gb*cd/*-
//ONP: b~cd/~+g^h^
//ONP: abcdefg+*-/%
//ONP: a~b~~+c~/~d~*~e~^~
//ONP: xabcd^^=
//ONP: klmo**/tr%+


//Wyjscie//
//ONP: abcd/*+gr^+pmn+/-
//ONP: byhc*+ygk^/-=
//ONP: error
//ONP: error
//ONP: abc*+bc/ad*+/
//ONP: abcyt*+<cd+<=
//ONP: lac*ymo+*+^
//ONP: error
//ONP: abm^^cd+*
//ONP: a~b~~+c~/~d~*~e~^~
//INF: a*(b-c)+a^b/c
//INF: ~~~~a
//INF: ~(a+b)
//INF: ~~(a+b)
//INF: (a+b)/c-g*b*(c/d)
//INF: ((~b+~(c/d))^g)^h
//INF: error
//INF: ~(~(~((~a+~~b)/~c)*~d)^~e)
//INF: error
//INF: k/(l*(m*o))+t%r