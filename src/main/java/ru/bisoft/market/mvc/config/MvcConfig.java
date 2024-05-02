package ru.bisoft.market.mvc.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import ru.tinkoff.piapi.core.InvestApi;

@Configuration(proxyBeanMethods = false)
public class MvcConfig {

    @Bean
    ObjectMapper objectMapper(WebMvcProperties mvcProperties) {
        //JacksonAutoConfiguration
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(mvcProperties.getFormat().getDate());
        DateTimeFormatter localDataTimeFormatter = DateTimeFormatter.ofPattern(mvcProperties.getFormat().getDateTime());

        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dtf));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dtf));

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(localDataTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(localDataTimeFormatter));
        mapper.registerModule(javaTimeModule);

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }

    @Bean
    InvestApi investApi(@Value("${investToken}") String investToken) {
        InvestApi api = InvestApi.createSandbox(investToken);
        return api;
    }

    // @Bean
    // При регистрации этих преобразователей слетает регистрация ObjectMapper и LocalDateSerializer
    // public HttpMessageConverters customConverters() {
    //     ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter = new ProtobufJsonFormatHttpMessageConverter();

    //     ProtobufHttpMessageConverter protobufHttpMessageConverter = new ProtobufHttpMessageConverter();
    //     protobufHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON,
    //             MediaType.parseMediaType(MediaType.TEXT_PLAIN_VALUE +
    //                     ";charset=ISO-8859-1")));
    //     return new HttpMessageConverters(protobufJsonFormatHttpMessageConverter, protobufHttpMessageConverter);
    // }
}
