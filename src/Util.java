import java.util.ArrayList;
import java.util.Random;

public class Util {

    //传入a个可能结果，b个总体结果数。按照a/b的几率返回true
    public static boolean random(int a, int b) {
        Random ran = new Random();
        boolean[] bool = new boolean[b];
        for (int x = 0; x < bool.length; x++) {
            bool[x] = false;
        }
        for (int n = 0; n < a; n++) {
            bool[randomInt(b - 1)] = true;
        }
        return bool[randomInt(b - 1)];
    }

    //返回范围内整数
    public static int randomInt(int max) {
        return new Random().nextInt(max);
    }

    public static double normalDistribution(float u, float v) {
        java.util.Random random = new java.util.Random();
        return Math.sqrt(v) * random.nextGaussian() + u;
    }

    public static City randomCity(ArrayList<City> cityList) {
        //System.out.println(new Random().nextInt(cityList.size()));
        return cityList.get(new Random().nextInt(cityList.size()));
    }

    public static int getRandomInt(int min, int max) {
        int result = new Random().nextInt(max);
        if (result <= min) {
            return getRandomInt(min, max);
        } else {
            return result;
        }
    }
}
