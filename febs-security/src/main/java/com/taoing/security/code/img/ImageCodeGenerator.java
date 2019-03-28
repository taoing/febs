package com.taoing.security.code.img;

import com.taoing.security.code.ValidateCodeGenerator;
import com.taoing.security.properties.FebsSecurityProperties;
import com.taoing.security.properties.ImageCodeProperties;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageCodeGenerator implements ValidateCodeGenerator {

    private FebsSecurityProperties securityProperties;

    @Override
    public ImageCode createCode() {
        ImageCodeProperties imageCodeProperties = securityProperties.getCode().getImage();
        int width = imageCodeProperties.getWidth(); // 验证码图片宽度
        int height = imageCodeProperties.getHeight(); // 验证码图片高度
        int length = imageCodeProperties.getLength(); // 验证码位数
        int expireIn = imageCodeProperties.getExpireIn(); // 验证码有效时间
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 38));
        g.setColor(getRandColor(150, 200));
        // 绘制验证码图片背景
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(250);
            int y1 = random.nextInt(250);
            g.drawLine(x, y, x + x1, y + y1);
        }
        // 绘制验证码字符串
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            g.setColor(new Color(10 + random.nextInt(110), 10 + random.nextInt(110),
                    10 + random.nextInt(110)));
            g.drawString(rand, 18 * i + 12, 32);
        }
        g.dispose();
        return new ImageCode(image, sRand.toString(), expireIn);
    }

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public FebsSecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(FebsSecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
