// Magdalena Lipka gr. 7
import java.util.Scanner;

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

  DoubleObject pop() {
    return array[top--];
  }

  String result() {
    // zwraca wynik przechodzac od najmniejszego indeksu stosu
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
      // obliczenie czy istnieje rozwiazanie
      // gdy w zbiorze znajdzie sie element
      // spod obecnego indeksu
      String IncludeResult = RecursivePack(
        Objects,
        MissingVolume - Objects[CurrentIndex],
        CurrentIndex + 1,
        CurrentElements + " " + Objects[CurrentIndex]
      );
      if (!IncludeResult.equals("")) {
        // jesli jest rozwiazanie z
        // elementem spod aktualnego indeksu
        return IncludeResult;
      } else {
        // jesli nie ma, to szukam rozwiazania
        // bez elementu spod aktualnego indeksu
        return RecursivePack(
          Objects,
          MissingVolume,
          CurrentIndex + 1,
          CurrentElements
        );
      }
    } else {
      // jesli element spod aktualnego indeksu
      // jest za duzy to szukam rozwiazania bez niego
      return RecursivePack(
        Objects,
        MissingVolume,
        CurrentIndex + 1,
        CurrentElements
      );
    }
  }

  static String IterativePack(int[] Elements, int Volume) {
    // left to indeksy right to wartosci
    // na stosie przechowuje elementy aktualnie wchodzace
    // w sklad sprawdzanego podzbioru
    DoubleStack Stack = new DoubleStack(Elements.length + 1);
    int MissingSum = Volume;
    int CurrentIndex = 0;

    boolean Found = false;

    while (true) {
      if (MissingSum == 0) {
        Found = true;
        break;
      }

      // pomocniczy warunek dla (*) (nizej) jesli sciaga sie element
      // ktory jest ostatni na stosie i jednoczesnie ostatni
      // w tablicy
      if (CurrentIndex == Elements.length && !Stack.isEmpty()) {
        DoubleObject Elem = Stack.pop();
        MissingSum += Elem.right;
        CurrentIndex = Elem.left + 1;
      }

      if (Elements[CurrentIndex] <= MissingSum) {
        // jesli element jest mniejszy bdz rowny
        // brakujaces sumie to klade go na stos
        Stack.push(CurrentIndex, Elements[CurrentIndex]);
        MissingSum -= Elements[CurrentIndex];
      }
      CurrentIndex++;

      if (MissingSum == 0) {
        Found = true;
        break;
      }

      // (*)
      if (
        (MissingSum < 0 || CurrentIndex == Elements.length) && !Stack.isEmpty()
      ) {
        // jesli suma jest nieprawidlowa lub napotkano koniec tablicy
        // usuwam ostatni element ze stosu i kontynuuje
        DoubleObject Elem = Stack.pop();
        MissingSum += Elem.right;
        CurrentIndex = Elem.left + 1;
      }

      // jesli napotkano koniec tablicy a na stosie nie ma nic to zakanczam
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
// 1
// 5
// 4
// 6 6 6 6
// BRAK
