package br.com.alura.screenmatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private Double episodeRating;
    private LocalDate launch_date;

    public Episode(Integer number, EpisodeData e) {
        this.season = number;
        this.title = e.title();
        this.episodeNumber = e.number();

        try {
            this.episodeRating = Double.valueOf(e.rating());
        } catch (NumberFormatException ex) {
            this.episodeRating = 0.0;
        }
        try {
            this.launch_date = LocalDate.parse(e.launch_date());
        } catch (DateTimeParseException ex) {
            this.launch_date = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Double getEpisodeRating() {
        return episodeRating;
    }

    public void setEpisodeRating(Double episodeRating) {
        this.episodeRating = episodeRating;
    }

    public LocalDate getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(LocalDate launch_date) {
        this.launch_date = launch_date;
    }

    @Override
    public String toString() {
        return "season=" + season +
                ", title='" + title + '\'' +
                ", episodeNumber=" + episodeNumber +
                ", episodeRating=" + episodeRating +
                ", launch_date=" + launch_date;
    }
}
