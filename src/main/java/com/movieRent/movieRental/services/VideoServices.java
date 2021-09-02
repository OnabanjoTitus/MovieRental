package com.movieRent.movieRental.services;

import com.movieRent.movieRental.data.model.CalculateVideoPriceDto;
import com.movieRent.movieRental.data.model.VideoDto;
import com.movieRent.movieRental.data.model.VideoDtoWithPriceAndUsername;
import com.movieRent.movieRental.data.model.VideoPrice;
import com.movieRent.movieRental.web.Exception.VideoException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface VideoServices {
    List<VideoDto> findAllVideos();
    VideoDto addVideo(VideoDto videoDto) throws VideoException;
    VideoDtoWithPriceAndUsername calculateVideoPrice(CalculateVideoPriceDto calculateVideoPriceDto) throws VideoException;

    VideoPrice findVideoByTitle(String videoId) throws VideoException;
}
