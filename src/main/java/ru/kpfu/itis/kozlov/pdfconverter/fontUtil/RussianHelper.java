package ru.kpfu.itis.kozlov.pdfconverter.fontUtil;

import com.itextpdf.text.pdf.BaseFont;

public class RussianHelper {
    public BaseFont baseFont;
    public static final String RUSSIAN_FONT = "assets/fonts/arial.ttf";


    public RussianHelper() {
        try {
            baseFont = BaseFont.createFont(RUSSIAN_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
