package com.starface.hackathon.team3.pixooapi;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.apache.commons.codec.binary.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class FrameBuffer {
    private final int width;
    private final int height;
    private final ByteBuffer buffer;

    public FrameBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.buffer = ByteBuffer.allocate(width * height * 3);
    }

    public void clear(int rgb) {
        byte r = (byte)((rgb >> 16) & 0xFF);
        byte g = (byte)((rgb >> 8) & 0xFF);
        byte b = (byte)(rgb & 0xFF);

        for (int i = 0; i < width * height; i++) {
            int idx = i * 3;
            buffer.put(idx, r);
            buffer.put(idx + 1, g);
            buffer.put(idx + 2, b);
        }
    }

    public void setPixel(int x, int y, int rgb) {
        if (x < 0 || y < 0 || x >= width || y >= height) return;

        int idx = (y * width + x) * 3;
        buffer.put(idx,   (byte)((rgb >> 16) & 0xFF)); // R
        buffer.put(idx+1, (byte)((rgb >> 8) & 0xFF));  // G
        buffer.put(idx+2, (byte)(rgb & 0xFF));         // B
    }

    public void drawRectangle(int x, int y, int w, int h, int rgb, boolean fill) {
        if (fill) {
            for (int yy = y; yy < y + h; yy++) {
                for (int xx = x; xx < x + w; xx++) {
                    setPixel(xx, yy, rgb);
                }
            }
        } else {
            for (int xx = x; xx < x + w; xx++) {
                setPixel(xx, y, rgb);           // obere Kante
                setPixel(xx, y + h - 1, rgb);   // untere Kante
            }
            for (int yy = y; yy < y + h; yy++) {
                setPixel(x, yy, rgb);           // linke Kante
                setPixel(x + w - 1, yy, rgb);   // rechte Kante
            }
        }
    }

    public void drawPNG(int x, int y, byte[] pngBytes) {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(pngBytes));
            if (img == null) {
                throw new IllegalArgumentException("Ungültiges PNG-Bild");
            }

            int w = img.getWidth();
            int h = img.getHeight();

            for (int yy = 0; yy < h; yy++) {
                for (int xx = 0; xx < w; xx++) {
                    int argb = img.getRGB(xx, yy);

                    // nur setzen, wenn Pixel nicht transparent
                    int alpha = (argb >> 24) & 0xFF;
                    if (alpha > 0) {
                        int rgb = argb & 0xFFFFFF;
                        setPixel(x + xx, y + yy, rgb);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim PNG-Zeichnen", e);
        }
    }

    public void drawText(int x, int y, String text, int rgb) {
        int cursorX = x;
        for (char c : text.toCharArray()) {
            int[] glyph = VariFont8.getGlyph(c);

            for (int row = 0; row < 8; row++) {
                int line = glyph[row]; // 8 Bit für eine Zeile
                for (int col = 0; col < 8; col++) {
                    if (((line >> (7 - col)) & 1) == 1) {
                        setPixel(cursorX + col, y + row, rgb);
                    }
                }
            }
            cursorX += 9; // fester Abstand = 8px pro Zeichen
        }
    }

    public void drawText5x7(int x, int y, String text, int rgb) {
        int cursorX = x;
        for (char c : text.toCharArray()) {
            int[] glyph = Font5x7.getGlyph(c);
            for (int row = 0; row < 7; row++) {
                int line = glyph[row];
                for (int col = 0; col < 5; col++) {
                    if (((line >> (4 - col)) & 1) == 1) {
                        setPixel(cursorX + col, y + row, rgb);
                    }
                }
            }
            cursorX += 6; // 5 Pixel + 1 Pixel Abstand
        }
    }


    public String toBase64() {
        byte[] raw = buffer.array();
        return Base64.encodeBase64String(raw);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}