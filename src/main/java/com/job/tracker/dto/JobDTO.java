package com.job.tracker.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

public class JobDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateJobRequest {
        private String company;
        private String roleName;
        private String jobUrl;
        private String jobDescription;
        private String jobType;
        private String location;
        private String salary;
        private String companySize;
        private LocalDate postedDate;
        private LocalDate deadline;
        private String status;
        private String notes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateJobRequest {
        private String company;
        private String roleName;
        private String jobUrl;
        private String jobDescription;
        private String jobType;
        private String location;
        private String salary;
        private String companySize;
        private LocalDate deadline;
        private String status;
        private String notes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobResponse {
        private Long id;
        private String company;
        private String roleName;
        private String jobUrl;
        private String jobDescription;
        private String jobType;
        private String location;
        private String salary;
        private String companySize;
        private LocalDate postedDate;
        private LocalDate deadline;
        private String status;
        private String notes;
        private String createdAt;
        private String updatedAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobListResponse {
        private java.util.List<JobResponse> data;
        private long totalCount;
    }
}
