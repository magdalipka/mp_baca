// Magdalena Lipka
import java.util.Scanner;

// Pomocnicza klasa do przechowywania iteratora podczas create
class IntegerStorage {

  int Value;

  IntegerStorage() {
    this.Value = 0;
  }

  IntegerStorage(int Value) {
    this.Value = Value;
  }
}

// Prosty stos
class Stack {

  int Top;
  Node[] Elems;

  Stack(int MaxHeight) {
    Elems = new Node[MaxHeight];
    Top = -1;
  }

  boolean isEmpty() {
    return Top == -1;
  }

  void push(Node NewNode) {
    Elems[++Top] = NewNode;
  }

  Node pop() {
    return Elems[Top--];
  }

  Node Top() {
    return Elems[Top];
  }
}

class Person {

  public String Name;
  public String Surname;
  public int Priority;

  // Utworzenie nowej osoby z napisu <priorytet> <imie> <nazwisko>
  Person(String CreationString) {
    String[] Data = CreationString.split(" ");
    this.Priority = Integer.parseInt(Data[1]);
    this.Name = Data[2];
    this.Surname = Data[3];
  }

  // Utworzenie nowej osoby z podanych danych
  Person(int Priority, String Name, String Surname) {
    this.Priority = Priority;
    this.Name = Name;
    this.Surname = Surname;
  }

  String Print() {
    return this.Priority + " - " + this.Name + " " + this.Surname;
  }
}

class Node {

  public Person Info;
  public Node BiggerChild;
  public Node SmallerChild;

  Node(Person NewPerson) {
    this.Info = NewPerson;
    this.BiggerChild = null;
    this.SmallerChild = null;
  }
}

class Tree {

  Node Root;

  Tree() {
    this.Root = null;
  }

  private int max(int a, int b) {
    return a > b ? a : b;
  }

  void CreatePostorder(Person[] People) {
    // Iterator jest przekazywany referencyjnie dzieki czemu zlozonoc hest liniowa gdyz funkcja createpostorderworker wywolywana jest rekurensyjnie dla kolejnych wartosci tablicy
    IntegerStorage Iterator = new IntegerStorage(People.length - 1);
    this.Root =
      CreatePostorderWorker(
        People,
        Iterator,
        Integer.MIN_VALUE,
        Integer.MAX_VALUE
      );
  }

  Node CreatePostorderWorker(
    Person[] People,
    IntegerStorage Iterator,
    int Left,
    int Right
  ) {
    if (Iterator.Value < 0) {
      return null;
    }

    Node Curr = null;

    Person CurrPerson = People[Iterator.Value];

    // Jesli osoba jest w zakresie to tworze dla niej nowy element i wywoluje rekurenyjnie dla jej prawego i lewego poddrzewam dzielac zakresy na jej wartoci

    if (CurrPerson.Priority > Left && CurrPerson.Priority < Right) {
      Curr = new Node(CurrPerson);

      Iterator.Value--;

      // Poniewax iterator jest referencja to druga rekurencja idzie od miejsca gdzie zakonczyla sie pierwsza
      Curr.BiggerChild =
        CreatePostorderWorker(People, Iterator, CurrPerson.Priority, Right);
      Curr.SmallerChild =
        CreatePostorderWorker(People, Iterator, Left, CurrPerson.Priority);
    }

    return Curr;
  }

  void CreatePreorder(Person[] People) {
    // Tak samo jak createpostorder z tym ze tutaj iterator sie zwieksza a tam sie zmniehszal
    IntegerStorage Iterator = new IntegerStorage();
    this.Root =
      CreatePreorderWorker(
        People,
        Iterator,
        Integer.MIN_VALUE,
        Integer.MAX_VALUE
      );
  }

  Node CreatePreorderWorker(
    Person[] People,
    IntegerStorage Iterator,
    int Left,
    int Right
  ) {
    if (Iterator.Value >= People.length) {
      return null;
    }

    Node Curr = null;

    Person CurrPerson = People[Iterator.Value];

    if (CurrPerson.Priority > Left && CurrPerson.Priority < Right) {
      Curr = new Node(CurrPerson);

      // tak samo jak createpostorderm z tym ze iterator jest zwiekszany
      Iterator.Value++;

      Curr.SmallerChild =
        CreatePreorderWorker(People, Iterator, Left, CurrPerson.Priority);
      Curr.BiggerChild =
        CreatePreorderWorker(People, Iterator, CurrPerson.Priority, Right);
    }

    return Curr;
  }

