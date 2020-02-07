import java.util.ArrayList;

public class Hospital {
    private ArrayList<Bed> bedList = null;
    private int maxBedNums = 1000;

    private int pos_x;
    private int pos_y;

    public Hospital(int bedNums, int pos_x, int pos_y) {
        bedList = new ArrayList<>();
        maxBedNums = bedNums;

        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public boolean recieve(Person p) {
        if (bedList.size() - 1 < maxBedNums) {
            p.changeState(Person.HOSPITAL);
            bedList.add(new Bed(0, 0, p));
            return true;
        } else {
            return false;
        }
    }
}
