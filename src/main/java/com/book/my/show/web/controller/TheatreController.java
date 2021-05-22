package com.book.my.show.web.controller;

import com.book.my.show.response.AvailableSeatResponse;
import com.book.my.show.response.RunningShowResponse;
import com.book.my.show.response.TheatreResponse;
import com.book.my.show.service.impl.TheatreService;
import com.book.my.show.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/bookmyshow")
public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping("/ext/v1/cities/{cityName}/theatres")
    public ResponseEntity<TheatreResponse> getTheatresByCity(@PathVariable("cityName") String cityName) throws InstantiationException, IllegalAccessException {
        return ResponseEntity
                .ok().body(theatreService.getTheatresByCity(cityName.toUpperCase()));
    }

    @GetMapping("/ext/v1/cities/{cityName}/movies/{movieName}/theatres")
    public ResponseEntity<TheatreResponse> getTheatresByCityAndMovie(@PathVariable("cityName") String cityName,
                                                                     @PathVariable("movieName") String movieName) {
        return ResponseEntity
                .ok().body(theatreService.getTheatresByCityAndMovie(cityName.toUpperCase(), movieName.toUpperCase()));
    }

    @GetMapping(value = "/ext/v1/cities/{cityName}/theatres/{theatreName}/auditoriums/shows")
    public ResponseEntity<RunningShowResponse> getAuditoriumsAndShowsByTheatreAndCity(@PathVariable("cityName") String cityName,
                                                                      @PathVariable("theatreName") String theatreName) {
        return ResponseEntity
                .ok().body(theatreService.getAuditoriumsAndShowsByTheatreAndCity(theatreName.toUpperCase(), cityName.toUpperCase()));
    }

    @GetMapping("/ext/v1/city/{cityName}/theatres/{theatreName}/movies/{movieName}/shows/{showTime}")
    public ResponseEntity<AvailableSeatResponse> getAvailableSeats(@PathVariable("cityName") String cityName,
                                                                   @PathVariable("theatreName") String theatreName, @PathVariable("movieName") String movieName,
                                                                   @PathVariable("showTime") String showTime, @RequestParam("showDay") String showDate) {
        return ResponseEntity
                .ok().body(theatreService.getAvailableSeats(cityName.toUpperCase(), theatreName.toUpperCase(),
                        movieName.toUpperCase(), showTime.toUpperCase(), LocalDate.parse(showDate, Constants.formatter)));
    }
}
