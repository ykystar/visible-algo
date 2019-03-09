public class MazeData {

    public static final int ROAD=' ';
    public static final int WALL='#';

    private int N,M;

    public boolean[][] visited;
    public char[][] maze;

    private int EntranceX;
    private int EntranceY;

    private int ExitX;
    private int ExitY;

    public boolean[][] inMist;

    public MazeData(int N,int M){
        if(N%2==0 || M%2==0)
            throw new IllegalArgumentException("needs odd number");

        this.N = N;
        this.M = M;


        visited = new boolean[N][M];
        maze = new char[N][M];
        inMist = new boolean[N][M];

        for(int i=0;i<N;i++)
            for(int j=0;j<M;j++){
                if(i%2 ==1 && j%2 ==1)
                    maze[i][j]= ROAD;
                else
                    maze[i][j]= WALL;
                visited[i][j]=false;
                inMist[i][j]=true;
            }

        EntranceX = 1;
        EntranceY = 0;
        ExitX = N-2;
        ExitY = M-1;

        maze[EntranceX][EntranceY] = ROAD;
        maze[ExitX][ExitY] = ROAD;
    }

    public int N(){return N;}
    public int M(){return M;}
    public int getEntranceX(){return EntranceX;}
    public int getEntranceY(){return EntranceY;}
    public int getExitX(){return ExitX;}
    public int getExitY(){return ExitY;}

    public boolean inArea(int x,int y){
        return x>=0 && x<N && y>=0 && y<M;
    }

    public void openMist(int x,int y){
        if(!inArea(x,y))
            throw new IllegalArgumentException("x,y out of index");

        for(int i = x-1;i<=x+1; i++)
            for(int j = y-1;j<=y+1;j++){
                if(inArea(i,j))
                    inMist[i][j] = false;
            }
        return;
    }
}
