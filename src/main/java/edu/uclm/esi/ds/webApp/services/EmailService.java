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
	
	/*public void sendConfirmationEmail(User user, Token token) {
		String htmlContent = "<html><head></head><body><p>Por favor confirma tu cuenta</p><p>Haz click aquí <a href='http://localhost/users/confirm/" + token.getId() + "'>para confirmar la creación de tu cuenta</a></p></body></html>";
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				
				JSONObject jsoSender = new JSONObject().
					put("name", "Juegos S.A.").
					put("email", "David.Gomez31@alu.uclm.es");
				
				JSONObject jsoTo = new JSONObject().	
					put("email", user.getEmail()).
					put("name",user.getName());
				
				JSONArray jsaTo = new JSONArray().
					put(jsoTo);
				
				JSONObject jsoBody = new JSONObject();
					jsoBody.put("sender", jsoSender);
					jsoBody.put("to",jsaTo);
					jsoBody.put("subject", "Bienvenido a los juegos");
					
					jsoBody.put("hmtlContent", htmlContent);
					jsoBody.put("textContent", htmlContent);	
					
				RequestBody body = RequestBody.create(mediaType, jsoBody.toString());
				Request request = new Request.Builder()
				  .url("https://api.brevo.com/v3/smtp/email")
				  .post(body)
				  .addHeader("accept", "application/json")
				  .addHeader("api-key", "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") //Introducir la api-key de Brevo
				  .addHeader("content-type", "application/json")
				  .build();
				try {
					Response response = client.newCall(request).execute();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}*/

	public void sendRecover(Map<String, Object> info) {
		String email = info.get(EMAIL).toString();
		String token = org.apache.commons.codec.digest.DigestUtils.sha512Hex(email);
		String htmlContent = "<html><head></head><body><p>Por favor usa el enlace de a continuacion para recuperar tu cuenta</p><p>Haz click aquí <a href='http://localhost:4200/modificar-contrasena/" + token + "'> para confirmar la recuperacion de tu cuenta</a></p></body></html>";
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