package com.snacksprint.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data

public class ContactInformation {
    private String email;
    private String mobile;
    private String twitter;
    private String instagram;
}
