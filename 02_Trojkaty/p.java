// Magdalena Lipka gr. 7//
import java.util.Scanner;

class Triangles {

  int Length;
  int[] Sides;
  int ResolutionsCount;

  Triangles(int L, int[] Values) {
    this.Length = L;
    this.Sides = new int[this.Length];
    this.Sides = Values;
    this.ResolutionsCount = 0;
  }

  // bubble sort do przesortowania tablicy rosnaco
  void sort() {
    for (int i = 0; i < Length - 1; i++) {
      for (int j = i + 1; j < Length; j++) {
        if (this.Sides[i] > this.Sides[j]) {
          int temp = this.Sides[i];
          this.Sides[i] = this.Sides[j];
          this.Sides[j] = temp;
        }
      }
    }
  }

  void printSides() {
    for (int i = 0; i < Length; i++) {
      System.out.print(this.Sides[i]);
    }
    System.out.println();
  }

  void printResolutionsCount() {
    System.out.println("Num_triangles= " + this.ResolutionsCount);
  }

  void CountTriangles() {
    /*
Dla kazdej pary (i, j) znajduje maksymalne k, takie ze mozna utworzyc
trojkat (i, j, k). Rozwiazanie jest zlozonosci O(n^2):
Zewnetrzna petla po 'i' jest zlozonosci O(n).
Zostaje
```
int k = i + 2;
for (int j = i + 1; j < this.Length; ++j) {
  while (
    k < this.Length && this.Sides[i] + this.Sides[j] > this.Sides[k]
  ) {
    ++k;
  }
  if (k > j) {
    this.ResolutionsCount += k - j - 1;
  }
}
```
Trzeba zauwazyc ze petla while przechodzi od 'i' do 'this.Length' przy czym
na kazdym indeksie tablicy zatrzymuje sie tylko raz na przestrzeni dzialania
wszystkich przebiegow petli for.
Poniewaz k jest zadeklarowane na zewnatrz petli for, to zawatosc petli while
wykona sie Length-i razy podczas wykonywania wszystkich petli for.
Przykladowo, jesli petla while przejdzie po wszystkich indeksach poczas pierwzzego
przebiegu petli for, to w nastepnych przebiegach petli for, petla while nie wykona sie
juz wgle. Moze zdarzyc sie tez ze dla kazdego przebiegu petli for, petla while
wykona po przykladwo 1-2 kroki, sumarycznie zostajac na O(n) krokach na przestrzeni
wszytskich przebiegow petli for.
Widac zatem ze ogolna zlozonosc kodu podanego kilka linijek wyzej, mimo dwoch 
zagniezdzonych w sobie petli, to O(n).
*/

    for (int i = 0; i < this.Length - 2; ++i) {
      int k = i + 2;
      for (int j = i + 1; j < this.Length; ++j) {
        while (
          k < this.Length && this.Sides[i] + this.Sides[j] > this.Sides[k]
        ) {
          ++k;
        }

        if (k > j) {
          this.ResolutionsCount += k - j - 1;
        }
      }
    }
  }
}

class Source {

  public static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int[] Sets = new int[in.nextInt()];

    for (int a : Sets) {
      int Length = in.nextInt();

      int[] Sides = new int[Length];

      for (int i = 0; i < Length; i++) {
        Sides[i] = in.nextInt();
      }

      Triangles Resolution = new Triangles(Length, Sides);

      Resolution.sort();
      // Resolution.printSides();
      Resolution.CountTriangles();
      Resolution.printResolutionsCount();
    }
  }
}
// 5
// 3
// 2 3 4
// 6
// 1 1 1 2 3 3
// 5
// 1 2 6 3 3
// 4
// 1 2 3 4
// 4
// 5 5 7 8
// Num_triangles= 1
// Num_triangles= 5
// Num_triangles= 2
// Num_triangles= 1
// Num_triangles= 4
