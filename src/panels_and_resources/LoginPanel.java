package panels_and_resources;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

/**
 * 登录界面
 *
 * @author LeoHao
 * 1. 添加背景图片
 * 2. 添加操作按钮图片
 * 3. 点击指定区域进入游戏界面
 */
public class LoginPanel extends JPanel implements Runnable, MouseMotionListener, MouseListener {
    /**
     * 1. 全局变量定义
     */
    private static String userName;
    /**
     * 登录界面背景
     */
    private static BufferedImage imgBackground;
    /**
     * 冒险模式
     */
    static BufferedImage imgAdventureMode;
    /**
     * 解密模式
     */
    static BufferedImage imgChallengeMode;
    /**
     * 生存模式
     */
    private static BufferedImage imgSurvivalMode;
    /**
     * 选项图片
     */
    private static BufferedImage imgOption;
    /**
     * 帮助图片
     */
    private static BufferedImage imgHelp;
    /**
     * 退出图片
     */
    private static BufferedImage imgQuit;
    /**
     * 欢迎语标签
     */
    private static BufferedImage imgWoodSign1;
    /**
     * 存档检查标签
     */
    private static BufferedImage imgWoodSign2;
    /**
     * 详情界面图片
     */
    private static BufferedImage imgDetailDialog;
    /**
     * 标记是否选择详情查看
     */
    static boolean detailClicked;
    private static Font font = new Font("仿宋", Font.BOLD, 16);

    static {
        try {
            userName = "LeoHao";
            detailClicked = false;
            imgBackground = ImageIO.read(MainGamePanel.class.getResource("img/background.png"));
            imgAdventureMode = ImageIO
                    .read(MainGamePanel.class.getResource("img/SelectorScreen_Adventure_highlight.png"));
            imgChallengeMode = ImageIO
                    .read(MainGamePanel.class.getResource("img/SelectorScreen_Challenges_button.png"));
            imgSurvivalMode = ImageIO
                    .read(MainGamePanel.class.getResource("img/SelectorScreen_Vasebreaker_button.png"));
            imgOption = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_Options1.png"));
            imgHelp = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_Help1.png"));
            imgQuit = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_Quit1.png"));
            imgWoodSign1 = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_woodsign1.png"));
            imgWoodSign2 = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_woodsign2.png"));
            imgDetailDialog = ImageIO.read(MainGamePanel.class.getResource("img/loginPanel.png"));
            initLoginPanelComponents();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initLoginPanelComponents() {
        JTextField inputUserName = new JTextField();
        JTextField inputPassword = new JTextField();
        inputUserName.setBounds(320, 220, 370, 45);
        inputUserName.setText("  LeoHao");
        inputUserName.setFont(font);
        inputUserName.setBackground(null);
        inputUserName.setBorder(new MatteBorder(1, 1, 1, 1, new Color(153, 153, 153)));
        inputPassword.setBounds(320, 320, 370, 45);
        inputPassword.setText("  LeoHao");
        inputPassword.setFont(font);
        inputPassword.setBackground(null);
        inputPassword.setBorder(new MatteBorder(1, 1, 1, 1, new Color(153, 153, 153)));
    }

    /**
     * 3. 重绘方法
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 3.1 绘制背景图片
        paintBackgroundImg(g);
        // 3.2 绘制图片按钮
        paintButtonImg(g);
        // 3.3 绘制用户名
        paintUserName(g);
        // 3.4 绘制详情对话框
        paintDetailDialog(g);
    }

    /**
     * 3.1 绘制背景图片
     *
     * @param g 画笔工具
     */
    private void paintBackgroundImg(Graphics g) {

        g.drawImage(imgBackground, 0, 0, null);
    }

    /**
     * 3.2 绘制图片按钮
     *
     * @param g 画笔工具
     */
    private void paintButtonImg(Graphics g) {

        g.drawImage(imgAdventureMode, 480, 80, null);
        g.drawImage(imgChallengeMode, 480, 200, null);
        g.drawImage(imgSurvivalMode, 480, 300, null);
        g.drawImage(imgOption, 640, 490, null);
        g.drawImage(imgHelp, 730, 515, null);
        g.drawImage(imgQuit, 800, 500, null);
        g.drawImage(imgWoodSign1, 0, 0, null);
        g.drawImage(imgWoodSign2, 0, 140, null);
    }

    /**
     * 3.3 绘制用户名
     *
     * @param g 画笔工具
     */
    private void paintUserName(Graphics g) {

        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g.drawString(userName, 100, 112);
    }

    /**
     * 3.4 绘制详情对话框
     *
     * @param g 画笔工具
     */
    private void paintDetailDialog(Graphics g) {
        if (detailClicked) {
            g.drawImage(imgDetailDialog, getWidth() / 2 - imgDetailDialog.getWidth() / 2,
                    getHeight() / 2 - imgDetailDialog.getHeight() / 2, null);
        }
    }

    /**
     * 4. 业务逻辑处理区：鼠标事件监听和线程处理
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!detailClicked) {
            // 鼠标移动事件
            int x = e.getX();
            int y = e.getY();
            updateButtonImage(x, y);
        }
    }

    /**
     * 根据鼠标位置更新按钮图片
     *
     * @param x 横坐标
     * @param y 纵坐标
     */
    private void updateButtonImage(int x, int y) {
        try {
            if (x > 480 && x < 480 + imgAdventureMode.getWidth() && y > 80 && y < 80 + imgAdventureMode.getHeight()) {
                imgAdventureMode = ImageIO
                        .read(MainGamePanel.class.getResource("img/SelectorScreen_startAdventure_highlight.png"));
            } else {
                imgAdventureMode = ImageIO
                        .read(MainGamePanel.class.getResource("img/SelectorScreen_Adventure_highlight.png"));
            }
            if (x > 640 && x < 640 + imgOption.getWidth() && y > 490 && y < 490 + imgOption.getHeight()) {
                imgOption = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_options2.png"));
            } else {
                imgOption = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_options1.png"));
            }
            if (x > 730 && x < 730 + imgHelp.getWidth() && y > 515 && y < 515 + imgHelp.getHeight()) {
                imgHelp = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_help2.png"));
            } else {
                imgHelp = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_help1.png"));
            }
            if (x > 800 && x < 800 + imgQuit.getWidth() && y > 500 && y < 500 + imgQuit.getHeight()) {
                imgQuit = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_quit2.png"));
            } else {
                imgQuit = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_quit1.png"));
            }
            if (x > 0 && x < imgWoodSign2.getWidth() && y > 140 && y < 140 + imgWoodSign2.getHeight()) {
                imgWoodSign2 = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_woodsign2_press.png"));
            } else {
                imgWoodSign2 = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_woodsign2.png"));
            }
            if (x > 480 && x < 480 + imgChallengeMode.getWidth() && y > 200 && y < 200 + imgChallengeMode.getHeight()) {
                imgChallengeMode = ImageIO
                        .read(MainGamePanel.class.getResource("img/SelectorScreen_survival_button.png"));
            } else {
                imgChallengeMode = ImageIO
                        .read(MainGamePanel.class.getResource("img/SelectorScreen_Challenges_button.png"));
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        // 显示项目详情对话框
        if (x > 640 && x < 640 + imgOption.getWidth() && y > 490 && y < 490 + imgOption.getHeight()) {
            detailClicked = true;
        }
        if (!(x > 145 && x < 735 && y > 78 && y < 515)) {
            detailClicked = false;
        }
        // 点击退出按钮
        if (x > 800 && x < 800 + imgQuit.getWidth() && y > 500 && y < 500 + imgQuit.getHeight()) {
            System.exit(0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}