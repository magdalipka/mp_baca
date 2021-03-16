//Magdalena Lipka gr.7//
import java.util.Scanner;

class Source {

    static Scanner scan = new Scanner(System.in);

    static int area(int top, int bottom, int left, int right) {
        return (bottom - top + 1) * (right - left + 1);
    }

    static boolean isWinning(int top1, int bottom1, int left1, int right1, int top2, int bottom2, int left2,
            int right2) {
        if (area(top1, bottom1, left1, right1) < area(top2, bottom2, left2, right2)) {
            return true;
        } else if (area(top1, bottom1, left1, right1) > area(top2, bottom2, left2, right2)) {
            return false;
        } else {
            if (top1 < top2) {
                return true;
            } else if (top1 > top2) {
                return false;
            } else {
                if (bottom1 < bottom2) {
                    return true;
                } else if (bottom1 > bottom2) {
                    return false;
                } else {
                    if (left1 < left2) {
                        return true;
                    } else if (left1 > left2) {
                        return false;
                    } else {
                        if (right1 <= right2) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        int Quantity = scan.nextInt(); // ilosc zestawow//

        while (Quantity > 0) {

            int SetNumber; // numer zestawu
            int Columns, Lines; // wymiary pojedynczej tabeli//
            SetNumber = scan.nextInt();
            scan.next();
            Lines = scan.nextInt();
            Columns = scan.nextInt();

            int[][] Array = new int[Lines][Columns];
            int max_sum = 0;
            int max_left = 0;
            int max_right = 0;
            int max_top = 0;
            int max_height = 0;

            boolean only_neg = true;

            // wczytywanie//

            for (int i = 0; i < Lines; i++) {
                for (int j = 0; j < Columns; j++) {
                    int value = scan.nextInt();
                    Array[i][j] = value > 0 ? 3 * value : 2 * value;
                    if (Array[i][j] >= 0)
                        only_neg = false;

                }
            }
            max_sum = Array[0][0];

            if (only_neg) {
                System.out.println(SetNumber + ": n=" + Lines + " m=" + Columns + ", ms= 0, mstab is empty");
            } else {

                for (int temp_top = 0; temp_top < Lines; temp_top++) {

                    for (int temp_height = 0; temp_height + temp_top < Lines; temp_height++) {

                        // sumowanie podtabeli zacznajacej sie w temp_left o szerokowi temp_width//

                        int[] Sum = new int[Columns];
                        for (int i = 0; i < Columns; i++)
                            Sum[i] = 0;

                        for (int i = 0; i < Columns; i++) {
                            for (int j = temp_top; j <= temp_top + temp_height; j++) {
                                Sum[i] += Array[j][i];
                            }
                        }

                        int temp_sum = 0;
                        int temp_start = 0;

                        for (int i = 0; i < Columns; i++) {

                            // temp_sum += Sum[i];

                            if (Sum[i] > temp_sum + Sum[i]) {
                                temp_start = i;
                                temp_sum = Sum[i];
                            } else {
                                temp_sum = temp_sum + Sum[i];
                            }

                            if (temp_start != i && Sum[temp_start] == 0) {
                                temp_start++;
                            }

                            if (max_sum < temp_sum || (max_sum == temp_sum && !isWinning(max_top, max_top + max_height,
                                    max_left, max_right, temp_top, temp_top + temp_height, temp_start, i))) {
                                max_sum = temp_sum;
                                max_top = temp_top;
                                max_left = temp_start;
                                max_right = i;
                                max_height = temp_height;
                            }

                            // if ((temp_sum <= 0 && (temp_sum != temp_sum - Sum[i])
                            // || (Sum[i] == 0 && i < Columns - 1 && Sum[i + 1] != 0))) {
                            // temp_sum = 0;
                            // temp_start = i + 1;
                            // }

                        }

                    }

                }
                int max_bottom = max_top + max_height;
                if (max_left > max_right)
                    max_right = max_left;

                System.out.println(SetNumber + ": n=" + Lines + " m=" + Columns + ", ms= " + max_sum + ", mstab= a["
                        + max_top + ".." + max_bottom + "][" + max_left + ".." + max_right + "]");
            }

            Quantity--;
        }
    }
}
