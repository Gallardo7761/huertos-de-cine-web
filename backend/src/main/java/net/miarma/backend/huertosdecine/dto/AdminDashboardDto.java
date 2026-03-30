package net.miarma.backend.huertosdecine.dto;

import java.util.List;

public class AdminDashboardDto {
    private List<RequestDto.Response> requests;
    private List<ViewerDto> viewers;
    private List<MovieDto.Response> movies;

    public AdminDashboardDto(List<RequestDto.Response> requests, List<ViewerDto> viewers, List<MovieDto.Response> movies) {
        this.requests = requests;
        this.viewers = viewers;
        this.movies = movies;
    }

    public List<RequestDto.Response> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestDto.Response> requests) {
        this.requests = requests;
    }

    public List<ViewerDto> getViewers() {
        return viewers;
    }

    public void setViewers(List<ViewerDto> viewers) {
        this.viewers = viewers;
    }

    public List<MovieDto.Response> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDto.Response> movies) {
        this.movies = movies;
    }
}
