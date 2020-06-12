package plants;

import java.awt.image.BufferedImage;

import gameobjects.Base_MovingObject;
import panels_and_resources.MainGamePanel;

/**
 * 普通防御坚果
 *
 * @author LeoHao
 */
public class WallNut extends Base_MovingObject {
    private BufferedImage[] images;// 图片集
    private int index;// 图片转换值
    private boolean hitByZombie;
    private int indexStep;
    private int life0;// 初始生命值
    private boolean beenCracked1;// 啃食到达状态1
    private boolean beenCracked2;// 啃食到达状态2
    // 根据玩家拖动的位置构造植物

    public WallNut(int dragX, int dragY) {

        // 公共属性初始化
        image = MainGamePanel.img_wallNuts[0];// 初始化图片为图片集第一张图片
        width = image.getWidth();
        height = image.getHeight();
        x = dragX;
        y = dragY;
        this.life = 1000;
        // 私有属性初始化
        images = MainGamePanel.img_wallNuts;
        index = 0;
        hitByZombie = false;
        indexStep = 1;
        life0 = this.life;
        beenCracked1 = false;
        beenCracked2 = false;
    }

    // 判断坚果是否被僵尸碰撞
    public boolean hitByZombie(Base_MovingObject zombie) {
        // 注意此时碰撞条件
        if (this.life > 0 && this.x - 20 > zombie.x && zombie.x + zombie.width > this.x + width)
            hitByZombie = true;
        else
            hitByZombie = false;
        return hitByZombie;
    }

    @Override
    public void step() {

        // 修改坐标值
        this.index += indexStep;
        // 根据生命值改变坚果状态
        if (this.life <= 0.3 * life0) {
            if (!beenCracked1) {
                this.index = 0;
                xStep = 0;
                this.images = MainGamePanel.img_wallNutsCracked2;
                beenCracked1 = true;
            }
        } else if (this.life < 0.7 * life0) {
            if (!beenCracked2) {
                this.index = 0;
                this.images = MainGamePanel.img_wallNutsCracked1;
                beenCracked2 = true;
            }
        }
        // 运动频率，每运动10次，更换图片
        int ix = this.index / 10 % this.images.length;
        this.image = this.images[ix];
    }

    @Override
    public boolean outOfBounds() {

        return false;
    }
}
