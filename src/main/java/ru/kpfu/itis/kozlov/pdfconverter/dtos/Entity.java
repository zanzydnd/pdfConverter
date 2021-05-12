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
public class Entity {
    private Long num;
    private String institute;
    private String login;
    private Long stud_n;
    private Long report_no;
    private String report_type;
    private String date;
    private List<Participant> participants;
}
