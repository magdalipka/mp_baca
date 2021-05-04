// Magdalena Lipka gr. 7

/*

W rozwiazaniu korzystam z faktu ze pprawidlowa pozycje elementu mozna wyznaczyc
jednoznacznie znajac jego aktualna pozycje. Dla nastepujacej tablicy;
a b c d | e f g h   - `|` oddziela piosenki z pierwszej plyty od tych z drugiej
Piosenki po lewej powinny znalezc sie na pozycjach 0, 2, 4, 6 a piosenki
po prawej na pozycjach 1, 3, 5, 7
Prawidlowy index piosenki = 
- obecny * 2 - dla piosenek z pierwszej plyty
- (obecny - ostani indeks pierwszej plyty) * 2 + 1 - dla piosenek z drugiej plyty

Mozna wiec wziac element spod indeksu 1 (pomijam indeks zerowy, bo ta piosenka juz
jest w dobrym miejscu) i wstawic go na odpowiednia pozycje. Zapamietuje wartosc ktora
zostala w taki sposob nadpisana. Np. dla powyzszej tablicy, piosenka 'b' zostaje wstawiona
na index 2, a wartosc bedaca wczesniej na tym indeksie (`c`) zapamietana i teraz to ona
bedzie przenoszona.
Jednak mozna napotkac cykle wiec zaznaczam juz podstawione wartosci i jesli
petla przenoszaca elementy wrocila dopunkty w ktorym zaczela, to poczatkowy indeks startu
przenoszenia ustawiam na nastepny taki gdzie elementy nie sa jeszcze na swoim miejscu.
W kodzie sa to dwie petle while jedna w drugiej, jednak zlozonosc jest liniowa.
Gdyz wewnetrzna petla wykonuje sie maksymalne n razy i wtedy przechodzi po wszystkich
elementach wiec zewnetrzna wykona sie tylko raz.
Generalne zewnetrzna petla sprawia ze wewnetrzna wykonuje sie dokladnie raz na kazdym
elemencie tablicy.

Ogolny algorytm:

startingIndex := 1;
(*)while startingIndex != 0 :
  currentIndex := startingIndex
  travellingValue := Songs[currentIndex]
  // przeniesienie wartosci z cyklu zawierajacego currentIndex
  (**)do: (while currentIndex != startingIndex)
    wyznacz z wzoru nowy index dla travellingValue
    helper :=  Songs[nowy index]
    Songs[nowy index] := travellingValue
    oznacz Songs[nowy index] jako juz edytowane
    travellingValue := helper
    currentIndex := nowy index
  // wyznaczenie nowego startingIndex zeby edytowac tez inne cykle jesli wystepuja
  (***)do: (while StatringIndex != SongsQuantity AND Songs[StartingIndex] bylo juz edytowane)
    StartingIndex := StartingIndex + 1

Przy czym petla *** wykonuje sie doklanie n razy w sumiew ciagu wszystkich przebiegow
petli *. Petla ** wykonuje sie w sumie doklanie n razy w ciagu wszystkich przebiegow
petli *.

Przyklad:
SI - StartingIndex
CI - CurrentIndex
TV - Travelling Value
Minsami oznaczam wartosci juz edytowane
START
Obecna tablica: a b c d e f
SI: 1 , CI: 1 , TV: b 
Obecna tablica: a b -b d e f
SI: 1 , CI: 2 , TV: c
Obecna tablica: a b -b d -c f
SI: 1 , CI: 4 , TV: e
Obecna tablica: a b -b -e -c f
SI: 1 , CI: 3 , TV: d
Obecna tablica: a -d -b -e -c f
SI: 1 , CI: 1 , TV: b
SI = CI wiec koniec wewnetrznej petli
SI = 2 i Songs[2] bylo edytowane
SI = 3 i Songs[3] bylo edytowane
SI = 4 i Songs[4] bylo edytowane
SI = ostatni index w tablicy wiec koniec zewnetrznej petli

START
Obecna tablica: a b c d e f g h
SI: 1 , CI: 1 , TV: b 
Obecna tablica: a b -b d e f g h
SI: 1 , CI: 2 , TV: c 
Obecna tablica: a b -b d -c f g h
SI: 1 , CI: 4 , TV: e 
Obecna tablica: a -e -b d -c f g h
SI: 1 , CI: 1 , TV: b
SI = CI wiec koniec wewnetrznej petli
Obecna tablica: a -e -b d -c f g h
SI: 2  
Obecna tablica: a -e -b d -c f g h
SI: 3 , CI: 3 , TV: d
Obecna tablica: a -e -b d -c f -d h
SI: 3 , CI: 6 , TV: g
Obecna tablica: a -e -b d -c -g -d h
SI: 3 , CI: 5 , TV: f
Obecna tablica: a -e -b -f -c -g -d h
SI: 3 , CI: 3 , TV: f
SI = CI wiec koniec wewnetrznej petli
SI = 4 i Songs[4] bylo edytowane
SI = 5 i Songs[5] bylo edytowane
SI = 6 i Songs[6] bylo edytowane
Obecna tablica: a -e -b -f -c -g -d -h
SI: 7 , CI: 7 , TV: h
SI = CI wiec koniec wewnetznej petli
SI = ostatni index w tablicy wiec koniec zewnetrznej petli


*/

