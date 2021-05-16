package ru.kpfu.itis.kozlov.pdfconverter.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.kozlov.pdfconverter.dtos.Entity;
import ru.kpfu.itis.kozlov.pdfconverter.dtos.Participant;
import ru.kpfu.itis.kozlov.pdfconverter.dtos.Pdf;
import ru.kpfu.itis.kozlov.pdfconverter.exceptions.NotValidException;
import ru.kpfu.itis.kozlov.pdfconverter.fontUtil.RussianHelper;

import javax.servlet.http.Part;
import javax.xml.crypto.Data;
import java.io.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
public class ConverterService {

    static final RussianHelper helper = new RussianHelper();
    static final BaseFont base = helper.baseFont;

    static String DIRECTORY_PATH = "pdfs/";
    static final int FONT_SIZE_SMALL = 16;
    static final int FONT_SIZE_BIG = 32;
    static final int OFFSET = 40;
    static Font font1 = new Font(base, FONT_SIZE_SMALL, Font.NORMAL);
    static Font font_fat = new Font(base, FONT_SIZE_SMALL, Font.BOLD);
    static Font font3 = new Font(base, 9, Font.NORMAL);
    static Font font4 = new Font(base, 9, Font.BOLD);


    public static void validatePdf(Pdf pdf) throws NotValidException {
        if (pdf.getName() == null || pdf.getName().equals("")) {
            throw new NotValidException("Enter name");
        }
        //\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}
        //
        for (Entity entity : pdf.getEntities()) {

            if (entity.getInstitute() == null || entity.getInstitute().equals(""))
                throw new NotValidException("Enter Institute");

            if (entity.getDate() == null || entity.getDate().equals("")) {
                throw new NotValidException("Enter Date");
            } else {
                SimpleDateFormat dateValidator = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                try {
                    dateValidator.parse(entity.getDate());
                } catch (ParseException e) {
                    throw new NotValidException("Enter The valid data format HH:mm:ss dd-MM-yyyy");
                }
            }

            if (entity.getLogin() == null || entity.getLogin().equals(""))
                throw new NotValidException("Enter Login");

            if (entity.getReport_type() == null || entity.getReport_type().equals(""))
                throw new NotValidException("Enter ReportType");

            if (entity.getNum() == null)
                throw new NotValidException("Enter num");

            if (entity.getReport_no() == null)
                throw new NotValidException("Enter report n");

            if (entity.getStud_n() == null)
                throw new NotValidException("Enter report stud n");

            if (!entity.getParticipants().isEmpty()) {
                for (Participant participant : entity.getParticipants()) {
                    if (participant.getCreated() == null)
                        throw new NotValidException("Enter created");
                    if (participant.getCreated().getCreatedTime() == null)
                        throw new NotValidException("Enter createdTime");
                    try {//dd-MM-yyyy
                        SimpleDateFormat dateValidator = new SimpleDateFormat("HH:mm:ss");
                        dateValidator.parse(participant.getCreated().createdTime);
                    } catch (ParseException e) {
                        throw new NotValidException("Enter correct date");
                    }

                    if (participant.getCreated().getCreatedDate() == null)
                        throw new NotValidException("Enter createdDate");
                    try {//dd-MM-yyyy
                        SimpleDateFormat dateValidator = new SimpleDateFormat("dd-MM-yyyy");
                        dateValidator.parse(participant.getCreated().getCreatedDate());
                    } catch (ParseException e) {
                        throw new NotValidException("Enter correct date");
                    }


                    if (participant.getFormalized() == null)
                        throw new NotValidException("Enter formalized");
                    if (participant.getFormalized().getFormalizedTime() == null)
                        throw new NotValidException("Enter formalizedTime");
                    try {//dd-MM-yyyy
                        SimpleDateFormat dateValidator = new SimpleDateFormat("HH:mm:ss");
                        dateValidator.parse(participant.getFormalized().getFormalizedTime());
                    } catch (ParseException e) {
                        throw new NotValidException("Enter correct date");
                    }

                    if (participant.getFormalized().getFormalizedDate() == null)
                        throw new NotValidException("Enter formalizedDate");
                    try {//dd-MM-yyyy
                        SimpleDateFormat dateValidator = new SimpleDateFormat("dd-MM-yyyy");
                        dateValidator.parse(participant.getFormalized().getFormalizedDate());
                    } catch (ParseException e) {
                        throw new NotValidException("Enter correct date");
                    }


                    if (participant.getCredited() == null)
                        throw new NotValidException("Enter credited");
                    if (participant.getCredited().getCreditedTime() == null)
                        throw new NotValidException("Enter creditedTime");
                    try {//dd-MM-yyyy
                        SimpleDateFormat dateValidator = new SimpleDateFormat("HH:mm:ss");
                        dateValidator.parse(participant.getCredited().getCreditedTime());
                    } catch (ParseException e) {
                        throw new NotValidException("Enter correct date");
                    }

                    if (participant.getCredited().getCreditedDate() == null)
                        throw new NotValidException("Enter creditedDate");
                    try {//dd-MM-yyyy
                        SimpleDateFormat dateValidator = new SimpleDateFormat("dd-MM-yyyy");
                        dateValidator.parse(participant.getCredited().getCreditedDate());
                    } catch (ParseException e) {
                        throw new NotValidException("Enter correct date");
                    }


                    if (participant.getComment() == null || participant.getComment().getCommentText() == null ||
                            participant.getComment().getCommentText().equals(""))
                        throw new NotValidException("Enter Commnet");

                    if (participant.getIpAddress() == null || participant.getIpAddress().equals(""))
                        throw new NotValidException("Enter IpAddress");

                    if (!participant.getIpAddress().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"))
                        throw new NotValidException("Enter Valid ipAddress");

                }
            } else {
                throw new NotValidException("Enter participants");
            }
        }
    }

    public void convertToPdf(Pdf pdf) throws IOException, DocumentException, IllegalAccessException, NotValidException {

        validatePdf(pdf);

        Document document = new Document();
        Date date = new Date();
        document.setPageSize(PageSize.A4);
        System.out.println(DIRECTORY_PATH + pdf.getName().trim() + date.getTime() + ".pdf");
        PdfWriter pdfWriter = PdfWriter.getInstance(
                document,
                new FileOutputStream(
                        DIRECTORY_PATH + pdf.getName().trim() + UUID.randomUUID().toString() + ".pdf")
        );
        pdfWriter.setPageEvent(new PageNumerator());
        document.open();


        fillInPdf(pdf, document);

        document.close();
    }


    public static void fillInPdf(Pdf pdf, Document document) throws IllegalAccessException, DocumentException, IOException {
        for (Entity entity : pdf.getEntities()) {
            Image img = Image.getInstance("images/default.jpg");
            Paragraph otchet_po_dannim = new Paragraph("Подготовленный отчет по данным по № ", font1);

            Chunk chunk = new Chunk(String.valueOf(entity.getNum()), font_fat);

            Phrase phrase = new Phrase();
            phrase.add(otchet_po_dannim);
            phrase.add(chunk);
            PdfPTable t = new PdfPTable(2);
            img.scalePercent(8);
            t.setWidthPercentage(100);
            t.setSpacingBefore(10f);
            t.setSpacingAfter(10f);
            PdfPCell cell = new PdfPCell(img);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(Rectangle.NO_BORDER);
            t.addCell(cell);
            cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            t.addCell(cell);
            document.add(t);

            document.add(fillInBeginInfo(entity));


            Phrase participants = new Phrase("Перечень участников " + entity.getDate().trim() + " : ", font3);
            document.add(participants);

            document.add(fillInParticipants(entity));
            Paragraph paragraph = new Paragraph("Примечание: время указано в часовом поясе MSK (UTC+3) в " +
                    "соответствии с системными часами сервера или АРМ.", font4);
            document.add(paragraph);
            document.newPage();
            document.setPageCount(1);
        }
    }

    private static PdfPTable fillInBeginInfo(Entity entity) throws IllegalAccessException, IOException {
        PdfPTable table = new PdfPTable(new float[]{9, 30});
        table.setWidthPercentage(100);
        table.setSpacingAfter(10);
        table.setSpacingBefore(10);
        InputStream getLocalJsonFile = new FileInputStream("assets/map/map.json");
        HashMap<String, String> jsonMap = new ObjectMapper().readValue(getLocalJsonFile, HashMap.class);
        Field[] fields = entity.getClass().getFields();
        for (Field field : fields) {
            PdfPCell cell = new PdfPCell(new Phrase(jsonMap.get(field.getName()) + ": ", font3));
            cell.setBorder(0);
            cell.setPaddingTop(9);
            cell.setPaddingBottom(8);
            table.addCell(cell);
            Object value = field.get(entity);
            cell = new PdfPCell(new Phrase(value.toString().trim(), font3));
            cell.setBorder(0);
            cell.setPaddingTop(9);
            cell.setPaddingBottom(8);
            table.addCell(cell);
        }

        return table;
    }

    private static PdfPTable fillInParticipants(Entity entity) throws IllegalAccessException {
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

            PdfPCell cell = null;

            for (Field startField : participant.getClass().getFields()) {
                if (startField.getName().equals("ipAddress")) {
                    Object val = startField.get(participant);
                    cell = new PdfPCell(new Paragraph(val.toString(), font3));
                    cell.setVerticalAlignment(5);
                    cell.setHorizontalAlignment(1);
                    if (i % 2 == 0) {
                        cell.setBackgroundColor(new BaseColor(211, 211, 211));
                    }
                    table.addCell(cell);
                } else {
                    String strToPut = "";
                    Object fieldObject = startField.get(participant);
                    for (Field innerField : fieldObject.getClass().getFields()) {
                        Object val = innerField.get(fieldObject);
                        strToPut += val.toString().trim() + "\n";
                    }
                    strToPut = strToPut.replaceAll("\\s+", " ");
                    cell = new PdfPCell(new Paragraph(strToPut, font3));
                    if (i % 2 == 0) {
                        cell.setBackgroundColor(new BaseColor(211, 211, 211));
                    }
                    cell.setHorizontalAlignment(1);
                    cell.setVerticalAlignment(5);
                    table.addCell(cell);
                }
            }
            i++;
        }
        return table;
    }


    //https://kb.itextpdf.com/home/it5kb/examples/page-numbers-and-pdf-a
    private static class PageNumerator extends PdfPageEventHelper {
        PdfTemplate template;

        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            template = writer.getDirectContent().createTemplate(30, 16);
            try {
                Image total = Image.getInstance(template);
                total.setRole(PdfName.ARTIFACT);
            } catch (DocumentException e) {
                throw new ExceptionConverter(e);
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable table = new PdfPTable(3);
            try {
                table.setWidths(new int[]{40, 5, 0});
                table.setTotalWidth(527);
                table.setLockedWidth(true);
                table.getDefaultCell().setFixedHeight(40);
                table.getDefaultCell().setBorderColor(BaseColor.WHITE);
                table.addCell(new Paragraph());
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(new Phrase(String.format("%d", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));
                table.addCell(new Paragraph());
                PdfContentByte canvas = writer.getDirectContent();
                canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
                table.writeSelectedRows(0, -1, 34, 50, canvas);
                canvas.endMarkedContentSequence();
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }

        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(template, Element.ALIGN_RIGHT,
                    new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                    2, 6, 0);
        }
    }
}
