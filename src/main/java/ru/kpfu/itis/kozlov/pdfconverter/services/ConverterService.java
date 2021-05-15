package ru.kpfu.itis.kozlov.pdfconverter.services;


import com.itextpdf.layout.element.Cell;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.kozlov.pdfconverter.dtos.Entity;
import ru.kpfu.itis.kozlov.pdfconverter.dtos.Participant;
import ru.kpfu.itis.kozlov.pdfconverter.dtos.Pdf;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Date;

@Service
public class ConverterService {

    static String DIRECTORY_PATH = System.getProperty("user.home") + "\\pdfs\\";
    static final int FONT_SIZE_SMALL = 16;
    static final int FONT_SIZE_BIG = 32;
    static final int OFFSET = 40;
    static Font font1 = new Font(Font.FontFamily.HELVETICA, FONT_SIZE_SMALL, Font.NORMAL);
    static Font font_fat = new Font(Font.FontFamily.HELVETICA, FONT_SIZE_SMALL, Font.BOLD);
    static Font font3 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
    static Font font4 = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);


    public void convertToPdf(Pdf pdf) throws FileNotFoundException, DocumentException, IllegalAccessException {
        Document document = new Document();
        Date date = new Date();
        document.setPageSize(PageSize.A4);
        System.out.println(DIRECTORY_PATH);
        PdfWriter.getInstance(
                document,
                new FileOutputStream(
                        new File(DIRECTORY_PATH + pdf.getName() + date.getTime() + ".pdf"))
        );

        document.open();

        fillInPdf(pdf, document);

    }


    public static void fillInPdf(Pdf pdf, Document document) throws IllegalAccessException, DocumentException {
        for (Entity entity : pdf.getEntities()) {
            Paragraph otchet_po_dannim = new Paragraph("Подготовленный отчет по данным \n по № ", font1);

            Chunk chunk = new Chunk(String.valueOf(entity.getNum()), font_fat);

            Phrase phrase = new Phrase();
            phrase.add(otchet_po_dannim);
            phrase.add(chunk);
            PdfPTable t = new PdfPTable(2);

            document.add(fillInBeginInfo(entity));

            Phrase participants = new Phrase("Перечень участников " + entity.getDate() + " : ", font3);
            document.add(participants);

        }
    }

    private static PdfPTable fillInBeginInfo(Entity entity) throws IllegalAccessException {
        PdfPTable table = new PdfPTable(new float[]{9, 30});
        table.setWidthPercentage(100);
        table.setSpacingAfter(10);
        table.setSpacingBefore(10);


        Field[] fields = entity.getClass().getFields();
        for (Field field : fields) {
            PdfPCell cell = new PdfPCell(new Phrase(field.getName() + ": ", font1));
            cell.setBorder(0);
            cell.setPaddingTop(6);
            table.addCell(cell);
            Object value = field.get(entity);
            cell = new PdfPCell(new Phrase((String) value, font1));
            table.addCell(cell);
        }

        return table;
    }

    private static PdfPTable fillInParticipants(Entity entity) {
        PdfPTable table = new PdfPTable(new float[]{11, 11, 11, 25, 29, 11});
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        String[] columns = new String[]{"Сформирован", "Оформлен", "Зачислил", "Комметарий", "ФИО, Должность", "IP-адрес"};

        for (String column : columns) {
            PdfPCell cell = new PdfPCell(new Phrase(column, font4));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingTop(15);
            if (column.equals("Сформирован")) {
                cell.setPaddingLeft(0);
                cell.setPaddingRight(0);
            } else {
                table.setHeaderRows(1);
            }
            cell.setPaddingBottom(15);
            table.addCell(cell);
        }

        int i = 0;
        for (Participant participant : entity.getParticipants()) {
            PdfPCell cellForParticipant = new PdfPCell(new Paragraph(participant.getCreated().getCreatedDate() + "\n" +
                    participant.getCreated().getCreatedTime()));
            if (i % 2 == 0) {
                cellForParticipant.setBackgroundColor(new BaseColor(100, 100, 100));
            }
            cellForParticipant.setHorizontalAlignment(1);
            cellForParticipant.setVerticalAlignment(5);
            table.addCell(cellForParticipant);

            cellForParticipant = new PdfPCell(new Paragraph(participant.getFormalized().getFormalizedDate() + "\n" +
                    participant.getFormalized().getFormalizedTime()));
            cellForParticipant.setVerticalAlignment(5);
            cellForParticipant.setHorizontalAlignment(1);
            if (i % 2 == 0) {
                cellForParticipant.setBackgroundColor(new BaseColor(100, 100, 100));
            }
            table.addCell(cellForParticipant);

            cellForParticipant = new PdfPCell(new Paragraph(participant.getCredited().getCreditedDate() + "\n" +
                    participant.getCredited().getCreditedTime()));
            cellForParticipant.setVerticalAlignment(5);
            cellForParticipant.setHorizontalAlignment(1);
            if (i % 2 == 0) {
                cellForParticipant.setBackgroundColor(new BaseColor(100, 100, 100));
            }
            table.addCell(cellForParticipant);

            cellForParticipant = new PdfPCell(new Paragraph(participant.getComment().getCommentText()));
            cellForParticipant.setVerticalAlignment(5);
            cellForParticipant.setHorizontalAlignment(1);
            if (i % 2 == 0) {
                cellForParticipant.setBackgroundColor(new BaseColor(100, 100, 100));
            }
            table.addCell(cellForParticipant);

            cellForParticipant = new PdfPCell(new Paragraph(participant.getPerson().getPersonName() + "\n" +
                    participant.getPerson().getPersonJob()));
            cellForParticipant.setVerticalAlignment(5);
            cellForParticipant.setHorizontalAlignment(1);
            if (i % 2 == 0) {
                cellForParticipant.setBackgroundColor(new BaseColor(100, 100, 100));
            }
            table.addCell(cellForParticipant);

            cellForParticipant = new PdfPCell(new Paragraph(participant.getIpAddress()));

            cellForParticipant.setVerticalAlignment(5);
            cellForParticipant.setHorizontalAlignment(1);
            if (i % 2 == 0) {
                cellForParticipant.setBackgroundColor(new BaseColor(100, 100, 100));
            }
            table.addCell(cellForParticipant);

            i++;
        }
        return table;
    }
}
