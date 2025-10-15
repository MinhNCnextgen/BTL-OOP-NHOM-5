//package org.askeroid.objects.gameObjects.PowerUp;
//
//import javafx.scene.canvas.Canvas;
//import org.askeroid.objects.gameObjects.GameObject;
//import org.askeroid.objects.gameObjects.Paddle;
//import org.askeroid.scenes.PlayScreen;
//
//import java.util.ArrayList;
//
//public abstract class PUObject extends GameObject {
//
//    public static ArrayList<PUObject> PUList = new ArrayList<PUObject>();
//    public PUObject(double x, double y, double width, double height, double speed) {
//        super(x, y, width, height, speed);
//    }
//
//    public void update(double dt, Canvas canvas, Paddle paddle, PlayScreen ps) {
//        dt /= 1_000_000_000.0;
//        y += speed * dt;
//        if (intersects(paddle)) {
//            grantBuff(ps);
//        }
//    }
//
//    public static void spawnPU(double x, double y, double width, double height, double speed, PUType type) {
//        PUObject pu = null;
//        switch (type) {
//            case WIDEPADDLE:
//                pu = new WidePaddlePU(x, y, width, height, speed);
//                break;
//            case FASTBALL:
//                pu = new FastBallPU(x, y, width, height, speed);
//                break;
//            case MULTIBALL:
//                pu = new MultiBallPU(x, y, width, height, speed);
//                break;
//            case LASER:
//                pu = new LaserPU(x, y, width, height, speed);
//                break;
//            case STICKY:
//                pu = new StickyPaddlePU(x, y, width, height, speed);
//                break;
//            case SLOWBALL:
//                pu = new SlowBallPU(x, y, width, height, speed);
//                break;
//            case EXTRA_LIFE:
//                pu = new ExtraLifePU(x, y, width, height, speed);
//                break;
//        }
//        if (pu != null) {
//            PUList.add(pu);
//        }
//    }
//
//    protected abstract void grantBuff(PlayScreen ps);
//}
