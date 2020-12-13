package game_object.obstacles;

import animations.ExplosionEffect;
import animations.PurpleExplosionEffect;
import animations.TimeEffect;
import game_object.PlayerShip;
import game_object.bullets.Bullet;
import game_object.bullets.GrenadeBullet;
import javafx.scene.media.AudioClip;
import util.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

//TODO another interface needed for sprite and Obstacle
abstract public class Obstacle implements Drawable, Serializable
{
    private static double MAX_EXPLOSION_VELOCITY_X = 22;
    private static double MAX_EXPLOSION_VELOCITY_Y = 14;
    private static double MAX_SPEED = 10;
    private static double MIN_SPEED=1;
    private static int MAX_LEVEL = 200;
    private static Random rand;

    private int level;
    private double speed;
    private Vector position;
    private boolean isDestroyed;
    private boolean isDouble;
    //sounds
    private static AudioClip timeBulletSound;
    private static AudioClip bulletHitSound;
    private static AudioClip destroySound;
    private static AudioClip destroyByShieldSound;
    private static AudioClip tickTickSound;
    //sounds


    public boolean isDouble()
    {
        return isDouble;
    }

    public void setDouble(boolean aDouble)
    {
        isDouble = aDouble;
    }

    public void setCW(boolean CW)
    {

    }

    public void calcSpeedFromLevel()
    {
        double newSpeed = MIN_SPEED + (MAX_SPEED-MIN_SPEED)*((double)level/MAX_LEVEL);
        setSpeed(newSpeed);
//        System.out.println(speed);
    }

    public boolean isDestroyed()
    {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed)
    {
        isDestroyed = destroyed;
    }

    private ObstacleSegment[] segments;

    static{
        rand = new Random();
        //init sounds
        bulletHitSound = new AudioClip("file:res/sound/bullet_hit.mp3");
        timeBulletSound = new AudioClip("file:res/sound/time_effect.mp3");
        destroyByShieldSound = new AudioClip("file:res/sound/protection_destroy_obstacle.mp3");
        tickTickSound = new AudioClip("file:res/sound/time_effect.mp3");
//        destroyByShieldSound = new AudioClip("file:res/sound/protection_destroy_obstacle.mp3");
    }

    public Vector getPosition()
    {
        return position;
    }

    abstract public void update();

    public void shiftPosition(Vector delta)
    {
        getPosition().subtract(delta);
        for(int i=0 ;i<4; i++)
        {
            getSegments()[i].shiftPosition(delta);
        }
    }

    abstract public boolean isInFrame();


    public void setPosition(double x, double y)
    {
        position.set(x,y);
        for(int i=0; i<4; i++)
        {
            ObstacleSegment segment = getSegments()[i];
            segment.setPosition(this.getPosition());
        }
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public double getSpeed()
    {
        return speed;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public ObstacleSegment[] getSegments()
    {
        return segments;
    }

    public void setSegments(ObstacleSegment[] segments)
    {
        this.segments = segments;
    }

    public Obstacle(int level)
    {
        this.level=level;
        isDestroyed=false;
        position = new Vector(0,0);
        segments = new ObstacleSegment[4];
        for(int i=0; i<4; i++)
        {
            //init Obstacle Segment objects
            segments[i] = new ObstacleSegment();
        }
        calcSpeedFromLevel();
        isDouble=false;

    }

    public boolean didCollisionWithShip(PlayerShip ship)
    {

        //check for ship collision
        GameColor collisionColor = didCollide(ship);
        if(collisionColor == ship.getColor()) // passing through
        {
            //can add effects here
            //TODO beware! multiple executions!
//            ship.addScore(50);

        }
        else if(collisionColor == GameColor.NONE) // not interacting with the obstacle
        {
            //do nothing
        }
        else // collided with obstacle
        {
            if(ship.isProtected())
            {
                ship.addEffect(new PurpleExplosionEffect(ship.getPosition()));
                destroyByShieldSound.play();
                destroy();
            }
            else
            {
                return true;
            }

        }
        //check missiles

        ArrayList<Bullet> bullets = ship.getBulletsFired();

        if(!bullets.isEmpty())
        {
            Iterator itr = bullets.iterator();

            while (itr.hasNext())
            {
                Bullet current = (Bullet)itr.next();
                if(didCollide(current)!=GameColor.NONE) // collided
                {
                    if(current.getClass() == GrenadeBullet.class) // grenade bullet
                    {
                        destroy();
                        segments[0].addEffect(new ExplosionEffect(current.getPosition()));
                        ship.addScore(200);
                        bulletHitSound.play();
                    }
                    else // ice bullet
                    {
                        timeBulletSound.play();
                        tickTickSound.play();
                        //slow down obstacle
                        slow();
                        this.segments[0].addEffect(new TimeEffect(current.getPosition()));
                        ship.addScore(100);
                        //TODO implement this and use speed in rotating obstacle to get delta angle
                    }

                    if(!isDouble)
                    {
                        current.destroy();
                        itr.remove();
                    }
                }
            }
        }




        return false;
    }


    public void slow()
    {
        this.setSpeed(this.getSpeed()/2);
    }

    public GameColor didCollide(Sprite other)
    {
        if(!isDestroyed())
        {
            for (int i = 0; i < 4; i++)
            {
                if (segments[i].didCollide(other))
                {
                    return segments[i].getColor();
                }
            }
        }
        return GameColor.NONE; // if not colliding
    }

    public void destroy()
    {
        //TODO add more explosions

        //set destroyed flag
        isDestroyed=true;
        //turn on gravity for segments and set random velocities


        for(int i=0; i<4; i++)
        {
            segments[i].setActive(false);//avoid collision
            segments[i].setAcceleration(new Vector(0,0.1)); // set gravity
            segments[i].setVelocity(new Vector((rand.nextDouble()-0.5)*MAX_EXPLOSION_VELOCITY_X, (rand.nextDouble()-0.5)*MAX_EXPLOSION_VELOCITY_Y));
        }
    }
}
