package com.example.consultasdn_1.controller;
import com.example.consultasdn_1.dao.ConsultaDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value="/sdn")
public class controlador {
    @Autowired
    ConsultaDao consultaDao;

    @GetMapping("/list")
    public Object verificarCredenciales() throws JsonProcessingException {

        String usuario = "munoz.e@pucp.edu.pe";
        String password = "mangoCero";

        //Obtengo la respuesta del Web Server
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/consulta/" + usuario + "/" + password;
        ResponseEntity<Object> responseMap = restTemplate.getForEntity(url, Object.class);
        Object respuestaObj = responseMap.getBody();

        //Convierto el objeto object a string
        ObjectWriter ow = new ObjectMapper().writer();
        String respuestaJson = ow.writeValueAsString(respuestaObj);
        String cadena = respuestaJson;

        //Vemos si existe o no el usuario
        String existencia = cadena.split("\\{")[1].split(",")[0].split(":")[1];
        String existe = "";
        String username = "";
        String contrasena = "";
        ArrayList<String> roles = new ArrayList<>();
        if(existencia.equals("false")){
            existe = "0";
        }else{
            existe = "1";
            //Obtenemos los datos
            username = cadena.split("\"varUsCorreo\":\"")[1].split("\"")[0];
            contrasena = cadena.split("\"varUsPassword\":\"")[1].split("\"")[0];
            //Obtenemos los roles que tiene el usuario
            String[] datosDeRoles = cadena.split("\"categorias\":\\[")[1].split("\\]")[0].split(",");
            for (String rol:datosDeRoles){
                roles.add(rol.split("\"")[1]);
            }
        }

        //Imprimimos
        if(existe.equals("0")){
            System.out.println("Las credenciales ingresadas no existen");
        }else{
            System.out.println("Las credenciales ingresadas si existen");
            System.out.println("Username: " + username);
            System.out.println("Password: " + contrasena);
            System.out.println("Roles: ");
            for (String rolcito:roles){
                System.out.println(rolcito);
            }
        }
        return respuestaObj;
    }

}
