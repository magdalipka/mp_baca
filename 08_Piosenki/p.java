// Magdalena Lipka
import java.util.Scanner;
import javax.print.attribute.standard.MediaPrintableArea;

class Source {

  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    // Wczytywanie danych

    int Sets = in.nextInt();

    for (int i = 0; i < Sets; i++) {
      int Length = in.nextInt();

      int[] Songs = new int[Length];

      for (int j = 0; j < Length; j++) {
        Songs[j] = in.nextInt();
      }

      int Questions = in.nextInt();

      for (int j = 0; j < Questions; j++) {
        int Question = in.nextInt();

        // Wyznaczenie indeksu k-tego elementu.
        int result = kthSmallest(Songs, 0, Length - 1, Question);
        if (Question < 0 || Question >= Length) {
          System.out.println(Question + " brak");
        } else {
          System.out.println(Question + " " + Songs[result]);
        }
      }
    }
  }

  public static int kthSmallest(int[] arr, int left, int right, int k) {
    // Podzial na podzbiory piecio elkementowe i wyznaczenie ich median, krtore umieszczam na poczatku tablicy
    int medianIndex = left;
    for (int i = left; i < right; i = i + 5) {
      // get end index of 5 elem array (in case of last array shorter than 5)
      int endIndex = min(i + 4, right);
      sort(arr, i, endIndex);
      // swap median into beginning and increment next median index
      swap(arr, medianIndex, (i + endIndex) / 2);
      medianIndex++;
    }

    if (medianIndex > left + 1) {
      kthSmallest(arr, left, medianIndex - 1, (medianIndex - left) / 2);
    }
    // int medOfMed = (i == 1)
    //   ? 0
    //   : kthSmallest(arr, left, left + ilosc_median - 1, i / 2);
    // int medianValue = arr[medOfMed];

    // Podzial tablicy na wartoci mediany
    // Poniewaz podzial jest na medianie z median to zawsze lewa i prawa strona maja po conajmnieh 1/4 elementow wiec podzial jest optymalny co gwarantuje liniowa zlozonosc algorytmu.
    int position;
    position = partition(arr, left, right);

    if (position == k - 1) {
      return position;
    }

    // Zmiana granic szukania w zalewznosci od tego ile elemetow jest po leweh stronie podzialu
    if (position > k - 1) {
      return kthSmallest(arr, left, position - 1, k);d 
    }
    if (position < k - 1) {
      return kthSmallest(arr, position + 1, right, k);
    }

    return -1;
  }

  static int median(int left, int right) {
    return (left + right) / 2;
  }

  // Bubble sort
  static void sort(int[] array, int left, int right) {
    for (int i = left; i < right; i++) {
      for (int j = i + 1; j <= right; j++) {
        if (array[i] < array[j]) {
          swap(array, i, j);
        }
      }
    }
  }

  // Zamiana wartosci na dwoch indeksach
  static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  // Funkcja do podzialu tablicy na wartosci x

  static int partition(int arr[], int l, int r) {
    int i = l;
    swap(arr, l, r);
    int x = arr[l];
    for (int j = l; j <= r - 1; j++) {
      if (arr[j] <= x) {
        swap(arr, i, j);
        i++;
      }
    }
    swap(arr, i, r);
    return i;
  }

  static int min(int a, int b) {
    return a < b ? a : b;
  }
}
// 1
// 6
// 1 1 1 2 2 2
// 8
// 0 1 2 3 4 5 6 7
// 0 brak
// 1 1
// 2 1
// 3 1
// 4 2
// 5 2
// 6 2
// 7 brak
