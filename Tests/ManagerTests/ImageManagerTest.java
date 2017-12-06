package ManagerTests;

import Manager.ImageManager;
import org.junit.Test;

public class ImageManagerTest {

    @Test
    public void testImageLoading(){
        ImageManager imageManager = new ImageManager();
        imageManager.getImage("G");
        imageManager.getImage("L1");
        imageManager.getImage("L2");
        imageManager.getImage("1");
        imageManager.getImage("2");
        imageManager.getImage("3");
    }
}
