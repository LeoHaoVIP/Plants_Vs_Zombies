package gameobjects;

import panels_and_resources.MainGamePanel;

public class Shovel extends Base_MovingObject {
    public Shovel(int x, int y) {

        this.image = MainGamePanel.img_shovel;
        width = image.getWidth();
        height = image.getHeight();
        this.x = x;
        this.y = y;
    }

    // 恢复铁铲位置到铲槽处
    public void resetPositionToBank() {
        this.x = 465;
        this.y = 10;
    }

    @Override
    public void step() {

    }

    @Override
    public boolean outOfBounds() {

        return false;
    }

    // 更新位置
    public void updatePosition(int x, int y) {

        this.x = x;
        this.y = y;
    }
}
