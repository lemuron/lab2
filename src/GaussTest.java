import java.util.Random;

public class GaussTest {

    public static void main(String args[]){
        Random gen = new Random();


        for(int i = 0;i<30;i++){
            System.out.println(gen.nextGaussian()*30 + 300);
        }

    }
}
