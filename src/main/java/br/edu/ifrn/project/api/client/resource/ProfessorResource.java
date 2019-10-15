package br.edu.ifrn.project.api.client.resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.edu.ifrn.project.api.client.dto.ProfessorDTO;

@RestController
public class ProfessorResource {
	
	private static final String URL_API_PROFESSOR = "https://projetoifhelp.herokuapp.com/api/professor/";

	@Autowired
	private RestTemplate restTemplate;

	public boolean POSTPUTcreateOrUpdate(ProfessorDTO objectBody) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("nome",objectBody.getNome());
		body.put("matricula",objectBody.getMatricula());
		body.put("email",objectBody.getEmail());

		HttpEntity<?> httpEntity = new HttpEntity<Object>(body,headers);
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> response = (objectBody.getId() == 0)?
        restTemplate.exchange(URL_API_PROFESSOR,HttpMethod.POST,httpEntity,Map.class):
        restTemplate.exchange("https://projetoifhelp.herokuapp.com/api/professor/"+objectBody.getId(),HttpMethod.PUT,httpEntity,Map.class);
	
		return (response.getStatusCode() == HttpStatus.UNAUTHORIZED)?false:true;
	}
	
	public boolean DELETEdelete(long id) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> httpEntity = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(URL_API_PROFESSOR+id, HttpMethod.DELETE,httpEntity,String.class);
		return (response.getStatusCode() == HttpStatus.UNAUTHORIZED)?false:true;
	}
		
	public List<ProfessorDTO> GETread() {	
		try {
			ResponseEntity<ProfessorDTO[]> response = this.restTemplate.getForEntity(URL_API_PROFESSOR,ProfessorDTO[].class);
			
			if(response.getStatusCodeValue() == 200){
				return Arrays.stream(response.getBody()).collect(Collectors.toList());
			}
		}catch (HttpClientErrorException ex) {
			
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				return null;
		    }
		}
		return null;
	}
}
