package gameobjects;

import java.awt.image.BufferedImage;

import panels_and_resources.MainGamePanel;

public class Sun extends Base_MovingObject {
    private BufferedImage[] images;// 图片集
    private int index;// 图片转换值
    public boolean collected;// 记录该太阳是否被收集

    // 根据太阳花的位置生成太阳
    public Sun(int x_sunFlower, int y_sunFlower) {

        // 公共属性初始化
        image = MainGamePanel.img_suns[0];// 初始化图片为图片集第一张图片
        width = image.getWidth();
        height = image.getHeight();
        // 在太阳花的周围随机位置生成太阳
        this.x = (x_sunFlower - 50) + (int) (Math.random() * 100);
        this.y = (y_sunFlower + 50) + (int) (Math.random() * 100);
        // 私有属性初始化
        images = MainGamePanel.img_suns;
        index = 0;
        xStep = 4;
        yStep = 4;
        collected = false;
    }

    // 太阳被收集
    public boolean isCollected(int xClick, int yClick) {
        if (xClick >= x && xClick <= x + width && yClick >= y && yClick < y + height)
            collected = true;
        else
            collected = false;
        return collected;
    }

    @Override
    public void step() {
        // 修改坐标值
        if (collected)// 若被收集，则太阳朝着太阳槽的方向移动
        {
            x -= xStep;
            y -= yStep;
            // System.out.println("x " + x + ", y " + y);
        }
        this.index++;
        // 运动频率，每运动2次，更换太阳图片
        int ix = this.index / 2 % this.images.length;
        this.image = this.images[ix];
    }

    @Override
    public boolean outOfBounds() {

        if (x <= 70 || y < 40)
            return true;
        return false;
    }
}
