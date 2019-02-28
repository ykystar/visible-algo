import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class AlgoVisualizer {


    private AlgoFrame frame;    // 视图
    private static int DELAY=5;
    private static int blockSide=8;

    private MazeData data;
    private static final int d[][] ={{-1,0},{0,1},{1,0},{0,-1}};

    public AlgoVisualizer(String mazeFile){

        // 初始化数据
        // TODO: 初始化数据
        data = new MazeData(mazeFile);
        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze-Go!", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){

        setData(-1,-1,false);

        LinkedList<Position> queue = new LinkedList<Position>();
        Position entrance = new Position(data.getEntranceX(),data.getEntranceY());
        queue.addLast(entrance);
        data.visited[entrance.getX()][entrance.getY()]=true;

        boolean isSoled = false;

        while(queue.size()!=0){
        }
    }

    private boolean go(int x,int y){
        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are out of index in go function!");

        data.visited[x][y] =true;
        setData(x,y,true);

        if(x == data.getExitX() && y == data.getExitY())
            return true;

        for(int i=0;i<4;i++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];

            if(data.inArea(newX,newY) && data.getMaze(newX,newY) == MazeData.ROAD && !data.visited[newX][newY])
                if(go(newX,newY))
                    return true;
        }

        setData(x,y,false);
        return false;
    }

    private void setData(int x,int y,boolean isPath){
        if(data.inArea(x,y))
            data.path[x][y] = isPath;
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }



    public static void main(String[] args) {


        String mazeFile = "101.txt";
        AlgoVisualizer vis = new AlgoVisualizer(mazeFile);
    }
}