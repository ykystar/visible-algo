import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    private MazeData data;        // 数据
    private AlgoFrame frame;    // 视图
    private static final int DELAY = 5;
    private static final int BLOCK = 5;

    private static final int d[][] = {{-1,0},{0,1},{1,0},{0,-1}};


    public AlgoVisualizer(int N, int M){

        // 初始化数据
        data = new MazeData(N,M);
        int sceneWidth = BLOCK * N;
        int sceneHeight = BLOCK * M;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("RandomA", sceneWidth, sceneHeight);
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){
        setData(-1,-1);

        RandomQueue<Position> queue = new RandomQueue<>();

        Position first = new Position(data.getEntranceX(),data.getEntranceY()+1);
        queue.add(first);
        data.visited[first.getX()][first.getY()]=true;
        data.openMist(first.getX(),first.getY());

        while(queue.size()!=0){
            Position curPos = queue.remove();

            for(int i=0; i<=4;i++){
                int newX = curPos.getX() + d[i][0] *2;
                int newY = curPos.getY() + d[i][1] *2;

                if(data.inArea(newX,newY) && !data.visited[newX][newY] && data.maze[newX][newY] == MazeData.ROAD){
                    data.visited[newX][newY] = true;
                    data.openMist(newX,newY);
                    setData(curPos.getX()+d[i][0],curPos.getY()+d[i][1]);
                    queue.add(new Position(newX,newY));
                }
            }
        }
        setData(-1,-1);
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

        AlgoVisualizer visualizer = new AlgoVisualizer(N, M);
    }
}