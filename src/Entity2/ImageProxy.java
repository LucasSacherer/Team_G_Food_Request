package Entity2;

//Is this the right import for image?
import javafx.scene.image.Image;

import java.io.InputStream;

public class ImageProxy {
    private final String path;
    private Image image;
    private final String floor;

    public ImageProxy(String path, String floor){
        this.path = path;
        this.floor = floor;
        image = null;
    }

    public Image getImage() {
        if (image == null){
            InputStream stream = ImageProxy.class.getResourceAsStream(path);
            image = new Image(stream);
        }
        return  image;
    }

    private String getFloor() {
        return floor;
    }
}
