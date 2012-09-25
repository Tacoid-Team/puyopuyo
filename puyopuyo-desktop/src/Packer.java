

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class Packer {
    public static void main (String[] args) {
        TexturePacker2.process("images/puyos", "images/puyos", "pages");
        TexturePacker2.process("images/controls", "images/controls", "pages");
        TexturePacker2.process("images/menu/plank-fr", "images/menu/plank-fr", "pages");
        TexturePacker2.process("images/panels/landscape", "images/panels/landscape", "pages");
        TexturePacker2.process("images/panels/portrait", "images/panels/portrait", "pages");
        TexturePacker2.process("images/bouttons", "images/bouttons", "pages");
    }
}