  void Enqueue(Person NewPerson) {
    Node NewNode = new Node(NewPerson);

    if (this.Root == null) {
      this.Root = NewNode;
    } else {
      Node Prev = null;
      Node Curr = this.Root;

      // Znajduje lisc do  ktorego zostanie dodana nowa osoba
      while (Curr != null) {
        Prev = Curr;
        if (NewNode.Info.Priority < Prev.Info.Priority) {
          Curr = Prev.SmallerChild;
        } else {
          Curr = Prev.BiggerChild;
        }
      }

      // Wstawienie na odpowiednie miejsce
      if (NewNode.Info.Priority < Prev.Info.Priority) {
        Prev.SmallerChild = NewNode;
      } else {
        Prev.BiggerChild = NewNode;
      }
    }
  }

  Person DequeueMax() {
    if (this.Root == null) return null;

    Node Prev = null;
    Node Curr = this.Root;

    // Przechodze maksymalnie w prawo
    while (Curr.BiggerChild != null) {
      Prev = Curr;
      Curr = Curr.BiggerChild;
    }
    // Przepinam referencje tak zeby pominac usuwwany element
    if (Curr == this.Root) {
      this.Root = Curr.SmallerChild;
    } else {
      Prev.BiggerChild = Curr.SmallerChild;
    }
    return Curr.Info;
  }

  Person DequeueMin() {
    if (this.Root == null) return null;

    Node Prev = null;
    Node Curr = this.Root;

    // Przechodze maksymalnie w lewo
    while (Curr.SmallerChild != null) {
      Prev = Curr;
      Curr = Curr.SmallerChild;
    }

    // Przepinam referencje tak zeby pominac usuwany element
    if (Curr == this.Root) {
      this.Root = Curr.BiggerChild;
    } else {
      Prev.SmallerChild = Curr.BiggerChild;
    }
    return Curr.Info;
  }

  Node Next(int Priority) {
    Node Curr = this.Root;
    Node BiggerParent = this.Root;
    // Szukam elementu po drodze zapamietujac najmniejsszego rodzica od niego wiekszego
    while (Curr != null && Curr.Info.Priority != Priority) {
      if (
        Curr.Info.Priority > Priority &&
        (
          BiggerParent.Info.Priority < Priority ||
          Curr.Info.Priority < BiggerParent.Info.Priority
        )
      ) {
        BiggerParent = Curr;
      }
      if (Curr.Info.Priority < Priority) {
        Curr = Curr.BiggerChild;
      } else {
        Curr = Curr.SmallerChild;
      }
    }

    if (Curr == null) return null;

    if (Curr.BiggerChild == null) {
      // jesli element nie ma prawego dziecka to zwracam najniejszego rodzica od niego wiekszego
      return BiggerParent.Info.Priority > Priority ? BiggerParent : null;
    } else {
      // Przechodze o jeden w prawo i maksymalnie w lewo
      Node FirstBigger = Curr.BiggerChild;
      while (FirstBigger.SmallerChild != null) {
        FirstBigger = FirstBigger.SmallerChild;
      }
      return FirstBigger;
    }
  }

  Node Prev(int Priority) {
    Node Curr = this.Root;
    Node SmallerParent = this.Root;
    // Szukam elementu po drodze zapamietujac najmniejszego rodzica oid niego wiekszego
    while (Curr != null && Curr.Info.Priority != Priority) {
      if (
        Curr.Info.Priority < Priority &&
        (
          SmallerParent.Info.Priority > Priority ||
          Curr.Info.Priority > SmallerParent.Info.Priority
        )
      ) {
        SmallerParent = Curr;
      }
      if (Curr.Info.Priority < Priority) {
        Curr = Curr.BiggerChild;
      } else {
        Curr = Curr.SmallerChild;
      }
    }

    if (Curr == null) return null;

    if (Curr.SmallerChild == null) {
      // Jesli element nie ma lewego dziecka to zwracam najwiekszego przodka od niego mniejszego
      return SmallerParent.Info.Priority < Priority ? SmallerParent : null;
    } else {
      // Przechodze o jeden w lewo i maksymalnie w prawo
      Node FirstSmaller = Curr.SmallerChild;
      while (FirstSmaller.BiggerChild != null) {
        FirstSmaller = FirstSmaller.BiggerChild;
      }
      return FirstSmaller;
    }
  }

