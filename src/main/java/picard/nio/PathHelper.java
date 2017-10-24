package picard.nio;

import picard.cmdline.ClassFinder;

/**
 * Created by farjoun on 11/13/17.
 */
public class PathHelper {

    private PathHelper() {}

    public static boolean isHasGoogle() {
        return hasGoogle;
    }

    private static boolean hasGoogle = false;

    public static void initilizeAll() {
        ClassFinder finder = new ClassFinder();
        finder.find("",Object.class);
        // google

        if (isGoogleOnPath()) {
            hasGoogle = true;
            initGoogle();
        }
    }

    private static void initGoogle() {
        GoogleStorageUtils.initialize();
    }

    private static boolean isGoogleOnPath() {
        try {

            new GoogleStorageUtils();
            return true;
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }
}
