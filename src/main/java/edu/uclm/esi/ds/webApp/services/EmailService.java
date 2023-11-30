package edu.uclm.esi.ds.webApp.services;

import java.util.Map;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class EmailService {

	public static final String EMAIL = "email";
	public static final String JSON = "application/json";
	/*
	 * ESTE METODO GENERA Y MANDA EL CORREO DE RECUPERACION DE CONTRASEÑA A PARTIR DE LA API WEB DE BREVO. 
	 * GENERAMOS UN TOKEN QUE CONCATENAMOS AL ENLACE , QUE ES EL MISMO QUE ESTA EN BASE DE DATOS. DE ESTA MANERA CADA ENLACE PARA CADA
	 * USUARIO ES UNICO.
	 */

	public void sendRecover(Map<String, Object> info) {
		String email = info.get(EMAIL).toString();
		String token = org.apache.commons.codec.digest.DigestUtils.sha512Hex(email);
		String htmlContent = "<html><head></head><body><p>Por favor usa el enlace de a continuacion para recuperar tu cuenta</p><p>Haz click aquí <a href='https://muevetic-30961.web.app/modificar-contrasena/" + token + "'> para confirmar la recuperacion de tu cuenta</a></p></body></html>";
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse(JSON);
				
				JSONObject jsoSender = new JSONObject().
					put("name", "MueveTIC").
					put(EMAIL, "manuel.flores1@alu.uclm.es");
				
				JSONObject jsoTo = new JSONObject().	
					put(EMAIL, email);
				
				JSONArray jsaTo = new JSONArray().
					put(jsoTo);
				
				JSONObject jsoBody = new JSONObject();
					jsoBody.put("sender", jsoSender);
					jsoBody.put("to",jsaTo);
					jsoBody.put("subject", "Recuperacion de contraseña");
					
					jsoBody.put("hmtlContent", htmlContent);
					jsoBody.put("textContent", htmlContent);	
					
				RequestBody body = RequestBody.create(mediaType, jsoBody.toString());
				Request request = new Request.Builder()
				  .url("https://api.brevo.com/v3/smtp/email")
				  .post(body)
				  .addHeader("accept", JSON)
				  .addHeader("api-key", "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") //Introducir la api-key de Brevo
				  .addHeader("content-type", JSON)
				  .build();
				try {
					Response response = client.newCall(request).execute();
				} catch (IOException e) {
					
				}
		
	}

}