// Magdalena Lipka
import java.util.Scanner;

class ContestantEntry {

  int Index;
  int ContestantId;
  int Value;

  ContestantEntry(int Index, int ContestantId, int Value) {
    this.Index = Index;
    this.ContestantId = ContestantId;
    this.Value = Value;
  }

  String Print() {
    return "[" + this.ContestantId + "][" + this.Index + "] = " + this.Value;
  }
}

class Contest {

  int[][] Data;
  int ContestantsAmount;
  int ContestantsLength;
  int[] ContestantsPoints;
  ContestantEntry[] Entries;

  Contest(int[][] Data, int ContestantsAmount, int ContestantsLength) {
    this.Data = Data;
    this.ContestantsAmount = ContestantsAmount;
    this.ContestantsLength = ContestantsLength;

    this.Entries = new ContestantEntry[ContestantsAmount];
    this.ContestantsPoints = new int[ContestantsAmount];

    for (int i = 0; i < ContestantsAmount; i++) {
      this.Entries[i] = new ContestantEntry(0, i, Data[i][0]);
      this.ContestantsPoints[i] = 0;
    }

    this.BuildHeap();
  }

  void Run() {
    //   i to numer rundy
    for (int i = 0; i < this.ContestantsLength; i++) {
      //   this.PrintData();
      //   this.PrintHeap();
      //   System.out.println();
      if (
        (
          RightChildIndex(0) < this.ContestantsAmount &&
          this.Entries[0].Value == this.Entries[RightChildIndex(0)].Value
        ) ||
        (
          LeftChildIndex(0) < this.ContestantsAmount &&
          this.Entries[0].Value == this.Entries[LeftChildIndex(0)].Value
        )
      ) {
        if (i != this.ContestantsLength - 1) {
          this.HandleTie(0, this.Entries[0].Value);
          this.BuildHeap();
        }
      } else {
        //   nie ma remisu, zastepuje zwyciezce nowym
        int Winner = this.Entries[0].ContestantId;
        this.ContestantsPoints[Winner]++;

        if (i != this.ContestantsLength - 1) {
          this.Entries[0] = this.GetContestantNextEntry(this.Entries[0]);
          this.Heapify(0);
        }
      }
    }
  }

  void HandleTie(int Index, int Value) {
    if (Index >= this.ContestantsAmount || this.Entries[Index].Value != Value) {
      return;
    } else {
      this.Entries[Index] = this.GetContestantNextEntry(this.Entries[Index]);
      this.HandleTie(LeftChildIndex(Index), Value);
      this.HandleTie(RightChildIndex(Index), Value);
    }
  }

  private ContestantEntry GetContestantNextEntry(ContestantEntry Entry) {
    int ContestantId = Entry.ContestantId;
    int Index = Entry.Index;
    ContestantEntry NewEntry = new ContestantEntry(
      Index + 1,
      ContestantId,
      this.Data[ContestantId][Index + 1]
    );
    return NewEntry;
  }

  private int LeftChildIndex(int i) {
    return 2 * i + 1;
  }

  private int RightChildIndex(int i) {
    return 2 * i + 2;
  }

  private void BuildHeap() {
    int StartIndex = (this.ContestantsAmount / 2) - 1;

    for (int i = StartIndex; i >= 0; i--) {
      //   System.out.println("i: " + i);
      this.Heapify(i);
    }
  }

  private void Heapify(int Index) {
    int ToPopIndex = Index;
    int LeftChildIndex = this.LeftChildIndex(Index);
    int RightChildIndex = this.RightChildIndex(Index);

    // System.out.println();
    // System.out.print("---" + ToPopIndex + "---");
    // System.out.println();
    // System.out.print(LeftChildIndex + "---" + RightChildIndex);
    // System.out.println();

    if (
      LeftChildIndex < this.ContestantsAmount &&
      this.Entries[LeftChildIndex].Value > this.Entries[ToPopIndex].Value
    ) {
      ToPopIndex = LeftChildIndex;
    }

    if (
      RightChildIndex < this.ContestantsAmount &&
      this.Entries[RightChildIndex].Value > this.Entries[ToPopIndex].Value
    ) {
      ToPopIndex = RightChildIndex;
    }

    if (ToPopIndex != Index) {
      this.swap(ToPopIndex, Index);
      this.Heapify(ToPopIndex);
    }
  }

  private void swap(int i, int j) {
    ContestantEntry Temp = this.Entries[i];
    this.Entries[i] = this.Entries[j];
    this.Entries[j] = Temp;
  }

  void PrintData() {
    for (int i = 0; i < this.ContestantsAmount; i++) {
      System.out.println(i);
      for (int j = 0; j < this.ContestantsLength; j++) {
        System.out.print(this.Data[i][j]);
        System.out.print(" ");
      }
      System.out.println();
    }
  }

  void PrintHeap() {
    for (int i = 0; i < ContestantsAmount; i++) {
      System.out.println(this.Entries[i].Print());
    }
  }
}

class Source {

  public static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int TestCasesQuantity = in.nextInt();
    for (int i = 0; i < TestCasesQuantity; i++) {
      int ContestantsQuantity = in.nextInt();
      int ContestantsLength = in.nextInt();

      String[] ContestantsNames = new String[ContestantsQuantity];
      int[][] Data = new int[ContestantsQuantity][ContestantsLength];

      for (int j = 0; j < ContestantsQuantity; j++) {
        ContestantsNames[j] = in.next();
        for (int k = 0; k < ContestantsLength; k++) {
          Data[j][k] = in.nextInt();
        }
      }

      Contest Race = new Contest(Data, ContestantsQuantity, ContestantsLength);

      Race.Run();

      for (int j = 0; j < ContestantsQuantity; j++) {
        System.out.println(
          ContestantsNames[j] + " - " + Race.ContestantsPoints[j] + " pkt."
        );
      }
    }
  }
}
