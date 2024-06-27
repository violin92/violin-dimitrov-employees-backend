package com.example.teamwork_analyzer.service;

import com.example.teamwork_analyzer.model.EmployeeProjectRecord;
import com.example.teamwork_analyzer.model.EmployeesCollaboration;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamworkAnalyzerService {
    Logger logger = LoggerFactory.getLogger(TeamworkAnalyzerService.class);

    public List<EmployeesCollaboration> analyzeFile(MultipartFile file) throws IOException {

        List<EmployeeProjectRecord> employeeProjectRecords = new ArrayList<>();
        Map<Long, List<EmployeeProjectRecord>> projectRecords = new HashMap<>();
        employeeProjectRecords = readCsvFile(file, employeeProjectRecords);

        employeeProjectRecords
                .forEach(record -> projectRecords.computeIfAbsent(
                        record.getProjectId(), k -> new ArrayList<>()).add(record));

        return analyzeCollaborations(projectRecords);
    }

    private List<EmployeesCollaboration> analyzeCollaborations(Map<Long, List<EmployeeProjectRecord>> projectRecords) {
        List<EmployeesCollaboration> employeesCollaborations = new ArrayList<>();

        for (Map.Entry<Long, List<EmployeeProjectRecord>> entry : projectRecords.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                EmployeeProjectRecord record1 = entry.getValue().get(i);
                for (int j = i + 1; j < entry.getValue().size(); j++) {
                    EmployeeProjectRecord record2 = entry.getValue().get(j);
                    long overlap = calculateOverlap(record1.getDateFrom(), record2.getDateFrom(), record1.getDateTo(), record2.getDateTo());
                    if (overlap > 0) {
                        employeesCollaborations.add(
                                new EmployeesCollaboration(record1.getEmployeeId(), record2.getEmployeeId(), record1.getProjectId(), overlap)
                        );
                    }
                }
            }
        }
        return employeesCollaborations;
    }

    private List<EmployeeProjectRecord> readCsvFile(MultipartFile file, List<EmployeeProjectRecord> employeeProjectRecords) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<EmployeeProjectRecord> csvToBean = new CsvToBeanBuilder<EmployeeProjectRecord>(reader)
                    .withType(EmployeeProjectRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            employeeProjectRecords = csvToBean.parse();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return employeeProjectRecords;
    }

    private long calculateOverlap(LocalDate dateFrom1, LocalDate dateFrom2, LocalDate dateTo1, LocalDate dateTo2) {
        LocalDate overlapStart = dateFrom1.isAfter(dateFrom2) ? dateFrom1 : dateFrom2;
        LocalDate overlapEnd = dateTo1.isBefore(dateTo2) ? dateTo1 : dateTo2;

        return ChronoUnit.DAYS.between(overlapStart, overlapEnd);
    }
}
