package com.movieRent.movieRental.data.model;

import lombok.Data;

@Data
public class VideoDtoWithPriceAndUsername {
    private String userName;
    private String videoSelected;
    private Integer numberOfDays;
    private Double priceOfMovie;
    private String videoType;
}
