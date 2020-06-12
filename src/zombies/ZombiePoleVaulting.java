package zombies;

import java.awt.image.BufferedImage;

import gameobjects.Base_MovingObject;
import panels_and_resources.MainGamePanel;

/**
 * 撑杆僵尸类
 *
 * @author LeoHao
 */
public class ZombiePoleVaulting extends Base_MovingObject {
    private BufferedImage[] images;// 图片集
    private int index;// 图片转换值
    private int life0;// 初始生命值
    private boolean beenLostHead;// 标记僵尸已被攻击为无头僵尸
    private boolean beenDead;// 标记僵尸已死亡
    private boolean isAttacking;// 标记正在攻击
    private int indexStep;
    private boolean isBombing;// 标记是否已经爆炸
    // 根据玩家拖动的位置构造植物

    public ZombiePoleVaulting() {

        // 公共属性初始化
        image = MainGamePanel.img_zombiesPoleVaulting[0];// 初始化图片为图片集第一张图片
        updateSize();
        this.life = 10;// 僵尸生命值初始为10
        // 随机生成僵尸的位置
        int y_up = 100;
        int y_down = 570;
        int rowsNum = 5;// 行数
        int row = (int) (Math.random() * rowsNum);
        int y_step = (y_down - y_up) / rowsNum;// 网格高度
        x = 1410 - width / 2;// 从最右面进入草坪
        y = y_up + (2 * row * y_step + y_step) / 2 - height;
        // System.out.println("行号："+(row+1));
        // 私有属性初始化
        images = MainGamePanel.img_zombiesPoleVaulting;
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

    // 僵尸被炸毁方法
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

    // 变换为攻击状态
    @Override
    public void startAttacking() {
        if (!isAttacking) {
            index = 0;
            if (beenLostHead)
                images = MainGamePanel.img_zombiesPoleVaultingLostHeadAttack;
            else
                images = MainGamePanel.img_zombiesPoleVaultingAttack;
            isAttacking = true;
        }
    }

    // 变换为普通状态
    @Override
    public void stopAttacking() {
        index = 0;
        if (beenLostHead)
            images = MainGamePanel.img_zombiesPoleVaultingLostHead;
        else
            images = MainGamePanel.img_zombiesPoleVaulting;
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
                    this.images = MainGamePanel.img_zombiesPoleVaultingDie;
                    beenDead = true;
                }
            } else if (this.life < 0.5 * life0) {
                if (!beenLostHead) {
                    this.index = 0;
                    images = MainGamePanel.img_zombiesPoleVaultingLostHead;
                    beenLostHead = true;
                }
            }
        }
        // 运动频率，每运动10次，更换僵尸图片
        int ix = this.index / 10 % this.images.length;
        this.image = this.images[ix];
        updateSize();
        if (beenDead && ix == images.length - 1)// 僵尸死亡且到达最后一张图片
        {
            indexStep = 0;
            this.image = this.images[ix];
        }
        if (isBombing && ix == images.length - 1)// 僵尸死亡且到达最后一张图片
        {
            indexStep = 0;
            this.image = this.images[ix];
        }
    }

    @Override
    public boolean outOfBounds() {

        if (this.x < 10)
            return true;
        return false;
    }
}
