import java.util.Scanner;

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
    // System.out.println(CreationString);
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

    Node Prev = this.Root;
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
    return null;
  }

  Person Next(int Priority) {
    return null;
  }

  Person Prev(int Priority) {
    return null;
  }

  void Delete(int Priority) {}

  void Preorder() {}

  void Inorder() {
    Stack Stos = new Stack(100);
    String Result = "";

    Node Curr = this.Root;

    while (Curr != null || !Stos.isEmpty()) {
      if (Curr != null) {
        Stos.push(Curr);
        Curr = Curr.SmallerChild;
      } else {
        Curr = Stos.pop();

        Result += ", " + Curr.Info.Print();

        Curr = Curr.BiggerChild;
      }
    }

    Result = "INORDER:" + (Result.length() > 1 ? Result.substring(1) : " BRAK");
    System.out.println(Result);
  }

  void Postorder() {}

  int Height() {
    return 0;
  }
}

class Source {

  public static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int TestCasesQuantity = in.nextInt();
    for (int i = 0; i < TestCasesQuantity; i++) {
      System.out.println("Zestaw " + (i + 1));

      int CommandsQuantity = in.nextInt();
      in.nextLine();

      Tree Kolejka = new Tree();

      for (int j = 0; j < CommandsQuantity; j++) {
        String Command = in.next();
        // System.out.println(Command);

        switch (Command) {
          case "ENQUE":
            String PersonData = in.nextLine();
            Person NewPerson = new Person(PersonData);
            Kolejka.Enqueue(NewPerson);
            break;
          case "DEQUEMAX":
            in.nextLine();
            Person RemovedPersonMax = Kolejka.DequeueMax();
            System.out.println(
              "DEQUEMAX: " +
              (RemovedPersonMax != null ? RemovedPersonMax.Print() : " BRAK")
            );
            break;
          case "DEQUEMIN":
            in.nextLine();
            Person RemovedPersonMin = Kolejka.DequeueMin();
            System.out.println("DEQUEMIN: " + RemovedPersonMin.Print());
            break;
          case "NEXT":
            break;
          case "MIN":
            break;
          case "CREATE":
            String Type = in.next(" ");
            Kolejka = new Tree();
            break;
          case "DELETE":
            break;
          case "PREORDER":
            break;
          case "INORDER":
            Kolejka.Inorder();
            break;
          case "POSTORDER":
            break;
          case "HEIGHT":
            break;
        }
      }
    }
  }
}
