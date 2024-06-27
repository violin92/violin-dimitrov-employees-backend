package com.example.teamwork_analyzer.controller;

import com.example.teamwork_analyzer.model.EmployeesCollaboration;
import com.example.teamwork_analyzer.service.TeamworkAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/analyze-file")
@CrossOrigin(origins = "http://localhost:4200")
public class TeamworkAnalyzerController {

    @Autowired
    private TeamworkAnalyzerService teamworkAnalyzerService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeesCollaboration>> analyzeFile(@RequestParam("file") MultipartFile file) {
        try {
            var result = teamworkAnalyzerService.analyzeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

}
