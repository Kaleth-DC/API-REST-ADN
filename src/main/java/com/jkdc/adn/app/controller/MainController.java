package com.jkdc.adn.app.controller;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//importamos el analizador de adn al controller
import com.jkdc.adn.app.object.ADN;

@RestController
@Validated
public class MainController {
	
	
	//controlador
	@PostMapping("/mutation/")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String post(@RequestParam Map<String,String> allParams){
		//System.out.println(allParams.entrySet());
		
		//respuesta API REST
		boolean response = false;
		
		try {
			//Descomponemos el son en un Arraglo
			JSONArray array = new JSONArray(new JSONObject(allParams.get("POST").toString()).get("dna").toString());
			
			String[] data = new String[array.length()];
			
			for(int i=0;i<array.length();i++) {
				data[i] = array.get(i).toString();
				System.out.println(data[i]);
			}
			
			//analizamos la muestra del adn
			response = new ADN().hasMutation(data);
			
			//System.out.println(array.length());
		}catch(Exception e) {
			//System.out.println("error"+e.toString());
			return "HTTP 403-Forbidden";
		}
		
		
		if(response) { //si existe mutacion
			return "HTTP 200 OK";
		}
		//si no existe mutacion
		return "HTTP 403-Forbidden";
		
		//return allParams.get("POST").toString();
	}
	
	
}
