// Magdalena Lipka
import java.util.Scanner;

class IntegerStorage {

  int Value;

  IntegerStorage() {
    this.Value = 0;
  }

  IntegerStorage(int Value) {
    this.Value = Value;
  }
}

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

  Person(String CreationString) {
    String[] Data = CreationString.split(" ");
    this.Priority = Integer.parseInt(Data[1]);
    this.Name = Data[2];
    this.Surname = Data[3];
  }

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

    if (CurrPerson.Priority > Left && CurrPerson.Priority < Right) {
      Curr = new Node(CurrPerson);

      Iterator.Value--;

      Curr.BiggerChild =
        CreatePostorderWorker(People, Iterator, CurrPerson.Priority, Right);
      Curr.SmallerChild =
        CreatePostorderWorker(People, Iterator, Left, CurrPerson.Priority);
    }

    return Curr;
  }

  void CreatePreorder(Person[] People) {
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

      while (Curr != null) {
        Prev = Curr;
        if (NewNode.Info.Priority < Prev.Info.Priority) {
          Curr = Prev.SmallerChild;
        } else {
          Curr = Prev.BiggerChild;
        }
      }

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

    while (Curr.BiggerChild != null) {
      Prev = Curr;
      Curr = Curr.BiggerChild;
    }
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

    while (Curr.SmallerChild != null) {
      Prev = Curr;
      Curr = Curr.SmallerChild;
    }
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
      return BiggerParent.Info.Priority > Priority ? BiggerParent : null;
    } else {
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
      return SmallerParent.Info.Priority < Priority ? SmallerParent : null;
    } else {
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
      Node NextPrev = Curr;
      Node NextCurr = Curr.BiggerChild;

      if (NextCurr.SmallerChild == null) {
        NextCurr.SmallerChild = Curr.SmallerChild;
      } else {
        while (NextCurr.SmallerChild != null) {
          NextPrev = NextCurr;
          NextCurr = NextCurr.SmallerChild;
        }

        NextPrev.SmallerChild = NextCurr.BiggerChild;

        NextCurr.SmallerChild = Curr.SmallerChild;
        NextCurr.BiggerChild = Curr.BiggerChild;
      }

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
    Stack HelperStack = new Stack(100);

    String Result = "";

    HelperStack.push(this.Root);

    while (!HelperStack.isEmpty()) {
      Node Curr = HelperStack.pop();
      if (Curr == null) continue;

      Result = ", " + Curr.Info.Print() + Result;

      HelperStack.push(Curr.SmallerChild);
      HelperStack.push(Curr.BiggerChild);
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
