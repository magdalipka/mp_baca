// Magdalena Lipka
import java.util.Scanner;

class Source {

  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
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
        int result = kthSmallest(Songs, 0, Length - 1, Question);
        if (result == -1) {
          System.out.println(Question + " brak");
        } else {
          System.out.println(Question + " " + Songs[result]);
        }
      }
    }
  }

  public static int kthSmallest(int[] arr, int left, int right, int k) {
    if (k <= 0 || k > right - left + 1) {
      return -1;
    }

    int i = 0;
    int n = right - left + 1;
    int ilosc_median = 0;

    for (i = 0; i < n / 5; i++) {
      sort(arr, left + i * 5, 5);
      int median = median(left + i * 5, left + (i * 5) + 4);
      swap(arr, left + i, median);
      ilosc_median++;
    }

    if (n % 5 != 0) {
      sort(arr, left + i * 5, n % 5);
      int median = median(left + i * 5, (left + i * 5 + n % 5 - 1));
      // System.out.println(left + " " + right);
      swap(arr, left + i, median);
      i++;
      ilosc_median++;
    }
    ilosc_median--;
    sort(arr, left, ilosc_median);
    int median = median(left, left + ilosc_median);
    int medianValue = arr[median];

    int position;
    position = partition(arr, left, right, medianValue);

    if (position - left == k - 1) {
      return position;
    }
    if (position - left > k - 1) {
      return kthSmallest(arr, left, position - 1, k);
    }
    if (position - left < k - 1) {
      return kthSmallest(arr, position + 1, right, k - position + left - 1);
    }

    return -1;
  }

  static int median(int left, int right) {
    return (left + right) / 2;
  }

  static void sort(int[] array, int left, int right) {
    for (int i = left; i < right - 1; i++) {
      for (int j = i + 1; j < right; j++) {
        if (array[i] > array[j]) {
          swap(array, i, j);
        }
      }
    }
  }

  static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  static int partition(int arr[], int l, int r, int x) {
    int i;
    for (i = l; i < r; i++) if (arr[i] == x) break;
    swap(arr, i, r);
    i = l;
    for (int j = l; j <= r - 1; j++) {
      if (arr[j] <= x) {
        swap(arr, i, j);
        i++;
      }
    }
    swap(arr, i, r);
    return i;
  }
}
