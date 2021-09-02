package com.movieRent.movieRental.services;

import com.movieRent.movieRental.data.model.CalculateVideoPriceDto;
import com.movieRent.movieRental.data.model.Genre;
import com.movieRent.movieRental.data.model.VideoDto;
import com.movieRent.movieRental.data.model.VideoDtoWithPrice;
import com.movieRent.movieRental.data.repository.VideoRepository;
import com.movieRent.movieRental.web.Exception.VideoException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class VideoServicesImplTest {

    @Autowired
    VideoServices videoServices;

    CalculateVideoPriceDto calculateVideoPriceDto;

    VideoDto videoDto;

    @BeforeEach
    void setUp() {
        videoDto= new VideoDto();
        calculateVideoPriceDto= new CalculateVideoPriceDto();
    }

    @AfterEach
    void tearDown() {
        videoDto=null;
    }
    @Test
    void testThatWeCanSaveAVideoToDB() throws VideoException {
        videoDto.setVideoGenre("Action");
        videoDto.setVideoTitle("Jackie");
        videoDto.setVideoType("Children's Movie: 19");
        videoServices.addVideo(videoDto);
        List<VideoDto>videoDtoList=videoServices.findAllVideos();
        log.info("The list of vid in th db are -->{}",videoDtoList);
        assertTrue(videoDtoList.contains(videoDto));
    }

    @Test
    void testThatWeCanFetchAllVideosFromDB() throws VideoException {
        videoDto.setVideoGenre("Action");
        videoDto.setVideoTitle("Jackie");
        videoDto.setVideoType("New Release: 3");
        videoServices.addVideo(videoDto);
        List<VideoDto>videoDtoList=videoServices.findAllVideos();
        log.info("The list of vid in th db are -->{}",videoDtoList);
        assertFalse(videoDtoList.isEmpty());
    }
    @Test
    void testThatWeCanCalculateThePriceOfAMovieRegular() throws VideoException {
        videoDto.setVideoGenre("Action");
        videoDto.setVideoTitle("Jackie");
        videoDto.setVideoType("REGULAR");
        videoServices.addVideo(videoDto);
        calculateVideoPriceDto.setUserName("Titus");
        calculateVideoPriceDto.setNumberOfDays(3);
        calculateVideoPriceDto.setVideoSelectedTitle("Jackie");
      VideoDtoWithPrice videoDtoWithPrice= videoServices.calculateVideoPrice(calculateVideoPriceDto);
      log.info("The movie dto with price is -->{}",videoDtoWithPrice);
      assertEquals(videoDtoWithPrice.getPriceOfMovie(),30);
    }
    @Test
    void testThatWeCanCalculateThePriceOfAMovieChildrenMovie() throws VideoException {
        videoDto.setVideoGenre("Action");
        videoDto.setVideoTitle("Jackie");
        videoDto.setVideoType("Children's Movie:20");
        videoServices.addVideo(videoDto);
        calculateVideoPriceDto.setUserName("Titus");
        calculateVideoPriceDto.setNumberOfDays(3);
        calculateVideoPriceDto.setVideoSelectedTitle("Jackie");
        VideoDtoWithPrice videoDtoWithPrice= videoServices.calculateVideoPrice(calculateVideoPriceDto);
        log.info("The movie dto with price is -->{}",videoDtoWithPrice);
        assertEquals(videoDtoWithPrice.getPriceOfMovie(),34);
    }
    @Test
    void testThatWeCanCalculateThePriceOfAMovieNewRelease() throws VideoException {
        videoDto.setVideoGenre("Action");
        videoDto.setVideoTitle("Jackie");
        videoDto.setVideoType("REGULAR");
        videoServices.addVideo(videoDto);
        calculateVideoPriceDto.setUserName("Titus");
        calculateVideoPriceDto.setNumberOfDays(3);
        calculateVideoPriceDto.setVideoSelectedTitle("Jackie");
        VideoDtoWithPrice videoDtoWithPrice= videoServices.calculateVideoPrice(calculateVideoPriceDto);
        log.info("The movie dto with price is -->{}",videoDtoWithPrice);
        assertEquals(videoDtoWithPrice.getPriceOfMovie(),30);
    }


}