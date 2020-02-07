import java.util.Random;

public class Bed {
    private Person patient = null;
    private int cureDays = 0;
    private int healthDays;
    private int pos_x;
    private int pos_y;

    public Person getPatient() {
        return patient;
    }

    public void setPatient(Person p) {
        patient = p;
    }

    public Bed(int pos_x, int pos_y, Person p) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        patient = p;
        healthDays = Util.randomInt(30);
    }

    //TODO 在View线程中遍历执行cure
    public void cure() {
        cureDays++;
        if (cureDays == healthDays) {
            patient.changeState(Person.NORMAL);
            patient.setPos_x(100 * new Random().nextGaussian() + Util.randomCity(World.cityList).getCenter_x());
            patient.setPos_y(100 * new Random().nextGaussian() + Util.randomCity(World.cityList).getCenter_y());
        }
    }
}
