package gameobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import panels_and_resources.MainGamePanel;

/**
 * 太阳池卡槽：由多类植物卡片组成
 *
 * @author LeoHao
 */
public class SunBank {
    public BufferedImage image;// 卡槽图片
    BufferedImage[] img_sunFlower;
    BufferedImage[] img_beanShooter;
    BufferedImage[] img_wallNut;
    BufferedImage[] img_cherryBomb;
    BufferedImage[] img_potatoMine;
    BufferedImage[] img_wallNutRoll;
    public static Card[] cards = new Card[6];
    public static int cardInterval;
    public int width;
    public int height;
    public int x;
    public int y;
    private BufferedImage[] images;
    private String rootPath;
    private String[] images_path;

    public SunBank() {
        init();
        width = image.getWidth();
        height = image.getHeight();
        x = 20;
        y = 5;
    }

    private void init() {
        image = MainGamePanel.img_sunBank;
        rootPath = "img/cards/";
        images_path = new String[]{"sunflower", "peashooter", "wallnut", "cherrybomb", "potatomine", "wallnutroll"};
        img_sunFlower = new BufferedImage[3];
        img_beanShooter = new BufferedImage[3];
        img_wallNut = new BufferedImage[3];
        img_cherryBomb = new BufferedImage[3];
        img_potatoMine = new BufferedImage[3];
        img_wallNutRoll = new BufferedImage[3];
        // 初始化各状态下的卡片图片集
        try {
            for (int i = 0; i < 3; i++) {
                img_sunFlower[i] = ImageIO
                        .read(MainGamePanel.class.getResource(rootPath + images_path[0] + i + ".png"));
                img_beanShooter[i] = ImageIO
                        .read(MainGamePanel.class.getResource(rootPath + images_path[1] + i + ".png"));
                img_wallNut[i] = ImageIO.
                        read(MainGamePanel.class.getResource(rootPath + images_path[2] + i + ".png"));
                img_cherryBomb[i] = ImageIO
                        .read(MainGamePanel.class.getResource(rootPath + images_path[3] + i + ".png"));
                img_potatoMine[i] = ImageIO
                        .read(MainGamePanel.class.getResource(rootPath + images_path[4] + i + ".png"));
                img_wallNutRoll[i] = ImageIO
                        .read(MainGamePanel.class.getResource(rootPath + images_path[5] + 0 + ".png"));
            }
        } catch (IOException e) {
        }
        images = new BufferedImage[]{img_sunFlower[0], img_beanShooter[0], img_wallNut[0], img_cherryBomb[0],
                img_potatoMine[0], img_wallNutRoll[0]};
        cardInterval = 10;// 卡片间距
        for (int i = 0; i < images.length; i++)
            cards[i] = new Card(images[i], 95 + i * (cardInterval + images[i].getWidth()), 12);// 创建每张卡片
    }

    // 根据当前能量值更改卡片灰度颜色
    public void updateSunBank() {
        // 依次遍历每类植物 (滚动坚果除外)
        for (int i = 0; i < images.length - 1; i++) {
            int currentSunEnergy = MainGamePanel.currentSunEnergy;// 获取当前太阳能量值
            int[] sunEnergy = MainGamePanel.sunEnergy;// 获取各类植物种植所需能量
            switch (i) {
                case 0:
                    if (currentSunEnergy < sunEnergy[i] * 0.5)
                        cards[i].image = img_sunFlower[2];
                    else if ((currentSunEnergy < sunEnergy[i]))
                        cards[i].image = img_sunFlower[1];
                    else
                        cards[i].image = img_sunFlower[0];
                    break;
                case 1:
                    if (currentSunEnergy < sunEnergy[i] * 0.5)
                        cards[i].image = img_beanShooter[2];
                    else if ((currentSunEnergy < sunEnergy[i]))
                        cards[i].image = img_beanShooter[1];
                    else
                        cards[i].image = img_beanShooter[0];

                    break;
                case 2:
                    if (currentSunEnergy < sunEnergy[i] * 0.5)
                        cards[i].image = img_wallNut[2];
                    else if ((currentSunEnergy < sunEnergy[i]))
                        cards[i].image = img_wallNut[1];
                    else
                        cards[i].image = img_wallNut[0];

                    break;
                case 3:
                    if (currentSunEnergy < sunEnergy[i] * 0.5)
                        cards[i].image = img_cherryBomb[2];
                    else if ((currentSunEnergy < sunEnergy[i]))
                        cards[i].image = img_cherryBomb[1];
                    else
                        cards[i].image = img_cherryBomb[0];

                    break;
                case 4:
                    if (currentSunEnergy < sunEnergy[i] * 0.5)
                        cards[i].image = img_potatoMine[2];
                    else if ((currentSunEnergy < sunEnergy[i]))
                        cards[i].image = img_potatoMine[1];
                    else
                        cards[i].image = img_potatoMine[0];

                    break;
                case 5:
                    if (currentSunEnergy < sunEnergy[i] * 0.5)
                        cards[i].image = img_wallNutRoll[2];
                    else if ((currentSunEnergy < sunEnergy[i]))
                        cards[i].image = img_wallNutRoll[1];
                    else
                        cards[i].image = img_wallNutRoll[0];

                    break;
                default:
                    break;
            }
        }
    }
}
