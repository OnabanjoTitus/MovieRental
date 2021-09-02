package com.movieRent.movieRental.data.model;

import lombok.Data;

@Data
public class CalculateVideoPriceDto {
    private String userName;
    private String videoSelectedTitle;
    private Integer numberOfDays;
}
