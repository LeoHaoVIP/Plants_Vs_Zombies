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
    /**
     * 卡槽图片
     */
    public BufferedImage image;
    private BufferedImage[] imgSunFlower;
    private BufferedImage[] imgBeanShooter;
    private BufferedImage[] imgWallNut;
    private BufferedImage[] imgCherryBomb;
    private BufferedImage[] imgPotatoMine;
    private BufferedImage[] imgWallNutRoll;
    public static Card[] cards = new Card[6];
    public static int cardInterval;
    public int width;
    public int height;
    public int x;
    public int y;
    private BufferedImage[] images;

    public SunBank() {
        init();
        width = image.getWidth();
        height = image.getHeight();
        x = 20;
        y = 5;
    }

    private void init() {
        image = MainGamePanel.img_sunBank;
        String rootPath = "img/cards/";
        String[] imagesPath = new String[]{"sunflower", "peashooter", "wallnut", "cherrybomb", "potatomine", "wallnutroll"};
        imgSunFlower = new BufferedImage[3];
        imgBeanShooter = new BufferedImage[3];
        imgWallNut = new BufferedImage[3];
        imgCherryBomb = new BufferedImage[3];
        imgPotatoMine = new BufferedImage[3];
        imgWallNutRoll = new BufferedImage[3];
        // 初始化各状态下的卡片图片集
        try {
            for (int i = 0; i < 3; i++) {
                imgSunFlower[i] = ImageIO
                        .read(MainGamePanel.class.getResource(rootPath + imagesPath[0] + i + ".png"));
                imgBeanShooter[i] = ImageIO
                        .read(MainGamePanel.class.getResource(rootPath + imagesPath[1] + i + ".png"));
                imgWallNut[i] = ImageIO.
                        read(MainGamePanel.class.getResource(rootPath + imagesPath[2] + i + ".png"));
                imgCherryBomb[i] = ImageIO
                        .read(MainGamePanel.class.getResource(rootPath + imagesPath[3] + i + ".png"));
                imgPotatoMine[i] = ImageIO
                        .read(MainGamePanel.class.getResource(rootPath + imagesPath[4] + i + ".png"));
                imgWallNutRoll[i] = ImageIO
                        .read(MainGamePanel.class.getResource(rootPath + imagesPath[5] + 0 + ".png"));
            }
        } catch (IOException ignored) {
        }
        images = new BufferedImage[]{imgSunFlower[0], imgBeanShooter[0], imgWallNut[0], imgCherryBomb[0],
                imgPotatoMine[0], imgWallNutRoll[0]};
        // 卡片间距
        cardInterval = 10;
        for (int i = 0; i < images.length; i++) {
            // 创建每张卡片
            cards[i] = new Card(images[i], 95 + i * (cardInterval + images[i].getWidth()), 12);
        }
    }

    /**
     * 根据当前能量值更改卡片灰度颜色
     */
    public void updateSunBank() {
        // 依次遍历每类植物 (滚动坚果除外)
        for (int i = 0; i < images.length - 1; i++) {
            // 获取当前太阳能量值
            int currentSunEnergy = MainGamePanel.currentSunEnergy;
            // 获取各类植物种植所需能量
            int[] sunEnergy = MainGamePanel.sunEnergy;
            switch (i) {
                case 0:
                    if (currentSunEnergy < sunEnergy[i] * 0.5) {
                        cards[i].image = imgSunFlower[2];
                    } else if ((currentSunEnergy < sunEnergy[i])) {
                        cards[i].image = imgSunFlower[1];
                    } else {
                        cards[i].image = imgSunFlower[0];
                    }
                    break;
                case 1:
                    if (currentSunEnergy < sunEnergy[i] * 0.5) {
                        cards[i].image = imgBeanShooter[2];
                    } else if ((currentSunEnergy < sunEnergy[i])) {
                        cards[i].image = imgBeanShooter[1];
                    } else {
                        cards[i].image = imgBeanShooter[0];
                    }

                    break;
                case 2:
                    if (currentSunEnergy < sunEnergy[i] * 0.5) {
                        cards[i].image = imgWallNut[2];
                    } else if ((currentSunEnergy < sunEnergy[i])) {
                        cards[i].image = imgWallNut[1];
                    } else {
                        cards[i].image = imgWallNut[0];
                    }

                    break;
                case 3:
                    if (currentSunEnergy < sunEnergy[i] * 0.5) {
                        cards[i].image = imgCherryBomb[2];
                    } else if ((currentSunEnergy < sunEnergy[i])) {
                        cards[i].image = imgCherryBomb[1];
                    } else {
                        cards[i].image = imgCherryBomb[0];
                    }

                    break;
                case 4:
                    if (currentSunEnergy < sunEnergy[i] * 0.5) {
                        cards[i].image = imgPotatoMine[2];
                    } else if ((currentSunEnergy < sunEnergy[i])) {
                        cards[i].image = imgPotatoMine[1];
                    } else {
                        cards[i].image = imgPotatoMine[0];
                    }

                    break;
                case 5:
                    if (currentSunEnergy < sunEnergy[i] * 0.5) {
                        cards[i].image = imgWallNutRoll[2];
                    } else if ((currentSunEnergy < sunEnergy[i])) {
                        cards[i].image = imgWallNutRoll[1];
                    } else {
                        cards[i].image = imgWallNutRoll[0];
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
