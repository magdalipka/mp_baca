// Magdalena Lipka gr. 7
import java.util.Scanner;

class Converter {

  String Expression;
  String Letters;
  String Operators;
  String LeftJoiningOperators;
  String RightJoiningOperators;
  String UnaryOperators;
  String DoubleOperators;

  Converter(String Expression) {
    this.Expression = Expression;
    this.Letters = "qwertyuiopasdfghjklzxcvbnm";
    this.LeftJoiningOperators = "*/%+-<>?&|";
    this.RightJoiningOperators = "!~^=";
    this.UnaryOperators = "!~";
    this.DoubleOperators = "*/%+-<>?&|=^";
    this.Operators = this.LeftJoiningOperators + this.RightJoiningOperators;
  }

  int Priority(String symbol) {
    //funkcja nadajaca priorytety operatorom
    switch (symbol) {
      case "!":
        return 9;
      case "~":
        return 9;
      case "^":
        return 8;
      case "*":
        return 7;
      case "/":
        return 7;
      case "%":
        return 7;
      case "+":
        return 6;
      case "-":
        return 6;
      case "<":
        return 5;
      case ">":
        return 5;
      case "?":
        return 4;
      case "&":
        return 3;
      case "|":
        return 2;
      case "=":
        return 1;
    }
    if (this.Letters.contains(symbol)) return 9; else return -1;
  }
}

class SingleStack {

  String[] Elems;
  int Top;

  SingleStack() {
    this.Elems = new String[256];
    this.Top = -1;
  }

  void PUSH(String value) {
    this.Elems[++this.Top] = value;
  }

  String TOP() {
    if (this.Top < 0) return null;
    return this.Elems[this.Top];
  }

  String POP() {
    if (this.Top == -1) return null;
    return this.Elems[this.Top--];
  }
}

class DoubleElem {

  String Value;
  String Priority;

  DoubleElem(String value, String priority) {
    this.Value = value;
    this.Priority = priority;
  }
}

class DoubleStack {

  DoubleElem[] Elems;
  int Top;

  DoubleStack() {
    this.Elems = new DoubleElem[256];
    this.Top = -1;
  }

  void PUSH(String value, String priority) {
    DoubleElem newElem = new DoubleElem(value, priority);
    this.Elems[++this.Top] = newElem;
  }

  DoubleElem TOP() {
    if (this.Top < 0) return null;
    return this.Elems[this.Top];
  }

  DoubleElem POP() {
    if (this.Top == -1) return null;
    return this.Elems[this.Top--];
  }
}

class INFtoONP extends Converter {

  INFtoONP(String Expression) {
    super(Expression);
    this.Expression = Expression;
    this.Operators = "()!~^*/%+-<>?&|=";
  }

  void ClearExpression() {
    String whiteExpression = "";
    for (char c : Expression.toCharArray()) {
      String znak = Character.toString(c);
      if (this.Letters.contains(znak) || this.Operators.contains(znak)) {
        whiteExpression += znak;
      }
    }
    this.Expression = whiteExpression;
  }

  Boolean ValidateExpression() {
    int state = 0;
    int openedMargins = 0;
    for (char c : this.Expression.toCharArray()) {
      String znak = Character.toString(c);

      if (openedMargins < 0) {
        return false;
      }

      switch (state) {
        case 0:
          if (znak.equals("(")) {
            openedMargins++;
            state = 0;
          } else if (this.UnaryOperators.contains(znak)) {
            state = 2;
          } else if (this.Letters.contains(znak)) {
            state = 1;
          } else {
            return false;
          }
          break;
        case 1:
          if (znak.equals("(")) {
            return false;
          } else if (znak.equals(")")) {
            openedMargins--;
            state = 1;
          } else if (this.Operators.contains(znak)) {
            state = 0;
          } else {
            return false;
          }
          break;
        case 2:
          if (znak.equals("(")) {
            openedMargins++;
            state = 0;
          } else if (this.UnaryOperators.contains(znak)) {
            state = 2;
          } else if (this.Letters.contains(znak)) {
            state = 1;
          } else {
            return false;
          }
          break;
      }
    }
    if (state != 1 || openedMargins != 0) {
      return false;
    } else {
      return true;
    }
  }

