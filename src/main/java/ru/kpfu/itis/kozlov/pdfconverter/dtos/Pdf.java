package ru.kpfu.itis.kozlov.pdfconverter.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pdf {
    String name;
    List<Entity> entities;
}
