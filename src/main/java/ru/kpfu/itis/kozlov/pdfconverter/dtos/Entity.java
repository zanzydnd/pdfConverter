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
    public Long num;
    public String institute;
    public String login;
    public Long stud_n;
    public Long report_no;
    public String report_type;
    public String date;
    private List<Participant> participants;
}
