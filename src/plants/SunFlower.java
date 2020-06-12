package plants;

import java.awt.image.BufferedImage;

import gameobjects.Base_MovingObject;
import gameobjects.Sun;
import panels_and_resources.MainGamePanel;

/**
 * 太阳花
 *
 * @author LeoHao
 */
public class SunFlower extends Base_MovingObject {
    private BufferedImage[] images;// 图片集
    private int index;// 图片转换值
    private boolean hitByZombie;
    private int produceSunTime;//生产太阳的时间
    private int produceSunTime0;
    // 根据玩家拖动的位置构造植物

    public SunFlower(int dragX, int dragY) {
        // 公共属性初始化
        image = MainGamePanel.img_sunFlowers[0];// 初始化图片为图片集第一张图片
        width = image.getWidth();
        height = image.getHeight();
        x = dragX;
        y = dragY;
        life = 200;
        // 私有属性初始化
        images = MainGamePanel.img_sunFlowers;
        index = 0;
        hitByZombie = false;
        produceSunTime = 3000;
        produceSunTime0 = this.produceSunTime;
    }

    // 判断太阳花是否被僵尸碰撞
    public boolean hitByZombie(Base_MovingObject zombie) {
        // 注意此时碰撞条件
        if (this.life > 0 && this.x - 20 > zombie.x && zombie.x + zombie.width > this.x + width)
            hitByZombie = true;
        else
            hitByZombie = false;
        return hitByZombie;
    }

    // 生成一个太阳的能量，返回一个Sun对象
    public Sun generateSun() {
        // 可采用返回Sun对象的方式
        Sun sun = null;
        if (produceSunTime <= 0) {
            sun = new Sun(x, y);
            produceSunTime = produceSunTime0;
        }
        return sun;
    }

    @Override
    public void step() {
        produceSunTime--;
        // 修改坐标值
        this.index++;
        // 运动频率，每运动10次，更换英雄机图片
        int ix = this.index / 9 % this.images.length;
        this.image = this.images[ix];
    }

    @Override
    public boolean outOfBounds() {

        return false;
    }
}
