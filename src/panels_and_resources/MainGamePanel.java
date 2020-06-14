package panels_and_resources;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gameobjects.BeanBullet;
import gameobjects.Car;
import gameobjects.Card;
import gameobjects.BaseMovingObject;
import gameobjects.Shadow;
import gameobjects.Shovel;
import gameobjects.Sun;
import gameobjects.SunBank;
import gameobjects.ToBePlanted;
import plants.BeanShooter;
import plants.CherryBomb;
import plants.PotatoMine;
import plants.SunFlower;
import plants.WallNut;
import plants.WallNutRoll;
import zombies.ZombieFlag;
import zombies.ZombieNormal;
import zombies.ZombiePoleVaulting;

/**
 * @author LeoHao
 * * 可将豌豆射击作为汇报的一个模块，包括僵尸状态变换中遇到的问题等...
 * 1. 背景图片
 * 庭院背景、小车、太阳卡槽、铁锹池
 * 2. 创建抽象类MovingObject，一切可运动物体均继承自该类
 * 如小车car、各类植物、僵尸、豌豆子弹等
 * 3. 建立各植物类
 * 包括太阳花、普通豌豆射手(包括增强豌豆射手)、爆炸樱桃、土豆地雷、坚果(防御坚果和滚动坚果)
 * 特别注意坚果被啃食的操作
 * 4. 建立豌豆子弹类
 * 豌豆子弹类同样继承自MovingObject，可参考雷霆战机的子弹定义
 * 5. 建立太阳类
 * 太阳类具有xStep和yStep，且为动态图片显示，采用帧图像的处理方式
 * 6. 建立僵尸类
 * 僵尸类别较多
 * 7. 重点
 * 根据植物放置时点击的鼠标位置x和y，确定植物放置的区域块（位置标准化）
 * 8. 撞击方法HitAction()需要进行的操作
 * 若被击中，则僵尸生命值-1，不同类僵尸生命值不同。
 * 子弹击中僵尸时，要变换为击碎状态，且此时消除子弹
 * 僵尸生命值减少到一定值后，变换僵尸状态为lostHead
 * 僵尸生命值减少为0时，此时不去除该僵尸对象！而是更新僵尸图片为Die图片集 OK
 * 9. 选择植物种植区域的阴影显示 OK
 * 10.不同植物技能的实现
 * 注意：太阳花和豌豆射手同样具有防御功能，可被僵尸啃食，植物自身生命值减少 OK
 * 防御坚果：OK
 * 土豆地雷：OK
 * 樱桃炸弹：OK
 * 滚动坚果：OK
 * 门前小车：OK
 * 11. 植物的铲除 OK
 * 12. 植物卡片的冷冻效果、根据当前能量值购买植物 OK
 * 13. 选择植物、点击阳光、使用铁铲时规定指定区域不可点击 OK
 * 14. 给用户种植植物的时间，之后出现僵尸 OK
 * 15. 控制游戏难度 OK
 * 16. 用户进入游戏主界面后，显示：准备！安放！植物！OK
 * 17. 实现用户自定义游戏场景切换（白天+夜晚） OK
 * 18. 点击雪花按钮，将所有豌豆射手(图片集)更换为冷冻豌豆射手 ，再点击一次恢复 OK
 * 19. 各类僵尸各个状态下的图片大小重新裁剪，要与普通僵尸大小相似_20190506 OK
 * 20. 即将死亡(无头)僵尸复活的问题解决 OK
 * 21. 程序运行卡慢的问题解决 OK
 * 22. 主界面中添加其他小游戏 OK
 */
public class MainGamePanel extends JPanel implements Runnable, MouseListener, MouseMotionListener {
    /**
     * 1. 全局变量定义
     * 1.1 庭院和门前小车背景图片
     */
    private static BufferedImage img_yard;
    public static BufferedImage img_car;
    private static Car[] cars;
    /**
     * 1.2 待种植植物(阴影显示)
     */
    private static ToBePlanted nowToBePlanted;
    /**
     * 1.3 各类植物待种植图片集
     */
    private static BufferedImage[] imgsToBePlanted;
    /**
     * 1.4 太阳池卡槽
     */
    public static BufferedImage img_sunBank;
    private static SunBank sunBank;
    /**
     * 1.5 当前太阳池能量
     * 初始化为500
     */
    public static int currentSunEnergy = 500;
    /**
     * 1.6 太阳花sunFlower图片集
     */
    public static BufferedImage[] img_sunFlowers;
    /**
     * 1.7 太阳花数组
     * 动态数组(当用户种植时，数组元素增加1个)
     */
    private SunFlower[] sunFlowers = {};
    /**
     * 1.8 各类植物种植所需能量值数组
     * 太阳花、豌豆射手、防御坚果、爆炸樱桃、土豆地雷
     */
    public static int[] sunEnergy = {50, 100, 50, 150, 25, 0};
    /**
     * 1.9 豌豆射手beanShooter图片集
     */
    public static BufferedImage[] img_beanShooters;
    /**
     * 1.10 豌豆射手数组
     * 动态数组(当用户种植时，数组元素增加1个)
     */
    private BeanShooter[] beanShooters = {};
    /**
     * 1.11 防御坚果图片集
     */
    public static BufferedImage[] img_wallNuts;
    /**
     * 1.12 防御坚果数组
     * 动态数组(当用户种植时，数组元素增加1个)
     */
    private WallNut[] wallNuts = {};
    /**
     * 1.13 爆炸樱桃图片集
     */
    public static BufferedImage[] img_cherryBombs;
    /**
     * 1.14 爆炸樱桃数组
     * 动态数组(当用户种植时，数组元素增加1个)
     */
    private CherryBomb[] cherryBombs = {};
    /**
     * 1.15 土豆地雷图片集
     */
    public static BufferedImage[] img_potatoMines;
    /**
     * 1.16 土豆地雷数组
     * 动态数组(当用户种植时，数组元素增加1个)
     */
    private PotatoMine[] potatoMines = {};
    /**
     * 1.17 滚动坚果图片集
     */
    public static BufferedImage[] img_wallNuts_roll;
    /**
     * 1.18 滚动坚果数组
     * 动态数组(当用户种植时，数组元素增加1个)
     */
    private WallNutRoll[] wallNutsRoll = {};
    /**
     * 1.19 第一类僵尸图片集(普通僵尸)
     */
    public static BufferedImage[] img_zombiesNormal;
    /**
     * 1.20 第二类僵尸图片集(撑杆僵尸)
     */
    public static BufferedImage[] img_zombiesPoleVaulting;
    /**
     * 1.21 第三类僵尸图片集(举旗僵尸)
     */
    public static BufferedImage[] img_zombiesFlag;
    /**
     * 1.22 各类僵尸数组
     * 动态僵尸数组
     */
    private BaseMovingObject[] zombies = {};
    /**
     * 1.23 僵尸出现频率控制
     */
    private int generateIndex = 0;
    /**
     * 1.24 太阳图片集
     */
    public static BufferedImage[] img_suns;
    /**
     * 1.25 太阳数组
     */
    private Sun[] suns = {};
    /**
     * 1.26 豌豆子弹图片
     */
    public static BufferedImage img_beanBullet;
    /**
     * 1.26 豌豆子弹击碎图片
     */
    public static BufferedImage img_beanBulletHit;
    /**
     * 1.28 豌豆子弹数组
     * 动态子弹数组
     */
    private BeanBullet[] beanBullets = {};
    /**
     * 1.29 控制射击频率
     */
    private int shootIndex = 0;
    /**
     * 1.30 击碎状态的子弹
     */
    private BeanBullet beanBulletHit;
    /**
     * 1.31 无头僵尸图片集
     */
    public static BufferedImage[] img_zombiesLostHead;
    /**
     * 1.32 僵尸死亡图片集
     */
    public static BufferedImage[] img_zombiesDie;
    /**
     * 1.33 清除僵尸的速度
     */
    private int clearIndex = 1;
    /**
     * 1.34 种植区域阴影图片shadow
     */
    public static BufferedImage img_shadow;
    /**
     * 1.35 阴影区域shadow对象
     */
    private static Shadow shadow;
    /**
     * 1.36 铲槽图片
     */
    private static BufferedImage img_shovelBank;
    /**
     * 1.37 铁铲图片
     */
    public static BufferedImage img_shovel;
    /**
     * 1.38 铁铲对象
     */
    private static Shovel shovel;
    /**
     * 1.39 标记是否处于铲除植物状态
     */
    private static boolean shovel_used;
    /**
     * 1.40 被啃食防御坚果图片集
     */
    public static BufferedImage[] img_wallNutsCracked1;
    /**
     * 1.41 被啃食防御坚果图片集
     */
    public static BufferedImage[] img_wallNutsCracked2;
    /**
     * 1.42 攻击状态僵尸图片集
     */
    public static BufferedImage[] img_zombiesAttacking;
    /**
     * 1.43 无头攻击僵尸图片集
     */
    public static BufferedImage[] img_zombiesAttackingLostHead;
    /**
     * 1.44 爆炸僵尸图片集
     */
    public static BufferedImage[] img_zombiesBomb;
    /**
     * 1.45 土豆地雷爆破图片
     */
    public static BufferedImage img_potatoMineBomb;
    /**
     * 1.46 土豆地雷准备状态图片
     */
    public static BufferedImage img_potatoMineNotReady;
    /**
     * 1.47 樱桃炸弹爆炸图片
     */
    public static BufferedImage img_cherryBombBomb;
    /**
     * 1.48 准备安放植物图片集
     */
    private static BufferedImage[] img_prepareGrowPlants;
    /**
     * 1.59 一大波僵尸即将接近图片
     */
    private static BufferedImage img_largeWave;
    /**
     * 1.60 僵尸胜利图片
     */
    private static BufferedImage img_zombiesWon;
    /**
     * 1.61 用户胜利图片
     */
    private static BufferedImage img_userWon;
    /**
     * 1.62 僵尸胜利标志
     */
    private static boolean zombiesWon;
    /**
     * 1.63 用户胜利标志
     */
    private static boolean userWon;

