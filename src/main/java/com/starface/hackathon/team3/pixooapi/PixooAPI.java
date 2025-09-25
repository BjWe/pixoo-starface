package com.starface.hackathon.team3.pixooapi;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PixooAPI {
    private final String host;
    private final boolean verbose;

    public PixooAPI(String host, boolean verbose) {
        this.host = host;
        this.verbose = verbose;
    }

    // --- FrameBuffer senden ---
    public void sendFrameBuffer(FrameBuffer fb, int picId, int picNum, int picOffset, int picSpeed) throws Exception {
        String b64 = fb.toBase64();

        String json = "{"
                + "\"Command\":\"Draw/SendHttpGif\","
                + "\"PicNum\":"+ (picNum) +","
                + "\"PicWidth\":" + fb.getWidth() + ","
                + "\"PicOffset\":"+(picOffset)+","
                + "\"PicID\":" + (picId) + ","
                + "\"PicSpeed\":"+(picSpeed)+","
                + "\"PicData\":\"" + b64 + "\""
                + "}";

        postJson(json);
    }

    // --- Brightness einstellen (0-100) ---
    public void setBrightness(int brightness) throws Exception {
        brightness = Math.max(0, Math.min(100, brightness));
        String json = "{"
                + "\"Command\":\"Channel/SetBrightness\","
                + "\"Brightness\":" + brightness
                + "}";
        postJson(json);
    }

    // --- Kanal setzen (0 = Uhr, 1 = Cloud, 2 = Visualizer, 3 = Custom) ---
    public void setChannel(int index) throws Exception {
        String json = "{"
                + "\"Command\":\"Channel/SetIndex\","
                + "\"SelectIndex\":" + index
                + "}";
        postJson(json);
    }

    // --- Zeit setzen (Sekunden seit Unix Epoch) ---
    public void setTime(long epochSeconds) throws Exception {
        String json = "{"
                + "\"Command\":\"Device/SetTime\","
                + "\"Time\":" + epochSeconds
                + "}";
        postJson(json);
    }

    // --- PicID zurücksetzen ---
    public void resetPicId(int picId) throws Exception {
        String json = "{"
                + "\"Command\":\"Draw/ResetHttpGifId\""
                + "}";
        postJson(json);
    }

    public void resetPicId() throws Exception{
        resetPicId(1);
    }

    // --- Hilfsmethode für alle POSTs ---
    private void postJson(String json) throws Exception {
        URL url = new URL("http://" + host + ":80/post");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        if (verbose) {
            System.out.println("➡️  POST: " + json);
        }

        try (OutputStream os = con.getOutputStream()) {
            os.write(json.getBytes());
        }

        int code = con.getResponseCode();
        if (verbose) {
            System.out.println("⬅️  HTTP " + code);
        }
        con.disconnect();
    }
}