package plants;

import java.awt.image.BufferedImage;

import gameobjects.BaseMovingObject;
import panels_and_resources.MainGamePanel;

/**
 * 滚动坚果
 *
 * @author LeoHao
 */
public class WallNutRoll extends BaseMovingObject {
    /**
     * 图片集
     */
    private BufferedImage[] images;
    /**
     * 图片转换值
     */
    private int index;
    private boolean hitByZombie;

    /**
     * 根据玩家拖动的位置构造植物
     *
     * @param dragX 用户鼠标位置x
     * @param dragY 用户鼠标位置y
     */
    public WallNutRoll(int dragX, int dragY) {
        // 公共属性初始化
        // 初始化图片为图片集第一张图片
        image = MainGamePanel.img_wallNuts_roll[0];
        width = image.getWidth();
        height = image.getHeight();
        x = dragX;
        y = dragY;
        // 私有属性初始化
        images = MainGamePanel.img_wallNuts_roll;
        index = 0;
        xStep = 2;
        hitByZombie = false;
    }

    /**
     * 判断坚果是否被僵尸碰撞
     *
     * @param zombie 僵尸对象
     * @return 是否碰撞
     */
    public boolean hitByZombie(BaseMovingObject zombie) {
        // 注意此时碰撞条件
        hitByZombie = this.x - 20 > zombie.x && zombie.x + zombie.width > this.x + width;
        return hitByZombie;
    }

    @Override
    public void step() {
        // 修改坐标值
        this.x += xStep;
        this.index++;
        // 运动频率，每运动10次，更换英雄机图片
        int ix = this.index / 10 % this.images.length;
        this.image = this.images[ix];
    }

    @Override
    public boolean outOfBounds() {
        return x > 1400 - width;
    }
}
