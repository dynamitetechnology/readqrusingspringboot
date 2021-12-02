package com.urbanledger.qrcode.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

@Service
public class Util {
	
	// Function to read the QR file
    public  String readQRCode(String path, String charset, Map hashMap)
        throws FileNotFoundException, IOException,
               NotFoundException
    {
        BinaryBitmap binaryBitmap
            = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                    ImageIO.read(
                        new FileInputStream(path)))));
 
        Result result
            = new MultiFormatReader().decode(binaryBitmap);
 
        return result.getText();
    }

}
