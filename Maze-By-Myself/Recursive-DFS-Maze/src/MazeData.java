public class MazeData {

    public static final char ROAD=' ';
    public static final char WALL='#';

    private int N;
    private int M;

    private int getEntranceX;
    private int getEntranceY;
    private int exitX;
    private int exitY;

    private char[][] maze;
    private boolean[][] visited;
    private boolean[][] path;

    public MazeData(int N,int M){
        this.N = N;
        this.M = M;

        maze = new char[N][M];
        visited = new boolean[N][M];
        path = new boolean[N][M];

        for(int i=0;i<N; i++){
            for(int j=0;j<M;j++){
                if(i%2!=0 && j%2 !=0)
                    maze[i][j] = ROAD;
                else
                    maze[i][j] = WALL;

                visited[i][j]=false;
                path[i][j] = false;
            }
        }

        getEntranceX = 1;
        getEntranceY = 0;
        exitX = N-2;
        exitY = M-1;

        maze[getEntranceX][getEntranceY]= ROAD;
        maze[exitX][exitY] = ROAD;
    }

    public int N(){return N;}
    public int M(){return M;}
    public int getEntranceX(){return exitX;}
    public int getEntranceY(){return exitY;}

    public boolean inArea(int x,int y){
        return x>=0 && x<N && y>=0 && y<M;
    }
}
