package com.movieRent.movieRental.services;

import com.movieRent.movieRental.data.model.*;
import com.movieRent.movieRental.data.repository.VideoRepository;
import com.movieRent.movieRental.web.Exception.VideoException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        if( videoDto.getVideoType().isEmpty()||videoDto.getVideoType().isBlank()||videoDto.getVideoType()==null){
            throw new VideoException("Video Type cannot be empty");
        }
        if(videoDto.getVideoTitle()==null|| videoDto.getVideoTitle().equals("")){
            throw new VideoException("Video Name cannot be empty");
        }
        if(notAType(videoDto.getVideoType())){
            throw new VideoException("Video Type is Invalid");
        }
        Video video= new Video();
        video.setVideoGenre(videoGenreCheck(videoDto.getVideoGenre()));
        video.setVideoTitle(videoDto.getVideoTitle().toUpperCase());
        video.setVideoType(videoDto.getVideoType());
        video.setVideoPrice(setVideoPrice(video.getVideoType()));
        videoRepository.save(video);
        return videoDto;
    }

    private boolean notAType(String videoType) {
        String type= videoType;
        String check=type;
        if(videoType.contains(":")){
                String[] types=type.split(":");
                check=types[0].trim();
        }
        if(check.equalsIgnoreCase("regular")||check.equalsIgnoreCase("children'smovie")||check.equalsIgnoreCase("newrelease")){
            return false;
        }
        return true;
    }


    private Double setVideoPrice(String videoType) {
        videoType=videoType.toLowerCase();
        String[] types=videoType.split(":");
        switch (types[0]){
            case "regular":
                return 10.0;
            case "children's movie":
                return 8.0;
            case "new release":
                return 15.0;
        }
        return null;

    }

    @Override
    public VideoDtoWithPriceAndUsername calculateVideoPrice(CalculateVideoPriceDto calculateVideoPriceDto) throws VideoException {
        if(calculateVideoPriceDto.getNumberOfDays()<1){
            throw new VideoException("Number of days is invalid");
        }
        if(calculateVideoPriceDto.getUserName()==null||calculateVideoPriceDto.getUserName().isEmpty()||calculateVideoPriceDto.getUserName().isBlank()){
            throw new VideoException("UserName cannot be empty");
        }
        Pageable firstPageWithTwoElements = PageRequest.of(0, findAllVideos().size());
        Page<Video> videoList=videoRepository.findAll(firstPageWithTwoElements);
        Video video=videoList
                .stream().filter(videos ->videos.getVideoTitle().equalsIgnoreCase(calculateVideoPriceDto.getVideoSelectedTitle())).findFirst().orElseThrow(()->new VideoException("Video with this title not found"));
            Integer integer= videoTypeCheck(video.getVideoType());
            VideoDtoWithPriceAndUsername videoDtoWithPriceAndUsername = new VideoDtoWithPriceAndUsername();
            modelMapper.map(video,videoDtoWithPriceAndUsername);
            videoDtoWithPriceAndUsername.setUserName(calculateVideoPriceDto.getUserName());
            videoDtoWithPriceAndUsername.setNumberOfDays(calculateVideoPriceDto.getNumberOfDays());
            videoDtoWithPriceAndUsername.setVideoSelected(calculateVideoPriceDto.getVideoSelectedTitle());
            videoDtoWithPriceAndUsername.setPriceOfMovie(calculateVideoPriceBasedOnVideoType(integer,video.getVideoType(),calculateVideoPriceDto.getNumberOfDays()));

           return videoDtoWithPriceAndUsername;
    }

    @Override
    public VideoPrice findVideoByTitle(String videoTitle) throws VideoException {
        Pageable firstPageWithTwoElements = PageRequest.of(0, findAllVideos().size());
        Page<Video> videoList=videoRepository.findAll(firstPageWithTwoElements);
        Video videoFound=videoList
                .stream().filter(video ->video.getVideoTitle().equalsIgnoreCase(videoTitle)).findFirst().orElseThrow(()->new VideoException("Video with this title not found"));
        VideoPrice videoDto= new VideoPrice();

        modelMapper.map(videoFound,videoDto);
        return videoDto;
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
                return 15.0*numberOfDays-(integer);
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
