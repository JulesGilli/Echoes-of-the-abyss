package io.github.maingame.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.tools.bmfont.BitmapFontWriter;

public class FontGenerator extends ApplicationAdapter {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(200, 200);
        config.setTitle("Font Generator");
        new Lwjgl3Application(new FontGenerator(), config);
    }

    @Override
    public void create() {
        int[] sizes = {50, 56, 64, 96};
        String fontPath = "fonts/Jacquard12-Regular.ttf";

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));

        for (int size : sizes) {
            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
            param.size = size;
            param.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "éèêëàâäùûüôöîïçÉÈÊËÀÂÄÙÛÜÔÖÎÏÇ:+-";
            param.packer = new PixmapPacker(1024, 1024, Pixmap.Format.RGBA8888, 2, false);

            BitmapFont font = generator.generateFont(param);

            String outputDir = "assets/fonts/generated/";
            Gdx.files.local(outputDir).mkdirs();

            BitmapFontWriter.FontInfo info = new BitmapFontWriter.FontInfo();
            info.face = "Jacquard12";
            info.size = size;

            int pageIndex = 0;
            for (PixmapPacker.Page page : param.packer.getPages()) {
                String pageName = "jacquard_" + size + "_" + pageIndex + ".png";
                PixmapIO.writePNG(Gdx.files.local(outputDir + pageName), page.getPixmap());
                pageIndex++;
            }

            String[] pageNames = new String[pageIndex];
            for (int i = 0; i < pageIndex; i++) {
                pageNames[i] = "jacquard_" + size + "_" + i + ".png";
            }

            BitmapFontWriter.writeFont(
                font.getData(),
                pageNames,
                Gdx.files.local(outputDir + "jacquard_" + size + ".fnt"),
                info,
                1024, 1024
            );

            Gdx.app.log("FontGen", "Generated font size " + size + " (" + pageIndex + " pages)");
            font.dispose();
            param.packer.dispose();
        }

        generator.dispose();
        Gdx.app.log("FontGen", "All fonts generated in assets/fonts/generated/");
        Gdx.app.exit();
    }
}
