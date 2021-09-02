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
//
//    private String videoTypeCheck(String videoType) {
//        String type=videoType.toString();
//        String[] types=type.split(":");
//        switch (types[0]){
//            case "Regular":
//                return "Regular";
//            case "Children's Movie":
//                return new ChildrenMovie(types[1]);
//            case "New Release":
//                LocalDate date = LocalDate.parse(types[1]);
//                return new NewRelease(date);
//        }
//        return null;
//    }
}
