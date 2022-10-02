package Main;

import java.awt.*;
import java.util.ArrayList;

import Data.Vector2D;
import Data.collisionBox;
import Data.spriteInfo;
import logic.Control;
import timer.stopWatchX;

public class Main {
    // Fields (Static) below...
    public static Color c = new Color(255, 255, 255);
    public static stopWatchX walkingSpeed = new stopWatchX(1);
    public static stopWatchX animationSpeed = new stopWatchX(60); //60
    public static spriteInfo current = new spriteInfo(new Vector2D(500, 330), "horse1d");

    //Move right (d)
    public static ArrayList<spriteInfo> spritesD = new ArrayList<>();
    public static int currentSpriteIndexD = 0;
    public static boolean isDpressed = false;

    //Move left (a)
    public static ArrayList<spriteInfo> spritesA = new ArrayList<>();
    public static int currentSpriteIndexA = 0;
    public static boolean isApressed = false;

    //Move up (w)
    public static ArrayList<spriteInfo> spritesW = new ArrayList<>();
    public static int currentSpriteIndexW = 0;
    public static boolean isWpressed = false;
    public static boolean faceForward = false;

    //Move down(d)
    public static ArrayList<spriteInfo> spritesS = new ArrayList<>();
    public static int currentSpriteIndexS = 0;
    public static boolean isSpressed = false;

    //items and background stuff
    public static boolean isSpacePressed = false;
    public static ArrayList<spriteInfo> backGroundItems = new ArrayList<>();

    //Collision box for borders
    public static ArrayList<collisionBox> collisionBoxes = new ArrayList<>();


    // End Static fields...
    public static void main(String[] args) {
        Control ctrl = new Control();                // Do NOT remove!
        ctrl.gameLoop();                            // Do NOT remove!
    }

    /* This is your access to things BEFORE the game loop starts */
    public static void start() {
        // TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)

        //background and items into collection
        backGroundItems.add(new spriteInfo(new Vector2D(0,0),"background"));
        backGroundItems.add(new spriteInfo(new Vector2D(400,190),"sign"));
        backGroundItems.add(new spriteInfo(new Vector2D(190,300),"chest"));

        // border collision
        collisionBoxes.add(new collisionBox(0, 1280, 0, 10));     // 0 - top border
        collisionBoxes.add(new collisionBox(0, 10, 0, 720));      // 1 - left border
        collisionBoxes.add(new collisionBox(0, 1280, 600, 720));  // 2 bottom border
        collisionBoxes.add(new collisionBox(1150, 1280, 0, 720)); // 3 - right border

        // item collision
        collisionBoxes.add(new collisionBox(420, 460, 150, 230)); // 4 - sign
        collisionBoxes.add(new collisionBox(155, 230, 260, 310)); // 4 - chest


        // d frames
        int spriteNumberD = 1;
        for (int j = 0; j < (1280); j += 5) {

            Vector2D location = new Vector2D(j, 0);

            String currentSpriteD = "horse" + spriteNumberD + "d";
            spriteInfo sprite = new spriteInfo(location, currentSpriteD);
            spritesD.add(sprite);

            spriteNumberD++;
            if (spriteNumberD == 5) {
                spriteNumberD = 1;
            }
        }

        // a frames
        int spriteNumberA = 1;
        for (int j = 1100; j != 0; j -= 5) {

            Vector2D location = new Vector2D(j, 0);
            String currentSpriteA = "horse" + spriteNumberA + "a";
            spriteInfo sprite = new spriteInfo(location, currentSpriteA);
            spritesA.add(sprite);

            spriteNumberA++;
            if (spriteNumberA == 5) {
                spriteNumberA = 1;
            }
        }

        // w frames
        int spriteNumberW = 1;
        for (int i = 720; i != 0; i -= 5) {
            Vector2D location = new Vector2D(0, i);
            String currentSpriteW = "horse" + spriteNumberW + "w";
            spriteInfo sprite = new spriteInfo(location, currentSpriteW);
            spritesW.add(sprite);

            spriteNumberW++;
            if (spriteNumberW == 5) {
                spriteNumberW = 1;
            }

        }

        // s frames
        int spriteNumberS = 1;
        for (int i = 0; i < 720; i += 5) {
            Vector2D location = new Vector2D(0, i);
            String currentSpriteS = "horse" + spriteNumberS + "s";
            spriteInfo sprite = new spriteInfo(location, currentSpriteS);
            spritesS.add(sprite);

            spriteNumberS++;
            if (spriteNumberS == 5) {
                spriteNumberS = 1;
            }
        }
    }

