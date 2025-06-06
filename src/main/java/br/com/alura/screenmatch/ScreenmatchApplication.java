package br.com.alura.screenmatch;

import br.com.alura.screenmatch.models.SeriesData;
import br.com.alura.screenmatch.services.ConsumeApi;
import br.com.alura.screenmatch.services.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		var consumeApi = new ConsumeApi();
		var json = consumeApi.getData("http://www.omdbapi.com/?t=gilmore+girls&apikey=e739aba9");
		ConvertData convertData = new ConvertData();
		SeriesData data = convertData.getData(json, SeriesData.class);
		System.out.println(data);
	}
}