import java.util.Scanner;

class Source {

  static Scanner in = new Scanner(System.in);

  static int min(int i, int j) {
    return i < j ? i : j;
  }

  // Funkcja zwraacajaca najdluzszy wspolny prefix dla dwoch
  // napisow za pomoca binary searcha skrajnie prawego
  static String CommonPrefix(String A, String B) {
    if (A.length() == 0 || B.length() == 0 || A.charAt(0) != B.charAt(0)) {
      return "";
    }

    int LeftIndex = 0;
    int RightIndex = min(A.length(), B.length()) - 1;

    int MediumIndex = (LeftIndex + RightIndex) / 2;
    while (LeftIndex < RightIndex) {
      // System.out.println("       l:" + LeftIndex + " r: " + RightIndex);
      MediumIndex = (LeftIndex + RightIndex) / 2;
      if (
        A.substring(0, MediumIndex + 1).equals(B.substring(0, MediumIndex + 1))
      ) {
        LeftIndex = MediumIndex + 1;
      } else {
        RightIndex = MediumIndex;
      }
    }

    int LastMatchingIndex = RightIndex - 1;

    if (
      LastMatchingIndex + 1 < min(A.length(), B.length()) &&
      A.charAt(LastMatchingIndex + 1) == B.charAt(LastMatchingIndex + 1)
    ) {
      LastMatchingIndex++;
    }

    String CommonPrefix = A.substring(0, LastMatchingIndex + 1);

    return CommonPrefix;
  }

  public static void main(String[] args) {
    int SetsQuantity = in.nextInt();
    for (int i = 0; i < SetsQuantity; i++) {
      int SongsQuantity = in.nextInt();

      in.nextLine();
      String[] Songs = in.nextLine().split(" ");

      if (Songs.length == 1) {
        System.out.println(Songs[0] + " ");
        System.out.println(Songs[0]);
        continue;
      }

      int SongsArrayLength = SongsQuantity;

      // Jesli jest nieparzysta ilosc piosene to srodkowa
      // od razu zostaje przesunieta na wlasciwe miejsce
      // czyli na ostatnia pozycje w tablicy.
      // Od teraz traktuje tablice tak jakby miala parzysta
      // ilosc elementow.
      if (SongsQuantity % 2 == 1) {
        SongsArrayLength = SongsQuantity - 1;
        for (int j = SongsQuantity / 2; j < SongsQuantity - 1; j++) {
          String temp = Songs[j];
          Songs[j] = Songs[j + 1];
          Songs[j + 1] = temp;
        }
      }

      int StartingIndex = 1;

      String CommonPrefix = Songs[0];

      while (StartingIndex != 0) {
        int CurrentIndex = StartingIndex;
        String TravellingValue = Songs[CurrentIndex];

        do {
          CommonPrefix = CommonPrefix(CommonPrefix, TravellingValue);

          // Elementy w lewej polowce tablicy maja swoje wlasciwe
          // miejsce na indeksie = obecna pozycja * 2.
          // Elementy z prawej pozycji na indeksie =
          // odleglosc od polowy * 2 + 1 =
          // (obecna pozycja * 2 + 1) mod dlugosc tablicy
          int NewIndex = CurrentIndex < SongsArrayLength / 2
            ? (CurrentIndex * 2) % SongsArrayLength
            : (CurrentIndex * 2 + 1) % SongsArrayLength;
          String Helper = Songs[NewIndex];
          // minusami oznaczam elementy ktore juz zostaly
          // wstawione na dobra pozycje
          Songs[NewIndex] = "-" + TravellingValue;

          CurrentIndex = NewIndex;
          TravellingValue = Helper;
        } while (CurrentIndex != StartingIndex);

        // wyznaczenie nowego StartingIndex
        do {
          StartingIndex = (StartingIndex + 1) % SongsArrayLength;
        } while (
          StartingIndex != SongsArrayLength - 1 &&
          Songs[StartingIndex].indexOf("-") == 0
        );
      }

      // wyczyszczenie minusow
      for (int j = 0; j < SongsQuantity; j++) {
        if (Songs[j].indexOf("-") == 0) {
          Songs[j] = Songs[j].substring(1);
        }
      }

      for (int j = 0; j < SongsQuantity; j++) {
        System.out.print(Songs[j] + " ");
      }
      System.out.print("\n");
      System.out.println(CommonPrefix);
    }
  }
}
// 3
// 7
// aaa aab aac aaa aa aa a
// 6
// ILoveMyFriends1 ILoveMyFriends2 ILoveMyFriends3 ILoveMyFriends4 ILoveMyFriends5 ILoveMyFriends
// 5
// Silent Silent Silent Silent Silent

// aaa aa aab aa aac a aaa
// a
// ILoveMyFriends1 ILoveMyFriends4 ILoveMyFriends2 ILoveMyFriends5 ILoveMyFriends3 ILoveMyFriends
// ILoveMyFriends
// Silent Silent Silent Silent Silent
// Silent
