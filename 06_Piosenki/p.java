// Magdalena Lipka gr. 7
import java.util.Scanner;

class Source {

  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int SetsQuantity = in.nextInt();
    for (int i = 0; i < SetsQuantity; i++) {
      int SongsQuantity = in.nextInt();

      // int FirstDiscSongsQuantity = 0;

      // if (SongsQuantity % 2 == 0) {
      //   FirstDiscSongsQuantity = SongsQuantity / 2;
      // } else {
      //   FirstDiscSongsQuantity = SongsQuantity / 2 + 1;
      // }

      in.nextLine();
      String[] Songs = in.nextLine().split(" ");

      int SongsArrayLength = SongsQuantity;

      if (SongsQuantity % 2 == 1) {
        SongsArrayLength = SongsQuantity - 1;
        for (int j = SongsQuantity / 2; j < SongsQuantity - 1; j++) {
          String temp = Songs[j];
          Songs[j] = Songs[j + 1];
          Songs[j + 1] = temp;
        }
      }

      int StartingIndex = 1;

      while (StartingIndex != 0) {
        System.out.println("SI: " + StartingIndex);
        int CurrentIndex = StartingIndex;
        // int CurrentIndex = 1;
        String TravellingValue = Songs[CurrentIndex];

        do {
          System.out.println(
            "  CI: " + CurrentIndex + " TV: " + TravellingValue
          );
          in.nextLine();

          int NewIndex = CurrentIndex < SongsArrayLength / 2
            ? (CurrentIndex * 2) % SongsArrayLength
            : (CurrentIndex * 2 + 1) % SongsArrayLength;
          String Helper = Songs[NewIndex];
          Songs[NewIndex] = "-" + TravellingValue;

          CurrentIndex = NewIndex;
          TravellingValue = Helper;
        } while (CurrentIndex != StartingIndex);

        // wyznaczenie StartingIndex
        do {
          StartingIndex = (StartingIndex + 1) % SongsArrayLength;
        } while (StartingIndex != 0 && Songs[StartingIndex].indexOf("-") == 0);
      }

      for (int j = 0; j < SongsQuantity; j++) {
        System.out.println(Songs[j]);
      }
    }
  }
}
