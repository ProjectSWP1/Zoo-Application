package com.thezookaycompany.zookayproject.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.thezookaycompany.zookayproject.model.entity.Orders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

// For Ticket QR Generators after Payment Receipt Sent
public class TicketQRCodeGenerator {
    private String url = "http://localhost:3000";

    public void generateQRCodeImage(Orders orders, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url+"/scan-ticket?orderID="+ orders.getOrderID() +"&email="+ orders.getEmail(), BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath+orders.getOrderID()+"-QRCODE.png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public byte[] getQRCodeImage(Orders orders, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url+"/scan-ticket?orderID=&"+ orders.getOrderID() +"&email="+ orders.getEmail(), BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

//    public static String generateQRCode(Orders orders, int width, int height) {
//        StringBuilder result = new StringBuilder();
//
//        if (orders != null) {
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            try {
//                QRCodeWriter writer = new QRCodeWriter();
//                BitMatrix bitMatrix = writer.encode(frontEndUrl+"/scan-ticket?orderID=&"+ orders.getOrderID() +"email="+ orders.getEmail(), BarcodeFormat.QR_CODE, width, height);
//
//                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
//                ImageIO.write(bufferedImage, "png", os);
//
//                result.append("data:image/png;base64,");
//                result.append(new String(Base64.getEncoder().encode(os.toByteArray())));
//
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return result.toString();
//
//    }
}
