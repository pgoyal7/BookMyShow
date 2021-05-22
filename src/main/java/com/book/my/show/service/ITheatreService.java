package com.book.my.show.service;

import com.book.my.show.response.AvailableSeatResponse;
import com.book.my.show.response.RunningShowResponse;
import com.book.my.show.response.TheatreResponse;

import java.time.LocalDate;

public interface ITheatreService {
    TheatreResponse getTheatresByCity(String cityName);
    TheatreResponse getTheatresByCityAndMovie(String cityName, String movieName);
    RunningShowResponse getAuditoriumsAndShowsByTheatreAndCity(String theatreName, String cityName);
    AvailableSeatResponse getAvailableSeats(String cityName, String theatreName, String movieName, String showTime, LocalDate showDay);
}
