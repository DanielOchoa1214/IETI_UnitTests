package org.adaschool.Weather;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class WeatherApplicationTests {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private WeatherReportService service;

	@BeforeEach
	public void beforeTests(){
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetWeather(){
		// ARRANGE
		String API_KEY = "82c65689932767824e83835180547308";
		String API_URL = "https://api.openweathermap.org/data/2.5/weather";
		String url = API_URL + "?lat=" + 37.8267 + "&lon=" + 122.4233 + "&appid=" + API_KEY;

		WeatherApiResponse weatherApiResponse = new WeatherApiResponse();
		WeatherApiResponse.Main main = new WeatherApiResponse.Main();
		main.setHumidity(81);
		main.setTemperature(0);
		weatherApiResponse.setMain(main);

		WeatherReport report = new WeatherReport();
		report.setTemperature(main.getTemperature());
		report.setHumidity(main.getHumidity());


		Mockito.when(restTemplate.getForObject(url, WeatherApiResponse.class)).thenReturn(weatherApiResponse);
		// ACT
		WeatherReport weatherReport = service.getWeatherReport(37.8267, 122.4233);
		// ASSERT
		Assertions.assertEquals(report.getTemperature(), weatherReport.getTemperature());
		Assertions.assertEquals(report.getHumidity(), weatherReport.getHumidity());
	}

}
