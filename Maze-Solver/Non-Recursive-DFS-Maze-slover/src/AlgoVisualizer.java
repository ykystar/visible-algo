import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Stack;

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

        Stack<Position> stack = new Stack<Position>();
        Position entrance = new Position(data.getEntranceX(),data.getEntranceY());
        stack.push(entrance);
        data.visited[entrance.getX()][entrance.getY()]=true;

        boolean isSolved = false;

        while(!stack.empty()){
            Position curPos = stack.pop();
            setData(curPos.getX(),curPos.getY(),true);

            if(curPos.getX() == data.getExitX() && curPos.getY() == data.getExitY()){
                isSolved = true;
                findPath(curPos);
                break;
            }

            for(int i=0 ; i<4; i++){
                int newX = curPos.getX() + d[i][0];
                int newY = curPos.getY() + d[i][1];

                if(data.inArea(newX,newY) && !data.visited[newX][newY] && data.getMaze(newX,newY) == MazeData.ROAD){
                    stack.push(new Position(newX,newY,curPos));
                    data.visited[newX][newY] = true;
                }
            }

        }

        if(!isSolved)
            System.out.println("the maze has no Solution");
        setData(-1,-1,false);
    }

    private void findPath(Position des){

        Position cur = des;
        while(cur !=null){
            data.result[cur.getX()][cur.getY()]= true;
            cur = cur.getPrev();
        }
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