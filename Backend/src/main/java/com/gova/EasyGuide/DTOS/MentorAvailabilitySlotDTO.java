package com.gova.EasyGuide.DTOS;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MentorAvailabilitySlotDTO {
    private String weekday;
    private String startTime;
    private String endTime;
    private Boolean bookingStatus;

    public MentorAvailabilitySlotDTO(String weekday, String startTime, String endTime, Boolean bookingStatus) {
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingStatus = bookingStatus;
    }
}
