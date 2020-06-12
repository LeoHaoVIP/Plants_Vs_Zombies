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
@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements Runnable, MouseMotionListener, MouseListener {
    /**
     * 1. 全局变量定义
     */
    static String userName;
    static String password;
    static BufferedImage img_background;// 登录界面背景
    static BufferedImage img_adventureMode;// 冒险模式
    static BufferedImage img_challengeMode;// 解密模式
    static BufferedImage img_survivalMode;// 生存模式
    static BufferedImage img_option;// 选项图片
    static BufferedImage img_help;// 帮助图片
    static BufferedImage img_quit;// 退出图片
    static BufferedImage img_woodSign1;// 欢迎语标签
    static BufferedImage img_woodSign2;// 存档检查标签
    static BufferedImage img_detailDialog;// 详情界面图片
    static JTextField input_userName;
    static JTextField input_password;
    public static boolean detailClicked;//标记是否选择详情查看
    static Font font = new Font("仿宋", Font.BOLD, 16);
    Color color = new Color(102, 153, 204);

    /**
     * 2. 静态代码块
     */
    static {
        try {
            userName = "LeoHao";
            password = "";
            detailClicked = false;
            img_background = ImageIO.read(MainGamePanel.class.getResource("img/background.png"));
            img_adventureMode = ImageIO
                    .read(MainGamePanel.class.getResource("img/SelectorScreen_Adventure_highlight.png"));
            img_challengeMode = ImageIO
                    .read(MainGamePanel.class.getResource("img/SelectorScreen_Challenges_button.png"));
            img_survivalMode = ImageIO
                    .read(MainGamePanel.class.getResource("img/SelectorScreen_Vasebreaker_button.png"));
            img_option = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_Options1.png"));
            img_help = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_Help1.png"));
            img_quit = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_Quit1.png"));
            img_woodSign1 = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_woodsign1.png"));
            img_woodSign2 = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_woodsign2.png"));
            img_detailDialog = ImageIO.read(MainGamePanel.class.getResource("img/loginPanel.png"));
            initLoginPanelComponents();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initLoginPanelComponents() {
        input_userName = new JTextField();
        input_password = new JTextField();
        input_userName.setBounds(320, 220, 370, 45);
        input_userName.setText("  LeoHao");
        input_userName.setFont(font);
        input_userName.setBackground(null);
        input_userName.setBorder(new MatteBorder(1, 1, 1, 1, new Color(153, 153, 153)));
        input_password.setBounds(320, 320, 370, 45);
        input_password.setText("  LeoHao");
        input_password.setFont(font);
        input_password.setBackground(null);
        input_password.setBorder(new MatteBorder(1, 1, 1, 1, new Color(153, 153, 153)));
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

    // 3.1 绘制背景图片
    private void paintBackgroundImg(Graphics g) {

        g.drawImage(img_background, 0, 0, null);
    }

    // 3.2 绘制图片按钮
    private void paintButtonImg(Graphics g) {

        g.drawImage(img_adventureMode, 480, 80, null);
        g.drawImage(img_challengeMode, 480, 200, null);
        g.drawImage(img_survivalMode, 480, 300, null);
        g.drawImage(img_option, 640, 490, null);
        g.drawImage(img_help, 730, 515, null);
        g.drawImage(img_quit, 800, 500, null);
        g.drawImage(img_woodSign1, 0, 0, null);
        g.drawImage(img_woodSign2, 0, 140, null);
    }

    // 3.3 绘制用户名
    private void paintUserName(Graphics g) {

        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g.drawString(userName, 100, 112);
    }

    // 3.4 绘制详情对话框
    private void paintDetailDialog(Graphics g) {
        if (detailClicked) {
            g.drawImage(img_detailDialog, getWidth() / 2 - img_detailDialog.getWidth() / 2,
                    getHeight() / 2 - img_detailDialog.getHeight() / 2, null);
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
            // System.out.println("MouseMoved!"+x+","+y);
        }
    }

    // 根据鼠标位置更新按钮图片
    public void updateButtonImage(int x, int y) {
        try {
            if (x > 480 && x < 480 + img_adventureMode.getWidth() && y > 80 && y < 80 + img_adventureMode.getHeight())
                img_adventureMode = ImageIO
                        .read(MainGamePanel.class.getResource("img/SelectorScreen_startAdventure_highlight.png"));
            else
                img_adventureMode = ImageIO
                        .read(MainGamePanel.class.getResource("img/SelectorScreen_Adventure_highlight.png"));
            if (x > 640 && x < 640 + img_option.getWidth() && y > 490 && y < 490 + img_option.getHeight())
                img_option = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_options2.png"));
            else
                img_option = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_options1.png"));
            if (x > 730 && x < 730 + img_help.getWidth() && y > 515 && y < 515 + img_help.getHeight())
                img_help = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_help2.png"));
            else
                img_help = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_help1.png"));
            if (x > 800 && x < 800 + img_quit.getWidth() && y > 500 && y < 500 + img_quit.getHeight())
                img_quit = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_quit2.png"));
            else
                img_quit = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_quit1.png"));
            if (x > 0 && x < 0 + img_woodSign2.getWidth() && y > 140 && y < 140 + img_woodSign2.getHeight())
                img_woodSign2 = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_woodsign2_press.png"));
            else
                img_woodSign2 = ImageIO.read(MainGamePanel.class.getResource("img/SelectorScreen_woodsign2.png"));
            if (x > 480 && x < 480 + img_challengeMode.getWidth() && y > 200 && y < 200 + img_challengeMode.getHeight())
                img_challengeMode = ImageIO
                        .read(MainGamePanel.class.getResource("img/SelectorScreen_survival_button.png"));
            else
                img_challengeMode = ImageIO
                        .read(MainGamePanel.class.getResource("img/SelectorScreen_Challenges_button.png"));
        } catch (Exception e) {
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
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        int x = e.getX();
        int y = e.getY();
        // 显示项目详情对话框
        if (x > 640 && x < 640 + img_option.getWidth() && y > 490 && y < 490 + img_option.getHeight())
            detailClicked = true;
        if (!(x > 145 && x < 735 && y > 78 && y < 515))
            detailClicked = false;
        // 点击退出按钮
        if (x > 800 && x < 800 + img_quit.getWidth() && y > 500 && y < 500 + img_quit.getHeight())
            System.exit(0);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}