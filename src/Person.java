import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Person {

    public static final int NORMAL = 0;
    public static final int INSIDIOUS = 1;
    public static final int SICK = 2;
    public static final int DEAD = 3;
    public static final int HOSPITAL = -1;

    private final int MIN_SAFE_DIST = 5;
    //流动意向[0,10]
    private final int MAX_INTENTION = 10;

    private double pos_x = 0f;
    private double pos_y = 0f;
    private Point target = null;

    private int insidiousDays = 0;
    private int sickDays = 0;

    private int intention = 10;

    //流动速度 n个单位 每天[0,5]
    private final int SPEED = 5;
    private int state = NORMAL;
    private Random ran;
    private ArrayList<Person> nearPersonList = null;

    public Person() {
        ran = new Random();
    }

    public void move() {
        if (Util.random(intention, 10)) {
            if (target == null && (state != DEAD || state != HOSPITAL)) {
                int x = (int) (ran.nextGaussian() * 100 + Util.randomCity(World.cityList).getCenter_x());
                int y = (int) (ran.nextGaussian() * 100 + Util.randomCity(World.cityList).getCenter_y());
                target = new Point((int) (50 * ran.nextGaussian() + x), (int) (50 * ran.nextGaussian() + y));
                move();
            } else {
                moveTo(target);
            }
        }
    }

    public Color show() {
        switch (state) {
            case NORMAL:
                return Color.white;
            case INSIDIOUS:
                return Color.yellow;
            case SICK:
                return Color.red;
            case DEAD:
                return Color.blue;
            default:
                return null;
        }

    }

    public void changeState(int state) {
        if (state >= -1 && state <= 3) {
            this.state = state;
        }
        if (state == HOSPITAL) {
            this.target = null;
            intention = 0;
        } else if (state == NORMAL) {
            intention = MAX_INTENTION;
        }
    }

    public void setPos_x(double pos_x) {
        this.pos_x = pos_x;
    }

    public void setPos_y(double pos_y) {
        this.pos_y = pos_y;
    }

    private void moveTo(Point p) {
        if (p.getX() < pos_x)
            pos_x -= SPEED;
        if (p.getX() > pos_x)
            pos_x += SPEED;
        if (p.getY() < pos_y)
            pos_y -= SPEED;
        if (p.getY() > pos_y)
            pos_y += SPEED;
        if (p.getX() == pos_x && p.getY() == pos_y) {
            target = null;
            return;
        }
    }

    public double getPos_x() {
        return pos_x;
    }

    public double getPos_y() {
        return pos_y;
    }

    public int getState() {
        return state;
    }

    public void addDays() {
        if (state == Person.INSIDIOUS)
            insidiousDays++;
        if (state == Person.SICK)
            sickDays++;
    }

    public int getInsidiousDays() {
        return insidiousDays;
    }

    private double distance(Person p) {
        return Math.sqrt(Math.pow(pos_x - p.getPos_x(), 2) + Math.pow(pos_y - p.getPos_y(), 2));
    }

    public void aciton() {
        if (state != SICK) {
            move();
        }

        nearPersonList = getNearPerson();
        if (state == SICK || state == INSIDIOUS) {
            for (Person p : nearPersonList) {
                if (p.getState() == Person.NORMAL) {
                    Virus.infect(p);
                }
            }
        }

        //潜伏期具有传染性
        if (state == Person.INSIDIOUS) {
            if (getInsidiousDays() == Virus.INSIDIOUS_DAYS) {
                changeState(Person.SICK);
            } else {
                addDays();
            }
        } else if (state == Person.SICK) {
            if (getSickDays() >= Virus.MIN_DEADTH_DAY) {
                if (Util.random(Virus.DEADTH_RATE_WITHOUT_HOSPITAL, 10))
                    changeState(Person.DEAD);
                intention = 0;
                this.target = null;
                if (!World.deadList.contains(this)) {
                    World.deadList.add(this);
                }
            } else {
                addDays();
            }
        }
        if (state == DEAD) {
            for (Person p : nearPersonList) {
                if (p.getState() == Person.NORMAL) {
                    Virus.deadInfect(p);
                }
            }
        }


    }

    private ArrayList<Person> getNearPerson() {
        ArrayList<Person> result = new ArrayList<>();
        for (Person p : World.personList) {
            if (this.distance(p) < MIN_SAFE_DIST) {
                result.add(p);
            }
        }
        return result;
    }

    public int getSickDays() {
        return sickDays;
    }
}
