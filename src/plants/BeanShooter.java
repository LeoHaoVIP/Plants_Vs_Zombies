package plants;

import java.awt.image.BufferedImage;

import gameobjects.BeanBullet;
import gameobjects.Base_MovingObject;
import panels_and_resources.MainGamePanel;

/**
 * 豌豆射手
 *
 * @author LeoHao
 */
public class BeanShooter extends Base_MovingObject {

    private BufferedImage[] images;// 图片集
    private int index;// 图片转换值
    private boolean hitByZombie;
    private boolean isIceMode;
    // 根据玩家拖动的位置构造植物

    public BeanShooter(int dragX, int dragY) {
        // 公共属性初始化
        image = MainGamePanel.img_beanShooters[0];// 初始化图片为图片集第一张图片
        width = image.getWidth();
        height = image.getHeight();
        x = dragX;
        y = dragY;
        life = 200;
        // 私有属性初始化
        images = MainGamePanel.img_beanShooters;
        index = 0;
        hitByZombie = false;
        isIceMode = false;
    }

    // 每次射出一颗豌豆子弹
    public BeanBullet shoot() {
        BeanBullet beanBullet = new BeanBullet(x + width - 20, y + height / 2 - 20);
        return beanBullet;
    }

    // 判断豌豆射手是否被僵尸碰撞
    public boolean hitByZombie(Base_MovingObject zombie) {
        // 注意此时碰撞条件
        if (this.life > 0 && this.x - 20 > zombie.x && zombie.x + zombie.width > this.x + width)
            hitByZombie = true;
        else
            hitByZombie = false;
        return hitByZombie;
    }

    // 设定豌豆射手为冷冻模式
    public void setIceMode() {
        if (!isIceMode) {
            this.images = MainGamePanel.img_beanShootersIce;
            index = 0;
            isIceMode = true;
        }
    }

    // 设定豌豆射手为普通模式
    public void setNormalMode() {
        if (isIceMode) {
            this.images = MainGamePanel.img_beanShooters;
            index = 0;
            isIceMode = false;
        }
    }

    @Override
    public void step() {

        // 修改坐标值
        this.index++;
        // 运动频率，每运动10次，更换英雄机图片
        int ix = this.index / 10 % this.images.length;
        this.image = this.images[ix];
    }

    @Override
    public boolean outOfBounds() {

        return false;
    }

}
