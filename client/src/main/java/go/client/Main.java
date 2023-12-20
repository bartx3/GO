package go.client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        for(int i=0;i<=5;i++){
            System.out.println("player black "+i+"th move");
            System.out.println("Give the x coordinate[1-19]: ");
            int black_x = scan.nextInt();
            System.out.println("Give the y coordinate[1-19]: ");
            int black_y = scan.nextInt();
            new MoveCreater(black_x,black_y);

            System.out.println("player white "+i+"th move");
            System.out.println("Give the x coordinate[1-19]: ");
            int white_x = scan.nextInt();
            System.out.println("Give the y coordinate[1-19]: ");
            int white_y = scan.nextInt();
            new MoveCreater(white_x,white_y);

        }
    }
}
