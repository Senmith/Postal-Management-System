package iit.postalapp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {

    private static final String qrSavePath = "src/main/resources/static/qrcodes/";
    private static final int QR_CODE_SIZE = 300;

    //Qr code generation when registering parcel 
    public String generateQRCodeForParcel(Parcel parcel) throws WriterException, IOException {
        // Create QR code content with parcel information
        String qrContent = createQRContent(parcel);
        
        // Generate filename
        String fileName = "qr_" + parcel.getParcelId() + ".png";
        String filePath = qrSavePath + fileName;
        
        // Create directory if it doesn't exist
        createQRCodeDirectory();
        
        // Generate QR code
        generateQRCodeImage(qrContent, QR_CODE_SIZE, QR_CODE_SIZE, filePath);
        
        // Return the relative path for web access
        return "/qrcodes/" + fileName;
    }

    //Creates the content string for QR code - full URL for checkpoint page
    private String createQRContent(Parcel parcel) {
        // Return full URL for QR checkpoint page
        return "http://localhost:8080/qr/checkpoint/" + parcel.getParcelId();
    }

    //Generates QR code image and saves it to file
    private void generateQRCodeImage(String text, int width, int height, String filePath) 
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics();

        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (bitMatrix.get(j, i)) {
                    graphics.fillRect(j, i, 1, 1);
                }
            }
        }

        ImageIO.write(bufferedImage, "png", new File(filePath));
    }

    //Creates the QR code directory if it doesn't exist
    private void createQRCodeDirectory() throws IOException {
        Path qrCodeDir = Paths.get(qrSavePath);
        if (!Files.exists(qrCodeDir)) {
            Files.createDirectories(qrCodeDir);
        }
    }

    //Parses parcel ID from QR code content (URL)
    public String parseParcelIdFromQRContent(String qrContent) {
        // Extract parcel ID from URL format: http://localhost:8080/qr/checkpoint/{parcelId}
        if (qrContent != null && qrContent.contains("/qr/checkpoint/")) {
            String[] parts = qrContent.split("/qr/checkpoint/");
            if (parts.length > 1) {
                return parts[1].trim();
            }
        }
        return null;
    }

    //Validates if QR content is for a parcel checkpoint URL
    public boolean isValidParcelQRCode(String qrContent) {
        // Check if QR code contains a valid checkpoint URL
        return qrContent != null && qrContent.contains("/qr/checkpoint/") && !qrContent.trim().isEmpty();
    }

    //Deletes QR code file for a parcel
    public void deleteQRCodeFile(String parcelId) {
        try {
            String fileName = "qr_" + parcelId + ".png";
            Path filePath = Paths.get(qrSavePath + fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log error but don't throw exception
            System.err.println("Failed to delete QR code file for parcel: " + parcelId);
        }
    }
}