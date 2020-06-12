package gameobjects;

import java.awt.image.BufferedImage;

/**
 * 植物选择卡片类
 * 方便放置卡片的位置
 * 随当前能量值更新各类植物卡片的状态
 *
 * @author LeoHao
 */
public class Card {
    public BufferedImage image;
    public int width;
    public int height;
    public int x, y;

    Card(BufferedImage image, int x, int y) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        this.x = x;
        this.y = y;
    }
}
