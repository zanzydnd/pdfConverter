package ru.kpfu.itis.kozlov.pdfconverter.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participant {
    @NotNull
    @Valid
    public Created created;
    @NotNull
    @Valid
    public Formalized formalized;
    @NotNull
    @Valid
    public Credited credited;

    @NotNull
    @Valid
    public Comment comment;
    @NotNull
    @Valid
    public Person person;
    @NotBlank
    @NotNull
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$")
    @Valid
    public String ipAddress;
}
