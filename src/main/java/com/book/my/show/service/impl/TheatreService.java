package com.book.my.show.service.impl;

import com.book.my.show.dto.SeatDTO;
import com.book.my.show.entity.Auditorium;
import com.book.my.show.entity.Seat;
import com.book.my.show.entity.Show;
import com.book.my.show.entity.Theatre;
import com.book.my.show.exception.ErrorMapping;
import com.book.my.show.exception.ResourceNotFoundException;
import com.book.my.show.response.AvailableSeatResponse;
import com.book.my.show.response.RunningShow;
import com.book.my.show.response.RunningShowResponse;
import com.book.my.show.response.TheatreResponse;
import com.book.my.show.respository.AuditoriumRepository;
import com.book.my.show.respository.SeatRepository;
import com.book.my.show.respository.TheatreRepository;
import com.book.my.show.respository.specification.AuditoriumSpecification;
import com.book.my.show.respository.specification.SeatSpecification;
import com.book.my.show.respository.specification.TheatreSpecification;
import com.book.my.show.service.ITheatreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service("bookService")
@Slf4j
public class TheatreService implements ITheatreService {

    private final TheatreRepository theatreRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final SeatRepository seatRepository;

    public TheatreService(TheatreRepository theatreRepository, AuditoriumRepository auditoriumRepository,
                          SeatRepository seatRepository) {
        this.theatreRepository = theatreRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.seatRepository = seatRepository;
    }

    @Cacheable("Theatres")
    @Override
    public TheatreResponse getTheatresByCity(String cityName) {
        log.info("");//Aspect add for logging

        TheatreSpecification theatreSpecification = TheatreSpecification
                .builder()
                .cityName(cityName)
                .build();

        List<Theatre> theatres = theatreRepository.findAll(theatreSpecification);
        if(theatres.size() != 0) {
            return new TheatreResponse()
                    .setTheatres(theatres.stream().map(Theatre::getName).collect(Collectors.toList()));
        }

        throw new ResourceNotFoundException(HttpStatus.NO_CONTENT, ErrorMapping.BMS001);
    }

    @Cacheable("Theatres")
    @Override
    public TheatreResponse getTheatresByCityAndMovie(String cityName, String movieName) {
        TheatreSpecification theatreSpecification = TheatreSpecification.builder()
                .cityName(cityName)
                .movieName(movieName)
                .build();

        List<Theatre> theatres = theatreRepository.findAll(theatreSpecification);
        if(theatres.size() != 0) {
            return new TheatreResponse()
                    .setTheatres(theatres.stream().map(Theatre::getName)
                            .collect(Collectors.toList()));
        }

        throw new ResourceNotFoundException(HttpStatus.NO_CONTENT, ErrorMapping.BMS002);
    }

    @Cacheable("RunningShows")
    @Override
    public RunningShowResponse getAuditoriumsAndShowsByTheatreAndCity(String theatreName, String cityName) {
        AuditoriumSpecification auditoriumSpecification = AuditoriumSpecification.builder()
                .cityName(cityName)
                .theatreName(theatreName)
                .build();

        List<Auditorium> auditoriums = auditoriumRepository.findAll(auditoriumSpecification);
        if(auditoriums.size() != 0) {
            return new RunningShowResponse()
                    .setRunningShows(auditoriums.stream().map((auditorium) -> new RunningShow()
                            .setAuditoriumName(auditorium.getName())
                            .setMovieName(auditorium.getMovie().getName())
                            .setShowTimes(auditorium.getShows().stream()
                                    .map(Show::getName)
                                    .collect(Collectors.toList())))
                            .collect(Collectors.toList()));
        }

        throw new ResourceNotFoundException(HttpStatus.NO_CONTENT, ErrorMapping.BMS003);
    }

    @Cacheable("AvailableSeats")
    @Override
    public AvailableSeatResponse getAvailableSeats(String cityName, String theatreName, String movieName, String showTime, LocalDate showDay) {
        SeatSpecification seatSpecification = SeatSpecification.builder()
                .cityName(cityName)
                .theatreName(theatreName)
                .movieName(movieName)
                .showTime(showTime)
                .showDay(showDay)
                .build();

        List<Seat> seats = seatRepository.findAll(seatSpecification);
        if(seats.size() != 0) {
            return new AvailableSeatResponse()
                    .setSeats(seats.stream().map((seat) -> new SeatDTO()
                            .setSeatName(seat.getName())
                            .setSeatType(seat.getSeatType()))
                            .collect(Collectors.toList()));
        }

        throw new ResourceNotFoundException(HttpStatus.NO_CONTENT, ErrorMapping.BMS004);
    }
}
