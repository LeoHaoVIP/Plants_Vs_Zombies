package gameobjects;

import java.awt.image.BufferedImage;

/**
 * 待种植的植物blur显示
 * 设置该类的目的是为了方便待种植植物的移动
 *
 * @author LeoHao
 */
public class ToBePlanted extends BaseMovingObject {
    /**
     * 待种植植物编号
     */
    public int plantIndex;

    public ToBePlanted(BufferedImage image, int plantIndex, int x, int y) {

        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        this.x = x;
        this.y = y;
        this.plantIndex = plantIndex;
    }

    @Override
    public void step() {
    }

    @Override
    public boolean outOfBounds() {
        return false;
    }

    /**
     * 更新位置
     *
     * @param x 横坐标
     * @param y 纵坐标
     */
    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
