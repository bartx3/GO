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
            MoveCreater black_move=new MoveCreater(black_x,black_y);

            if(black_move.checkMove()==true){
                System.out.println("wrong coordinates, try again");
                System.out.println("player black "+i+"th move");
                System.out.println("Give the x coordinate[1-19]: ");
                black_x = scan.nextInt();

                System.out.println("Give the y coordinate[1-19]: ");
                black_y = scan.nextInt();
                black_move=new MoveCreater(black_x,black_y);
            }

            System.out.println("player white "+i+"th move");
            System.out.println("Give the x coordinate[1-19]: ");
            int white_x = scan.nextInt();

            System.out.println("Give the y coordinate[1-19]: ");
            int white_y = scan.nextInt();
            MoveCreater white_move=new MoveCreater(white_x,white_y);

            if(white_move.checkMove()==true){
                System.out.println("wrong coordinates, try again");
                System.out.println("player white "+i+"th move");
                System.out.println("Give the x coordinate[1-19]: ");
                white_x = scan.nextInt();

                System.out.println("Give the y coordinate[1-19]: ");
                white_y = scan.nextInt();
                white_move=new MoveCreater(white_x,white_y);
            }

        }
    }
}
