package com.book.my.show.request;

import com.book.my.show.response.SeatInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Getter
@NoArgsConstructor
public class TheatreInfo {
    private String cityName;
    private String theatreName;
    private String movieName;
    private String auditoriumName;
    private String showDay;
    private String showTime;
    private List<SeatInfo> requestedSeatList;
}
