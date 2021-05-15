package ru.kpfu.itis.kozlov.pdfconverter.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.kozlov.pdfconverter.dtos.Pdf;

import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.*;

@Controller
public class LonelyController {

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {APPLICATION_XML_VALUE,APPLICATION_JSON_VALUE},
            produces = {APPLICATION_XML_VALUE,APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public ResponseEntity toPdf(@RequestBody @NotNull Pdf pdf) {
        System.out.println(pdf);
        return ResponseEntity.ok(null);
    }

    /*@RequestMapping(
            method = RequestMethod.POST,
            consumes = {APPLICATION_JSON_VALUE},
            produces = {APPLICATION_JSON_VALUE}
            , value = "/json"
    )
    @ResponseBody
    public ResponseEntity toPdfJson(@RequestBody Pdf pdf) {
        System.out.println(pdf);
        return ResponseEntity.ok(null);
    }

*/
}
