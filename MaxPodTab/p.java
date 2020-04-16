package MaxPodTab;

//Magdalena Lipka gr.1//
import java.util.Scanner;

class Source {
    static Scanner scan =  new Scanner(System.in);
    public static void main(String [] args) {
               
        int Quantity = scan.nextInt(); //ilosc zestawow//

        while(Quantity > 0) {
            
            int Columns, Lines; //wymiary pojedynczej tabeli//
            Lines = scan.nextInt();
            Columns = scan.nextInt();
            
            int[][] Array = new int[Lines][Columns];
            int max_sum = 0;
            int max_left = 0;
            int max_right = Columns-1;
            int max_top = 0;
            int max_height = Lines-1;

            boolean only_neg = true;
            boolean any_zero = false;
            int zero_l = 0;
            int zero_c = 0;

            //wczytywanie//
            ////

            for(int i = 0; i < Lines; i++) {
                for(int j = 0; j < Columns; j++) {
                    Array[i][j] = scan.nextInt(); 
                    max_sum += Array[i][j];  
                    if(Array[i][j] > 0) only_neg = false;
                    if(Array[i][j] == 0 && !any_zero) {
                        any_zero = true;
                        zero_l = i;
                        zero_c = j;   
                    }          
                }
            }

            ////
            //koniec wczytywania//


            //wlasciwy program//
            ////

            if( only_neg && !any_zero) System.out.println("empty");
            else{
            if(any_zero && only_neg) {
                max_sum = 0;
                max_left = zero_c;
                max_top = zero_l;
                max_height = 0;
                max_right = zero_c;
                
            } else{
            for(int temp_top = 0; temp_top < Lines; temp_top++) {
                
                for(int temp_height = 0; temp_height + temp_top < Lines; temp_height++) {

                    //sumowanie podtabeli zacznajacej sie w temp_left o szerokowi temp_width//
                    ////

                    int[] Sum = new int[Columns];
                    for(int i = 0; i<Columns; i++) Sum[i]=0;

                    for(int i = 0; i < Columns; i++) {
                        for(int j = temp_top; j<=temp_top+temp_height; j++) {
                            Sum[i] += Array[j][i];
                        }
                    }

                    ////
                    //koniec sumowani podtabeli//

                    //liniowa maksymalna podtablica//
                    ////

                    int temp_sum = 0;
                    int temp_start = 0;
                
                    
                    

                    for(int i = 0; i < Columns; i++) {
                        
                        temp_sum += Sum[i];

                        if ( ( temp_sum <= 0 &&  (temp_sum != temp_sum - Sum[i]) || ( Sum[i] == 0 && i<Columns-1 && Sum[i+1] != 0 ) ) ) {
                            temp_sum = 0;
                            temp_start = i + 1;
                        }


                        if(temp_sum > max_sum) {
                            max_sum = temp_sum;
                            max_top = temp_top;
                            max_left = temp_start;
                            max_right = i;
                            max_height = temp_height;


                        }
                        else if(temp_sum == max_sum) {
                            if( (max_height+1)*(max_right - max_left +1) > (temp_height + 1)*(i - temp_start + 1) ) {
                            max_sum = temp_sum;
                            max_top = temp_top;
                            max_left = temp_start;
                            max_right = i;
                            max_height = temp_height;
                            }
                            
                        }

                        
                    }

                    ////
                    //koniec liniowej maksymalnej podtablicy//
                }
            }
            }
            int max_bottom = max_top + max_height;
            if (max_left > max_right) max_right = max_left;
            System.out.println("max_sum = " + max_sum + ", a[" + max_top + ".." + max_bottom + "][" + max_left + ".." + max_right + "]");
            }
            
            ////
            ////////////////
            Quantity--;
        }
    }
}
