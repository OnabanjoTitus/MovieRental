package com.movieRent.movieRental.services;

import com.movieRent.movieRental.data.model.*;
import com.movieRent.movieRental.data.repository.VideoRepository;
import com.movieRent.movieRental.web.Exception.VideoException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class VideoServicesImpl implements VideoServices{
    @Autowired
    VideoRepository videoRepository;

    @Autowired
    ModelMapper modelMapper=new ModelMapper();


    @Override
    public List<VideoDto> findAllVideos() {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 10);
        Page<Video> videoList=videoRepository.findAll(firstPageWithTwoElements);
        List<VideoDto>videoDtoList=videoList
                .stream().map(video ->modelMapper.map(video,VideoDto.class)).collect(Collectors.toList());
        return videoDtoList;
    }

    @Override
    public VideoDto addVideo(VideoDto videoDto) throws VideoException {

        if(videoDto.getVideoGenre()==null|| videoDto.getVideoGenre().toString().equals("")){
            throw new VideoException("Video Genre cannot be null");
        }
        if(videoDto.getVideoType().equals("") ||videoDto.getVideoType()==null){
            throw new VideoException("Video Type cannot be empty");
        }
        if(videoDto.getVideoTitle()==null|| videoDto.getVideoTitle().equals("")){
            throw new VideoException("Video Genre cannot be empty");
        }
        Video video= new Video();
        video.setVideoGenre(videoGenreCheck(videoDto.getVideoGenre()));
        video.setVideoTitle(videoDto.getVideoTitle());
        video.setVideoType(videoDto.getVideoType());
        videoRepository.save(video);
        return videoDto;
    }

    @Override
    public VideoDtoWithPrice calculateVideoPrice(CalculateVideoPriceDto calculateVideoPriceDto) throws VideoException {
           Video video= videoRepository.findAllByVideoTitle(calculateVideoPriceDto.getVideoSelectedTitle()).orElseThrow(()->new VideoException("There is no video with this title"));
           Integer integer= videoTypeCheck(video.getVideoType());
           VideoDtoWithPrice videoDtoWithPrice= new VideoDtoWithPrice();
           videoDtoWithPrice.setPriceOfMovie(calculateVideoPriceBasedOnVideoType(integer,video.getVideoType(),calculateVideoPriceDto.getNumberOfDays()));
           videoDtoWithPrice.setUserName(calculateVideoPriceDto.getUserName());
           videoDtoWithPrice.setNumberOfDays(calculateVideoPriceDto.getNumberOfDays());
           videoDtoWithPrice.setVideoSelected(calculateVideoPriceDto.getVideoSelectedTitle());

           return videoDtoWithPrice;
    }

    private Double calculateVideoPriceBasedOnVideoType(Integer integer, String videoType,Integer numberOfDays) {
        log.info("Calculator-->{}{}",videoType,integer);
        videoType=videoType.toLowerCase();
        String[] types=videoType.split(":");
        log.info("Calculator2-->{}{}",videoType,integer);
        switch (types[0]){
            case "regular":
                return 10.0*numberOfDays;
            case "children's movie":
                return 8.0*numberOfDays+(integer/2.0);
            case "new release":
                return 15.0*numberOfDays-(integer/2.0);
        }
        return null;
    }

    private Genre videoGenreCheck(String videoGenre) {
        if(videoGenre.equalsIgnoreCase("Action")){
            return Genre.Action;
        }
        if(videoGenre.equalsIgnoreCase("Drama")){
            return Genre.Drama;
        }
        if(videoGenre.equalsIgnoreCase("Romance")){
            return Genre.Romance;
        }
        if(videoGenre.equalsIgnoreCase("Comedy")){
            return Genre.Comedy;
        }
        if(videoGenre.equalsIgnoreCase("Horror")){
            return Genre.Horror;
        }
        return null;
    }

    private Integer videoTypeCheck(String videoType) {
        String type= videoType;
        String check=type;
        String type2="";
        if(!type.equalsIgnoreCase("regular")){
        String[] types=type.split(":");
        type2=types[1];
        check=types[0];
        log.info("The price na-->{}",types[1]);
        }

        switch (check){
            case "Regular":
                return 0;
            case "Children's Movie":
            case "New Release":
                return Integer.valueOf(type2.trim());
        }
        return null;
    }
}
