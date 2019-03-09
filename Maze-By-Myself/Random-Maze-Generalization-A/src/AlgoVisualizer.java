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

    private final int DELAY = 5;
    private final int BLOCK = 5;


    public AlgoVisualizer(int N, int M){

        // 初始化数据
        data = new MazeData(N,M);
        int sceneWidth = data.N() * BLOCK;
        int sceneHeight = data.M() * BLOCK;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("None-Recursive-Maze-Generalization", sceneWidth, sceneHeight);

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
        Stack<Position> stack= new Stack<>();
        Position firstPoint = new Position(x,y);

        stack.push(firstPoint);
        data.visited[x][y]=true;

        while(stack.size()!=0){
            Position p  = stack.pop();

            for(int i=0;i<4;i++){
                int newX = p.getX() + d[i][0] *2;
                int newY = p.getY() + d[i][1] *2;

                if(data.inArea(newX,newY) && data.maze[newX][newY]==MazeData.ROAD && !data.visited[newX][newY]){
                    data.visited[newX][newY]=true;
                    stack.push(new Position(newX,newY));
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


    public static void main(String[] args) {

        int N = 101;
        int M = 101;

        // TODO: 根据需要设置其他参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(N, M);
    }
}