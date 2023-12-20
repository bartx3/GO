package go.client;

public class MoveChecker implements Gamestate{
    Move move;
    //do storowania koordynatów przyda sie tez jakis playerhandler (w serverze będzie), kótry później sprawdzi z ztymi oddechami
    int coordinates_list[][]=new int[360][360];

    @Override
    public Move createMove() {
        return move;
    }

    @Override
    public boolean checkMove() {
        boolean flag=false;
        int checked_x=move.getX();
        int checked_y=move.getY();

        int checked[][]=new int[1][1];
        int i1 = checked[checked_x][checked_y];

        //czy ten ruch nie był już zrobiony
        for(int i=0;i<=360;i++){
            for (int j=0;j<=360;j++) {
                if (i1==coordinates_list[i][j]) {
                    flag=true;
                }
            }
        }

            //czy ten ruch zabierze ostatni oddech tego gracza?
        return flag;
    }

    @Override
    public void makeMove() {
    }
}
