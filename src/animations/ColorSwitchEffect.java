package animations;

import javafx.scene.image.Image;
import util.Vector;

public class ColorSwitchEffect extends AnimatedEffect
{
    @Override
    public void loadFrames()
    {
        String fileBasePath = "file:res/img/effects/color_switch_effect/color_switch_";
        for(int i = 1; i<=64; i++)
        {
            String filePath = fileBasePath;
            if(i<10)
            {
                filePath+="0";
            }
            filePath= filePath + i + ".png";

            //load image
            if(i%2==0) // take only odd frames
                getFrames().add(new Image(filePath));
        }
        setImage(getFrames().get(0));
    }

    public ColorSwitchEffect(Vector position)
    {
        loadFrames();
        setImageScaleFactor(1.4);
        setPosition(position);
        setLoopFrames(false);
        setAnimationSpeed(1);
    }
}
