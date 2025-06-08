package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.models.SeasonData;
import br.com.alura.screenmatch.models.SeriesData;
import br.com.alura.screenmatch.services.ConsumeApi;
import br.com.alura.screenmatch.services.ConvertData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner reader = new Scanner(System.in);
    private ConsumeApi consumeApi = new ConsumeApi();
    private ConvertData convertData = new ConvertData();

    private final String ADDRESS = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e739aba9";

    public void showMenu() {
        System.out.println("Type the name of the series for search");
        var serieName = reader.nextLine();

        var json = consumeApi.getData(ADDRESS + serieName.replace(' ', '+') + API_KEY);

        SeriesData data = convertData.getData(json, SeriesData.class);
        System.out.println(data);

        List<SeasonData> seasons = new ArrayList<>();
		for (int i = 1; i < data.totalSeasons(); i++) {
			json = consumeApi.getData(ADDRESS + serieName.replace(' ', '+') + "&season=" + i + API_KEY);
			SeasonData seasonData = convertData.getData(json, SeasonData.class);
			seasons.add(seasonData);
		}

		seasons.forEach(System.out::println);

        seasons.forEach(s -> s.episodes().forEach(e -> System.out.println((e.title()))));
    }
}
