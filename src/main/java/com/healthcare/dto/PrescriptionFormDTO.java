package com.healthcare.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PrescriptionFormDTO {

    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    private String instructions;

    private LocalDate validUntil;

    private List<PrescriptionItemDTO> items = new ArrayList<>();
}
