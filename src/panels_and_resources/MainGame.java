package panels_and_resources;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import audioplaytools.audioPlayHelper;
import tinygames.TomCatPanel;

/**
 * 游戏主框架：Panel管理
 * 由LoginPanel、TomCatPanel、MainGamePanel组成
 * 默认显示LoginPanel
 *
 * @author LeoHao
 */
@SuppressWarnings("serial")
public class MainGame extends JFrame {
    public static JPanel mainPanel;// 主页面
    private static LoginPanel loginPanel;// 登录界面
    static MainGamePanel gamePanel;// 主游戏界面
    static TomCatPanel tomCatPanel;// 小游戏——TomCat游戏界面
    static BufferedImage img_icon;
    public static CardLayout cardLayout;//卡片布局管理器定义
    public static MainGame frame;

    private MainGame(String title) {
        super.setTitle(title);
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
    }

    public static void main(String[] args) {
        try {
            img_icon = ImageIO.read(MainGamePanel.class.getResource("img/icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame = new MainGame("植物大战僵尸 (人类命运共同体小组)");
        frame.setIconImage(img_icon);
        mainPanel.setLayout(cardLayout);// 设置布局管理器
        frame.setSize(900, 645);
        // 登录界面
        {
            loginPanel = new LoginPanel();
            loginPanel.addMouseMotionListener(loginPanel);
            loginPanel.setFocusable(true);
            // 为loginPanel同时添加鼠标点击事件
            loginPanel.addMouseListener(loginPanel);
            loginPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // System.out.println("mouseClicked!");
                    // 鼠标点击事件：进入游戏主界面
                    int x = e.getX();
                    int y = e.getY();
                    // 注意此时要保证LoginPanel未点击详情对话框
                    if (!LoginPanel.detailClicked && x > 480 && x < 480 + LoginPanel.img_adventureMode.getWidth() && y > 80
                            && y < 80 + LoginPanel.img_adventureMode.getHeight()) {
                        frame.setSize(1400, 645);
                        frame.setLocationRelativeTo(null);
                        cardLayout.show(mainPanel, "maingame");
                        audioPlayHelper.stop();
                        audioPlayHelper.open(MainGamePanel.class.getResource("audio/audio_game.wav"));
                        audioPlayHelper.play();
                        // 待界面显示完毕时，执行线程
                        Thread thread = new Thread(gamePanel);
                        thread.start();
                    }
                    if (!LoginPanel.detailClicked && x > 480 && x < 480 + LoginPanel.img_challengeMode.getWidth() && y > 200
                            && y < 200 + LoginPanel.img_challengeMode.getHeight()) {
                        frame.setSize(512, 768);
                        frame.setLocationRelativeTo(null);
                        cardLayout.show(mainPanel, "tomcat");
                        //添加线程关联
                        Thread thread = new Thread(tomCatPanel);
                        thread.start();
                    }
                }
            });
            Thread thread = new Thread(loginPanel);
            thread.start();
        }
        // 游戏界面
        {
            gamePanel = new MainGamePanel();
            gamePanel.addMouseListener(gamePanel);
            gamePanel.addMouseMotionListener(gamePanel);
            gamePanel.setFocusable(true);
        }
        // 小游戏——TomCat
        {
            tomCatPanel = new TomCatPanel();
            //添加鼠标监听事件
            tomCatPanel.addMouseListener(tomCatPanel);
            //焦点设置
            tomCatPanel.setFocusable(true);
        }
        mainPanel.add("login", loginPanel);// 添加登录界面
        mainPanel.add("tomcat", tomCatPanel);// 添加TomCat游戏界面
        mainPanel.add("maingame", gamePanel);// 添加主游戏界面
        frame.add(mainPanel);
        cardLayout.show(mainPanel, "login");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        audioPlayHelper.open(MainGamePanel.class.getResource("audio/audio_login.wav"));
        audioPlayHelper.play();
    }
}
