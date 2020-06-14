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

/**
 * @author LeoHao
 */
public class TomCatPanel extends JPanel implements Runnable, MouseListener {
    /**
     * 变量声明
     * 初始化动作为eat
     */
    private int actionCode = 4;
    private int count = -1;
    private int index = 0;
    private BufferedImage imgBackground;
    private int[] actionImageNum;
    /**
     * 背景图片数组
     */
    private String[] imgEatList, imgDrinkList, imgAngryList, imgCymbalList, imgFartList, imgFootLeftList,
            imgFootRightList;
    private String[] imgKnockoutList, imgPieList, imgScratchList, imgStomachList;
    private BufferedImage imgEatButton, imgCymbalButton, imgDrinkButton, imgFartButton, imgScratchButton, imgPieButton;
    /**
     * 植物大战僵尸图标
     */
    private static BufferedImage imgIcon;

    /**
     * 构造方法
     */
    public TomCatPanel() {
        init();
        try {
            imgBackground = ImageIO.read(TomCatPanel.class.getResource("Animations/eat/eat_00.jpg"));
            imgCymbalButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/cymbal.png"));
            imgDrinkButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/drink.png"));
            imgFartButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/fart.png"));
            imgScratchButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/scratch.png"));
            imgEatButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/eat.png"));
            imgPieButton = ImageIO.read(TomCatPanel.class.getResource("Buttons/pie.png"));
            imgIcon = ImageIO.read(MainGamePanel.class.getResource("img/icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 画笔方法
     *
     * @param g 画笔工具
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // observer代表绘制在哪个对象上
        int interval = 70;
        int startPos = 350;
        g.drawImage(imgBackground, 0, 0, 512, 768, null);
        // eat按钮图片
        g.drawImage(imgEatButton, 50, startPos, 60, 60, null);
        // pie按钮图片
        g.drawImage(imgPieButton, 50, startPos + interval, 60, 60, null);
        // drink按钮图片
        g.drawImage(imgDrinkButton, 50, startPos + 2 * interval, 60, 60, null);
        // cymbal按钮图片
        g.drawImage(imgCymbalButton, 400, startPos, 60, 60, null);
        // fart按钮图片
        g.drawImage(imgFartButton, 400, startPos + interval, 60, 60, null);
        // scratch按钮图片
        g.drawImage(imgScratchButton, 400, startPos + 2 * interval, 60, 60, null);
        // 植物大战僵尸图标
        g.drawImage(imgIcon, 30, 30, null);
    }

    /**
     * 1 angry 2 cymbal 3 drink 4 eat 5 fart 6 footLeft 7 footRight 8 knockout 9 pie
     * 10 scratch 11 stomach
     */
    @Override
    public void run() {
        // 1.业务执行
        while (true) {
            changeView();// 更换视图
            // 2.线程睡眠
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 3.重绘方法
            repaint();
        }
    }

    /**
     * 根据动作码更换视图
     */
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
                        imgBackground = ImageIO.read(TomCatPanel.class.getResource(imgAngryList[index]));
                        break;
                    case 2:
                        imgBackground = ImageIO.read(TomCatPanel.class.getResource(imgCymbalList[index]));
                        break;
                    case 3:
                        imgBackground = ImageIO.read(TomCatPanel.class.getResource(imgDrinkList[index]));
                        break;
                    case 4:
                        imgBackground = ImageIO.read(TomCatPanel.class.getResource(imgEatList[index]));
                        break;
                    case 5:
                        imgBackground = ImageIO.read(TomCatPanel.class.getResource(imgFartList[index]));
                        break;
                    case 6:
                        imgBackground = ImageIO.read(TomCatPanel.class.getResource(imgFootLeftList[index]));
                        break;
                    case 7:
                        imgBackground = ImageIO.read(TomCatPanel.class.getResource(imgFootRightList[index]));
                        break;
                    case 8:
                        imgBackground = ImageIO.read(TomCatPanel.class.getResource(imgKnockoutList[index]));
                        break;
                    case 9:
                        imgBackground = ImageIO.read(TomCatPanel.class.getResource(imgPieList[index]));
                        break;
                    case 10:
                        imgBackground = ImageIO.read(TomCatPanel.class.getResource(imgScratchList[index]));
                        break;
                    case 11:
                        imgBackground = ImageIO.read(TomCatPanel.class.getResource(imgStomachList[index]));
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
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
        if (index < count) {
            this.setFocusable(false);
        } else {
            this.setFocusable(true);
        }
        if (mx > 30 && mx < 30 + imgIcon.getWidth() && my > 30 && my < 30 + imgIcon.getHeight()) {
            MainGame.cardLayout.show(MainGame.mainPanel, "login");
            MainGame.frame.setSize(900, 645);
            //Panel居中
            MainGame.frame.setLocationRelativeTo(null);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void init() {
        int eatNum = 40, drinkNum = 81, angryNum = 26, cymbalNum = 13, fartNum = 28, footLeftNum = 30;
        int footRightNum = 30, knockoutNum = 81, pieNum = 24, scratchNum = 56, stomachNum = 33;
        actionImageNum = new int[]{26, 13, 81, 40, 28, 30, 30, 81, 24, 56, 33};
        imgEatList = new String[eatNum];
        imgDrinkList = new String[drinkNum];
        imgAngryList = new String[angryNum];
        imgCymbalList = new String[cymbalNum];
        imgFartList = new String[fartNum];
        imgFootLeftList = new String[footLeftNum];
        imgFootRightList = new String[footRightNum];
        imgKnockoutList = new String[knockoutNum];
        imgPieList = new String[pieNum];
        imgScratchList = new String[scratchNum];
        imgStomachList = new String[stomachNum];
        // 初始化eat动作背景path数组
        for (int i = 0; i < eatNum; i++) {
            if (i < 10) {
                imgEatList[i] = "Animations/eat/eat_0" + i + ".jpg";
            } else {
                imgEatList[i] = "Animations/eat/eat_" + i + ".jpg";
            }
        }
        // 初始化drink动作背景path数组
        for (int i = 0; i < drinkNum; i++) {
            if (i < 10) {
                imgDrinkList[i] = "Animations/drink/drink_0" + i + ".jpg";
            } else {
                imgDrinkList[i] = "Animations/drink/drink_" + i + ".jpg";
            }
        }
        // 初始化angry动作背景path数组
        for (int i = 0; i < angryNum; i++) {
            if (i < 10) {
                imgAngryList[i] = "Animations/angry/angry_0" + i + ".jpg";
            } else {
                imgAngryList[i] = "Animations/angry/angry_" + i + ".jpg";
            }
        }
        // 初始化cymbal动作背景path数组
        for (int i = 0; i < cymbalNum; i++) {
            if (i < 10) {
                imgCymbalList[i] = "Animations/cymbal/cymbal_0" + i + ".jpg";
            } else {
                imgCymbalList[i] = "Animations/cymbal/cymbal_" + i + ".jpg";
            }
        }
        // 初始化fart动作背景path数组
        for (int i = 0; i < fartNum; i++) {
            if (i < 10) {
                imgFartList[i] = "Animations/fart/fart_0" + i + ".jpg";
            } else {
                imgFartList[i] = "Animations/fart/fart_" + i + ".jpg";
            }
        }
        // 初始化footLeft动作背景path数组
        for (int i = 0; i < footLeftNum; i++) {
            if (i < 10) {
                imgFootLeftList[i] = "Animations/footLeft/footLeft_0" + i + ".jpg";
            } else {
                imgFootLeftList[i] = "Animations/footLeft/footLeft_" + i + ".jpg";
            }
        }
        // 初始化footRight动作背景path数组
        for (int i = 0; i < footRightNum; i++) {
            if (i < 10) {
                imgFootRightList[i] = "Animations/footRight/footRight_0" + i + ".jpg";
            } else {
                imgFootRightList[i] = "Animations/footRight/footRight_" + i + ".jpg";
            }
        }
        // 初始化knockout动作背景path数组
        for (int i = 0; i < knockoutNum; i++) {
            if (i < 10) {
                imgKnockoutList[i] = "Animations/knockout/knockout_0" + i + ".jpg";
            } else {
                imgKnockoutList[i] = "Animations/knockout/knockout_" + i + ".jpg";
            }
        }
        // 初始化pie动作背景path数组
        for (int i = 0; i < pieNum; i++) {
            if (i < 10) {
                imgPieList[i] = "Animations/pie/pie_0" + i + ".jpg";
            } else {
                imgPieList[i] = "Animations/pie/pie_" + i + ".jpg";
            }
        }
        // 初始化scratch动作背景path数组
        for (int i = 0; i < scratchNum; i++) {
            if (i < 10) {
                imgScratchList[i] = "Animations/scratch/scratch_0" + i + ".jpg";
            } else {
                imgScratchList[i] = "Animations/scratch/scratch_" + i + ".jpg";
            }
        }
        // 初始化stomach动作背景path数组
        for (int i = 0; i < stomachNum; i++) {
            if (i < 10) {
                imgStomachList[i] = "Animations/stomach/stomach_0" + i + ".jpg";
            } else {
                imgStomachList[i] = "Animations/stomach/stomach_" + i + ".jpg";
            }
        }
    }
}
