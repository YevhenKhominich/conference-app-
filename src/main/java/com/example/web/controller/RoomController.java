package com.example.web.controller;

import com.example.data.entity.Room;
import com.example.exception.DBException;
import com.example.service.RoomService;
import com.example.web.validation.RoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Room>> getAll() {

        return new ResponseEntity<>(roomService.getAll(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody Room room) {

        RoomValidator.validate(room);

        try {
            roomService.save(room);
        } catch (RuntimeException e) {
            throw new DBException("Database exception");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        roomService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> getById(@PathVariable Long id) {

        return new ResponseEntity<>(roomService.get(id), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Room room) {

        RoomValidator.validate(room);
        try {
            roomService.update(room);
        } catch (RuntimeException e) {
            throw new DBException("Database exception");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
