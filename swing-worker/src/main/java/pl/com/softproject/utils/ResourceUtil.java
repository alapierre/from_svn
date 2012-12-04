package pl.com.softproject.utils;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;

/**
 * @author Sebastian Lukasiewicz (slukasiewicz@soft-project.pl)
 */
public class ResourceUtil {

    protected static final Logger logger = Logger.getLogger(ResourceUtil.class);
    
    private static Map<String, Image>     cacheImage     = new HashMap<String, Image>();
    private static Map<String, ImageIcon> cacheImageIcon = new HashMap<String, ImageIcon>();
    
    public synchronized static ImageIcon getImageIcon(String name) {
        try {
            if(cacheImageIcon.containsKey(name)) {
                return cacheImageIcon.get(name);
            } else {
                Image     image     = ImageIO.read(getResourceAsStream(name));
                ImageIcon imageIcon = new ImageIcon(image);
                cacheImageIcon.put(name, imageIcon);
                return imageIcon;
            }
        } catch(IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public synchronized static Image getImage(String name) {
        try {
            if(cacheImage.containsKey(name)) {
                return cacheImage.get(name);
            } else {
                Image image = ImageIO.read(getResourceAsStream(name));
                cacheImage.put(name, image);
                return image;
            }
        } catch(IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public synchronized static InputStream getResourceAsStream(String fileName) {
        if(logger.isDebugEnabled()) {
            logger.debug("resource to load : " + fileName);
        }
        InputStream is = ResourceUtil.class.getClassLoader().getResourceAsStream(fileName);
        return is;
    }

}
