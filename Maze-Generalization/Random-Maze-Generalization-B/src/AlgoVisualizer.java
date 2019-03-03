import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    // TODO: 创建自己的数据
    private MazeData data;        // 数据
    private AlgoFrame frame;    // 视图

    private static int DELAY=5;
    private static int blockSide = 6;
    private static final int d[][] ={{-1,0},{0,1},{1,0},{0,-1}};

    public AlgoVisualizer(int N, int M){

        // 初始化数据
        // TODO: 初始化数据
        data = new MazeData(N,M);
        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome", sceneHeight, sceneWidth);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
            frame.addKeyListener(new AlgoKeyListener());

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
        data.visited[first.getX()][first.getY()] = true;
        data.openMist(first.getX(),first.getY());

        while (queue.size()!=0){
            Position curPos = queue.remove();

            for(int i =0;i<4;i++){
                int newX = curPos.getX() + d[i][0]*2;
                int newY = curPos.getY() + d[i][1]*2;

                if(data.inArea(newX,newY) && !data.visited[newX][newY]&& data.maze[newX][newY] == MazeData.ROAD){
                    queue.add(new Position(newX,newY));
                    data.visited[newX][newY]=true;
                    data.openMist(newX,newY);
                    setData(curPos.getX() + d[i][0],curPos.getY() +d[i][1]);
                }
            }
        }
        setData(-1,-1);


    }

    private void setData(int x,int y){
        if(data.inArea(x,y))
            data.maze[x][y]  = MazeData.ROAD;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private boolean go(int x,int y){

        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are out of index");

        data.visited[x][y] = true;
        setPathData(x,y,true);

        if(x == data.getExitX() && y == data.getExitY())
            return true;

        for(int i = 0;i<4;i++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if(data.inArea(newX,newY) && data.maze[newX][newY] == MazeData.ROAD && !data.visited[newX][newY])
                if(go(newX,newY))
                    return true;
        }

        setPathData(x,y,false);

        return false;
    }

    private void setPathData(int x,int y ,boolean isPath){
        if(data.inArea(x,y))
            data.path[x][y] = isPath;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoKeyListener extends KeyAdapter{

        @Override
        public void keyReleased(KeyEvent event){
            if(event.getKeyChar() == ' '){
                for(int i=0; i<data.N();i++)
                    for(int j=0;j<data.M();j++)
                        data.visited[i][j] = false;

                new Thread(() ->{
                    go(data.getEntranceX(),data.getEntranceY());
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