package gameobjects;

import java.awt.image.BufferedImage;

/**
 * 移动物体类 MovingObject
 * 1. 公有变量
 * 图片来源：image
 * 图片宽度：width
 * 图片高度：height
 * 物体位置：x
 * 物体位置：y
 * 生命值：life 植物、僵尸均具有生命值，因为植物可被啃食、僵尸可被子弹攻击
 * 单步移动步数：xStep和yStep
 * 2. 公有方法
 * 一般方法： 	   移动方法step()
 * 一般方法： 	   出界判断方法outOfBounds()
 * 一般方法： 	   获取当前生命值方法getCurrentLife()
 * 一般方法： 	   生命值减1方法removeOneLife()
 * for 僵尸： 	   被子弹击中方法hitByBullet()
 * for 僵尸： 	   被炸毁方法startBombing()
 * for 僵尸： 	   停止运动方法stopMoving()
 * for 僵尸： 	   恢复运动方法startMoving()
 * for 僵尸： 	   开始攻击方法startAttacking()
 * for 僵尸： 	   停止攻击方法stopAttacking()
 *
 * @author LeoHao
 */
public abstract class BaseMovingObject {
    public BufferedImage image;
    public int width;
    public int height;
    public int x, y;
    /**
     * 生命值(植物、僵尸等物体均具有生命值)
     */
    public int life;
    /**
     * 一次横向移动步数
     */
    protected int xStep = 1;
    /**
     * 一次横向移动步数（部分物体）
     */
    int yStep = 1;

    /**
     * 物体移动方法
     */
    public abstract void step();

    /**
     * 判断出界方法
     *
     * @return boolean 是否出界
     */
    public abstract boolean outOfBounds();

    /**
     * 判断当前对象（僵尸）是否被豌豆子弹bt击中
     *
     * @param bt 豌豆子弹
     * @return boolean 是否击中
     */
    public boolean hitByBullet(BeanBullet bt) {
        // 注意：为了防止僵尸倒下死亡后仍被豌豆子弹射击，这里需要规定this.life>0
        return this.life > 0 && bt.x >= this.x && bt.x + bt.width <= this.x + this.width && bt.y >= this.y
                && bt.y + bt.height <= this.y + this.height;
    }

    /**
     * 停止运动
     */
    public void stopMoving() {
        xStep = 0;
    }

    /**
     * 恢复运动
     */
    public void startMoving() {
        xStep = 1;
    }

    /**
     * 生命值减1
     */
    public void removeOneLife() {
        this.life--;
    }

    /**
     * 变换为攻击状态
     */
    public void startAttacking() {
    }

    /**
     * 变换为普通状态
     */
    public void stopAttacking() {
    }

    /**
     * 获取当前生命值
     *
     * @return 生命值
     */
    public int getCurrentLife() {
        return this.life;
    }

    /**
     * 僵尸被炸毁方法
     */
    public void startBombing() {
    }
}