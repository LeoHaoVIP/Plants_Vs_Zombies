package plants;


import java.awt.image.BufferedImage;

import gameobjects.BaseMovingObject;
import panels_and_resources.MainGamePanel;

/**
 * 爆炸樱桃
 *
 * @author LeoHao
 */
public class CherryBomb extends BaseMovingObject {
    /**
     * 图片集
     */
    private BufferedImage[] images;
    /**
     * 图片转换值
     */
    private int index;
    private boolean hitByZombie;
    private boolean bombed;
    private int indexStep;

    /**
     * 根据玩家拖动的位置构造植物
     *
     * @param dragX 用户鼠标位置x
     * @param dragY 用户鼠标位置y
     */
    public CherryBomb(int dragX, int dragY) {
        // 公共属性初始化
        // 初始化图片为图片集第一张图片
        image = MainGamePanel.img_cherryBombs[0];
        width = image.getWidth();
        height = image.getHeight();
        x = dragX;
        y = dragY;
        //life>0即可
        life = 100;
        // 私有属性初始化
        images = MainGamePanel.img_cherryBombs;
        index = 0;
        hitByZombie = false;
        bombed = false;
        indexStep = 1;
    }

    /**
     * 判断樱桃是否被僵尸碰撞
     *
     * @param zombie 僵尸对象
     * @return 是否碰撞
     */
    public boolean hitByZombie(BaseMovingObject zombie) {
        // 注意此时碰撞条件
        hitByZombie = this.life > 0 && this.x - 20 > zombie.x && zombie.x + zombie.width > this.x + width;
        return hitByZombie;
    }

    /**
     * 樱桃爆炸方法
     */
    public void bomb() {
        if (!bombed) {
            index = 0;
            indexStep = 0;
            this.images = new BufferedImage[]{MainGamePanel.img_cherryBombBomb};
            // 设定樱桃生命值为0
            life = 0;
            bombed = true;
        }
    }

    @Override
    public void step() {
        // 修改坐标值
        this.index += indexStep;
        // 运动频率，每运动10次，更换英雄机图片
        int ix = this.index / 10 % this.images.length;
        this.image = this.images[ix];
    }

    @Override
    public boolean outOfBounds() {
        return false;
    }
}
