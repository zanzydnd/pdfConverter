package ru.kpfu.itis.kozlov.pdfconverter.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.kozlov.pdfconverter.serializer.DateSerializer;
import ru.kpfu.itis.kozlov.pdfconverter.serializer.TimeSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credited {
    @NotBlank
    @NotNull
    public String creditedDate;
    @NotBlank
    @NotNull
    public String creditedTime;
}
