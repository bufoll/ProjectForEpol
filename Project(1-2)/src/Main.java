import java.io.FileReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        FileReader fr = new FileReader(args[0]);
        Scanner sc = new Scanner(fr);
        int a = 1;

        while (sc.hasNextLine()) {
            System.out.println(a + " : " + sc.nextLine());
            a++;
        }
    }
}