  Person Delete(int Priority) {
    Node Prev = null;
    Node Curr = this.Root;

    boolean isBigger = false;

    // Szukam elementu i jego rodzica
    while (Curr != null && Curr.Info.Priority != Priority) {
      Prev = Curr;
      if (Curr.Info.Priority < Priority) {
        isBigger = true;
        Curr = Curr.BiggerChild;
      } else {
        isBigger = false;
        Curr = Curr.SmallerChild;
      }
    }

    if (Curr == null) return null;

    // Jesli element ma tylko jedno dziecko to wstawiam je na jego miejsce
    if (!(Curr.SmallerChild != null && Curr.BiggerChild != null)) {
      if (Prev == null) {
        this.Root =
          Curr.SmallerChild != null ? Curr.SmallerChild : Curr.BiggerChild;
      } else {
        if (isBigger) {
          Prev.BiggerChild =
            Curr.SmallerChild != null ? Curr.SmallerChild : Curr.BiggerChild;
        } else {
          Prev.SmallerChild =
            Curr.SmallerChild != null ? Curr.SmallerChild : Curr.BiggerChild;
        }
      }
    } else {
      // Element ma dwoje dzieci wiec szukam jego nastepnika i rodzica tego nastepnika
      Node NextPrev = Curr;
      Node NextCurr = Curr.BiggerChild;

      // Jesli prawe dziecko usuwanego elementu nie ma lewego dziecka, to tylko przepinam referenche na prawe
      if (NextCurr.SmallerChild == null) {
        NextCurr.SmallerChild = Curr.SmallerChild;
      } else {
        // Przechodze maksymalnie w lewo od prawego dziecka usuwanego elementu
        while (NextCurr.SmallerChild != null) {
          NextPrev = NextCurr;
          NextCurr = NextCurr.SmallerChild;
        }

        // Przepinam referencje tak zeby rodzic nastepnika juz na nie wskazywal, a anstepnik wskazywal na dzieci usuwanego elementu
        NextPrev.SmallerChild = NextCurr.BiggerChild;

        NextCurr.SmallerChild = Curr.SmallerChild;
        NextCurr.BiggerChild = Curr.BiggerChild;
      }

      // Przepinam referencje tak zeby rodzic usuwanego elementu wskazywal na jego nastepnik
      if (Prev == null) {
        this.Root = NextCurr;
      } else {
        if (isBigger) {
          Prev.BiggerChild = NextCurr;
        } else {
          Prev.SmallerChild = NextCurr;
        }
      }
    }

    return Curr.Info;
  }

  String Preorder() {
    String Result = "";
    Stack WorkingStack = new Stack(100);
    Node Curr = this.Root;

    // Za kazdym razem naspierw wyswietlam element i klade go na stos. przechodze do lewego poddrzewa. jesli nie element jest pusty to sciagam nowy ze stosu i przechodze do prawego podrzewa
    while (Curr != null || !WorkingStack.isEmpty()) {
      if (Curr != null) {
        Result += ", " + Curr.Info.Print();
        WorkingStack.push(Curr);
        Curr = Curr.SmallerChild;
      } else {
        Curr = WorkingStack.pop();
        Curr = Curr.BiggerChild;
      }
    }
    Result =
      "PREORDER:" + (Result.length() > 1 ? Result.substring(1) : " BRAK");
    return Result;
  }

  String Inorder() {
    Stack WorkingStack = new Stack(100);
    String Result = "";

    Node Curr = this.Root;

    // Podobnie jak preorder z tym ze dany element wyswietlam przed przejsciem do jego prawego poddrzewa
    while (Curr != null || !WorkingStack.isEmpty()) {
      if (Curr != null) {
        WorkingStack.push(Curr);
        Curr = Curr.SmallerChild;
      } else {
        Curr = WorkingStack.pop();
        Result += ", " + Curr.Info.Print();
        Curr = Curr.BiggerChild;
      }
    }

    Result = "INORDER:" + (Result.length() > 1 ? Result.substring(1) : " BRAK");
    return Result;
  }

