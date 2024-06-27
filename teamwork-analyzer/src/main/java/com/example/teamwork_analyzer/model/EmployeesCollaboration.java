package com.example.teamwork_analyzer.model;

public class EmployeesCollaboration {

    private final long employeeId1;

    private final long employeeId2;

    private final long projectId;

    private final long durationDays;

    public EmployeesCollaboration(long employeeId1, long employeeId2, long projectId, long durationDays) {
        this.employeeId1 = employeeId1;
        this.employeeId2 = employeeId2;
        this.projectId = projectId;
        this.durationDays = durationDays;
    }

    public long getEmployeeId1() {
        return employeeId1;
    }

    public long getEmployeeId2() {
        return employeeId2;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getDurationDays() {
        return durationDays;
    }
}
