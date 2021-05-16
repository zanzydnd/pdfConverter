package ru.kpfu.itis.kozlov.pdfconverter.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pdf {
    @NotBlank
    @NotNull
    String name;
    @NotNull
    List<Entity> entities;
}
