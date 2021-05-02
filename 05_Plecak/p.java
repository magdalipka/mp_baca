// Magdalena Lipka gr. 7
import java.util.Scanner;
import java.util.Stack;

class SingleStack {

  int[] array;
  int top;

  SingleStack(int size) {
    array = new int[size];
    top = -1;
  }

  boolean isEmpty() {
    return top == -1;
  }

  void push(int Element) {
    array[++top] = Element;
  }

  int pop() {
    return array[top--];
  }
}

class DoubleObject {

  int left;
  int right;

  DoubleObject(int Left, int Right) {
    this.left = Left;
    this.right = Right;
  }
}

class DoubleStack {

  DoubleObject[] array;
  int top;

  DoubleStack(int size) {
    array = new DoubleObject[size];
    top = -1;
  }

  boolean isEmpty() {
    return top == -1;
  }

  void push(int left, int right) {
    DoubleObject Obj = new DoubleObject(left, right);
    array[++top] = Obj;
  }

  void push(DoubleObject Element) {
    array[++top] = Element;
  }

  DoubleObject pop() {
    return array[top--];
  }

  String result() {
    if (!this.isEmpty()) {
      String Result = "";
      for (int i = 0; i <= this.top; i++) {
        DoubleObject Elem = array[i];
        Result += " " + Elem.right;
      }
      return Result;
    } else {
      return ("BRAK");
    }
  }

  void print() {
    System.out.println("Stack: ");
    if (!this.isEmpty()) {
      for (int i = 0; i <= this.top; i++) {
        DoubleObject Elem = array[i];
        System.out.println(Elem.left + " : " + Elem.right);
      }
    } else {
      System.out.println("empty");
    }
  }
}

class Source {

  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int SetsQuantity = in.nextInt();

    for (int i = 0; i < SetsQuantity; i++) {
      int PackVolume = in.nextInt();
      int ObjectsQuantity = in.nextInt();

      int[] ObjectsArray = new int[ObjectsQuantity];

      for (int j = 0; j < ObjectsQuantity; j++) {
        int Value = in.nextInt();
        ObjectsArray[j] = Value;
      }

      String IterResult = IterativePack(ObjectsArray, PackVolume);

      if (IterResult.equals("BRAK")) {
        System.out.println("BRAK");
        continue;
      }

      String RecResult = RecursivePack(ObjectsArray, PackVolume, 0, "");

      System.out.println("REC:  " + PackVolume + " =" + RecResult);
      System.out.println("ITER: " + PackVolume + " =" + IterResult);
    }
  }

  public static String RecursivePack(
    int[] Objects,
    int MissingVolume,
    int CurrentIndex,
    String CurrentElements
  ) {
    if (MissingVolume == 0) return CurrentElements;
    if (Objects.length == CurrentIndex) return "";
    if (Objects[CurrentIndex] <= MissingVolume) {
      String IncludeResult = RecursivePack(
        Objects,
        MissingVolume - Objects[CurrentIndex],
        CurrentIndex + 1,
        CurrentElements + " " + Objects[CurrentIndex]
      );
      if (!IncludeResult.equals("")) {
        return IncludeResult;
      } else {
        return RecursivePack(
          Objects,
          MissingVolume,
          CurrentIndex + 1,
          CurrentElements
        );
      }
    } else {
      return RecursivePack(
        Objects,
        MissingVolume,
        CurrentIndex + 1,
        CurrentElements
      );
    }
  }

  static String IterativePack(int[] Elements, int Volume) {
    // left is indexes, right is values
    DoubleStack Stack = new DoubleStack(Elements.length + 1);
    int MissingSum = Volume;
    int CurrentIndex = 0;

    boolean Found = false;

    while (true) {
      // Stack.print();
      // System.out.println("mis: " + MissingSum + ", ind: " + CurrentIndex);
      // System.out.println("Press enter to continue...");
      // in.nextLine();

      if (MissingSum == 0) {
        Found = true;
        break;
      }

      if (CurrentIndex == Elements.length && !Stack.isEmpty()) {
        DoubleObject Elem = Stack.pop();

        // System.out.println("    popped (1) " + Elem.right);

        MissingSum += Elem.right;
        CurrentIndex = Elem.left + 1;
      }

      if (Elements[CurrentIndex] <= MissingSum) {
        // System.out.println("    pushed " + Elements[CurrentIndex]);

        Stack.push(CurrentIndex, Elements[CurrentIndex]);
        MissingSum -= Elements[CurrentIndex];
      }
      CurrentIndex++;

      if (MissingSum == 0) {
        Found = true;
        break;
      }

      if (
        (MissingSum < 0 || CurrentIndex == Elements.length) && !Stack.isEmpty()
      ) {
        DoubleObject Elem = Stack.pop();

        // System.out.println("    popped (2) " + Elem.right);

        MissingSum += Elem.right;
        CurrentIndex = Elem.left + 1;
      }

      if (CurrentIndex == Elements.length && Stack.isEmpty()) {
        Found = false;
        break;
      }
    }

    if (Found) {
      return Stack.result();
    } else {
      return "BRAK";
    }
  }
}
