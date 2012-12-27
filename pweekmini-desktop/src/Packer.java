
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class Packer {
	private static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;
	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();

	        // previous code: destination.transferFrom(source, 0, source.size());
	        // to avoid infinite loops, should be:
	        long count = 0;
	        long size = source.size();              
	        while((count += destination.transferFrom(source, count, size-count))<size);
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
	
    public static void main (String[] args) {
        TexturePacker2.process("/home/max/git/puyopuyo/pweekmini/images/puyos", "/home/max/git/puyopuyo/pweekmini/images/puyos", "pages");
        TexturePacker2.process("/home/max/git/puyopuyo/pweekmini/images/menu/plank-fr", "/home/max/git/puyopuyo/pweekmini/images/menu/plank-fr", "pages");
        TexturePacker2.process("/home/max/git/puyopuyo/pweekmini/images/menu/plank-en", "/home/max/git/puyopuyo/pweekmini/images/menu/plank-en", "pages");
        TexturePacker2.process("/home/max/git/puyopuyo/pweekmini/images/panels/portrait", "/home/max/git/puyopuyo/pweekmini/images/panels/portrait", "pages");
        TexturePacker2.process("/home/max/git/puyopuyo/pweekmini/images/bouttons", "/home/max/git/puyopuyo/pweekmini/images/bouttons", "pages");
        
        try {
			copyFile(new File("/home/max/git/puyopuyo/pweekmini/images/puyos/pages.atlas"), new File("/home/max/git/puyopuyo/pweekmini-android/assets/images/puyos/pages.atlas"));
			copyFile(new File("/home/max/git/puyopuyo/pweekmini/images/puyos/pages.png"), new File("/home/max/git/puyopuyo/pweekmini-android/assets/images/puyos/pages.png"));
			
			copyFile(new File("/home/max/git/puyopuyo/pweekmini/images/menu/plank-fr/pages.atlas"), new File("/home/max/git/puyopuyo/pweekmini-android/assets/images/menu/plank-fr/pages.atlas"));
			copyFile(new File("/home/max/git/puyopuyo/pweekmini/images/menu/plank-fr/pages.png"), new File("/home/max/git/puyopuyo/pweekmini-android/assets/images/menu/plank-fr/pages.png"));
			
			copyFile(new File("/home/max/git/puyopuyo/pweekmini/images/menu/plank-en/pages.atlas"), new File("/home/max/git/puyopuyo/pweekmini-android/assets/images/menu/plank-en/pages.atlas"));
			copyFile(new File("/home/max/git/puyopuyo/pweekmini/images/menu/plank-en/pages.png"), new File("/home/max/git/puyopuyo/pweekmini-android/assets/images/menu/plank-en/pages.png"));
			
			copyFile(new File("/home/max/git/puyopuyo/pweekmini/images/panels/portrait/pages.atlas"), new File("/home/max/git/puyopuyo/pweekmini-android/assets/images/panels/portrait/pages.atlas"));
			copyFile(new File("/home/max/git/puyopuyo/pweekmini/images/panels/portrait/pages.png"), new File("/home/max/git/puyopuyo/pweekmini-android/assets/images/panels/portrait/pages.png"));
			
			copyFile(new File("/home/max/git/puyopuyo/pweekmini/images/bouttons/pages.atlas"), new File("/home/max/git/puyopuyo/pweekmini-android/assets/images/bouttons/pages.atlas"));
			copyFile(new File("/home/max/git/puyopuyo/pweekmini/images/bouttons/pages.png"), new File("/home/max/git/puyopuyo/pweekmini-android/assets/images/bouttons/pages.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
