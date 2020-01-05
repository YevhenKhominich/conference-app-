package com.example.web.controller;


import com.example.data.entity.Conference;
import com.example.exception.DBException;
import com.example.service.ConferenceService;
import com.example.web.dto.ConferenceDto;
import com.example.web.validation.ConferenceValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Api(value = "Conference Controller")
@Slf4j
@RestController
@RequestMapping(value = "/conferences")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List> getAll() {
        List<ConferenceDto> conferencesDto = new ArrayList<>();

        conferenceService.getAll()
                .forEach(c -> conferencesDto.add(ConferenceDto.from(c)));

        return new ResponseEntity<>(conferencesDto, HttpStatus.OK);
    }

//    @ApiOperation(value = "Create new Conference")
//    @ApiResponses({
//            @ApiResponse(code = 201,message = "Successfull created")
//
//    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody Conference conference) {

        //date validation regex
        ConferenceValidator.validate(conference);

        try {
            conferenceService.save(conference);
        } catch (RuntimeException e) {
            throw new DBException("Database exception");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        conferenceService.cancel(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConferenceDto> getById(@PathVariable Long id) {

        return new ResponseEntity<>(ConferenceDto.from(conferenceService.get(id)), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Conference conference) {
        ConferenceValidator.validate(conference);

        try {
            conferenceService.update(conference);
        } catch (RuntimeException e) {
            throw new DBException("Database exception");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "{id}/members", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List> getAllMembersForConference(@PathVariable long id) {

        return new ResponseEntity<>(conferenceService.getAllMembersForConference(id), HttpStatus.OK);
    }
}