  String Postorder() {
    Stack Stack = new Stack(100);

    String Result = "";

    Stack.push(this.Root);

    // Sciagam element ze stosu i dokladam go na koniec wyniku. wrzucam jego dzieci na stos.
    while (!Stack.isEmpty()) {
      Node Curr = Stack.pop();
      if (Curr == null) continue;

      Result = ", " + Curr.Info.Print() + Result;

      Stack.push(Curr.SmallerChild);
      Stack.push(Curr.BiggerChild);
    }

    Result =
      "POSTORDER:" + (Result.length() > 1 ? Result.substring(1) : " BRAK");
    return Result;
  }

  int Height() {
    return Height(this.Root);
  }

  int Height(Node Current) {
    if (Current == null) {
      return -1;
    } else {
      return (
        max(Height(Current.SmallerChild), Height(Current.BiggerChild)) + 1
      );
    }
  }
}

class Source {

  public static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int TestCasesQuantity = in.nextInt();
    in.nextLine();
    for (int i = 0; i < TestCasesQuantity; i++) {
      System.out.println("ZESTAW " + (i + 1));

      int CommandsQuantity = in.nextInt();
      in.nextLine();

      Tree Queue = new Tree();

      for (int j = 0; j < CommandsQuantity; j++) {
        try {
          String Command = in.next();

          switch (Command) {
            case "ENQUE":
              String PersonData = in.nextLine();
              Person NewPerson = new Person(PersonData);
              Queue.Enqueue(NewPerson);
              break;
            case "DEQUEMAX":
              in.nextLine();
              Person RemovedPersonMax = Queue.DequeueMax();
              System.out.println(
                "DEQUEMAX: " +
                (RemovedPersonMax != null ? RemovedPersonMax.Print() : "BRAK")
              );
              break;
            case "DEQUEMIN":
              in.nextLine();
              Person RemovedPersonMin = Queue.DequeueMin();
              System.out.println(
                "DEQUEMIN: " +
                (RemovedPersonMin != null ? RemovedPersonMin.Print() : "BRAK")
              );
              break;
            case "NEXT":
              int PriorityNext = in.nextInt();
              Node NextNode = Queue.Next(PriorityNext);
              Person NextPerson = (NextNode != null ? NextNode.Info : null);
              System.out.println(
                "NEXT " +
                PriorityNext +
                ": " +
                (NextPerson != null ? NextPerson.Print() : "BRAK")
              );
              in.nextLine();
              break;
            case "PREV":
              int PriorityPrev = in.nextInt();
              Node PrevNode = Queue.Prev(PriorityPrev);
              Person PrevPerson = (PrevNode != null ? PrevNode.Info : null);
              System.out.println(
                "PREV " +
                PriorityPrev +
                ": " +
                (PrevPerson != null ? PrevPerson.Print() : "BRAK")
              );
              in.nextLine();
              break;
            case "CREATE":
              String Type = in.next();

              int PeopleQuantity = in.nextInt();
              Person[] People = new Person[PeopleQuantity];
              for (int k = 0; k < PeopleQuantity; k++) {
                int NewPersonPriority = in.nextInt();
                String NewPersonName = in.next();
                String NewPersonSurname = in.next();
                Person _NewPerson = new Person(
                  NewPersonPriority,
                  NewPersonName,
                  NewPersonSurname
                );
                People[k] = _NewPerson;
              }

              Queue = new Tree();
              if (Type.equals("PREORDER")) {
                Queue.CreatePreorder(People);
              } else {
                Queue.CreatePostorder(People);
              }

              in.nextLine();
              break;
            case "DELETE":
              int PriorityToDelete = in.nextInt();
              Person DeletedPerson = Queue.Delete(PriorityToDelete);
              if (DeletedPerson == null) {
                System.out.println("DELETE " + PriorityToDelete + ": BRAK");
              }
              in.nextLine();
              break;
            case "PREORDER":
              System.out.println(Queue.Preorder());
              in.nextLine();
              break;
            case "INORDER":
              System.out.println(Queue.Inorder());
              in.nextLine();
              break;
            case "POSTORDER":
              System.out.println(Queue.Postorder());
              in.nextLine();
              break;
            case "HEIGHT":
              System.out.println("HEIGHT: " + Queue.Height());
              in.nextLine();
              break;
          }
        } catch (Exception e) {}
      }
    }
  }
}
// 6
// 8
// ENQUE 35 Andrzej Wolny
// INORDER
// ENQUE 30 Andrzej Inny
// INORDER
// ENQUE 40 Iwona Wolny
// INORDER
// ENQUE 45 Iwona Inny
// INORDER
// 14
// ENQUE 35 Andrzej Wolny
// ENQUE 30 Andrzej Inny
// ENQUE 40 Iwona Wolny
// ENQUE 45 Iwona Inny
// DEQUEMAX
// INORDER
// DEQUEMAX
// INORDER
// DEQUEMAX
// INORDER
// DEQUEMAX
// INORDER
// DEQUEMAX
// INORDER
// 14
// ENQUE 35 Andrzej Wolny
// ENQUE 30 Andrzej Inny
// ENQUE 40 Iwona Wolny
// ENQUE 45 Iwona Inny
// DEQUEMIN
// INORDER
// DEQUEMIN
// INORDER
// DEQUEMIN
// INORDER
// DEQUEMIN
// INORDER
// DEQUEMIN
// INORDER
// 8
// ENQUE 35 Andrzej Wolny
// ENQUE 30 Andrzej Inny
// ENQUE 40 Iwona Wolny
// ENQUE 45 Iwona Inny
// NEXT 30
// NEXT 35
// NEXT 40
// NEXT 45
// 8
// ENQUE 35 Andrzej Wolny
// ENQUE 30 Andrzej Inny
// ENQUE 40 Iwona Wolny
// ENQUE 45 Iwona Inny
// PREV 30
// PREV 35
// PREV 40
// PREV 45
// 2
// CREATE PREORDER 9 3 3 3 1 1 1 2 2 2 6 6 6 5 5 5 4 4 4 8 8 8 7 7 7 9 9 9
// INORDER
// ZESTAW 1
// INORDER: 35 - Andrzej Wolny
// INORDER: 30 - Andrzej Inny, 35 - Andrzej Wolny
// INORDER: 30 - Andrzej Inny, 35 - Andrzej Wolny, 40 - Iwona Wolny
// INORDER: 30 - Andrzej Inny, 35 - Andrzej Wolny, 40 - Iwona Wolny, 45 - Iwona Inny
// ZESTAW 2
// DEQUEMAX: 45 - Iwona Inny
// INORDER: 30 - Andrzej Inny, 35 - Andrzej Wolny, 40 - Iwona Wolny
// DEQUEMAX: 40 - Iwona Wolny
// INORDER: 30 - Andrzej Inny, 35 - Andrzej Wolny
// DEQUEMAX: 35 - Andrzej Wolny
// INORDER: 30 - Andrzej Inny
// DEQUEMAX: 30 - Andrzej Inny
// INORDER: BRAK
// DEQUEMAX: BRAK
// INORDER: BRAK
// ZESTAW 3
// DEQUEMIN: 30 - Andrzej Inny
// INORDER: 35 - Andrzej Wolny, 40 - Iwona Wolny, 45 - Iwona Inny
// DEQUEMIN: 35 - Andrzej Wolny
// INORDER: 40 - Iwona Wolny, 45 - Iwona Inny
// DEQUEMIN: 40 - Iwona Wolny
// INORDER: 45 - Iwona Inny
// DEQUEMIN: 45 - Iwona Inny
// INORDER: BRAK
// DEQUEMIN: BRAK
// INORDER: BRAK
// ZESTAW 4
// NEXT 30: 35 - Andrzej Wolny
// NEXT 35: 40 - Iwona Wolny
// NEXT 40: 45 - Iwona Inny
// NEXT 45: BRAK
// ZESTAW 5
// PREV 30: BRAK
// PREV 35: 30 - Andrzej Inny
// PREV 40: 35 - Andrzej Wolny
// PREV 45: 40 - Iwona Wolny
// ZESTAW 6
// INORDER: 1 - 1 1, 2 - 2 2, 3 - 3 3, 4 - 4 4, 5 - 5 5, 6 - 6 6, 7 - 7 7, 8 - 8 8, 9 - 9 9
