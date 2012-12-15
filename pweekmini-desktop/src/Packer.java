

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class Packer {
    public static void main (String[] args) {
        TexturePacker2.process("/home/max/git/puyopuyo/pweekmini-android/assets/images/puyos", "/home/max/git/puyopuyo/pweekmini-android/assets/images/puyos", "pages");
        TexturePacker2.process("/home/max/git/puyopuyo/pweekmini-android/assets/images/menu/plank-fr", "/home/max/git/puyopuyo/pweekmini-android/assets/images/menu/plank-fr", "pages");
        TexturePacker2.process("/home/max/git/puyopuyo/pweekmini-android/assets/images/menu/plank-en", "/home/max/git/puyopuyo/pweekmini-android/assets/images/menu/plank-en", "pages");
        TexturePacker2.process("/home/max/git/puyopuyo/pweekmini-android/assets/images/panels/portrait", "/home/max/git/puyopuyo/pweekmini-android/assets/images/panels/portrait", "pages");
        TexturePacker2.process("/home/max/git/puyopuyo/pweekmini-android/assets/images/bouttons", "/home/max/git/puyopuyo/pweekmini-android/assets/images/bouttons", "pages");
    }
}
