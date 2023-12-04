package com.nowcoder.community;

import java.io.IOException;

public class WKTests {
    public static void main(String[] args) {
        String cmd = "D:/develop_tools/wkhtmltopdf/bin/wkhtmltoimage --quality 75 https://www.nowcoder.com D:/develop_tools/data/wk-images/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
