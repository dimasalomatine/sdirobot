/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author dmitry
 */

 
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
 
import javax.imageio.ImageIO;
 
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

 
public class QRCode {
 
        public static String Generate(String myCodeText){
          String basepath=System.getProperty("user.dir");
          
          String filePath = basepath+File.separator+"QRGen";
          File f=new File(filePath);
          
          if(!f.exists())
          {
           f.mkdirs();
          }
          if(f.exists()){
              filePath+=File.separator+"QR_id_.png";
		int size = 250;
		String fileType = "png";
		File myFile = new File(filePath);
		try {
			
			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			
			// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
 
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size,
					size, hintMap);
			int Width = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(Width, Width,
					BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
 
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, Width, Width);
			graphics.setColor(Color.BLACK);
 
			for (int i = 0; i < Width; i++) {
				for (int j = 0; j < Width; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, myFile);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\n\nYou have successfully created QR Code.");
          }else{
           filePath="";
          }
          return filePath;
        }
	public static void main(String[] args) {
		String myCodeText = "https://en.wikipedia.org/wiki/Nervous_system";
		System.out.println("\n"+Generate(myCodeText));
	}
}