  String Convert() {
    String result = "";

    SingleStack stack = new SingleStack();

    for (char c : this.Expression.toCharArray()) {
      String znak = Character.toString(c);

      if (this.Letters.contains(znak)) {
        result += znak;
        continue;
      }

      if (znak.equals("(")) {
        stack.PUSH(znak);
        continue;
      }

      if (znak.equals(")")) {
        while (stack.TOP() != null && !stack.TOP().equals("(")) {
          result += stack.POP();
        }
        stack.POP();
        continue;
      }

      if (this.LeftJoiningOperators.contains(znak)) {
        while (
          stack.TOP() != null &&
          this.Priority(stack.TOP()) >= this.Priority(znak)
        ) {
          result += stack.POP();
        }
        stack.PUSH(znak);
        continue;
      }

      if (this.RightJoiningOperators.contains(znak)) {
        while (
          stack.TOP() != null &&
          this.Priority(stack.TOP()) > this.Priority(znak)
        ) {
          result += stack.POP();
        }
        stack.PUSH(znak);
        continue;
      }
    }

    while (stack.TOP() != null) {
      result += stack.POP();
    }

    return result;
  }
}

class ONPtoINF extends Converter {

  ONPtoINF(String Expression) {
    super(Expression);
  }

  void ClearExpression() {
    String whiteExpression = "";
    for (char c : Expression.toCharArray()) {
      String znak = Character.toString(c);
      if (this.Letters.contains(znak) || this.Operators.contains(znak)) {
        whiteExpression += znak;
      }
    }
    this.Expression = whiteExpression;
  }

  Boolean ValidateExpression() {
    int mode = 0; // ilosc podanych, niewykorzystanych wyrazen

    for (char c : this.Expression.toCharArray()) {
      String znak = Character.toString(c);

      if (this.Letters.contains(znak)) {
        mode++;
      }

      if (this.DoubleOperators.contains(znak)) {
        mode--;
      }

      if (mode <= 0) return false;
    }

    if (mode != 1) {
      return false;
    } else {
      return true;
    }
  }

  String Convert() {
    DoubleStack stack = new DoubleStack();

    for (char c : this.Expression.toCharArray()) {
      String znak = Character.toString(c);

      if (this.Letters.contains(znak)) {
        stack.PUSH(znak, znak);
        continue;
      }

      if (this.UnaryOperators.contains(znak)) {
        String result = znak;
        DoubleElem elem = stack.POP();
        if (this.Priority(znak) > this.Priority(elem.Priority)) {
          result += "(" + elem.Value + ")";
        } else {
          result += elem.Value;
        }

        stack.PUSH(result, znak);

        continue;
      }

      if (this.Operators.contains(znak)) {
        String result = "";
        DoubleElem rightElem = stack.POP();
        DoubleElem leftElem = stack.POP();

        // System.out.print(rightElem.Priority);

        if (
          this.Priority(znak) > this.Priority(leftElem.Priority) ||
          (
            this.Priority(znak) == this.Priority(leftElem.Priority) &&
            this.RightJoiningOperators.contains(znak)
          )
        ) {
          result += "(" + leftElem.Value + ")";
        } else {
          result += leftElem.Value;
        }

        result += znak;

        if (
          this.Priority(znak) > this.Priority(rightElem.Priority) ||
          (
            this.Priority(znak) == this.Priority(rightElem.Priority) &&
            this.LeftJoiningOperators.contains(znak)
          )
        ) {
          result += "(" + rightElem.Value + ")";
        } else {
          result += rightElem.Value;
        }

        stack.PUSH(result, znak);
        continue;
      }
    }
    return stack.POP().Value;
  }
}

class Source {

  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int ExpressionsQuantity = Integer.parseInt(in.nextLine());
    // int ExpressionsQuantity = 1;

    for (int i = 0; i < ExpressionsQuantity; i++) {
      String Expression = in.nextLine();
      // String Expression = "ONP: a*aa+ ";

      String ExpressionType = Expression.substring(0, 3);

      if (ExpressionType.equals("INF")) {
        INFtoONP converter = new INFtoONP(Expression.substring(4));
        converter.ClearExpression();
        if (converter.ValidateExpression()) {
          String result = converter.Convert();
          System.out.println("ONP: " + String.join(" ", result.split("")));
        } else {
          System.out.println("ONP: error");
        }
      } else {
        //   ExpressionType = "ONP"
        ONPtoINF converter = new ONPtoINF(Expression.substring(4));
        converter.ClearExpression();
        if (converter.ValidateExpression()) {
          String result = converter.Convert();
          System.out.println("INF: " + String.join(" ", result.split("")));
        } else {
          System.out.println("INF: error");
        }
      }
    }
  }
}
