package ru.kpfu.itis.kozlov.pdfconverter.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NameJob {
    @NotBlank
    @NotNull
    public String name;
    @NotBlank
    @NotNull
    public String job;
}
