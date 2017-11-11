package org.mvc.controller.restutil;

import java.util.HashMap;
import java.util.Map;

import org.mvc.controller.mapping.UserInfo;
import org.springframework.web.client.RestTemplate;

public class RestClientUtils {

	//public static final String REST_SERVICE_URI = "<a class="vglnk" href="http://localhost:8080/Spring4MVCCRUDRestService" rel="nofollow"><span>http</span><span>://</span><span>localhost</span><span>:</span><span>8080</span><span>/</span><span>Spring4MVCCRUDRestService</span></a>";
	private static void getUserNames() {
		final String uri = "http://localhost:8080/spring4MVCHelloWorldNoXMLDemo/rest/UserExists/{username}";
		//final String uri = "http://localhost:8080/springmvc/rest/UserExists/{username}";
		Map<String, String> params = new HashMap<String, String>();
	    params.put("username", "vamsia");

	    RestTemplate restTemplate = new RestTemplate();
	    //boolean blnUserExists = restTemplate.getForObject(uri, Boolean.class, params);
	    //boolean blnUserExists = restTemplate.postForObject(uri, params, Boolean.class);

	    restTemplate.postForEntity(uri, String.class, Boolean.class, params);

	    //System.out.println("is User Exists >>"+blnUserExists);
	}

	/* GET */
    private static void getUser(){
        System.out.println("Testing getUser API----------");

        RestTemplate restTemplate = new RestTemplate();
        UserInfo user = (UserInfo) restTemplate.getForObject("http://localhost:8080/spring4MVCHelloWorldNoXMLDemo/rest/user/1", UserInfo.class);
        System.out.println(user);
    }
	public static void main(String[] args) {
		//new RestClientUtils().getUserNames();
		getUser();
	}
}