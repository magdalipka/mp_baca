//Magdalena Lipka gr.7//
import java.util.Scanner;

/*
Ogolna idea programu jes czytanie danyh z wejscia i w odpowiedni sposob
ukladanie ich na stosach.
W przypadku konwersji ONP->INF na poczatek na stos kladzione sa litery.
Przykladowo dla wyrazenia abc+* po odczytaniu pierwszych trzech znakow
stos bedzie wygladal nastepujaco: a|b|c. Nastepnym znakiem jest opeator,
wiec ze strosu zostaje sciagnieta odpowiednia ilosc argumentow, ktore
podlegaja operatorowi (powstaje wyrazenie b+c), ktore nasepnie zostaje
polozone na stos (a|b+c). Postepujemy tak ze wszystkimi znakami z wejscia,
po czym ostateczne wyrazenie jest wyjsciowa forma (zakladajac poprawnosc
wyrazenia wejciowego, ale od tego jest inna funkcja).
W przypadku konwersji INF->ONP litery od razu sa rukowae na ekran,
a opertory odkladane stos. W momence pojawienia sie nawiasu zaczynam
zdejmowac odpowiednia ilosc operatorow ze stosu i drukowac je do konsoli.
*/

class Stack {

  //Klasa Stack sluzaca do obslugi stosu w konwersji inf->onp

  char[] Elems;
  int maxSize = 256;
  int top;

  public Stack() {
    Elems = new char[maxSize];
    top = 0;
    Elems[0] = '#'; //przechowuje # w pierwszym elemencie, poniewaz pierwotnie mialam zamiar inaczej zorganizowac zdejmowanie ze stosu, pozniej wyszlo z tego kilka bledow, w efekcie ktorych czasem uzywam funkcji isEMPTY, a czasem korzystam z tego wlasnie hasha w pierwszej komorce
  }

  public char TOP() {
    //zwraca element na gorze stosu
    return Elems[top];
  }

  public char POP() {
    //usuwa i zwraca element z gory stosu
    if (Elems[top] == '#') return '#';
    char elem = Elems[top];
    Elems[top] = 0;
    top--;
    return elem;
  }

