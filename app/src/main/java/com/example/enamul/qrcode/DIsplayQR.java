package com.example.enamul.qrcode;

import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import static com.example.enamul.qrcode.R.drawable.ic_launcher_foreground;

public class DIsplayQR extends AppCompatActivity {
    ImageView imageView;
    Button createButton;
    EditText idEditText;
    String idString;
    public final static int QRcodeWidth = 400 ;
    Bitmap bitmap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qr);

        imageView = (ImageView)findViewById(R.id.imageView);
        idEditText = (EditText)findViewById(R.id.idEditText);
        createButton = (Button)findViewById(R.id.createButton);
        imageView.setImageResource(ic_launcher_foreground);





        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!idEditText.getText().toString().isEmpty()){
                    idString = idEditText.getText().toString();

                    try {
                        bitmap = TextToImageEncode(idString);
                        //Setting image to bitmap
                        imageView.setImageBitmap(bitmap);


                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    idEditText.requestFocus();
                    Toast.makeText(DIsplayQR.this, "Please Enter Your Scanned Test" , Toast.LENGTH_LONG).show();
                }

                createButton.setVisibility(View.INVISIBLE);

            }
        });
    }

    //Generate QR Code Bitmap
    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 400, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
