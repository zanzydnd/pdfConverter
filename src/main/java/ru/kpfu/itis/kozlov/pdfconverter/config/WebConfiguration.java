package ru.kpfu.itis.kozlov.pdfconverter.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@EnableWebMvc
@Configuration
@ComponentScan({"ru.kpfu.itis.kozlov.pdfconverter"})
@PropertySource("classpath:application.properties")
public class WebConfiguration {
    @Bean // ._.
    public XmlMapper xmlMapper(){return new XmlMapper();}


    @Bean
    public ObjectMapper objectMapper(){return new ObjectMapper();}
}
