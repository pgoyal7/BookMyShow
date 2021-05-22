package com.book.my.show.exception;

import lombok.Getter;

@Getter
public enum ErrorMapping {
    BMS001("BMS001", "No theatre is present for given city"),
    BMS002("BMS002", "No theatre is present for given city and movie combination"),
    BMS003("BMS003", "No Auditorium and show found for given city and theatre combination"),
    BMS004("BMS004", "No Seat available for given theatre, movie, show and movieDate combination");;

    private final String code;
    private final String message;

    ErrorMapping(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
