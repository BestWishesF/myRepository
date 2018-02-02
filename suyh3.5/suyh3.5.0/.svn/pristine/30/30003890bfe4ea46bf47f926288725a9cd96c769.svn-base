package cn.hotol.app.common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class ImageHelper {

	public static final int DEFAULT_PADDING = 0;

	public static final Color DEFAULT_PADDING_COLOR = Color.WHITE;

	public static final MathContext MATH_CONTEXT = new MathContext(10, RoundingMode.HALF_UP);

	public static String getContentType(File file) throws IOException {
		String type;
		InputStream inputStream = null;
		byte[] header = new byte[10];
		try {
			inputStream = new FileInputStream(file);
			inputStream.read(header);
		} finally {
			if (inputStream != null) try {
				inputStream.close();
			} catch (Exception e) {
			}
		}

		if (header[0] == (byte) 'G' && header[1] == (byte) 'I' && header[2] == (byte) 'F')
			type = "GIF";
			// PNG
		else if (header[1] == (byte) 'P' && header[2] == (byte) 'N' && header[3] == (byte) 'G')
			type = "PNG";
			// JPG
		else if (header[6] == (byte) 'J' && header[7] == (byte) 'F' && header[8] == (byte) 'I' && header[9] == (byte) 'F')
			type = "JPG";
		else
			type = "Unknown";

		return type;
	}

	public static void resizeFix(File sourceFile, File targetFile, int fixW, int fixH) throws IOException {
		resizeFix(sourceFile, targetFile, fixW, fixH, DEFAULT_PADDING, DEFAULT_PADDING_COLOR);
	}

	public static void resizeFix(InputStream in, File targetFile, int fixW, int fixH) throws IOException {
		resizeFix(in, new FileOutputStream(targetFile), fixW, fixH, DEFAULT_PADDING, DEFAULT_PADDING_COLOR);
	}

	public static void resizeFix(File in, File out, int fixW, int fixH, int padding, Color paddingColor) throws IOException {
		resizeFix(new FileInputStream(in), new FileOutputStream(out), fixW, fixH, padding, paddingColor);
	}

	public static final void writeImage(BufferedImage image, OutputStream out) throws IOException {
		ImageIO.write(image, "JPEG", out);
	}

	public static void resizeFix(InputStream in, OutputStream out, int fixW, int fixH, int padding, Color paddingColor) throws IOException {
		try {
			Image sourceImage = ImageIO.read(in);
			int sourceWidth = sourceImage.getWidth(null);
			int sourceHeight = sourceImage.getHeight(null);
			int w, h, pw, ph;
			BigDecimal bSourceWidth = BigDecimal.valueOf(sourceWidth);
			BigDecimal bSourceHeight = BigDecimal.valueOf(sourceHeight);
			BigDecimal bFixW = BigDecimal.valueOf(fixW);
			BigDecimal bFixH = BigDecimal.valueOf(fixH);
			if (bSourceWidth.divide(bSourceHeight, MATH_CONTEXT).compareTo(bFixW.divide(bFixH, MATH_CONTEXT)) > 0) {
				w = fixW - 2 * padding;
				h = bFixW.divide(bSourceWidth, MATH_CONTEXT).multiply(bSourceHeight).intValue() - 2 * padding;
				pw = padding;
				ph = (fixH - h) / 2 + padding;
			} else {
				w = bFixH.divide(bSourceHeight, MATH_CONTEXT).multiply(bSourceWidth).intValue() - 2 * padding;
				h = fixH - 2 * padding;
				pw = (fixW - w) / 2 + padding;
				ph = padding;
			}
			BufferedImage _image = new BufferedImage(fixW, fixH, BufferedImage.TYPE_INT_RGB);
			Graphics graphics = _image.getGraphics();
			graphics.setColor(paddingColor);
			graphics.fillRect(0, 0, fixW, fixH);
			graphics.drawImage(sourceImage.getScaledInstance(w, h, Image.SCALE_SMOOTH), pw, ph, w, h, null);
			/*JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(_image);*/
			writeImage(_image, out);
		} finally {
			if (in != null) try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (out != null) try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}