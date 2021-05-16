package ru.kpfu.itis.kozlov.pdfconverter.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Formalized {
    @NotBlank
    @NotNull
    public String formalizedDate;
    @NotBlank
    @NotNull
    public String formalizedTime;
}