    /**
     * 1.64 撑杆跳僵尸攻击图片集
     */
    public static BufferedImage[] img_zombiesPoleVaultingAttack;
    /**
     * 1.65 撑杆跳僵尸无头图片集
     */
    public static BufferedImage[] img_zombiesPoleVaultingLostHead;
    /**
     * 1.66 撑杆跳僵尸无头攻击图片集
     */
    public static BufferedImage[] img_zombiesPoleVaultingLostHeadAttack;
    /**
     * 1.67 举旗僵尸攻击图片集
     */
    public static BufferedImage[] img_zombiesFlagAttack;
    /**
     * 1.68 举旗僵尸无头图片集
     */
    public static BufferedImage[] img_zombiesFlagLostHead;
    /**
     * 1.69 举旗僵尸无头攻击图片集
     */
    public static BufferedImage[] img_zombiesFlagLostHeadAttack;
    /**
     * 1.70 撑杆跳僵尸死亡图片集
     */
    public static BufferedImage[] img_zombiesPoleVaultingDie;
    /**
     * 1.71 白天场景按钮
     */
    private static BufferedImage img_daytime_button;
    /**
     * 1.72 夜晚场景按钮
     */
    private static BufferedImage img_night_button;
    /**
     * 1.73 标记当前处于白天场景
     */
    private static boolean isDaytime;
    /**
     * 1.74 白天场景图片
     */
    private static BufferedImage img_daytime_scene;
    /**
     * 1.75 夜晚场景图片
     */
    private static BufferedImage img_night_scene;
    /**
     * 1.77 冷冻豌豆射手图片集
     */
    public static BufferedImage[] img_beanShootersIce;
    /**
     * 1.78 冷冻豌豆射手子弹图片
     */
    public static BufferedImage img_beanBulletIce;
    /**
     * 1.79 雪花图片按钮图片
     */
    private static BufferedImage img_snow_button;
    /**
     * 1.80 标记当前处于冰冻模式
     */
    private static boolean isIceMode;
    /**
     * 游戏界面边界
     */
    private int x_left = 250, x_right = 970, y_up = 55, y_down = 570;
    /**
     * 草坪行数
     */
    private int rowsNum = 5;
    /**
     * 草坪列数
     */
    private int columnsNum = 9;

