package util;

import java.awt.image.BufferedImage;
/**
 * 将生成的图片和数字封装成一个对象
 * @author lenovo
 *
 */
public class ValidateCode {
	private String rand;
	private BufferedImage image;

	public String getRand() {
		return rand;
	}

	public void setRand(String rand) {
		this.rand = rand;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
