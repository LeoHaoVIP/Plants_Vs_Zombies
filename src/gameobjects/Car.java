package gameobjects;

import panels_and_resources.MainGamePanel;

/**
 * 门前小车类
 * 具有攻击僵尸属性
 * 触发条件：与僵尸接触
 *
 * @author LeoHao
 */
public class Car extends Base_MovingObject {
    private boolean hitByZombie;

    public Car(int x, int y) {

        image = MainGamePanel.img_car;
        // 固定小车宽度和高度为70*70
        width = 70;
        height = 70;
        this.x = x;
        this.y = y;
        // 私有属性初始化
        xStep = 0;// 初始化xStep为0，只有当僵尸碰到小车时，设置xStep为4
        hitByZombie = false;// 初始化为未碰撞
    }

    // 判断小车是否被僵尸碰撞
    public boolean hitByZombie(Base_MovingObject zombie) {
        // 注意此时碰撞条件
        if (this.x - 20 > zombie.x && zombie.x + zombie.width > this.x + width)
            hitByZombie = true;
        else
            hitByZombie = false;
        return hitByZombie;
    }

    @Override
    public void step() {

        if (hitByZombie)
            xStep = 4;
        x += xStep;
    }

    @Override
    public boolean outOfBounds() {

        if (x > 1400 - width)
            return true;
        return false;
    }
}
