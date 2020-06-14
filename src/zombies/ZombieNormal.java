package zombies;

import java.awt.image.BufferedImage;

import gameobjects.BaseMovingObject;
import panels_and_resources.MainGamePanel;

/**
 * 普通僵尸类
 *
 * @author LeoHao
 */
public class ZombieNormal extends BaseMovingObject {
    /**
     * 图片集
     */
    private BufferedImage[] images;
    /**
     * 图片转换值
     */
    private int index;
    /**
     * 初始生命值
     */
    private int life0;
    /**
     * 标记僵尸已被攻击为无头僵尸
     */
    private boolean beenLostHead;
    /**
     * 标记僵尸已死亡
     */
    private boolean beenDead;
    /**
     * 标记正在攻击
     */
    private boolean isAttacking;
    private int indexStep;
    /**
     * 标记是否已经爆炸
     */
    private boolean isBombing;

    public ZombieNormal() {
        // 公共属性初始化
        // 初始化图片为图片集第一张图片
        image = MainGamePanel.img_zombiesNormal[0];
        updateSize();
        // 僵尸生命值初始为10
        this.life = 10;
        // 随机生成僵尸的位置
        int y_up = 100;
        int y_down = 570;
        int rowsNum = 5;// 行数
        int row = (int) (Math.random() * rowsNum);
        // 网格高度
        int y_step = (y_down - y_up) / rowsNum;
        // 从最右面进入草坪
        x = 1400 - width / 2;
        y = y_up + (2 * row * y_step + y_step) / 2 - height;
        // 私有属性初始化
        images = MainGamePanel.img_zombiesNormal;
        index = 0;
        xStep = 1;
        life0 = this.life;
        beenDead = false;
        beenLostHead = false;
        indexStep = 1;
        isAttacking = false;
        isBombing = false;
    }

    private void updateSize() {
        width = image.getWidth();
        height = image.getHeight();
    }

    /**
     * 僵尸被炸毁方法
     */
    @Override
    public void startBombing() {
        if (!isBombing) {
            index = 0;
            xStep = 0;
            // 更换僵尸图片集为爆炸僵尸
            images = MainGamePanel.img_zombiesBomb;
            isBombing = true;
            life = 0;// 设置生命值为0
        }
    }

    /**
     * 变换为攻击状态
     */
    @Override
    public void startAttacking() {
        if (!isAttacking) {
            index = 0;
            if (beenLostHead) {
                images = MainGamePanel.img_zombiesAttackingLostHead;
            } else {
                images = MainGamePanel.img_zombiesAttacking;
            }
            isAttacking = true;
        }
    }

    /**
     * 变换为普通状态
     */
    @Override
    public void stopAttacking() {
        index = 0;
        if (beenLostHead)
            images = MainGamePanel.img_zombiesLostHead;
        else
            images = MainGamePanel.img_zombiesNormal;
        isAttacking = false;
    }

    @Override
    public void step() {
        // 修改坐标值
        this.x -= xStep;
        this.index += indexStep;
        // 根据生命值改变僵尸运动状态(注意：僵尸死亡到达最后一张图片时xStep应置为0，且index不再变化)
        // 爆炸时不用更换图片集
        if (!isBombing) {
            if (this.life <= 0) {
                if (!beenDead) {
                    this.index = 0;
                    xStep = 0;
                    this.images = MainGamePanel.img_zombiesDie;
                    beenDead = true;
                }
            } else if (this.life < 0.5 * life0) {
                if (!beenLostHead) {
                    this.index = 0;
                    images = MainGamePanel.img_zombiesLostHead;
                    beenLostHead = true;
                }
            }
        }
        //更新僵尸图片（动态图）
        updateZombieImage();
    }

    /**
     * 更新僵尸图片（动态图）
     */
    private void updateZombieImage() {
        // 运动频率，每运动10次，更换僵尸图片
        int ix = this.index / 10 % this.images.length;
        this.image = this.images[ix];
        updateSize();
        // 僵尸死亡且到达最后一张图片
        if (beenDead && ix == images.length - 1) {
            indexStep = 0;
            this.image = this.images[ix];
        }
        // 僵尸死亡且到达最后一张图片
        if (isBombing && ix == images.length - 1) {
            indexStep = 0;
            this.image = this.images[ix];
        }
    }

    @Override
    public boolean outOfBounds() {
        return this.x < 10;
    }
}