  public void PUSH(char elem) {
    //kladzie element na gore stosu
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

  //Klasa sluzaca do obslugi stosu z dwoma polami w konwersji onp->inf

  String[] Elems; //przechowuje wywazenie
  char[] Priors; //przechowuje najbardziej znaczacy operator z wyrazenia
  int maxSize = 256;
  int top;

  public DoubleStack() {
    Elems = new String[maxSize];
    Priors = new char[maxSize];
    top = 0;
    Elems[0] = "#"; //hash analagocznie jak w klasie Stack
  }

  public String TOP() {
    //zwraca element z gory stosu
    return Elems[top];
  }

  public char TOPprior() {
    //zwraca operator (priorytet) z gory stosu
    return Priors[top];
  }

  public char POP() {
    //zdejmuje elementy z gory stosu
    if (Elems[top] == "#") return '#';
    Elems[top] = null;
    Priors[top] = 0;
    top--;
    return 1;
  }

  public void PUSH(String elem, char prior) {
    //kladzie element na gore stosu
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

  static Scanner scan = new Scanner(System.in);

  public static void main(String[] args) {
    int Quantity = Integer.parseInt(scan.nextLine()); //ilosc wyrazen
    //scan.nextLine();

    for (int q = 0; q < Quantity; q++) {
      String MayBeExpression = scan.nextLine(); //zczytanie wyrazenia

      String Expression = "";

      if (MayBeExpression.toCharArray()[0] == 'I') { //jezeli wyrazenie jest w postaci INF
        for (char read : MayBeExpression.toCharArray()) {
          //usuwanei zbeenych znakow//
          if (Acceptable(MayBeExpression, read, 1)) {
            Expression += read;
          }
        }

        if (Automat(Expression, 1)) { //sprawdzam czy wyrazenie jest poprawne
          Stack theStack = new Stack(); //utworzenie pustego stosu na operatory

          String result = "";

          for (char c : Expression.toCharArray()) { //iteruje po wszystkich znakach z wyrazenia
            if (c >= 'a' && c <= 'z') { //jezeli znak jest literka to wypisuje ja na ekran
              result += c;
            } else if (c == '(') { //jezeli znak jest nawiasem otwierajacym to nalezy go odlozyc na stos operatorow
              theStack.PUSH(c);
            } else if (
              c == '<' ||
              c == '>' ||
              c == '+' ||
              c == '-' ||
              c == '*' ||
              c == '/' ||
              c == '%' ||
              c == '?' ||
              c == '&' ||
              c == '|'
            ) { //operator lewostronnie laczny
              //zdejmuje ze stosu operatorow priorytecie niemniejszym niz priorytek aktualnego znaku
              while (Priority(theStack.TOP()) >= Priority(c)) {
                result += theStack.POP();
              }
              theStack.PUSH(c); //aktualny znak klade na stos
            } else if (c == '=' || c == '^' || c == '~' || c == '!') { //operator prawostronnie laczny
              //zdejmuje ze stosu operatory o priorytecie wiekszym niz priorytet aktualnego
              while (Priority(theStack.TOP()) > Priority(c)) {
                result += theStack.POP();
              }
              theStack.PUSH(c); //klade na stos aktualny operator
            } else if (c == ')') {
              //zdejmuje ze stosu wszystkie operatory az do nawiasu otwierajacego
              while (theStack.TOP() != '(') {
                result += theStack.POP();
              }
              theStack.POP(); //zdejmuje nawias otwierajacy, bez drukowania go do konsoli
            }
          }
          //zdejmowanie pozostalosci ze stosu//
          while (theStack.TOP() != '#') {
            result += theStack.POP();
          }
          System.out.println("ONP: " + String.join(" ", result.split("")));
        } else System.out.println("ONP: error");
      } else { //jezeli wyrazenie jest w postaci ONP
        for (char read : MayBeExpression.toCharArray()) {
          //usuwane zbednych znakow//
          if (Acceptable(MayBeExpression, read, 0)) {
            Expression += read;
          }
        }

        if (Automat(Expression, 0)) { //sprawdzam czy wyrazenie jest poprawne
          DoubleStack theDoubleStack = new DoubleStack(); //utworzenie pustego stosu na "podwyrazenia"

          for (char c : Expression.toCharArray()) { //iteruje po kazdym znaku wyrazenia
            if (c >= 'a' && c <= 'z') { //jezeli aktualny znak jest literka to odkladam go na stos
              String xxx = "";
              xxx += c;
              theDoubleStack.PUSH(xxx, c);
            } else if (
              c == '=' ||
              c == '<' ||
              c == '>' ||
              c == '-' ||
              c == '+' ||
              c == '%' ||
              c == '/' ||
              c == '*' ||
              c == '^' ||
              c == '?' ||
              c == '&' ||
              c == '|'
            ) { //jezeli wyrazenie jest operaorem to trzeba sciagnac dwa ostatnie wyrazenia ze stosu i je polaczyc
              String Result = "";

              String Right = theDoubleStack.TOP(); //sciagam prawe wyrazenie ze stosu
              char right = theDoubleStack.TOPprior(); //zczytuje najistotniejszy operator z prawego wyrazenia
              theDoubleStack.POP(); //sciagam prawe wyrazenie ze stosu

              String Left = theDoubleStack.TOP(); // pobieram ze stosu prawe wyrazenie
              char left = theDoubleStack.TOPprior(); //pobieram ze stosu najistotniejszy operator podwyrazenia
              theDoubleStack.POP(); // usuwam podwyrazenie ze stosu

              //teraz nastepuje polaczenie wyrazen z uzyciem aktualnego znaku-operatora z minimalna iloscia nawiasow
              if (
                Priority(c) > Priority(left) ||
                (
                  Priority(c) == Priority(left) &&
                  (c == '=' || c == '^' || c == '~' || c == '!')
                )
              ) { //nawiasu na lewym wyrazeniu potrzebujemy jesli priorytet aktualnego operatora jest wiekzy niz proprytet lewego wyrazenia lub priorytety sa rowne, ale aktualny operator i tak wymaga nawiasu z racji prawolacznosci
                Result += '(' + Left + ')';
              } else Result += Left;

              Result += c;

              if (
                Priority(c) > Priority(right) ||
                (
                  Priority(c) == Priority(right) &&
                  (
                    c == '<' ||
                    c == '>' ||
                    c == '-' ||
                    c == '+' ||
                    c == '*' ||
                    c == '%' ||
                    c == '/' ||
                    c == '?' ||
                    c == '&' ||
                    c == '|'
                  )
                )
              ) { //nawiasu na prawym wyrazeniu potrzebujemy jesli priorytet aktualnego operatora jest wiekzy niz proprytet prawego wyrazenia lub priorytety sa rowne, ale aktualny operator i tak wymaga nawiasu z racji lewolacznosci
                Result += '(' + Right + ')';
              } else Result += Right;

              theDoubleStack.PUSH(Result, c); // na stos kladziemy utworzone w taki sposow wyrazenie, z aktualnym wyrazeniem jako najistotoniejszym
            } else if (c == '~') { // tylde nalezy obsluzyc soobno poniewaz jest operatorem jednoargumentowym
              String Result = "";
              Result += '~';
              //ponizej sprawdzamy czy sciagane wyrazenie wymaga nawiasu
              //nawias jest wymagany gdy podwyrazenie sklada sie z wiecej niz jednego znaku i najistotniejszy operator takiego podwyrazenia nie jest tylda
              if (
                theDoubleStack.TOP().length() != 1 &&
                theDoubleStack.TOPprior() != '~' &&
                theDoubleStack.TOPprior() != '!'
              ) Result += '(';
              Result += theDoubleStack.TOP();
              if (
                theDoubleStack.TOP().length() != 1 &&
                theDoubleStack.TOPprior() != '~' &&
                theDoubleStack.TOPprior() != '!'
              ) Result += ')';
              theDoubleStack.POP();
              theDoubleStack.PUSH(Result, c);
            } else if (c == '!') { // tylde nalezy obsluzyc soobno poniewaz jest operatorem jednoargumentowym
              String Result = "";
              Result += '!';
              //ponizej sprawdzamy czy sciagane wyrazenie wymaga nawiasu
              //nawias jest wymagany gdy podwyrazenie sklada sie z wiecej niz jednego znaku i najistotniejszy operator takiego podwyrazenia nie jest tylda
              if (
                theDoubleStack.TOP().length() != 1 &&
                theDoubleStack.TOPprior() != '~' &&
                theDoubleStack.TOPprior() != '!'
              ) Result += '(';
              Result += theDoubleStack.TOP();
              if (
                theDoubleStack.TOP().length() != 1 &&
                theDoubleStack.TOPprior() != '~' &&
                theDoubleStack.TOPprior() != '!'
              ) Result += ')';
              theDoubleStack.POP();
              theDoubleStack.PUSH(Result, c);
            }
          }

          System.out.println(
            "INF: " + String.join(" ", theDoubleStack.TOP().split(""))
          );
        } else System.out.println("INF: error");
      }
    }
  }

  static boolean Acceptable(String T, int symbol, int type) {
    //funkcja do sprawdzania czy znak 'symbol' jest dopuszczalny

    String AcceptedONP = "qwertyuiopasdfghjklzxcvbnm=<>+-*%^~/!?&|";
    String AcceptedINF = "qwertyuiopasdfghjklzxcvbnm=<>+-*%^~/()!?&|";

    if (type == 1) {
      for (char c : AcceptedINF.toCharArray()) {
        if (c == symbol) return true;
      }
    } else {
      for (char c : AcceptedONP.toCharArray()) {
        if (c == symbol) return true;
      }
    }
    return false;
  }

  static boolean Automat(String Exp, int type) {
    //funkcja sprawdzajaca czy wyrazenie jest obliczalne
    if (type == 1) { //sprawdzanie wyrazenia w postaci inf
      int state = 0; //rozpoczytamy dzialanie "maszyny" w stanie zero
      int opened = 0; //ilosc otworzynych nawiasow
      for (char c : Exp.toCharArray()) {
        switch (state) {
          case 0:
            //w stanie 0 poprawne oczekujemy jakiegos wyrazenia rozpoczynajacego, a wiec nawiasow, tylky lub litery
            if (c == '(') {
              opened++;
              state = 0;
            } else if (c == '~' || c == '!') state = 2; else if (
              c >= 'a' && c <= 'z'
            ) state = 1; else return false;
            break;
          case 1:
            //w stanie 1 oczekujemy wyrazenia zamykajacego lub kontynuujacego, a wiec nawiasow zamyjajacych badz tez operatorow
            if (c == ')') {
              opened--;
              state = 1;
            } else if (
              c == '^' ||
              c == '*' ||
              c == '/' ||
              c == '%' ||
              c == '+' ||
              c == '-' ||
              c == '<' ||
              c == '>' ||
              c == '=' ||
              c == '?' ||
              c == '&' ||
              c == '|'
            ) state = 0; else return false;
            break;
          case 2:
            //w stanie 2 (mimo, ze wyglada pozornie tak samo jak stan 0) wyrazenia kontynuujacego
            if (c == '(') {
              opened++;
              state = 0;
            } else if (c == '~' || c == '!') state = 2; else if (
              c >= 'a' && c <= 'z'
            ) state = 1; else return false;
            break;
        }
        if (opened < 0) return false; //jesli w jakimkolwiek momencie jest wiecej zamykajacych niz otwierajacych nawiasow to wychodzimy
      }
      if (state != 1) return false;
      if (opened != 0) return false; //jesli nie zostaly zamkniete wszystkie nawiasy to wychodzimy
    } else { //sprawdzanie wyrazenia w postaci onp
      int mode = 0; //ilosc wymaganych aktualnie literek
      for (char c : Exp.toCharArray()) {
        if (c >= 'a' && c <= 'z') mode++; else if (
          c == '^' ||
          c == '*' ||
          c == '/' ||
          c == '%' ||
          c == '+' ||
          c == '-' ||
          c == '<' ||
          c == '>' ||
          c == '=' ||
          c == '?' ||
          c == '&' ||
          c == '|'
        ) mode--; //kazdy operator zmniejsza ilosc wymaganych do poprawnosci literek
        if (mode <= 0) return false; //jesli w jakimkolwiek momencie liczba wymaganych literek jest mniejsza od zera to wychodzimy
      }
      if (mode != 1) return false; //jesli okazuje sie ze zostalo podanych za duzo literek to rowniez wychodzimy
    }
    return true;
  }

  static int Priority(char symbol) {
    //funkcja snadajaca priorytety operatorom
    switch (symbol) {
      case '!':
        return 9;
      case '~':
        return 9;
      case '^':
        return 8;
      case '*':
        return 7;
      case '/':
        return 7;
      case '%':
        return 7;
      case '+':
        return 6;
      case '-':
        return 6;
      case '<':
        return 5;
      case '>':
        return 5;
      case '?':
        return 4;
      case '&':
        return 3;
      case '|':
        return 2;
      case '=':
        return 1;
    }
    if (symbol >= 'a' && symbol <= 'z') return 9; else return -1;
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
// ONP: a b c d / * + g r ^ + p m n + / -
// ONP: b y h c * + y g k ^ / - =
// ONP: error
// ONP: error
// ONP: a b c * + b c / a d * + /
// ONP: a b c y t * + < c d + < =
// ONP: l a c * y m o + * + ^
// ONP: error
// ONP: a b m ^ ^ c d + *
// ONP: a ~ b ~ ~ + c ~ / ~ d ~ * ~ e ~ ^ ~
// INF: a * ( b - c ) + a ^ b / c
// INF: ~ ~ ~ ~ a
// INF: ~ ( a + b )
// INF: ~ ~ ( a + b )
// INF: ( a + b ) / c - g * b * ( c / d )
// INF: ( ( ~ b + ~ ( c / d ) ) ^ g ) ^ h
// INF: error
// INF: ~ ( ~ ( ~ ( ( ~ a + ~ ~ b ) / ~ c ) * ~ d ) ^ ~ e )
// INF: error
// INF: k / ( l * ( m * o ) ) + t % r
