package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RandomNumber {

	// 先通过随机数生成四位数字，然后把这四位数字生成图片，响应到客户端，返回这四位数字
	public ValidateCode generateImage() throws IOException {

		String sRand = "";//初始化字符串
		Random random = new Random();

		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;//显示随机数字
		}

		// 创建图象
		int width = 80, height = 30;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 设定背景色
		g.setColor(getRandColor(100, 250));
		// 画一个矩形
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(new Font("Timess New Roman", Font.PLAIN, 28));

		// 随机产生155条干扰线，使图象中的数字不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 设置颜色
		g.setColor(new Color(20 + random.nextInt(110),
				20 + random.nextInt(110), 20 + random.nextInt(110)));
		// 将验证码显示到图象中
		g.drawString(sRand, 10, 26);

		// 绘图工具释放
		g.dispose();

		ValidateCode vc = new ValidateCode();
		vc.setImage(image);
		vc.setRand(sRand);
		return vc;
	}

	Color getRandColor(int fc, int bc) {
		// 给定范围获得随机颜色
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
	public void main(String[] args){
		
	}
}
