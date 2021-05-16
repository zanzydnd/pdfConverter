package ru.kpfu.itis.kozlov.pdfconverter.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participant {
    public Created created;
    public Formalized formalized;
    public Credited credited;
    public Comment comment;
    public Person person;
    public String ipAddress;
}
