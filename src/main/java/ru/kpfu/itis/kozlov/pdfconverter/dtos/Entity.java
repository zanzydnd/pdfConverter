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
public class Entity {
    @NotBlank
    @NotNull
    public Long num;
    @NotBlank
    @NotNull
    public String institute;
    @NotBlank
    @NotNull
    public String login;
    @NotBlank
    @NotNull
    public Long stud_n;
    @NotBlank
    @NotNull
    public Long report_no;
    @NotBlank
    @NotNull
    public String report_type;
    @NotBlank
    @NotNull
    public String date;
    @NotNull
    private List<Participant> participants;
}
