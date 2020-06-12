package gameobjects;

import java.awt.image.BufferedImage;
import java.util.Random;

import panels_and_resources.MainGamePanel;

/**
 * @author LeoHao
 */
public class Sun extends BaseMovingObject {
    /**
     * 图片集
     */
    private BufferedImage[] images;
    /**
     * 图片转换值
     */
    private int index;
    /**
     * 记录该太阳是否被收集
     */
    public boolean collected;

    /**
     * 根据太阳花的位置生成太阳
     *
     * @param sunFlowerX 太阳花横坐标
     * @param sunFlowerY 太阳花纵坐标
     */
    public Sun(int sunFlowerX, int sunFlowerY) {

        // 公共属性初始化
        // 初始化图片为图片集第一张图片
        image = MainGamePanel.img_suns[0];
        width = image.getWidth();
        height = image.getHeight();
        // 在太阳花的周围随机位置生成太阳
        this.x = (sunFlowerX - 50) + new Random().nextInt(100);
        this.y = (sunFlowerY + 50) + new Random().nextInt(100);
        // 私有属性初始化
        new Random().nextInt();
        images = MainGamePanel.img_suns;
        index = 0;
        xStep = 4;
        yStep = 4;
        collected = false;
    }

    /**
     * 太阳被收集
     *
     * @param xClick 鼠标点击-横坐标
     * @param yClick 鼠标点击-纵坐标
     * @return 太阳是否被点击收集
     */
    public boolean isCollected(int xClick, int yClick) {
        collected = xClick >= x && xClick <= x + width && yClick >= y && yClick < y + height;
        return collected;
    }

    @Override
    public void step() {
        // 修改坐标值
        if (collected) {
            // 若被收集，则太阳朝着太阳槽的方向移动
            x -= xStep;
            y -= yStep;
        }
        this.index++;
        // 运动频率，每运动2次，更换太阳图片
        int ix = this.index / 2 % this.images.length;
        this.image = this.images[ix];
    }

    @Override
    public boolean outOfBounds() {

        if (x <= 70 || y < 40) {
            return true;
        }
        return false;
    }
}
