//Magdalena Lipka gr.1//
import java.util.Scanner;

class Stack {

   char[] Elems;
   int maxSize = 256;
   int top;

   public Stack() {
      Elems = new char[maxSize];
      top = 0;
      Elems[0]='#';
   }

   public char TOP() {
      return Elems[top];
   }
   public char POP() {
      if(Elems[top] == '#') return '#';
      char elem = Elems[top];
      Elems[top] = 0;
      top--;
      return elem;
   }
   public void PUSH(char elem) {
      top++;
      Elems[top] = elem;   
   }
   public boolean isEMPTY() {
      if (top == 0) return true;
      return false;
   }

}

class DoubleStack {

   String[] Elems;
   char[] Priors;
   int maxSize = 256;
   int top;

   public DoubleStack() {
      Elems = new String[maxSize];
      Priors = new char[maxSize];
      top = 0;
      Elems[0]="#";
   }
   public String TOP() {
      return Elems[top];
   }
   public char TOPprior() {
      return Priors[top];
   }
   public char POP() {
      if(Elems[top]=="#") return '#';
      Elems[top] = null;
      Priors[top] = 0;
      top--;
      return 1;
   }
   public void PUSH(String elem, char prior) {
      top++;
      Elems[top] = elem;
      Priors[top] = prior;   
   }
   public boolean isEMPTY() {
      if (top == 0) return true;
      return false;
   }

}

class Source {
	static Scanner scan =  new Scanner(System.in);
	public static void main(String [] args) {
    
		int Quantity = Integer.parseInt(scan.nextLine());
      //scan.nextLine();

		for(int q = 0; q<Quantity; q++) {

         

			String MayBeExpression = scan.nextLine();

			String Expression = "";
            
			if(MayBeExpression.toCharArray()[0]=='I') {
					//usuwane zbednych znakow//
					//
					for( char read : MayBeExpression.toCharArray()) {

						if ( Acceptable( MayBeExpression, read, 1 ) ) {
							Expression += read;
						}
					}
					//
               //koniec usuwania zbednych znakow//
               
					
               if ( Automat(Expression, 1) ) { //poprawnosc wyrazenia//
                  //WLASCIE DZIALANE PROGRAMU+++++++++++++++++++++++++++++++++//
                  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
                  //
                  
                  Stack theStack = new Stack();

                  System.out.print("ONP: ");
                  for(char c : Expression.toCharArray()) {

                     if (c >= 'a' && c <= 'z') {
                        System.out.print(c);
                     }
                     else if ( c == '(') {
                        theStack.PUSH(c);
                     } else if (c == '<' || c == '>' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%' ) { //operator lewostronnie laczny//
                        //zdejmowanie ze stosu operatorow o priortecie niemniejszym//
                        while (Priority(theStack.TOP()) >= Priority(c)) {
                           System.out.print(theStack.POP());
                        }
                        theStack.PUSH(c);
                     } else if ( c == '=' || c == '^' || c == '~' ) { //operator prawostronnie laczny//
                        //zdejmoanie ze stosu operatorow o priorytecie wiekszym//
                        while (Priority(theStack.TOP()) > Priority(c)) {
                           System.out.print(theStack.POP());
                        }
                        theStack.PUSH(c);
                     } else if ( c == ')' ) {
                        //zdejmonie ze stosu wszystkiego az do nawiasu otwierajacego//
                        while( theStack.TOP() != '(' ) {
                           System.out.print(theStack.POP());
                        }
                        theStack.POP();
                     }
                  }
                  //zdejmowanie pozostalosci ze stosu//
                  while ( theStack.TOP() != '#' ) {
                     System.out.print(theStack.POP());
                  }
                  System.out.print("\n");
                  //System.out.println("ONP: " + Output);
                  //
                  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
                  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
               } else System.out.println("ONP: error");
					
					}
				else {
					//usuwane zbednych znakow//
					//
					for( char read : MayBeExpression.toCharArray()) {
						if ( Acceptable( MayBeExpression, read, 0 ) ) {
							Expression += read;
						}
					}
					//
					//koniec usuwania zbednych znakow//    

               if ( Automat(Expression, 0) ) {
                  //WLASCIE DZIALANE PROGRAMU+++++++++++++++++++++++++++++++++//
                  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
                  //

                  DoubleStack theDoubleStack = new DoubleStack();

                  for ( char c : Expression.toCharArray() ) {
                     if ( c >= 'a' && c <= 'z' ) {
                        String xxx = "";
                        xxx += c;
                        theDoubleStack.PUSH(xxx, c);
                     }
                     else if ( c == '='  || c == '<' || c == '>' || c == '-' || c == '+' || c == '%' || c == '/' || c == '*' || c == '^' ) {
                        String Result = "";
                        
                        String Right = theDoubleStack.TOP();
                        char right = theDoubleStack.TOPprior();
                        
                        theDoubleStack.POP();
                        
                        String Left = theDoubleStack.TOP();
                        char left =theDoubleStack.TOPprior();

                        theDoubleStack.POP();

                        if (Priority(c) > Priority(left) || (Priority(c) == Priority(left) && (c == '=' || c == '^' || c == '~') )) {
                           Result += '(' + Left + ')';
                        } else Result += Left;

                        Result += c;

                        if (Priority(c) > Priority(right) || (Priority(c) == Priority(right) && (c == '<' || c == '>' || c == '-' || c == '+' || c == '*' || c == '%' || c == '/') )) {
                           Result += '(' + Right + ')';
                        } else Result += Right;

                        theDoubleStack.PUSH(Result, c);
                     }
                     else if ( c == '~' ) {
                        String Result = "";
                        Result += '~';
                        if(theDoubleStack.TOP().length() != 1 && theDoubleStack.TOPprior() != '~') Result += '(';
                        Result += theDoubleStack.TOP();
                        if(theDoubleStack.TOP().length() != 1  && theDoubleStack.TOPprior() != '~') Result += ')';
                        theDoubleStack.POP();
                        theDoubleStack.PUSH(Result, c);
                     }
                     
                  }
                  
                  System.out.println("INF: " + theDoubleStack.TOP());
                  //
                  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
                  //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
               } else System.out.println("INF: error");

				}

			}
		}

		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

