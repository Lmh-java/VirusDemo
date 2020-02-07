import java.util.ArrayList;
import java.util.Random;

public class World extends Thread {
    //人数
    private final int PERSON_NUM = 3000;
    //投放病毒数量
    private final int VIRUS_NUM = 2;
    //生成城市数量
    private final int CITY_NUM = 3;

    public static ArrayList<Person> personList = null;
    public static ArrayList<City> cityList = null;
    public static ArrayList<Person> deadList = null;

    private View windows = null;

    public void initialize() {

        personList = new ArrayList<>();
        cityList = new ArrayList<>();
        deadList = new ArrayList<>();
        for (int i = 0; i < PERSON_NUM; i++) {
            personList.add(new Person());
//            System.out.println(i);
        }

        Random ran = new Random();
        //正态分布人员位置
        //city = new City(400,400);

        for (int x = 0; x < CITY_NUM; x++) {
            cityList.add(new City(Util.getRandomInt(300, 700), Util.getRandomInt(300, 700)));
            //System.out.println(x+"\n"+cityList.get(x).getCenter_x()+"\n"+cityList.get(x).getCenter_y());
        }

        for (Person p : personList) {
            p.setPos_x(100 * ran.nextGaussian() + Util.randomCity(cityList).getCenter_x());
            p.setPos_y(100 * ran.nextGaussian() + Util.randomCity(cityList).getCenter_y());
            System.out.println(p.getPos_x() + "\n" + p.getPos_y());
        }

        //随机发放n个病毒
        for (int y = 0; y < VIRUS_NUM; y++) {
            Virus.spawnVirus(personList.get(ran.nextInt(personList.size())));
        }

        windows = new View();
        windows.start();

    }
}