    static {
        try {
            // 背景初始化
            img_yard = ImageIO.read(MainGamePanel.class.getResource("img/background_game_night.jpg"));
            img_car = ImageIO.read(MainGamePanel.class.getResource("img/car.png"));
            initCars();
            // 初始化待种植植物为null
            nowToBePlanted = null;
            // 初始化shadow为null
            shadow = null;
            // 待种植植物图片集初始化
            imgsToBePlanted = new BufferedImage[]{
                    ImageIO.read(MainGamePanel.class.getResource("img/blurs/sunflower.png")),
                    ImageIO.read(MainGamePanel.class.getResource("img/blurs/Peashooter.png")),
                    ImageIO.read(MainGamePanel.class.getResource("img/blurs/WallNut.png")),
                    ImageIO.read(MainGamePanel.class.getResource("img/blurs/CherryBomb.png")),
                    ImageIO.read(MainGamePanel.class.getResource("img/blurs/PotatoMine.png")),
                    ImageIO.read(MainGamePanel.class.getResource("img/blurs/WallNut.png"))};
            // 卡槽初始化
            img_sunBank = ImageIO.read(MainGamePanel.class.getResource("img/seedbank.png"));
            sunBank = new SunBank();
            // 太阳花图片集初始化
            img_sunFlowers = new BufferedImage[18];
            for (int i = 0; i < img_sunFlowers.length; i++) {
                img_sunFlowers[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/sunflower/sunflower_" + i + ".png"));
            }
            // 豌豆射手图片集初始化
            img_beanShooters = new BufferedImage[13];
            for (int i = 0; i < img_beanShooters.length; i++) {
                img_beanShooters[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/peashooter/peashooter_" + i + ".png"));
            }
            // 防御坚果图片集初始化
            img_wallNuts = new BufferedImage[16];
            for (int i = 0; i < img_wallNuts.length; i++) {
                img_wallNuts[i] = ImageIO.read(MainGamePanel.class.getResource("img/wallNut/wallNut_" + i + ".png"));
            }
            // 爆炸樱桃图片集初始化
            img_cherryBombs = new BufferedImage[7];
            for (int i = 0; i < img_cherryBombs.length; i++) {
                img_cherryBombs[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/cherryBomb/cherryBomb_" + i + ".png"));
            }
            // 土豆地雷图片集初始化
            img_potatoMines = new BufferedImage[8];
            for (int i = 0; i < img_potatoMines.length; i++) {
                img_potatoMines[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/potatoMine/potatoMine_" + i + ".png"));
            }
            // 滚动坚果图片集初始化
            img_wallNuts_roll = new BufferedImage[8];
            for (int i = 0; i < img_wallNuts_roll.length; i++) {
                img_wallNuts_roll[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/WallNutRoll/WallNutRoll_" + i + ".png"));
            }
            // 第一类僵尸图片集初始化
            img_zombiesNormal = new BufferedImage[22];
            for (int i = 0; i < img_zombiesNormal.length; i++) {
                img_zombiesNormal[i] = ImageIO.read(MainGamePanel.class.getResource("img/zombie1/frame" + i + ".png"));
            }
            // 第二类僵尸图片集初始化
            img_zombiesPoleVaulting = new BufferedImage[10];
            for (int i = 0; i < img_zombiesPoleVaulting.length; i++) {
                img_zombiesPoleVaulting[i] = ImageIO.read(MainGamePanel.class.getResource("img/ZombiePoleVaulting/ZombiePoleVaulting " + i + ".png"));
            }
            // 第三类僵尸图片集初始化
            img_zombiesFlag = new BufferedImage[12];
            for (int i = 0; i < img_zombiesFlag.length; i++) {
                img_zombiesFlag[i] = ImageIO.read(MainGamePanel.class.getResource("img/ZombieFlag/ZombieFlag_" + i + ".png"));
            }
            // 太阳图片集初始化
            img_suns = new BufferedImage[22];
            for (int i = 0; i < img_suns.length; i++) {
                img_suns[i] = ImageIO.read(MainGamePanel.class.getResource("img/sun/" + i + ".png"));
            }
            // 豌豆子弹图片初始化
            img_beanBullet = ImageIO.read(MainGamePanel.class.getResource("img/bullets/PeaShooter.png"));
            // 豌豆子弹击碎图片初始化
            img_beanBulletHit = ImageIO.read(MainGamePanel.class.getResource("img/bullets/PeaShooterHit.png"));
            // 无头僵尸图片集初始化
            img_zombiesLostHead = new BufferedImage[18];
            for (int i = 0; i < img_zombiesLostHead.length; i++) {
                img_zombiesLostHead[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/ZombieLostHead/frame" + i + ".png"));
            }
            // 僵尸死亡图片集初始化
            img_zombiesDie = new BufferedImage[14];
            for (int i = 0; i < img_zombiesDie.length; i++) {
                img_zombiesDie[i] = ImageIO.read(MainGamePanel.class.getResource("img/zombieDie/frame" + i + ".png"));
            }
            // 种植区域阴影图片shadow初始化
            img_shadow = ImageIO.read(MainGamePanel.class.getResource("img/shadow.png"));
            // 铲槽图片初始化
            img_shovelBank = ImageIO.read(MainGamePanel.class.getResource("img/shovelBank.png"));
            // 铲子图片初始化
            img_shovel = ImageIO.read(MainGamePanel.class.getResource("img/shovel.png"));
            // 初始化铁铲对象
            shovel = new Shovel(465, 10);
            // 初始化铁铲未使用
            shovel_used = false;
            // 被啃食防御坚果图片集1
            img_wallNutsCracked1 = new BufferedImage[11];
            for (int i = 0; i < img_wallNutsCracked1.length; i++) {
                img_wallNutsCracked1[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/Wallnut_cracked1/Wallnut_cracked1_" + i + ".png"));
            }
            // 被啃食防御坚果图片集2
            img_wallNutsCracked2 = new BufferedImage[15];
            for (int i = 0; i < img_wallNutsCracked2.length; i++) {
                img_wallNutsCracked2[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/Wallnut_cracked2/Wallnut_cracked2_" + i + ".png"));
            }
            // 攻击状态僵尸图片集
            img_zombiesAttacking = new BufferedImage[21];
            for (int i = 0; i < img_zombiesAttacking.length; i++) {
                img_zombiesAttacking[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/ZombieAttack/frame" + i + ".png"));
            }
            // 无头攻击僵尸图片集
            img_zombiesAttackingLostHead = new BufferedImage[11];
            for (int i = 0; i < img_zombiesAttackingLostHead.length; i++) {
                img_zombiesAttackingLostHead[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/ZombieLostHeadAttack/frame" + i + ".png"));
            }
            // 爆炸僵尸图片集
            img_zombiesBomb = new BufferedImage[21];
            for (int i = 0; i < img_zombiesBomb.length; i++) {
                img_zombiesBomb[i] = ImageIO.read(MainGamePanel.class.getResource("img/boom/frame" + i + ".png"));
            }
            // 土豆地雷爆破图片
            img_potatoMineBomb = ImageIO.read(MainGamePanel.class.getResource("img/PotatoMine_Boom.png"));
            // 土豆地雷准备状态图片
            img_potatoMineNotReady = ImageIO.read(MainGamePanel.class.getResource("img/PotatoMineNotReady.png"));
            // 樱桃炸弹爆炸图片
            img_cherryBombBomb = ImageIO.read(MainGamePanel.class.getResource("img/cherryBomb_Boom.png"));
            // 准备安放植物图片集
            img_prepareGrowPlants = new BufferedImage[3];
            for (int i = 0; i < img_prepareGrowPlants.length; i++) {
                img_prepareGrowPlants[i] = ImageIO.read(MainGamePanel.class.getResource("img/PrepareGrowPlants" + (i + 1) + ".png"));
            }
            // 一大波僵尸即将接近图片
            img_largeWave = ImageIO.read(MainGamePanel.class.getResource("img/largeWave.png"));
            //  僵尸胜利图片
            img_zombiesWon = ImageIO.read(MainGamePanel.class.getResource("img/zombiesWon.png"));
            // 用户胜利图片
            img_userWon = ImageIO.read(MainGamePanel.class.getResource("img/zombiesWon.png"));
            // 胜利状态初始化
            zombiesWon = false;
            userWon = false;
            // 1.64 撑杆跳僵尸攻击图片集
            img_zombiesPoleVaultingAttack = new BufferedImage[14];
            for (int i = 0; i < img_zombiesPoleVaultingAttack.length; i++) {
                img_zombiesPoleVaultingAttack[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/ZombiePoleVaultingAttack/ZombiePoleVaultingAttack " + i + ".png"));
            }
            // 1.64 撑杆跳僵尸无头图片集
            img_zombiesPoleVaultingLostHead = new BufferedImage[10];
            for (int i = 0; i < img_zombiesPoleVaultingLostHead.length; i++) {
                img_zombiesPoleVaultingLostHead[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/ZombiePoleVaultingLostHead/ZombiePoleVaultingLostHead " + i + ".png"));
            }
            // 1.65 撑杆跳僵尸无头攻击图片集
            img_zombiesPoleVaultingLostHeadAttack = new BufferedImage[14];
            for (int i = 0; i < img_zombiesPoleVaultingLostHeadAttack.length; i++) {
                img_zombiesPoleVaultingLostHeadAttack[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/ZombiePoleVaultingLostHeadAttack/ZombiePoleVaultingLostHeadAttack " + i + ".png"));
            }
            // 1.66 举旗僵尸攻击图片集
            img_zombiesFlagAttack = new BufferedImage[11];
            for (int i = 0; i < img_zombiesFlagAttack.length; i++) {
                img_zombiesFlagAttack[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/ZombieFlagAttack/ZombieFlagAttack " + i + ".png"));
            }
            // 1.66 举旗僵尸无头图片集
            img_zombiesFlagLostHead = new BufferedImage[12];
            for (int i = 0; i < img_zombiesFlagLostHead.length; i++) {
                img_zombiesFlagLostHead[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/ZombieFlagLostHead/ZombieFlagLostHead " + i + ".png"));
            }
            // 1.66 举旗僵尸无头攻击图片集
            img_zombiesFlagLostHeadAttack = new BufferedImage[11];
            for (int i = 0; i < img_zombiesFlagLostHeadAttack.length; i++) {
                img_zombiesFlagLostHeadAttack[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/ZombieFlagLostHeadAttack/ZombieFlagLostHeadAttack " + i + ".png"));
            }
            // 1.67 撑杆跳僵尸死亡图片集
            img_zombiesPoleVaultingDie = new BufferedImage[9];
            for (int i = 0; i < img_zombiesPoleVaultingDie.length; i++) {
                img_zombiesPoleVaultingDie[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/ZombiePoleVaultingDie/ZombiePoleVaultingDie " + i + ".png"));
            }
            // 1.66 白天场景按钮
            img_daytime_button = ImageIO.read(MainGamePanel.class.getResource("img/button_daytime.png"));
            // 1.67 夜晚场景按钮
            img_night_button = ImageIO.read(MainGamePanel.class.getResource("img/button_night.png"));
            // 1.68 初始化当前处于白天场景
            isDaytime = true;
            // 场景背景图片初始化
            img_daytime_scene = ImageIO.read(MainGamePanel.class.getResource("img/background_game_daytime.jpg"));
            img_night_scene = ImageIO.read(MainGamePanel.class.getResource("img/background_game_night.jpg"));
            // 1.77 冷冻豌豆射手图片集
            img_beanShootersIce = new BufferedImage[15];
            for (int i = 0; i < img_beanShootersIce.length; i++) {
                img_beanShootersIce[i] = ImageIO
                        .read(MainGamePanel.class.getResource("img/beanShooterIce/beanShooterIce " + i + ".png"));
            }
            // 1.78 冷冻豌豆射手子弹图片
            img_beanBulletIce = ImageIO.read(MainGamePanel.class.getResource("img/bullets/peaShooterIce.png"));
            // 1.79 雪花图片按钮图片
            img_snow_button = ImageIO.read(MainGamePanel.class.getResource("img/button_snow.png"));
            // 1.80 初始化当前不处于冰冻模式
            isIceMode = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2.1 初始化放置门前小车
     */
    private static void initCars() {
        int carInterval = 100;
        // 创建initial_carNum个门前小车
        int initialCarNum = 5;
        cars = new Car[initialCarNum];
        for (int i = 0; i < initialCarNum; i++) {
            cars[i] = new Car(180, 70 + i * carInterval);
        }
    }

    /**
     * 3. 画笔方法
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 3.1 绘制庭院背景、小车等静态物体
        paintBasicObject(g);
        // 3.2 绘制太阳池卡槽和铲槽
        paintSunBank(g);
        // 3.3 绘制铁锹池和铁锹
        paintShovelBank(g);
        // 3.4 绘制当前太阳池能量
        drawSunEnergy(g);
        // 3.5 绘制植物
        drawPlants(g);
        // 3.6 绘制待种植植物和区域阴影
        drawToBePlantAndShadow(g);
        // 3.7 绘制僵尸
        drawZombies(g);
        // 3.8 绘制太阳
        drawSuns(g);
        // 3.9 绘制豌豆子弹
        drawBeanBullets(g);
        // 3.10 绘制游戏结束画面
        drawGameEndPicture(g);
        // 3.11 绘制场景切换按钮
        drawSceneChangeButton(g);
    }

    /**
     * 3.1 绘制庭院背景、小车、欢迎语(准备安放植物！僵尸来临)等静态物体
     *
     * @param g 画笔工具
     */
    private void paintBasicObject(Graphics g) {
        g.drawImage(img_yard, 0, 0, null);
        for (Car car : cars) {
            g.drawImage(car.image, car.x, car.y, car.width, car.height, null);
        }
        //绘制 准备安放植物！
        int interval = 70;
        if (generateIndex >= 20 && generateIndex < interval) {
            g.drawImage(img_prepareGrowPlants[0], getWidth() / 2 - 200, getHeight() / 2 - 100, null);
        } else if (generateIndex >= interval && generateIndex < 2 * interval) {
            g.drawImage(img_prepareGrowPlants[1], getWidth() / 2 - 200, getHeight() / 2 - 100, null);
        } else if (generateIndex >= 2 * interval && generateIndex <= 4 * interval) {
            g.drawImage(img_prepareGrowPlants[2], getWidth() / 2 - 200, getHeight() / 2 - 100, null);
        }
        // 绘制 一大波僵尸即将来临
        if (generateIndex > 3700 && generateIndex < 4000) {
            g.drawImage(img_largeWave, getWidth() / 2 - 200, getHeight() / 2 - 100, null);
        }
    }

    /**
     * 3.2 绘制太阳池卡槽和铲槽
     *
     * @param g 画笔工具
     */
    private void paintSunBank(Graphics g) {
        // 绘制太阳池卡槽
        g.drawImage(sunBank.image, sunBank.x, sunBank.y, null);
        for (int i = 0; i < SunBank.cards.length; i++) {
            Card card = SunBank.cards[i];
            g.drawImage(card.image, card.x, card.y, null);
        }
    }

    /**
     * 3.3 绘制铁锹池和铁锹
     *
     * @param g 画笔工具
     */
    private void paintShovelBank(Graphics g) {
        // 绘制铲槽
        g.drawImage(img_shovelBank, 470, 15, null);
        // 绘制铁铲
        g.drawImage(shovel.image, shovel.x, shovel.y, null);
    }

    /**
     * 3.4 绘制当前太阳池能量
     *
     * @param g 画笔工具
     */
    private void drawSunEnergy(Graphics g) {

        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g.drawString(String.valueOf(currentSunEnergy), 35, 85);
    }

    /**
     * 3.5 绘制植物
     *
     * @param g 画笔工具
     */
    private void drawPlants(Graphics g) {

        // 3.5.1 绘制太阳花
        for (SunFlower sunFlower : sunFlowers) {
            g.drawImage(sunFlower.image, sunFlower.x, sunFlower.y, null);
        }
        // 3.5.2 绘制豌豆射手
        for (BeanShooter beanShooter : beanShooters) {
            g.drawImage(beanShooter.image, beanShooter.x, beanShooter.y, null);
        }
        // 3.5.3 绘制防御坚果
        for (WallNut wallNut : wallNuts) {
            g.drawImage(wallNut.image, wallNut.x, wallNut.y, null);
        }
        // 3.5.4 绘制爆炸樱桃
        for (CherryBomb cherryBomb : cherryBombs) {
            g.drawImage(cherryBomb.image, cherryBomb.x, cherryBomb.y, null);
        }
        // 3.5.5 绘制土豆地雷
        for (PotatoMine potatoMine : potatoMines) {
            g.drawImage(potatoMine.image, potatoMine.x, potatoMine.y, null);
        }
        // 3.5.6 绘制滚动坚果
        for (WallNutRoll wallNut_roll : wallNutsRoll) {
            g.drawImage(wallNut_roll.image, wallNut_roll.x, wallNut_roll.y, null);
        }
    }

    // 3.6 绘制待种植植物和区域阴影
    private void drawToBePlantAndShadow(Graphics g) {

        if (nowToBePlanted != null) {
            g.drawImage(nowToBePlanted.image, nowToBePlanted.x, nowToBePlanted.y, null);
        }
        if (shadow != null) {
            g.drawImage(shadow.image, shadow.x, shadow.y, null);
        }
    }

    // 3.7 绘制僵尸
    private void drawZombies(Graphics g) {

        for (BaseMovingObject zombie : zombies) {
            g.drawImage(zombie.image, zombie.x, zombie.y, null);
        }
    }

    // 3.8 绘制太阳
    private void drawSuns(Graphics g) {

        for (Sun sun : suns) {
            g.drawImage(sun.image, sun.x, sun.y, null);
        }
    }

    // 3.9 绘制豌豆子弹
    private void drawBeanBullets(Graphics g) {
        for (BeanBullet beanBullet : beanBullets) {
            g.drawImage(beanBullet.image, beanBullet.x, beanBullet.y, null);
        }
        // 绘制击碎状态的豌豆子弹
        if (beanBulletHit != null) {
            g.drawImage(beanBulletHit.image, beanBulletHit.x, beanBulletHit.y, null);
            g.drawImage(beanBulletHit.image, beanBulletHit.x + 10, beanBulletHit.y + 10, null);
            beanBulletHit = null;
        }
    }

    /**
     * 3.10 绘制游戏结束画面
     *
     * @param g 画笔工具
     */
    private void drawGameEndPicture(Graphics g) {
        if (zombiesWon) {
            g.drawImage(img_zombiesWon, getWidth() / 2 - img_zombiesWon.getWidth() / 2, getHeight() / 2 - img_zombiesWon.getHeight() / 2, null);
        }
        if (userWon) {
            g.drawImage(img_userWon, getWidth() / 2 - img_userWon.getWidth() / 2, getHeight() / 2 - img_userWon.getHeight() / 2, null);
        }
    }

    /**
     * 3.11 绘制场景切换按钮
     */
    private void drawSceneChangeButton(Graphics g) {
        g.drawImage(img_night_button, 900, 10, null);
        g.drawImage(img_daytime_button, 1000, 10, null);
        g.drawImage(img_snow_button, 1100, 10, null);
    }

    /**
     * 4. 业务逻辑处理区
     */
    @Override
    public void run() {

        while (true) {
            // 1. 业务处理
            //游戏未结束时执行以下业务
            if (!(zombiesWon || userWon)) {
                // 1.1 生成僵尸和太阳
                generateZombiesAndSuns();
                // 1.2 调用对象的移动方法
                stepAction();
                // 1.3 调用所有对象的出界方法
                outOfBoundsActionAll();
                // 1.4 调用豌豆射手的射击子弹方法
                shootAction();
                // 1.5 碰撞处理操作
                hitAction();
                // 1.6 清除所有已死亡对象
                clearAllDeadObjects();
                // 1.7 更新当前卡片状态
                sunBank.updateSunBank();
                // 1.8 更新场景
                updateScene();
            }
            // 2. 线程睡眠
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            // 3. 重绘方法
            repaint();
        }
    }

    // 1.1 随机生成僵尸
    private void generateZombiesAndSuns() {
        generateIndex++;
        BaseMovingObject zombie = null;
        int generateInterval;
        if (generateIndex > 6000) {
            generateInterval = 1500;
        } else {
            generateInterval = 700;
        }
        // 生成僵尸 (规定范围的目的是为了给用户种植植物的时间)
        if (generateIndex % generateInterval == 0 && generateIndex > 4000) {
            // 创建僵尸对象(类别随机)
            int zombieType = new Random().nextInt(3);
            switch (zombieType) {
                case 0:
                    zombie = new ZombieNormal();
                    break;
                case 1:
                    zombie = new ZombiePoleVaulting();
                    break;
                case 2:
                    zombie = new ZombieFlag();
                    break;
                default:
                    break;
            }
            // 数组扩容
            zombies = Arrays.copyOf(zombies, zombies.length + 1);
            zombies[zombies.length - 1] = zombie;
        }
        // 太阳花生成太阳
        for (SunFlower sunFlower : sunFlowers) {
            Sun sun = sunFlower.generateSun();
            if (sun != null) {
                // 数组扩容
                suns = Arrays.copyOf(suns, suns.length + 1);
                suns[suns.length - 1] = sun;
            }
        }
        // 游戏界面随机生成太阳(不依赖太阳花)
        if (generateIndex % 1000 == 0) {
            Sun sun = new Sun((int) (Math.random() * 800) + 50, (int) (Math.random() * 400) - 100);
            // 数组扩容
            suns = Arrays.copyOf(suns, suns.length + 1);
            suns[suns.length - 1] = sun;
        }
    }

    // 1.2 调用对象的移动方法
    private void stepAction() {
        // 小车移动
        for (Car car : cars) {
            car.step();
        }
        // 太阳花移动
        for (SunFlower sunFlower : sunFlowers) {
            sunFlower.step();
        }
        // 豌豆射手移动
        for (BeanShooter beanShooter : beanShooters) {
            beanShooter.step();
        }
        // 爆炸樱桃移动
        for (CherryBomb cherryBomb : cherryBombs) {
            cherryBomb.step();
        }
        // 土豆地雷移动
        for (PotatoMine potatoMine : potatoMines) {
            potatoMine.step();
        }
        // 防御坚果移动
        for (WallNut wallNut : wallNuts) {
            wallNut.step();
        }
        // 滚动坚果移动
        for (WallNutRoll wallNutRoll : wallNutsRoll) {
            wallNutRoll.step();
        }
        // 僵尸移动
        for (BaseMovingObject zombie : zombies) {
            zombie.step();
        }
        // 太阳移动
        for (Sun sun : suns) {
            sun.step();
        }
        // 豌豆子弹移动
        for (BeanBullet beanBullet : beanBullets) {
            beanBullet.step();
        }
    }

    /**
     * 1.3 调用对象的出界方法
     */
    private void outOfBoundsActionAll() {
        // 门前小车car的出界处理
        outOfBoundsAction(cars);
        // 滚动坚果的出界处理
        outOfBoundsAction(wallNuts);
        // 僵尸的出界处理
        // 僵尸出界代表僵尸进入了你的房子！僵尸胜利！游戏结束！
        for (BaseMovingObject zombie : zombies) {
            if (zombie.outOfBounds()) {
                zombiesWon = true;
            }
        }
        // 太阳的出界处理
        outOfBoundsAction(suns);
        // 豌豆子弹的出界处理
        outOfBoundsAction(beanBullets);
    }

    /**
     * 单类对象的出界方法
     *
     * @param objects 对象列表
     */
    private void outOfBoundsAction(BaseMovingObject[] objects) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i].outOfBounds()) {
                objects[i] = objects[objects.length - 1];
                objects = Arrays.copyOf(objects, objects.length - 1);
            }
        }
    }

    /**
     * 1.4 调用豌豆射手的射击子弹方法
     */
    private void shootAction() {
        shootIndex++;
        int shootInterval = 200;
        if (shootIndex > 2000) {
            shootInterval = 100;
        }
        if (shootIndex % shootInterval == 0) {
            for (BeanShooter beanShooter : beanShooters) {
                // 射出一颗子弹
                BeanBullet beanBullet = beanShooter.shoot();
                // 数组扩容
                beanBullets = Arrays.copyOf(beanBullets, beanBullets.length + 1);
                beanBullets[beanBullets.length - 1] = beanBullet;
            }
        }
    }

    /**
     * 1.5 碰撞处理操作
     */
    private void hitAction() {
        // 处理豌豆子弹与僵尸的碰撞操作
        // 遍历豌豆子弹
        for (int i = 0; i < beanBullets.length; i++) {
            hitZombie(beanBullets[i], i);
        }
        // 门前小车与僵尸的碰撞操作
        // 遍历cars数组
        for (int i = 0; i < cars.length; i++) {
            // 遍历僵尸数组
            for (int j = 0; j < zombies.length; j++) {
                // 此时要控制car与zombie在同一行
                if (cars[i].hitByZombie(zombies[j]) && posToRowAndColumn(cars[i].x, cars[i].y)[0] == posToRowAndColumn(
                        zombies[j].x + zombies[j].width, zombies[j].y + zombies[j].height)[0]) {
                    System.out.print("row_car " + (i + 1) + " : " + posToRowAndColumn(cars[i].x, cars[i].y)[0]);
                    System.out.println(" ; row_zombie " + (j + 1) + " : " + posToRowAndColumn(
                            zombies[j].x + zombies[j].width, zombies[j].y + zombies[j].height)[0]);
                    // 设置僵尸生命值为0，之后会调用clearZombies()方法自动清除生命值为0的僵尸
                    zombies[j].life = 0;
                    break;
                }
            }
        }
        // 滚动坚果与僵尸的碰撞操作
        // 遍历wallNut_roll数组
        for (WallNutRoll wallNut_roll : wallNutsRoll) {
            // 遍历僵尸数组
            for (BaseMovingObject zombie : zombies) {
                // 此时要控制car与zombie在同一行
                if (wallNut_roll.hitByZombie(zombie)
                        && posToRowAndColumn(wallNut_roll.x + wallNut_roll.width / 2,
                        wallNut_roll.y + wallNut_roll.height)[0] == posToRowAndColumn(
                        zombie.x + zombie.width, zombie.y + zombie.height)[0]) {
                    // 设置僵尸生命值为0，之后会调用clearZombies()方法自动清除生命值为0的僵尸
                    zombie.life = 0;
                    break;
                }
            }
        }
        // 防御坚果与僵尸的碰撞操作
        // 遍历wallNut数组
        for (WallNut wallNut : wallNuts) {
            // 遍历僵尸数组
            for (BaseMovingObject zombie : zombies) {
                // 此时要控制car与zombie在同一行
                if (wallNut.hitByZombie(zombie) && posToRowAndColumn(wallNut.x + wallNut.width / 2,
                        wallNut.y + wallNut.height)[0] == posToRowAndColumn(zombie.x + zombie.width,
                        zombie.y + zombie.height)[0]) {
                    wallNut.removeOneLife();
                    zombie.stopMoving();// 使僵尸停止运动
                    zombie.startAttacking();// 变为攻击状态
                    // 变换僵尸为攻击状态
                    if (wallNut.getCurrentLife() <= 0) {
                        // 坚果死亡后，与坚果同行的僵尸继续运动，恢复正常状态
                        for (BaseMovingObject baseMovingObject : zombies) {
                            if (posToRowAndColumn(wallNut.x + wallNut.width / 2,
                                    wallNut.y + wallNut.height)[0] == posToRowAndColumn(
                                    baseMovingObject.x + baseMovingObject.width, baseMovingObject.y + baseMovingObject.height)[0]) {
                                // 继续运动
                                baseMovingObject.startMoving();
                                // 状态恢复
                                baseMovingObject.stopAttacking();
                            }
                        }
                    }
                    // 此时不需要break，多个僵尸可啃食一个防御坚果
                }
            }
        }
        // 太阳花与僵尸的碰撞操作
        // 遍历sunFlowers数组
        for (SunFlower sunFlower : sunFlowers) {
            // 遍历僵尸数组
            for (BaseMovingObject zombie : zombies) {
                int rowSunFlower = posToRowAndColumn(sunFlower.x + sunFlower.width / 2,
                        sunFlower.y + sunFlower.height)[0];
                int rowZombie = posToRowAndColumn(
                        zombie.x + zombie.width / 2, zombie.y + zombie.height)[0];
                // 此时要控制car与zombie在同一行
                if (sunFlower.hitByZombie(zombie) && rowSunFlower == rowZombie) {
                    sunFlower.removeOneLife();
                    // 使僵尸停止运动
                    zombie.stopMoving();
                    // 变为攻击状态
                    zombie.startAttacking();
                    // 变换僵尸为攻击状态
                    if (sunFlower.getCurrentLife() <= 0) {
                        for (BaseMovingObject baseMovingObject : zombies) {
                            if (rowSunFlower == posToRowAndColumn(
                                    baseMovingObject.x + baseMovingObject.width / 2, baseMovingObject.y + baseMovingObject.height)[0]) {
                                // 继续运动
                                baseMovingObject.startMoving();
                                // 状态恢复
                                baseMovingObject.stopAttacking();
                            }
                        }
                    }
                }
            }
        }
        // 遍历beanShooters数组
        for (BeanShooter beanShooter : beanShooters) {
            // 遍历僵尸数组
            for (BaseMovingObject zombie : zombies) {
                if (beanShooter.hitByZombie(zombie)
                        && posToRowAndColumn(beanShooter.x + beanShooter.width / 2,
                        beanShooter.y + beanShooter.height)[0] == posToRowAndColumn(
                        // 此时要控制car与zombie在同一行
                        zombie.x + zombie.width / 2, zombie.y + zombie.height)[0]) {
                    beanShooter.removeOneLife();
                    // 使僵尸停止运动
                    zombie.stopMoving();
                    // 变为攻击状态
                    zombie.startAttacking();
                    // 变换僵尸为攻击状态
                    if (beanShooter.getCurrentLife() <= 0) {
                        for (BaseMovingObject baseMovingObject : zombies) {
                            if (posToRowAndColumn(beanShooter.x + beanShooter.width / 2,
                                    beanShooter.y + beanShooter.height)[0] == posToRowAndColumn(
                                    baseMovingObject.x + baseMovingObject.width / 2, baseMovingObject.y + baseMovingObject.height)[0]) {
                                // 继续运动
                                baseMovingObject.startMoving();
                                // 状态恢复
                                baseMovingObject.stopAttacking();
                            }
                        }
                    }
                }
            }
        }
        // 遍历potatoMines数组
        for (PotatoMine potatoMine : potatoMines) {
            // 遍历僵尸数组
            for (BaseMovingObject zombie : zombies) {
                if (potatoMine.hitByZombie(zombie)
                        && posToRowAndColumn(potatoMine.x + potatoMine.width / 2,
                        potatoMine.y + potatoMine.height)[0] == posToRowAndColumn(
                        // 此时要控制car与zombie在同一行
                        zombie.x + zombie.width / 2, zombie.y + zombie.height)[0]) {
                    // 地雷爆炸方法
                    potatoMine.bomb();
                    // 保证僵尸爆炸图片显示一段时间
                    clearIndex = 1;
                    zombie.startBombing();
                    break;
                }
            }
        }
        // 太阳花与僵尸的碰撞操作
        // 遍历cherryBombs数组
        for (CherryBomb cherryBomb : cherryBombs) {
            // 遍历僵尸数组
            for (BaseMovingObject zombie : zombies) {
                int cherryRow = posToRowAndColumn(cherryBomb.x + cherryBomb.width / 2,
                        cherryBomb.y + cherryBomb.height)[0];
                int cherryColumn = posToRowAndColumn(cherryBomb.x + cherryBomb.width / 2,
                        cherryBomb.y + cherryBomb.height)[1];
                int zombieRow = posToRowAndColumn(zombie.x + zombie.width / 2,
                        zombie.y + zombie.height)[0];
                int zombieColumn;
                // 此时要控制car与zombie在同一行
                if (cherryBomb.hitByZombie(zombie) && cherryRow == zombieRow) {
                    // 樱桃爆炸方法
                    cherryBomb.bomb();
                    // 保证僵尸爆炸图片显示一段时间
                    clearIndex = 1;
                    for (BaseMovingObject baseMovingObject : zombies) {
                        // 注意樱桃炸弹可炸毁周围(上下左右)区域的所有僵尸
                        zombieRow = posToRowAndColumn(baseMovingObject.x + baseMovingObject.width / 2,
                                baseMovingObject.y + baseMovingObject.height)[0];
                        zombieColumn = posToRowAndColumn(baseMovingObject.x + baseMovingObject.width / 2,
                                baseMovingObject.y + baseMovingObject.height)[1];
                        if ((zombieRow == cherryRow && zombieColumn >= cherryColumn - 1
                                && zombieColumn <= cherryColumn + 1)
                                || (zombieColumn == cherryColumn && zombieRow >= zombieRow - 1
                                && zombieRow <= zombieRow + 1)) {
                            // 十字架范围爆炸
                            // 僵尸爆炸方法
                            baseMovingObject.startBombing();
                        }
                    }
                }
            }
        }
    }

    // 1.5.1 子弹hit僵尸操作
    private void hitZombie(BeanBullet beanBullet, int beanBulletIndex) {
        // 子弹beanBullet碰撞到的僵尸编号
        int hitIndex = -1;
        // 遍历僵尸数组
        for (int i = 0; i < zombies.length; i++) {
            if (zombies[i].hitByBullet(beanBullet)) {
                // 更新碰撞僵尸编号
                hitIndex = i;
                // 检测到碰撞，立刻退出
                break;
            }
        }
        // 碰撞处理操作
        if (hitIndex != -1) {
            // 复制豌豆子弹
            beanBulletHit = new BeanBullet(beanBullets[beanBulletIndex].x + 100, beanBullets[beanBulletIndex].y);
            beanBulletHit.updateHitImage();
            // 消除豌豆子弹
            beanBullets[beanBulletIndex] = beanBullets[beanBullets.length - 1];
            beanBullets = Arrays.copyOf(beanBullets, beanBullets.length - 1);
            // 被击中的僵尸生命值减1
            zombies[hitIndex].removeOneLife();
            // 注意：这里不进行僵尸生命值减为0时，消除僵尸对象的操作
        }
    }

    /**
     * 1.6 清除所有已死亡对象
     */
    private void clearAllDeadObjects() {
        // 依次遍历僵尸数组，若僵尸生命值减为0，则消除该僵尸对象
        clearIndex++;
        // 为了暂留僵尸死亡效果
        if (clearIndex % 200 == 0) {
            // 清除死亡僵尸
            clearDeadObjects(zombies);
        }
        // 为了暂留爆炸樱桃和土豆地雷爆炸效果
        if (clearIndex % 100 == 0) {
            // 清除死亡樱桃
            clearDeadObjects(cherryBombs);
            // 清除死亡土豆地雷
            clearDeadObjects(potatoMines);
        }
        // 实时清除死亡防御坚果，不等待
        clearDeadObjects(wallNuts);
        // 实时清除死亡太阳花，不等待
        clearDeadObjects(sunFlowers);
        // 实时清除死亡豌豆射手，不等待
        clearDeadObjects(beanShooters);
    }

    /**
     * 清除单类死亡物体
     *
     * @param objects 物体列表
     */
    private void clearDeadObjects(BaseMovingObject[] objects) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i].getCurrentLife() <= 0) {
                objects[i] = objects[objects.length - 1];
                objects = Arrays.copyOf(objects, objects.length - 1);
            }
        }
    }

    // 1.8 更新当前场景
    private void updateScene() {
        if (isDaytime) {
            img_yard = img_daytime_scene;
        } else {
            img_yard = img_night_scene;
        }
        // 冰冻模式
        if (isIceMode) {
            for (BeanShooter beanShooter : beanShooters) {
                beanShooter.setIceMode();
            }
            for (BeanBullet beanBullet : beanBullets) {
                beanBullet.setIceMode();
            }
        } else {
            //非冰冻模式
            for (BeanShooter beanShooter : beanShooters) {
                beanShooter.setNormalMode();
            }
            for (BeanBullet beanBullet : beanBullets) {
                beanBullet.setNormalMode();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int plantIndex = -1;
        int x = e.getX();
        int y = e.getY();
        // 根据点击的位置判断点击的卡片
        for (int i = 0; i < SunBank.cards.length; i++) {
            int x_left = 95 + i * (SunBank.cardInterval + SunBank.cards[i].width);
            int x_right = x_left + SunBank.cards[i].width;
            int y_up = 12;
            int y_down = y_up + SunBank.cards[i].height;
            if (x > x_left && x < x_right && y > y_up && y < y_down) {
                // 获取点击的卡片标号
                plantIndex = i;
                break;
            }
        }
        // 1. 点击卡片
        // 注意：不能处于待铲除植物状态
        if (plantIndex != -1 && !shovel_used) {
            // 当前能量值满足购买条件时才能够点击卡片
            if (currentSunEnergy >= sunEnergy[plantIndex]) {
                // 生成待种植太阳花(阴影显示)
                nowToBePlanted = new ToBePlanted(imgsToBePlanted[plantIndex], plantIndex,
                        x - imgsToBePlanted[plantIndex].getWidth() / 2,
                        y - imgsToBePlanted[plantIndex].getHeight() / 2);
            }
        }
        // 2. 放置植物
        if (nowToBePlanted != null) {
            // 注意放置的区域范围在草坪上、且此时不能再点击卡片
            if (x > 250 && x < 970 && y > 90 && y < 570) {
                // 标准化放置区域
                Point point = positionToBlock(x, y);
                x = point.x;
                y = point.y;
                // 根据待种植植物编号放置植物，注意待放置的区域不能存在植物
                if (!isPlacedToBlock(x, y)) {
                    switch (nowToBePlanted.plantIndex) {
                        case 0:
                            // 添加太阳花对象
                            SunFlower sunFlower = new SunFlower(x - img_sunFlowers[0].getWidth() / 2,
                                    y - img_sunFlowers[0].getHeight() / 2);
                            // 数组扩容
                            sunFlowers = Arrays.copyOf(sunFlowers, sunFlowers.length + 1);
                            sunFlowers[sunFlowers.length - 1] = sunFlower;
                            // 去除种植耗费的能量
                            currentSunEnergy -= sunEnergy[0];
                            break;
                        case 1:
                            // 添加豌豆射手对象
                            BeanShooter beanShooter = new BeanShooter(x - img_beanShooters[0].getWidth() / 2,
                                    y - img_beanShooters[0].getHeight() / 2);
                            // 数组扩容
                            beanShooters = Arrays.copyOf(beanShooters, beanShooters.length + 1);
                            beanShooters[beanShooters.length - 1] = beanShooter;
                            // 去除种植耗费的能量
                            currentSunEnergy -= sunEnergy[1];
                            break;
                        case 2:
                            // 添加防御坚果对象
                            WallNut wallNut = new WallNut(x - img_wallNuts[0].getWidth() / 2,
                                    y - img_wallNuts[0].getHeight() / 2);
                            // 数组扩容
                            wallNuts = Arrays.copyOf(wallNuts, wallNuts.length + 1);
                            wallNuts[wallNuts.length - 1] = wallNut;
                            // 去除种植耗费的能量
                            currentSunEnergy -= sunEnergy[2];
                            break;
                        case 3:
                            // 添加爆炸樱桃对象
                            CherryBomb cherryBomb = new CherryBomb(x - img_cherryBombs[0].getWidth() / 2,
                                    y - img_cherryBombs[0].getHeight() / 2);
                            // 数组扩容
                            cherryBombs = Arrays.copyOf(cherryBombs, cherryBombs.length + 1);
                            cherryBombs[cherryBombs.length - 1] = cherryBomb;
                            // 去除种植耗费的能量
                            currentSunEnergy -= sunEnergy[3];
                            break;
                        case 4:
                            // 添加土豆地雷对象
                            PotatoMine potatoMine = new PotatoMine(x - img_potatoMines[0].getWidth() / 2,
                                    y - img_potatoMines[0].getHeight() / 2);
                            // 数组扩容
                            potatoMines = Arrays.copyOf(potatoMines, potatoMines.length + 1);
                            potatoMines[potatoMines.length - 1] = potatoMine;
                            // 去除种植耗费的能量
                            currentSunEnergy -= sunEnergy[4];
                            break;
                        case 5:
                            // 添加滚动坚果对象
                            WallNutRoll wallNut_roll = new WallNutRoll(x - img_wallNuts_roll[0].getWidth() / 2,
                                    y - img_wallNuts_roll[0].getHeight() / 2);
                            // 数组扩容
                            wallNutsRoll = Arrays.copyOf(wallNutsRoll, wallNutsRoll.length + 1);
                            wallNutsRoll[wallNutsRoll.length - 1] = wallNut_roll;
                            // 去除种植耗费的能量
                            currentSunEnergy -= sunEnergy[5];
                            break;
                        default:
                            break;
                    }
                    // 放置植物后置nowToBePlanted和shadow为空
                    nowToBePlanted = null;
                    shadow = null;
                }
            }
        }
        // 2. 点击阳光
        // 依次遍历太阳数组判断阳光是否被收集 (注意收集条件：未待铲除且未待种植)
        if (!shovel_used && nowToBePlanted == null) {
            for (Sun sun : suns) {
                // 注意：这里先不从太阳数组中去除该太阳对象
                if (!sun.collected && sun.isCollected(x, y)) {
                    currentSunEnergy += 50;
                }
            }
        }
        // 3. 铲除植物 (种植区域内)
        if (shovel_used && (x > 250 && x < 970 && y > 55 && y < 570)) {
            // 根据铁铲点击位置铲除植物
            removePlant(x, y);
            // 恢复铁铲位置
            shovel.resetPositionToBank();
            // 标记未使用铁铲
            shovel_used = false;
            // 阴影区域去除
            shadow = null;
        }
        // 4. 点击铁铲槽(470,15) 注意：3与4的先后位置，且点击铲槽时不能有待种植的植物
        if (nowToBePlanted == null && x >= 470 && x <= 470 + img_shovelBank.getWidth() && y >= 15
                && y <= 15 + img_shovelBank.getHeight())
        // 更改铁铲使用状态
        {
            shovel_used = !shovel_used;
        }
        // 5. 点击场景切换按钮
        // 夜晚场景
        if (x >= 900 && x <= 900 + img_night_button.getWidth() && y >= 10 && y <= 10 + img_night_button.getHeight()) {
            isDaytime = false;
        }
        // 白天场景
        if (x >= 1000 && x <= 1000 + img_daytime_button.getWidth() && y >= 10 && y <= 10 + img_daytime_button.getHeight()) {
            isDaytime = true;
        }
        // 冰冻场景
        if (x >= 1100 && x <= 1100 + img_snow_button.getWidth() && y >= 10 && y <= 10 + img_snow_button.getHeight()) {
            isIceMode = !isIceMode;
        }
    }

    /**
     * 判断某区域内是否有某类植物
     *
     * @return
     */
    private boolean isPlacedToBlockSingle(int x, int y, BaseMovingObject[] plants) {
        // 网格宽度
        int xStep = (x_right - x_left) / columnsNum;
        // 网格高度
        int yStep = (y_down - y_up) / rowsNum;
        // 遍历太阳花
        for (BaseMovingObject plant : plants) {
            // 植物中心X坐标
            int plantX = plant.x + plant.width / 2;
            // 植物中心Y坐标
            int plantY = plant.y + plant.height / 2;
            if (plantX >= x && plantX < x + xStep && plantY >= y && plantY < y + yStep) {
                return true;
            }
        }
        return false;
    }

    // 辅助方法：判断某区域(以左上角x,y坐标给出)是否存在植物
    private boolean isPlacedToBlock(int x, int y) {
        //判断区域内是否存在太阳花、豌豆射手、防御坚果、樱桃炸弹、土豆地雷
        return isPlacedToBlockSingle(x, y, sunFlowers) || isPlacedToBlockSingle(x, y, beanShooters)
                || isPlacedToBlockSingle(x, y, wallNuts) || isPlacedToBlockSingle(x, y, cherryBombs)
                || isPlacedToBlockSingle(x, y, potatoMines);
    }

    /**
     * 根据铁铲点击位置铲除植物
     *
     * @param x 横坐标
     * @param y 纵坐标
     */
    private void removePlant(int x, int y) {
        int x_left = 250;
        int x_right = 970;
        int y_up = 55;
        int y_down = 570;
        // 行数
        int rowsNum = 5;
        // 列数
        int columnsNum = 9;
        // 网格宽度
        int xStep = (x_right - x_left) / columnsNum;
        // 网格高度
        int yStep = (y_down - y_up) / rowsNum;
        // 获取铲除区域左上角坐标
        Point removePoint = positionToBlock(x, y);
        int removeX = removePoint.x;
        int removeY = removePoint.y;
        // 遍历太阳花
        for (SunFlower sunFlower : sunFlowers) {
            // 植物中心X坐标
            int plantX = sunFlower.x + sunFlower.width / 2;
            // 植物中心Y坐标
            int plantY = sunFlower.y + sunFlower.height / 2;
            if (plantX >= removeX && plantX < removeX + xStep && plantY >= removeY && plantY < removeY + yStep) {
                // 去除植物对象
                // 注意这里不直接去除植物对象，而是设置植物生命值为0，由clearObjects()方法完成植物清除操作
                sunFlower.life = 0;
                // 回收能量值
                currentSunEnergy += sunEnergy[0];
                break;
            }
        }
        // 遍历豌豆射手
        for (BeanShooter beanShooter : beanShooters) {
            // 植物中心X坐标
            int plantX = beanShooter.x + beanShooter.width / 2;
            // 植物中心Y坐标
            int plantY = beanShooter.y + beanShooter.height / 2;
            if (plantX >= removeX && plantX < removeX + xStep && plantY >= removeY && plantY < removeY + yStep) {
                // 去除植物对象
                // 注意这里不直接去除植物对象，而是设置植物生命值为0，由clearObjects()方法完成植物清除操作
                beanShooter.life = 0;
                // 回收能量值
                currentSunEnergy += sunEnergy[1];
                break;
            }
        }
        // 遍历防御坚果
        for (WallNut wallNut : wallNuts) {
            // 植物中心X坐标
            int plantX = wallNut.x + wallNut.width / 2;
            // 植物中心Y坐标
            int plantY = wallNut.y + wallNut.height / 2;
            if (plantX >= removeX && plantX < removeX + xStep && plantY >= removeY && plantY < removeY + yStep) {
                // 去除植物对象
                // 注意这里不直接去除植物对象，而是设置植物生命值为0，由clearObjects()方法完成植物清除操作
                wallNut.life = 0;
                // 回收能量值
                currentSunEnergy += sunEnergy[2];
                break;
            }
        }
        // 遍历樱桃炸弹
        for (CherryBomb cherryBomb : cherryBombs) {
            // 植物中心X坐标
            int plantX = cherryBomb.x + cherryBomb.width / 2;
            // 植物中心Y坐标
            int plantY = cherryBomb.y + cherryBomb.height / 2;
            if (plantX >= removeX && plantX < removeX + xStep && plantY >= removeY && plantY < removeY + yStep) {
                // 去除植物对象
                // 注意这里不直接去除植物对象，而是设置植物生命值为0，由clearObjects()方法完成植物清除操作
                cherryBomb.life = 0;
                // 回收能量值
                currentSunEnergy += sunEnergy[3];
                break;
            }
        }
        // 遍历土豆地雷
        for (PotatoMine potatoMine : potatoMines) {
            // 植物中心X坐标
            int plantX = potatoMine.x + potatoMine.width / 2;
            // 植物中心Y坐标
            int plantY = potatoMine.y + potatoMine.height / 2;
            if (plantX >= removeX && plantX < removeX + xStep && plantY >= removeY && plantY < removeY + yStep) {
                // 去除植物对象
                // 注意这里不直接去除植物对象，而是设置植物生命值为0，由clearObjects()方法完成植物清除操作
                potatoMine.life = 0;
                // 回收能量值
                currentSunEnergy += sunEnergy[4];
                break;
            }
        }
    }

    /**
     * 根据物体(植物or僵尸)中心位置x和y，确定物体所处草坪区域块的行号和列号 (行：5 列9)
     *
     * @param x 横坐标
     * @param y 纵坐标
     * @return 草坪行列号
     */
    private int[] posToRowAndColumn(int x, int y) {
        int row = -1, column = -1;
        // 网格宽度
        int x_step = (x_right - x_left) / columnsNum;
        // 网格高度
        int y_step = (y_down - y_up) / rowsNum;
        x -= x_left;
        y -= y_up;
        // 确定列
        for (int i = 0; i < columnsNum; i++) {
            if (x >= i * x_step && x < (i + 1) * x_step) {
                column = i;
                break;
            }
        }
        // 确定行 0-103-206-309-412-515
        for (int i = 0; i < rowsNum; i++) {
            if (y >= i * y_step && y < (i + 1) * y_step) {
                row = i;
                break;
            }
        }
        // 标准化后的位置(行+列)
        return new int[]{row, column};
    }

    /**
     * 根据植物放置时点击的鼠标位置x和y，确定植物放置的区域块（共45块）
     *
     * @param x 横坐标
     * @param y 纵坐标
     * @return 植物中心位置
     */
    private Point positionToBlock(int x, int y) {
        // 网格宽度
        int x_step = (x_right - x_left) / columnsNum;
        // 网格高度
        int y_step = (y_down - y_up) / rowsNum;
        int[] array = posToRowAndColumn(x, y);
        int row = array[0];
        int column = array[1];
        // 标准化后的位置
        return new Point(x_left + (2 * column * x_step + x_step) / 2, y_up + (2 * row * y_step + y_step) / 2);
    }

    @Override
    public void mousePressed(MouseEvent e) {

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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        // 若有待种植植物，植物跟随鼠标移动
        // 注意放置的区域范围在草坪上
        if (nowToBePlanted != null) {
            // 实时更新植物位置
            nowToBePlanted.updatePosition(x - nowToBePlanted.width / 2, y - nowToBePlanted.height / 2);
            // 在待种植区域显示阴影
            showShadow(x, y);
        }
        // 更新铁铲位置
        if (shovel_used) {
            // 在待种植区域显示阴影
            showShadow(x, y);
            // 更新铁铲位置
            shovel.updatePosition(x - shovel.width / 2, y - shovel.height / 2);
        }
    }

    /**
     * 在待种植区域显示阴影
     *
     * @param x 横坐标
     * @param y 纵坐标
     */
    private void showShadow(int x, int y) {
        if (x > 250 && x < 970 && y > 55 && y < 570) {
            Point plantPoint = positionToBlock(x, y);
            int plantX = plantPoint.x;
            int plantY = plantPoint.y;
            // 生成种植区域阴影
            shadow = new Shadow(plantX - img_shadow.getWidth() / 2, plantY - img_shadow.getHeight() / 2);
        } else {
            shadow = null;
        }
    }
}