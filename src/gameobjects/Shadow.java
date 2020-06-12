package gameobjects;

import panels_and_resources.MainGamePanel;

/**
 * 草地区域块阴影
 * 种植植物、铲除植物时标注待放置或铲除的位置
 *
 * @author LeoHao
 */
public class Shadow extends BaseMovingObject {
    public Shadow(int x, int y) {

        this.image = MainGamePanel.img_shadow;
        width = image.getWidth();
        height = image.getHeight();
        this.x = x;
        this.y = y;
    }

    @Override
    public void step() {
    }

    @Override
    public boolean outOfBounds() {
        return false;
    }

}
