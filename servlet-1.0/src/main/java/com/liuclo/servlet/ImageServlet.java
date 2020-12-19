package com.liuclo.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author liuclo
 * @Desciption //生成图片，并设置随机数字，每3秒刷新一次
 * @Date  2020/12/19
 **/

public class ImageServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //浏览器自动刷新
        resp.setHeader("refresh", "3");

        //在内存中创建图片
        BufferedImage bi = new BufferedImage(80, 20, BufferedImage.TYPE_INT_ARGB);
        //获取图片实例
        Graphics2D g2d = (Graphics2D) bi.getGraphics();
        //设置背景颜色
        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font(null, Font.BOLD, 20));
        g2d.drawString(getRandomNumber(), 0, 20);

        //请求方式
        resp.setContentType("image/jpg");

        //不设置缓存
        resp.setDateHeader("expires", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");

        //传给浏览器
        ImageIO.write(bi, "jpg", resp.getOutputStream());
    }

    //获取六位随机数
    private String getRandomNumber() {
        Random random = new Random();
        String s = random.nextInt(9999999) + "";
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<7-s.length(); i++) {
            sb.append("0");
        }
        s = sb.toString() + s;
        return s;
    }
}
