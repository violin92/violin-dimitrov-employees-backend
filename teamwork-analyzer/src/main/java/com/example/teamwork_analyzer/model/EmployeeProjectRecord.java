package com.example.teamwork_analyzer.model;

import com.example.teamwork_analyzer.service.MultiDateConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import java.time.LocalDate;

public class EmployeeProjectRecord {

    @CsvBindByName(column = "EmpID")
    private long employeeId;

    @CsvBindByName(column = "ProjectID")
    private long projectId;

    @CsvCustomBindByName(column = "DateFrom", converter = MultiDateConverter.class)
    private LocalDate dateFrom;

    @CsvCustomBindByName(column = "DateTo", converter = MultiDateConverter.class)
    private LocalDate dateTo;

    public EmployeeProjectRecord() {
    }

    public EmployeeProjectRecord(long employeeId, long projectId, LocalDate dateFrom) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
    }

    public EmployeeProjectRecord(long employeeId, long projectId, LocalDate dateFrom, LocalDate dateTo) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public long getProjectId() {
        return projectId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }
}
