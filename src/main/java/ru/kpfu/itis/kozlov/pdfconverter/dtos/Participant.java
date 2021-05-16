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
public class Participant {
    @NotBlank
    @NotNull
    public Created created;
    @NotBlank
    @NotNull
    public Formalized formalized;
    @NotBlank
    @NotNull
    public Credited credited;
    @NotBlank
    @NotNull
    public Comment comment;
    @NotBlank
    @NotNull
    public Person person;
    @NotBlank
    @NotNull
    public String ipAddress;
}