    /* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
    public static void update(Control ctrl) {
        //background image TODO: container this
        ctrl.addSpriteToFrontBuffer(backGroundItems.get(0).getCoords().getX(), backGroundItems.get(0).getCoords().getY(), backGroundItems.get(0).getTag());
        ctrl.addSpriteToFrontBuffer(backGroundItems.get(1).getCoords().getX(), backGroundItems.get(1).getCoords().getY(), backGroundItems.get(1).getTag());
        ctrl.addSpriteToFrontBuffer(backGroundItems.get(2).getCoords().getX(), backGroundItems.get(2).getCoords().getY(), backGroundItems.get(2).getTag());

        //movement frames w/a/s/d
        spriteInfo moveUP = spritesW.get(currentSpriteIndexW);
        spriteInfo moveLeft = spritesA.get(currentSpriteIndexA);
        spriteInfo moveRight = spritesD.get(currentSpriteIndexD);
        spriteInfo moveDown = spritesS.get(currentSpriteIndexS);

        //sign item
        collisionBox sign = collisionBoxes.get(4);
        collisionBox itemCheck = new collisionBox(current.getCoords().getX() - 20, current.getCoords().getX() + 20, current.getCoords().getY() - 30, current.getCoords().getY() + 20);
        if (isThereCollision(itemCheck, sign) && faceForward) {
            if (isSpacePressed)
                ctrl.drawString(current.getCoords().getX() + 15, current.getCoords().getY() + 15, "This is a sign that reads 'Hello'", c);
        }

        //chest item
        collisionBox chest = collisionBoxes.get(5);
        if (isThereCollision(itemCheck, chest) && faceForward) {
            if (isSpacePressed)
                ctrl.drawString(current.getCoords().getX() + 50, current.getCoords().getY() + 15, "You have found a chest that is empty :(", c);
        }


        //w
        if (isWpressed) {

            collisionBox tmp = new collisionBox(current.getCoords().getX() - 20, current.getCoords().getX() + 20, current.getCoords().getY() - 30, current.getCoords().getY() + 20);
            if (walkingSpeed.isTimeUp() && !isThereCollision(tmp)) {
                current.getCoords().adjustY(-2);
                walkingSpeed.resetWatch();
            }

            if (animationSpeed.isTimeUp()) {
                current.setTag(moveUP.getTag());
                currentSpriteIndexW++;
                animationSpeed.resetWatch();
            }

            if (currentSpriteIndexW == spritesW.size()) {
                currentSpriteIndexW = 0;
            }
        }

        //a
        if (isApressed) {

            collisionBox tmp = new collisionBox(current.getCoords().getX() - 30, current.getCoords().getX() + 20, current.getCoords().getY() - 20, current.getCoords().getY() + 20);
            if (walkingSpeed.isTimeUp() && !isThereCollision(tmp)) {
                current.getCoords().adjustX(-2);
                walkingSpeed.resetWatch();
            }

            if (animationSpeed.isTimeUp()) {
                current.setTag(moveLeft.getTag());
                currentSpriteIndexA++;
                animationSpeed.resetWatch();
            }

            if (currentSpriteIndexA == spritesA.size()) {
                currentSpriteIndexA = 0;
            }
        }

        //s
        if (isSpressed) {

            collisionBox tmp = new collisionBox(current.getCoords().getX() - 20, current.getCoords().getX() + 20, current.getCoords().getY() - 20, current.getCoords().getY() + 30);
            if (walkingSpeed.isTimeUp() && !isThereCollision(tmp)) {
                current.getCoords().adjustY(2);
                walkingSpeed.resetWatch();
            }

            if (animationSpeed.isTimeUp()) {
                current.setTag(moveDown.getTag());
                currentSpriteIndexS++;
                animationSpeed.resetWatch();
            }

            if (currentSpriteIndexS == spritesS.size()) {
                currentSpriteIndexS = 0;
            }
        }

        //d
        if (isDpressed) {

            collisionBox tmp = new collisionBox(current.getCoords().getX() - 20, current.getCoords().getX() + 30, current.getCoords().getY() - 20, current.getCoords().getY() + 20);
            if (walkingSpeed.isTimeUp() && !isThereCollision(tmp)) {
                current.getCoords().adjustX(2);
                walkingSpeed.resetWatch();
            }

            if (animationSpeed.isTimeUp()) {
                current.setTag(moveRight.getTag());
                currentSpriteIndexD++;
                animationSpeed.resetWatch();
            }

            if (currentSpriteIndexD == spritesD.size()) {
                currentSpriteIndexD = 0;
            }
        }

        ctrl.addSpriteToFrontBuffer(current.getCoords().getX(), current.getCoords().getY(), current.getTag());
    }

    // Additional Static methods below...(if needed)
    public static boolean isThereCollision(collisionBox box2) {
        boolean flag = false;

        for (int i = 0; i < collisionBoxes.size(); i++) {
            if ((collisionBoxes.get(i).getX1() > box2.getX2()) || (collisionBoxes.get(i).getX2() < box2.getX1()) || (collisionBoxes.get(i).getY1() > box2.getY2()) || (collisionBoxes.get(i).getY2() < box2.getY1())) {
                flag = false;
            } else {
                //System.out.println("collision detected");
                return true;
            }
        }

        //box1.X1 > box2.X2
        //box1.X2 < box2.X1
        //box1.Y1 > box2.Y2
        //box1.Y2 < box2.Y1
        // if any of these conditions are true no collision
        // if all of them are false we have a collision
        return flag;
    }

    public static boolean isThereCollision(collisionBox box1, collisionBox box2) {
        boolean flag = false;

        if ((box1.getX1() > box2.getX2()) || (box1.getX2() < box2.getX1()) || (box1.getY1() > box2.getY2()) || (box1.getY2() < box2.getY1())) {
            flag = false;
        } else {
            //System.out.println("collision detected");
            return true;
        }

        return flag;
    }


}
