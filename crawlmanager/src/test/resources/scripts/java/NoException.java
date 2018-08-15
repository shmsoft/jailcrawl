import java.io.File;

public class NoException{

    public static void main(String[] args) throws Exception{
        System.out.println("ran with no exception...");
        File file = new File(args[0]+"/test.html");
        file.createNewFile();
    }
}