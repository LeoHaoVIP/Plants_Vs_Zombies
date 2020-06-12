package panels_and_resources;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import audioplaytools.AudioPlayHelper;
import tinygames.TomCatPanel;

/**
 * 游戏主框架：Panel管理
 * 由LoginPanel、TomCatPanel、MainGamePanel组成
 * 默认显示LoginPanel
 *
 * @author LeoHao
 */
public class MainGame extends JFrame {
    /**
     * 主页面
     */
    public static JPanel mainPanel;
    /**
     * 主游戏界面
     */
    private static MainGamePanel gamePanel;
    /**
     * 小游戏——TomCat游戏界面
     */
    private static TomCatPanel tomCatPanel;


    private static BufferedImage imgIcon;
    /**
     * 卡片布局管理器定义
     */
    public static CardLayout cardLayout;
    public static MainGame frame;

    private MainGame(String title) {
        super.setTitle(title);
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
    }

    public static void main(String[] args) {
        try {
            imgIcon = ImageIO.read(MainGamePanel.class.getResource("img/icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame = new MainGame("植物大战僵尸 (人类命运共同体小组)");
        frame.setIconImage(imgIcon);
        // 设置布局管理器
        mainPanel.setLayout(cardLayout);
        frame.setSize(900, 645);
        // 登录界面
        LoginPanel loginPanel;
        {
            loginPanel = new LoginPanel();
            loginPanel.addMouseMotionListener(loginPanel);
            loginPanel.setFocusable(true);
            // 为loginPanel同时添加鼠标点击事件
            loginPanel.addMouseListener(loginPanel);
            loginPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 鼠标点击事件：进入游戏主界面
                    int x = e.getX();
                    int y = e.getY();
                    // 注意此时要保证LoginPanel未点击详情对话框
                    if (!LoginPanel.detailClicked && x > 480 && x < 480 + LoginPanel.imgAdventureMode.getWidth() && y > 80
                            && y < 80 + LoginPanel.imgAdventureMode.getHeight()) {
                        frame.setSize(1400, 645);
                        frame.setLocationRelativeTo(null);
                        cardLayout.show(mainPanel, "maingame");
                        AudioPlayHelper.stop();
                        AudioPlayHelper.open(MainGamePanel.class.getResource("audio/audio_game.wav"));
                        AudioPlayHelper.play();
                        // 待界面显示完毕时，执行线程
                        Thread thread = new Thread(gamePanel);
                        thread.start();
                    }
                    if (!LoginPanel.detailClicked && x > 480 && x < 480 + LoginPanel.imgChallengeMode.getWidth() && y > 200
                            && y < 200 + LoginPanel.imgChallengeMode.getHeight()) {
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
        // 添加登录界面
        mainPanel.add("login", loginPanel);
        // 添加TomCat游戏界面
        mainPanel.add("tomcat", tomCatPanel);
        // 添加主游戏界面
        mainPanel.add("maingame", gamePanel);
        frame.add(mainPanel);
        cardLayout.show(mainPanel, "login");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        AudioPlayHelper.open(MainGamePanel.class.getResource("audio/audio_login.wav"));
        AudioPlayHelper.play();
    }
}
