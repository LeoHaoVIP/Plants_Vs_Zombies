package gameobjects;

import panels_and_resources.MainGamePanel;

/**
 * 豌豆射手子弹类
 *
 * @author LeoHao
 */
public class BeanBullet extends Base_MovingObject {

    // 根据豌豆射手的位置生成
    public BeanBullet(int x, int y) {

        // 公共属性初始化
        image = MainGamePanel.img_beanBullet;// 初始化图片
        width = image.getWidth();
        height = image.getHeight();
        this.x = x - width / 2;
        this.y = y - height / 2;
        // 私有属性初始化
        xStep = 2;
    }

    // 更新击碎后的子弹图片
    public void updateHitImage() {
        image = MainGamePanel.img_beanBulletHit;
        width = image.getWidth();
        height = image.getHeight();
    }

    // 设定子弹为冷冻模式
    public void setIceMode() {
        this.image = MainGamePanel.img_beanBulletIce;
    }

    // 设定子弹为普通模式
    public void setNormalMode() {
        this.image = MainGamePanel.img_beanBullet;
    }

    @Override
    public void step() {

        // 修改坐标值
        this.x += xStep;
    }

    @Override
    public boolean outOfBounds() {

        if (this.x > 1200 - width)
            return true;
        return false;
    }
}
