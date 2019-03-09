import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Stack;

public class AlgoVisualizer {


    private MazeData data;        // 数据
    private AlgoFrame frame;    // 视图
    private int[][] d = {{0,-1},{0,1},{1,0},{-1,0}};

    private final int DELAY = 1;
    private final int BLOCK = 5;


    public AlgoVisualizer(int N, int M){

        // 初始化数据
        data = new MazeData(N,M);
        int sceneWidth = data.N() * BLOCK;
        int sceneHeight = data.M() * BLOCK;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("A-Recursive-Random-Maze-Generalization", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            new Thread(() -> {
                run();

            }).start();
        });
    }

    // 动画逻辑
    private void run(){

        setData(-1,-1);

        dfs(data.getEntranceX(),data.getEntranceY()+1);

        setData(-1,-1);

    }

    private void dfs(int x,int y){
        RandomQueue<Position> queue= new RandomQueue<>();
        Position firstPoint = new Position(x,y);

        queue.add(firstPoint);
        data.visited[x][y]=true;

        while(queue.size()!=0){
            Position p  = queue.remove();

            for(int i=0;i<4;i++){
                int newX = p.getX() + d[i][0] *2;
                int newY = p.getY() + d[i][1] *2;

                if(data.inArea(newX,newY) && data.maze[newX][newY]==MazeData.ROAD && !data.visited[newX][newY]){
                    data.visited[newX][newY]=true;
                    queue.add(new Position(newX,newY));
                    setData(p.getX()+d[i][0],p.getY()+d[i][1]);
                }

            }
        }

    }

    private void setData(int x,int y){

        if(data.inArea(x,y))
            data.maze[x][y]=MazeData.ROAD;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private boolean go(int x,int y){
        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are out of index");

        data.visited[x][y] = true;
        setPathData(x,y,true);

        if(x==data.getExitX() && y==data.getExitY())
            return true;

        for(int i=0;i<4;i++){
            int newX = x + d[i][0];
            int newY = x + d[i][1];

            if(data.inArea(newX,newY) && !data.visited[newX][newY] && data.maze[newX][newY]==MazeData.ROAD){
                if(go(newX,newY))
                    return true;
            }
        }

        setPathData(x,y,false);
        return false;
    }

    private void setPathData(int x,int y,boolean isPath){
        if(data.inArea(x,y))
            data.path[x][y] = isPath;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);

    }

    private class AlgoKeyListener extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent event){
            if(event.getKeyChar() == ' '){
                for(int i = 0 ; i < data.N() ; i ++)
                    for(int j = 0 ; j < data.M() ; j ++)
                        data.visited[i][j] = false;

                new Thread(() -> {
                    go(data.getEntranceX(), data.getEntranceY());
                }).start();
            }
        }
    }


    public static void main(String[] args) {

        int N = 101;
        int M = 101;

        // TODO: 根据需要设置其他参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(N, M);
    }
}