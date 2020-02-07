public class Virus {
    public static final int INFECTION_RATE = 9;
    public static final int INSIDIOUS_DAYS = 14;
    public static final int DEAD_INFECTION_RATE = 4;

    public static int infectionPersonNum = 0;

    //死亡率[0,10]
    public static final int DEADTH_RATE_WITHOUT_HOSPITAL = 5;
    public static final int MIN_DEADTH_DAY = 3;

    public static void infect(Person p) {
        if (Util.random(INFECTION_RATE, 10)) {
            p.changeState(Person.INSIDIOUS);
            infectionPersonNum++;
        }
    }

    public static void spawnVirus(Person p) {
        p.changeState(Person.INSIDIOUS);
    }

    public static void deadInfect(Person p) {
        if (Util.random(DEAD_INFECTION_RATE, 10)) {
            p.changeState(Person.INSIDIOUS);
            infectionPersonNum++;
        }
    }
}