   //funkcja do sprawdzania czy znak jest dopuszczalny------------------------//
	static boolean Acceptable( String T, int symbol, int type) {

      String AcceptedONP = "qwertyuiopasdfghjklzxcvbnm=<>+-*%^~/";
      String AcceptedINF = "qwertyuiopasdfghjklzxcvbnm=<>+-*%^~/()";

		if ( type == 1 ) {
			for(char c : AcceptedINF.toCharArray()) {
				if( c == symbol ) return true;
			}
			} else {
				for(char c : AcceptedONP.toCharArray()) {
					if( c == symbol ) return true;
				}
			}
	return false;
   }
   //--------------------------------------------------------------------//
   //
   //
   //
   //
   //funkcja sprawdzajaca czy wyrazenie jest obliczalne-----------------//
   static boolean Automat(String Exp, int type) {
      //poprawnosc postaci inf------------------------------------------//
      if(type==1) {
         int state = 0;
         int opened = 0;
         for(char c : Exp.toCharArray()) {
            switch(state) {
               case 0:
                  if ( c == '(' ) {opened++; state = 0;}
                  else if( c == '~' ) state = 2;
                  else if(c >= 'a' && c <= 'z') state = 1;
                  else return false;
               break;
               case 1:
                  if ( c == ')' ) {opened--; state = 1;}
                  else if ( c == '^' || c == '*' || c == '/' || c == '%' || c == '+' || c == '-' || c == '<' || c == '>' || c == '=' ) state = 0;
                  else return false;
               break;
               case 2:
                  if ( c == '(' ) {opened++; state = 0;}
                  else if ( c == '~' ) state = 2;
                  else if( c >= 'a' && c <= 'z' ) state = 1;
                  else return false;
               break;
            }
            if ( opened < 0 ) return false;
         }
         if ( state != 1 ) return false;
         if ( opened != 0 ) return false; 
      }
      //poprawnosc postaci onp------------------------------------------//
      else {
         int mode = 0;
         for(char c : Exp.toCharArray()) {
            if( c >= 'a' && c <= 'z' ) mode++;
            else if( c == '^' || c == '*' || c == '/' || c == '%' || c == '+' || c == '-' || c == '<' || c == '>' || c == '=' ) mode--;
            if ( mode <= 0 ) return false;
         }
         if ( mode != 1 ) return false;
      }
      return true;
   }
   //
   //
   //
   //funkcja sprawdzajaca priorytety---------------------------------------//
   static int Priority(char symbol) {
      switch(symbol) {
         case '~':
            return 6;
         case '^':
            return 5;
         case '*':
            return 4;
         case '/':
            return 4;
         case '%':
            return 4;
         case '+':
            return 3;
         case '-':
            return 3;
         case '<':
            return 2;
         case '>':
            return 2;
         case '=':
            return 1;
      }
      if ( symbol >= 'a' && symbol <= 'z' ) return 6;
      else return -1;
   }
   //
   //
   //
}


//TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_//
//TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_TESTY_//

//Wejscie//
//20
//INF: a+b*(c/d)+g^r-p/(m+n)
//INF: b=y+h*c-y/g^k
//INF: a~+b*c
//INF: ()+b
//INF: (a +,b..*c)/(b/c+a*d)
//INF: a=b<c+y*t<(c+d)
//INF: l^(a*c+y*(m+o))
//INF: (a+b)*(c+d)/y(
//INF: a^b^m*(c+d)
//INF: ~(~(~((~a+~~b)/~c)*~d)^~e)
//ONP: abc-*ab^c/+
//ONP: a~~~~
//ONP: ab+~
//ONP: ab+~~
//ONP: ab+c/gb*cd/*-
//ONP: b~cd/~+g^h^
//ONP: abcdefg+*-/%
//ONP: a~b~~+c~/~d~*~e~^~
//ONP: xabcd^^=
//ONP: klmo**/tr%+


//Wyjscie//
//ONP: abcd/*+gr^+pmn+/-
//ONP: byhc*+ygk^/-=
//ONP: error
//ONP: error
//ONP: abc*+bc/ad*+/
//ONP: abcyt*+<cd+<=
//ONP: lac*ymo+*+^
//ONP: error
//ONP: abm^^cd+*
//ONP: a~b~~+c~/~d~*~e~^~
//INF: a*(b-c)+a^b/c
//INF: ~~~~a
//INF: ~(a+b)
//INF: ~~(a+b)
//INF: (a+b)/c-g*b*(c/d)
//INF: ((~b+~(c/d))^g)^h
//INF: error
//INF: ~(~(~((~a+~~b)/~c)*~d)^~e)
//INF: error
//INF: k/(l*(m*o))+t%r