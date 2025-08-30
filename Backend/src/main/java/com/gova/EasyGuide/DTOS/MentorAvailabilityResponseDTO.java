package com.gova.EasyGuide.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class MentorAvailabilityResponseDTO {

    private Long userId;
    private List<MentorAvailabilitySlotDTO> slots;

    public MentorAvailabilityResponseDTO(Long userId, List<MentorAvailabilitySlotDTO> slots) {
        this.userId = userId;
        this.slots = slots;

    }
}
