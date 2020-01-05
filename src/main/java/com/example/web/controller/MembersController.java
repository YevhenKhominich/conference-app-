package com.example.web.controller;

import com.example.data.entity.Member;
import com.example.exception.DBException;
import com.example.service.MemberService;
import com.example.web.dto.MemberDto;
import com.example.web.validation.MemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/members")
public class MembersController {

    private final MemberService memberService;

    @Autowired
    public MembersController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MemberDto>> getAll() {

        List<MemberDto> memberDto = new ArrayList<>();

        memberService.getAll()
                .forEach(m -> memberDto.add(MemberDto.from(m)));

        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody Member member) {
        MemberValidator.validate(member);
        try {
            memberService.save(member);
        } catch (RuntimeException e) {
            throw new DBException("Database exception");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        memberService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDto> getById(@PathVariable Long id) {

        return new ResponseEntity<>(MemberDto.from(memberService.get(id)), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Member member) {

        MemberValidator.validate(member);

        try {
            memberService.update(member);
        } catch (RuntimeException e) {
            throw new DBException("Database exception");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/conference/{id}")
    public ResponseEntity<Void> addMemberToConference(@PathVariable Long id, @RequestBody Member member) {

        MemberValidator.validate(member);
        try {
            memberService.addMemberToConference(member, id);
        } catch (RuntimeException e) {
            throw new DBException("Database exception");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
