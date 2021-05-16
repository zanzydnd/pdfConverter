package ru.kpfu.itis.kozlov.pdfconverter.controllers;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.kozlov.pdfconverter.dtos.Pdf;
import ru.kpfu.itis.kozlov.pdfconverter.exceptions.NotValidException;
import ru.kpfu.itis.kozlov.pdfconverter.services.ConverterService;

import java.io.IOException;

import static org.springframework.http.MediaType.*;

@Controller
public class LonelyController {

    @Autowired
    private ConverterService converterService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE},
            produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public ResponseEntity toPdf(@RequestBody @Validated Pdf pdf) throws IOException, DocumentException, IllegalAccessException {
        try {
            converterService.convertToPdf(pdf);
        } catch (NotValidException e) {
            System.out.println("No");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(null);
    }


}
