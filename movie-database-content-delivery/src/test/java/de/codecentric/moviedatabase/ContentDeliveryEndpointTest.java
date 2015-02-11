package de.codecentric.moviedatabase;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class ContentDeliveryEndpointTest {

	@Ignore // Works only when server is running
	@Test
	public void testEndpoint(){
		RestTemplate restTemplate = new RestTemplate();
		List<NavigationElement> elements = restTemplate.getForObject("http://localhost:8084/content-delivery/navigation/navigation.json", NavigationResponse.class).getNavigationElements();
		assertThat(elements.size(),is(4));
		System.out.println(elements);
	}
}
