package com.example.consultasdn_1.dao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

@Component
public class ConsultaDao {
    public Object verificaCredencial(String usuario, String password){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/consulta/" + usuario + "/" +password;
        ResponseEntity<Object> responseMap = restTemplate.getForEntity(url, Object.class);
        Object respuesta = responseMap.getBody();
        return respuesta;
    }
}
