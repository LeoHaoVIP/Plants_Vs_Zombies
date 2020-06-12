package tinygames;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import panels_and_resources.MainGame;
import panels_and_resources.MainGamePanel;

@SuppressWarnings("serial")
public class TomCatPanel extends JPanel implements Runnable, MouseListener {
    /*
     * 变量声明
     */
    int actionCode = 4;// 初始化动作为eat
    int count = -1;
    int index = 0;
    BufferedImage img_background;
    int eatNum = 40, drinkNum = 81, angryNum = 26, cymbalNum = 13, fartNum = 28, footLeftNum = 30;
    int footRightNum = 30, knockoutNum = 81, pieNum = 24, scratchNum = 56, stomachNum = 33;
    int[] actionImageNum;
    // 背景图片数组
    String[] img_eatList, img_drinkList, img_angryList, img_cymbalList, img_fartList, img_footLeftList,
            img_footRightList;
    String[] img_knockoutList, img_pieList, img_scratchList, img_stomachList;
    BufferedImage img_eatButton, img_cymbalButton, img_drinkButton, img_fartButton, img_scratchButton, img_pieButton;
    // 植物大战僵尸图标
    static BufferedImage img_icon;

    /*
     * 构造方法
     */
    public TomCatPanel() {
        // TODO Auto-generated constructor stub
        init();
        try {
            img_background = ImageIO.read(TomCatPanel.class.getResource("Animations/eat/eat_00.jpg"));
            img_cymbalButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/cymbal.png"));
            img_drinkButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/drink.png"));
            img_fartButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/fart.png"));
            img_scratchButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/scratch.png"));
            img_eatButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/eat.png"));
            img_pieButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/pie.png"));
            img_icon = ImageIO.read(MainGamePanel.class.getResource("img/icon.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * 画笔方法
     */
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        // observer代表绘制在哪个对象上
        int interval = 70;
        int startPos = 350;
        g.drawImage(img_background, 0, 0, 512, 768, null);
        g.drawImage(img_eatButton, 50, startPos, 60, 60, null);// eat按钮图片
        g.drawImage(img_pieButton, 50, startPos + interval, 60, 60, null);// pie按钮图片
        g.drawImage(img_drinkButton, 50, startPos + 2 * interval, 60, 60, null);// drink按钮图片
        g.drawImage(img_cymbalButton, 400, startPos, 60, 60, null);// cymbal按钮图片
        g.drawImage(img_fartButton, 400, startPos + interval, 60, 60, null);// fart按钮图片
        g.drawImage(img_scratchButton, 400, startPos + 2 * interval, 60, 60, null);// scratch按钮图片

        // 植物大战僵尸图标
        g.drawImage(img_icon, 30, 30, null);
//		 g.fillRect(100, 130, 300,250);//KnockOut
        // g.fillRect(185, 680, 60, 50);//leftFoot
        // g.fillRect(260, 680, 60, 50);//rightFoot
        // g.fillRect(340, 580, 60, 110);//tail
    }

    /**
     * 1 angry 2 cymbal 3 drink 4 eat 5 fart 6 footLeft 7 footRight 8 knockout 9 pie
     * <p>
     * 10 scratch 11 stomach
     */
    @Override
    public void run() {
        // TODO Auto-generated method stub
        // 1.业务执行
        while (true) {
            changeView();// 更换视图
            // 2.线程睡眠
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 3.重绘方法
            repaint();
        }
    }

    // 根据动作码更换视图
    private void changeView() {
        // 根据ActionCode修改图片变化值
        index++;
        // 控制动画效果
        if (index >= count) {
            index = 0;
            count = -1;
        } else {
            // 根据下标值，修改图片内容
            try {
                switch (actionCode) {
                    case 1:
                        img_background = ImageIO.read(TomCatPanel.class.getResource(img_angryList[index]));
                        break;
                    case 2:
                        img_background = ImageIO.read(TomCatPanel.class.getResource(img_cymbalList[index]));
                        break;
                    case 3:
                        img_background = ImageIO.read(TomCatPanel.class.getResource(img_drinkList[index]));
                        break;
                    case 4:
                        img_background = ImageIO.read(TomCatPanel.class.getResource(img_eatList[index]));
                        break;
                    case 5:
                        img_background = ImageIO.read(TomCatPanel.class.getResource(img_fartList[index]));
                        break;
                    case 6:
                        img_background = ImageIO.read(TomCatPanel.class.getResource(img_footLeftList[index]));
                        break;
                    case 7:
                        img_background = ImageIO.read(TomCatPanel.class.getResource(img_footRightList[index]));
                        break;
                    case 8:
                        img_background = ImageIO.read(TomCatPanel.class.getResource(img_knockoutList[index]));
                        break;
                    case 9:
                        img_background = ImageIO.read(TomCatPanel.class.getResource(img_pieList[index]));
                        break;
                    case 10:
                        img_background = ImageIO.read(TomCatPanel.class.getResource(img_scratchList[index]));
                        break;
                    case 11:
                        img_background = ImageIO.read(TomCatPanel.class.getResource(img_stomachList[index]));
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        // 获取鼠标坐标值
        int mx = e.getX();
        int my = e.getY();
        int interval = 70;
        // 判断点击位置
        if (mx > 50 && mx < 50 + 60 && my > 350 && my < 350 + 60) {
            actionCode = 4;
            count = actionImageNum[actionCode - 1];
            System.out.println("eat!");
        }
        if (mx > 50 && mx < 50 + 60 && my > 350 + interval && my < 350 + interval + 60) {
            actionCode = 9;
            count = actionImageNum[actionCode - 1];
            System.out.println("pie!");
        }
        if (mx > 50 && mx < 50 + 60 && my > 350 + interval * 2 && my < 350 + interval * 2 + 60) {
            actionCode = 3;
            count = actionImageNum[actionCode - 1];
            System.out.println("drink!");
        }
        if (mx > 400 && mx < 400 + 60 && my > 350 && my < 350 + 60) {
            actionCode = 2;
            count = actionImageNum[actionCode - 1];
            System.out.println("cymbal!");
        }
        if (mx > 400 && mx < 400 + 60 && my > 350 + interval && my < 350 + interval + 60) {
            actionCode = 5;
            count = actionImageNum[actionCode - 1];
            System.out.println("fart!");
        }
        if (mx > 400 && mx < 400 + 60 && my > 350 + interval * 2 && my < 350 + interval * 2 + 60) {
            actionCode = 10;
            count = actionImageNum[actionCode - 1];
            System.out.println("scratch!");
        }
        if (mx > 180 && mx < 180 + 150 && my > 520 && my < 520 + 130) {
            actionCode = 11;
            count = actionImageNum[actionCode - 1];
            System.out.println("stomach!");
        }
        if (mx > 100 && mx < 100 + 300 && my > 130 && my < 130 + 250) {
            actionCode = 8;
            count = actionImageNum[actionCode - 1];
            System.out.println("knockout!");
        }
        if (mx > 185 && mx < 185 + 60 && my > 680 && my < 680 + 50) {
            actionCode = 7;
            count = actionImageNum[actionCode - 1];
            System.out.println("footRight!");
        }
        if (mx > 260 && mx < 260 + 60 && my > 680 && my < 680 + 50) {
            actionCode = 6;
            count = actionImageNum[actionCode - 1];
            System.out.println("footLeft!");
        }
        if (mx > 340 && mx < 340 + 60 && my > 580 && my < 580 + 110) {
            actionCode = 1;
            count = actionImageNum[actionCode - 1];
            System.out.println("angry-tail!");
        }
        //防止一个动作进行时，用户点击另一动作
        if (index < count)
            this.setFocusable(false);
        else this.setFocusable(true);
        if (mx > 30 && mx < 30 + img_icon.getWidth() && my > 30 && my < 30 + img_icon.getHeight()) {
            MainGame.cardLayout.show(MainGame.mainPanel, "login");
            MainGame.frame.setSize(900, 645);
            MainGame.frame.setLocationRelativeTo(null);//Panel居中
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    // 鼠标双击
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    // 鼠标退出
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    private void init() {
        eatNum = 40;
        drinkNum = 81;
        angryNum = 26;
        cymbalNum = 13;
        fartNum = 28;
        footLeftNum = 30;
        footRightNum = 30;
        knockoutNum = 81;
        pieNum = 24;
        scratchNum = 56;
        stomachNum = 33;
        /*********************************************************************/
        actionImageNum = new int[]{26, 13, 81, 40, 28, 30, 30, 81, 24, 56, 33};
        img_eatList = new String[eatNum];
        img_drinkList = new String[drinkNum];
        img_angryList = new String[angryNum];
        img_cymbalList = new String[cymbalNum];
        img_fartList = new String[fartNum];
        img_footLeftList = new String[footLeftNum];
        img_footRightList = new String[footRightNum];
        img_knockoutList = new String[knockoutNum];
        img_pieList = new String[pieNum];
        img_scratchList = new String[scratchNum];
        img_stomachList = new String[stomachNum];
        // 初始化eat动作背景path数组
        for (int i = 0; i < eatNum; i++) {
            if (i < 10)
                img_eatList[i] = "Animations/eat/eat_0" + i + ".jpg";
            else
                img_eatList[i] = "Animations/eat/eat_" + i + ".jpg";
        }
        // 初始化drink动作背景path数组
        for (int i = 0; i < drinkNum; i++) {
            if (i < 10)
                img_drinkList[i] = "Animations/drink/drink_0" + i + ".jpg";
            else
                img_drinkList[i] = "Animations/drink/drink_" + i + ".jpg";
        }
        // 初始化angry动作背景path数组
        for (int i = 0; i < angryNum; i++) {
            if (i < 10)
                img_angryList[i] = "Animations/angry/angry_0" + i + ".jpg";
            else
                img_angryList[i] = "Animations/angry/angry_" + i + ".jpg";
        }
        // 初始化cymbal动作背景path数组
        for (int i = 0; i < cymbalNum; i++) {
            if (i < 10)
                img_cymbalList[i] = "Animations/cymbal/cymbal_0" + i + ".jpg";
            else
                img_cymbalList[i] = "Animations/cymbal/cymbal_" + i + ".jpg";
        }
        // 初始化fart动作背景path数组
        for (int i = 0; i < fartNum; i++) {
            if (i < 10)
                img_fartList[i] = "Animations/fart/fart_0" + i + ".jpg";
            else
                img_fartList[i] = "Animations/fart/fart_" + i + ".jpg";
        }
        // 初始化footLeft动作背景path数组
        for (int i = 0; i < footLeftNum; i++) {
            if (i < 10)
                img_footLeftList[i] = "Animations/footLeft/footLeft_0" + i + ".jpg";
            else
                img_footLeftList[i] = "Animations/footLeft/footLeft_" + i + ".jpg";
        }
        // 初始化footRight动作背景path数组
        for (int i = 0; i < footRightNum; i++) {
            if (i < 10)
                img_footRightList[i] = "Animations/footRight/footRight_0" + i + ".jpg";
            else
                img_footRightList[i] = "Animations/footRight/footRight_" + i + ".jpg";
        }
        // 初始化knockout动作背景path数组
        for (int i = 0; i < knockoutNum; i++) {
            if (i < 10)
                img_knockoutList[i] = "Animations/knockout/knockout_0" + i + ".jpg";
            else
                img_knockoutList[i] = "Animations/knockout/knockout_" + i + ".jpg";
        }
        // 初始化pie动作背景path数组
        for (int i = 0; i < pieNum; i++) {
            if (i < 10)
                img_pieList[i] = "Animations/pie/pie_0" + i + ".jpg";
            else
                img_pieList[i] = "Animations/pie/pie_" + i + ".jpg";
        }
        // 初始化scratch动作背景path数组
        for (int i = 0; i < scratchNum; i++) {
            if (i < 10)
                img_scratchList[i] = "Animations/scratch/scratch_0" + i + ".jpg";
            else
                img_scratchList[i] = "Animations/scratch/scratch_" + i + ".jpg";
        }
        // 初始化stomach动作背景path数组
        for (int i = 0; i < stomachNum; i++) {
            if (i < 10)
                img_stomachList[i] = "Animations/stomach/stomach_0" + i + ".jpg";
            else
                img_stomachList[i] = "Animations/stomach/stomach_" + i + ".jpg";
        }
        /*********************************************************************/
        eatNum = -1;
        drinkNum = -1;
        angryNum = -1;
        cymbalNum = -1;
        fartNum = -1;
        footLeftNum = -1;
        footRightNum = -1;
        knockoutNum = -1;
        pieNum = -1;
        scratchNum = -1;
        stomachNum = -1;

    }
}
