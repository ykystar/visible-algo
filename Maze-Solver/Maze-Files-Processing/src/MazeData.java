import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class MazeData {

    public static final char ROAD=' ';
    public static final char WALL='#';

    private int N,M;
    private char[][] maze;

    public MazeData(String filename){
        if(filename == null)
            throw new IllegalArgumentException("Filename can't be null");

        Scanner scanner = null;

        try{
            File file = new File(filename);
            if(!file.exists())
                throw new IllegalArgumentException("File"+filename+"need exist");

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis),"UTF-8");

            // 读取第一行
            String nmline = scanner.nextLine();
            String[] nm = nmline.trim().split("\\s+");

            N = Integer.parseInt(nm[0]);

            M = Integer.parseInt(nm[1]);

            // 读取后续N行
            maze ＝ new char[N][M];
            

        }
        catch (){

        }
    }
}
