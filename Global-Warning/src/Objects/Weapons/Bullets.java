package Objects.Weapons;

import GameStates.Playing;

import static Utilities.Constants.GAME_WIDTH;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Bullets implements MouseListener {

    private double x, y, speed;
    private double directionX, directionY;
    private Weapon1 weapon; 
    private Playing playing; 
    public boolean SHOT = false; 

    public Bullets(Weapon1 weapon, Playing playing, double startX, double startY, double targetX, double targetY) {
        this.weapon = weapon; 
        this.playing = playing;
        this.x = startX;
        this.y = startY;
        this.speed = 5.0;
        setDirection(targetX, targetY);
    }

    public void setDirection(double targetX, double targetY) {
        double angle = Math.atan2(targetY - y, targetX - x);
        this.directionX = Math.cos(angle);
        this.directionY = Math.sin(angle);
    }

    public void move() {
        x += speed * directionX;
        y += speed * directionY;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g; 
        int drawX = (int) Math.round(x);
        int drawY = (int) Math.round(y);
        // System.out.println(drawX);
        g2d.setColor(Color.RED);
        g2d.fillOval(drawX - 5, drawY - 5, 10, 10);
        g2d.setColor(Color.BLACK);
        if(drawX >= weapon.x+400 || drawX >= GAME_WIDTH || drawX <= 0  || drawX <= weapon.x-400)
        playing.removeBullet();
    }

    public void updateBullets() {
        updateBulletDirections((int) playing.mouseX, (int) playing.mouseY);
        for (Bullets bullet : playing.bullets) {
            bullet.move();
        }
    }

    private void updateBulletDirections(int mouseX, int mouseY) {
        for (Bullets bullet : playing.bullets) {
            bullet.setDirection(mouseX, mouseY);
        }
    }

    public void setSHOT (boolean SHOT){
        this.SHOT = SHOT;
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

}
