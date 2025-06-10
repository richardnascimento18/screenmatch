package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.models.Episode;
import br.com.alura.screenmatch.models.EpisodeData;
import br.com.alura.screenmatch.models.SeasonData;
import br.com.alura.screenmatch.models.SeriesData;
import br.com.alura.screenmatch.services.ConsumeApi;
import br.com.alura.screenmatch.services.ConvertData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

//        Stream Example
//        List<String> names = Arrays.asList("Jacque", "Iasmin", "Paulo", "Rodrigo", "Nico");
//
//        names.stream()
//                .sorted()
//                .forEach(System.out::println);

        List<EpisodeData> episodesData = seasons.stream()
                .flatMap(s -> s.episodes().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 10 Episodes:");
        episodesData.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("First Filter(N/A)" + e))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .peek(e -> System.out.println("Sorting " + e))
                .limit(10)
                .peek(e -> System.out.println("Limit " + e))
                .map(e -> e.title().toUpperCase())
                .peek(e -> System.out.println("Mapping " + e))
                .forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(s -> s.episodes().stream()
                        .map(e -> new Episode(s.number(), e))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("What episode are you looking for?");
        var title = reader.nextLine();
        Optional<Episode> searchedEpisode = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(title.toUpperCase()))
                .findFirst();
                // .isPresent(System.out::println);

        if(searchedEpisode.isPresent()) {
            System.out.println("Episode Found: " + searchedEpisode.get());
        } else {
            System.out.println("Episode Not Found!");
        }

        System.out.println("In what range of time do you wish to list the episodes?");
        var year = reader.nextInt();
        reader.nextLine();

        LocalDate searchDate = LocalDate.of(year, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodes.stream()
                .filter(e -> e.getLaunch_date() != null && e.getLaunch_date().isAfter(searchDate))
                .forEach(e -> System.out.println(
                        "Season: " + e.getSeason() +
                                " Episode: " + e.getTitle() +
                                " Launch Date: " + e.getLaunch_date().format(formatter)
                ));
    }
}
