package ru.kpfu.itis.kozlov.pdfconverter.controllers;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.kozlov.pdfconverter.dtos.Pdf;
import ru.kpfu.itis.kozlov.pdfconverter.services.ConverterService;

import javax.validation.constraints.NotNull;

import java.io.FileNotFoundException;
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
    public ResponseEntity toPdf(@RequestBody @NotNull Pdf pdf) throws IOException, DocumentException, IllegalAccessException {
        converterService.convertToPdf(pdf);
        return ResponseEntity.ok(null);
    }


}
