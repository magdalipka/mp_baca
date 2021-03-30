//Magdalena Lipka gr.7//
import java.util.Scanner;

class Source {

  static Scanner scan = new Scanner(System.in);

  // Liczenie pola prostokata - ilosc komoorek z podtabeli
  static int area(int top, int bottom, int left, int right) {
    return (bottom - top + 1) * (right - left + 1);
  }

  // Sprawzdenie czy podtabela 1 jest lepsza (mniejsza rozmiarem, albo
  // leksykograficznie) od podtabeli 2.
  static boolean isWinning(
    int top1,
    int bottom1,
    int left1,
    int right1,
    int top2,
    int bottom2,
    int left2,
    int right2
  ) {
    if (
      area(top1, bottom1, left1, right1) < area(top2, bottom2, left2, right2)
    ) {
      return true;
    } else if (
      area(top1, bottom1, left1, right1) > area(top2, bottom2, left2, right2)
    ) {
      return false;
    } else {
      if (top1 < top2) {
        return true;
      } else if (top1 > top2) {
        return false;
      } else {
        if (bottom1 < bottom2) {
          return true;
        } else if (bottom1 > bottom2) {
          return false;
        } else {
          if (left1 < left2) {
            return true;
          } else if (left1 > left2) {
            return false;
          } else {
            if (right1 <= right2) {
              return true;
            } else {
              return false;
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int Quantity = scan.nextInt(); // ilosc zestawow

    while (Quantity > 0) {
      int SetNumber; // numer zestawu
      int Columns, Lines; // wymiary pojedynczej tabeli
      SetNumber = scan.nextInt();
      scan.next();
      Lines = scan.nextInt();
      Columns = scan.nextInt();

      int[][] Array = new int[Lines][Columns];
      int max_sum = 0;
      int max_left = 0;
      int max_right = 0;
      int max_top = 0;
      int max_height = 0;

      boolean only_neg = true;

      // Wczytywanie
      for (int i = 0; i < Lines; i++) {
        for (int j = 0; j < Columns; j++) {
          int value = scan.nextInt();
          Array[i][j] = value > 0 ? 3 * value : 2 * value;
          if (Array[i][j] >= 0) only_neg = false;
        }
      }
      // Poczatkowo uznajemy za 'wygrywajaca` podtablice pojedyncza pierwsza komorke.
      max_sum = Array[0][0];

      if (only_neg) {
        System.out.println(
          SetNumber +
          ": n=" +
          Lines +
          " m=" +
          Columns +
          ", ms= 0, mstab is empty"
        );
      } else {
        // Dla podtabeli zaczynajacej sie na kazdej wysokosci.
        for (int temp_top = 0; temp_top < Lines; temp_top++) {
          // Dla podtabeli o kazdej wysokosci.
          for (
            int temp_height = 0;
            temp_height + temp_top < Lines;
            temp_height++
          ) {
            // Inicjalizacja listy sum kolumn oryginalnej podtabeli.
            int[] Sum = new int[Columns];
            for (int i = 0; i < Columns; i++) Sum[i] = 0;

            // sumowanie podtabeli zacznajacej sie w temp_left o szerokosci temp_width do
            // pojedynczej listy.
            for (int i = 0; i < Columns; i++) {
              for (int j = temp_top; j <= temp_top + temp_height; j++) {
                Sum[i] += Array[j][i];
              }
            }

            int temp_sum = 0;
            int temp_start = 0;

            // Algorytm Kadane (nieco zmodyfikowany).
            // Dla kazdej komorki wyliczamy maksymalna sme konczaca sie na niej.
            for (int i = 0; i < Columns; i++) {
              // Jesli poprzednia suma jest ujemna to zaczynamy sumowanie od poczatku.
              if (Sum[i] > temp_sum + Sum[i]) {
                temp_start = i;
                temp_sum = Sum[i];
              } else {
                temp_sum = temp_sum + Sum[i];
              }

              if (temp_start != i && Sum[temp_start] == 0) {
                temp_start++;
              }

              // Sprawdzenie czy aktualna (temp) pdtablica jest lepsza od tej wygrywajacej do
              // tej pory.
              if (
                max_sum < temp_sum ||
                (
                  max_sum == temp_sum &&
                  !isWinning(
                    max_top,
                    max_top + max_height,
                    max_left,
                    max_right,
                    temp_top,
                    temp_top + temp_height,
                    temp_start,
                    i
                  )
                )
              ) {
                max_sum = temp_sum;
                max_top = temp_top;
                max_left = temp_start;
                max_right = i;
                max_height = temp_height;
              }
            }
          }
        }
        // Obliczenie dolnego indeksu
        int max_bottom = max_top + max_height;

        System.out.println(
          SetNumber +
          ": n=" +
          Lines +
          " m=" +
          Columns +
          ", ms= " +
          max_sum +
          ", mstab= a[" +
          max_top +
          ".." +
          max_bottom +
          "][" +
          max_left +
          ".." +
          max_right +
          "]"
        );
      }

      Quantity--;
    }
  }
}
// 5
// 1 : 2 4
// 0 0 0 0
// 0 0 -1 0
// 2 : 2 4
//  1 1 -500 2
//  -1 -1 -1 -3
// 3 : 2 5
// 2 2 0 0 0
// 2 -20 0 0 0
// 4 : 3 3
// -123 4 7
// -123 -123 -123
// -123 -123 11
// 5 : 1 2
// 0 1
// 1: n=2 m=4, ms= 0, mstab= a[0..0][0..0]
// 2: n=2 m=4, ms= 6, mstab= a[0..0][3..3]
// 3: n=2 m=5, ms= 12, mstab= a[0..0][0..1]
// 4: n=3 m=3, ms= 33, mstab= a[2..2][2..2]
// 5: n=1 m=2, ms= 3, mstab= a[0..0][1..1]
