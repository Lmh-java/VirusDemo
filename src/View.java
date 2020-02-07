import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class View extends Thread {

    private JFrame jf = null;
    private JPanel jp = null;
    private Graphics g = null;

    public static int dayNum = 0;

    private boolean isStop = false;

    public View() {

        jp = new JPanel();
        jp.setSize(1000, 1000);
        jp.setBackground(Color.black);
        //System.out.println("初始化jp\n" + g);

        jf = new JFrame();
        jf.setTitle("Virus Demo");
        jf.setVisible(true);
        jf.setSize(1000, 1000);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.add(jp);

        g = jp.getGraphics();
        //System.out.println(jp.getGraphics());

    }

    public void run() {
        while (!isStop) {
            System.out.println("正在执行");
            for (Person p : World.personList) {
                if (p.show() == null) {
                    World.personList.remove(p);
                    continue;
                }

                g.setColor(p.show());
                //g.drawLine((int) p.getPos_x(), (int) p.getPos_y(), (int) p.getPos_x()+5, (int) p.getPos_y()+5);
                g.fillOval((int) p.getPos_x(), (int) p.getPos_y(), 5, 5);
                p.aciton();
                //System.out.println("开始绘制人员");
            }

            try {
                this.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jp.update(g);
            dayNum++;
            g.setFont(new Font("微软雅黑", 0, 25));
            g.drawString("感染人数：" + Virus.infectionPersonNum, 30, 30);
            g.drawString("死亡人数：" + World.deadList.size(), 30, 60);
            g.drawString("天数：" + dayNum, 30, 90);
        }
    }
}
