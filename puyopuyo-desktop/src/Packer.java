

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class Packer {
    public static void main (String[] args) {
        TexturePacker2.process("images_src/puyos", "images/puyos", "pages");
        TexturePacker2.process("images_src/controls", "images/controls", "pages");
        TexturePacker2.process("images_src/menu/plank-fr", "images/menu/plank-fr", "pages");
        TexturePacker2.process("images_src/menu/plank-en", "images/menu/plank-en", "pages");
        TexturePacker2.process("images_src/panels/landscape", "images/panels/landscape", "pages");
        TexturePacker2.process("images_src/panels/portrait", "images/panels/portrait", "pages");
        TexturePacker2.process("images_src/bouttons", "images/bouttons", "pages");
        TexturePacker2.process("images_src/google/fr", "images/google/fr", "pages");
        TexturePacker2.process("images_src/google/en", "images/google/en", "pages");
    }
}
