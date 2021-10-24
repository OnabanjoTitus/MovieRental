package com.movieRent.movieRental.web.Controller;

import com.movieRent.movieRental.data.model.*;
import com.movieRent.movieRental.services.VideoServices;
import com.movieRent.movieRental.web.Exception.VideoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Slf4j
@RestController
@RequestMapping("/videoRental")
public class VideoController {

    @Autowired
    VideoServices videoServices;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody VideoDto videoDto )  {
        VideoDto videoDto1;
        try{
            videoDto1=videoServices.addVideo(videoDto);
        }
        catch (VideoException videoException){
            return new ResponseEntity<>(videoException.getMessage(),HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(videoDto1, HttpStatus.OK);

    }

    @GetMapping("/listAllVideos")
    public ResponseEntity<?> findAllMovie(){
        List<VideoDto> video=videoServices.findAllVideos()
                .stream()
                .map(videoDto-> videoDto.add(
                        linkTo(methodOn(VideoController.class).videoWithPrice(videoDto.getVideoTitle())).withRel("View Movie info"),
                        linkTo(methodOn(VideoController.class).findAllMovie()).withSelfRel()
                )).collect(Collectors.toList());
        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @GetMapping("/findVideoById/{id}")
    public ResponseEntity<?> videoWithPrice(@PathVariable("id")String id) {
        VideoPrice videoPrice;

        try{
            videoPrice=videoServices.findVideoByTitle(id);
            EntityModel<VideoPrice> videoDtoEntity=EntityModel.of(
                    videoPrice,linkTo(methodOn(VideoController.class).
                            findAllMovie()).withRel("All Movies"));
            return new ResponseEntity<>(videoDtoEntity, HttpStatus.OK);
        }
        catch (VideoException videoException){
            return new ResponseEntity<>(videoException.getMessage(),HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("/calculateVideoPrice")
    public ResponseEntity<?>rentAVideo(@RequestBody CalculateVideoPriceDto calculateVideoPriceDto) {
        VideoDtoWithPriceAndUsername videoDtoWithPriceAndUsername;

        try{
           videoDtoWithPriceAndUsername= videoServices.calculateVideoPrice(calculateVideoPriceDto);

        }
        catch (VideoException videoException){
            return new ResponseEntity<>(videoException.getMessage(),HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(videoDtoWithPriceAndUsername,HttpStatus.OK);
    }

}
