package Manager;

import Entity.ImageProxy;
import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;

public class ImageManager {

    private HashMap<String, ImageProxy> images;

    public ImageManager(){
        images = new HashMap<>();
        images.put("G", new ImageProxy("/Database/00_thegroundfloor.png","G"));
        images.put("L1", new ImageProxy("/Database/00_thelowerlevel1.png","L1"));
        images.put("L2", new ImageProxy("/Database/00_thelowerlevel2.png","L2"));
        images.put("1", new ImageProxy("/Database/01_thefirstfloor.png","1"));
        images.put("2", new ImageProxy("/Database/02_thesecondfloor.png","2"));
        images.put("3", new ImageProxy("/Database/03_thethirdfloor.png","3"));
    }

    public Image getImage(String floor){
        return images.get(floor).getImage();
    }

    public void setImage(String floor, File file){
        //Image newFloor = ImageIO.read();
    }
}
