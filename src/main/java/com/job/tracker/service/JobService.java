package com.job.tracker.service;

import com.job.tracker.dto.JobDTO;
import com.job.tracker.entity.Job;
import com.job.tracker.entity.User;
import com.job.tracker.repository.JobRepository;
import com.job.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public JobDTO.JobResponse createJob(Long userId, JobDTO.CreateJobRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = new Job();
        job.setUser(user);
        job.setCompany(request.getCompany());
        job.setRoleName(request.getRoleName());
        job.setJobUrl(request.getJobUrl());
        job.setJobDescription(request.getJobDescription());
        job.setJobType(Job.JobType.valueOf(request.getJobType()));
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setCompanySize(request.getCompanySize());
        job.setPostedDate(request.getPostedDate());
        job.setDeadline(request.getDeadline());
        job.setStatus(Job.ApplicationStatus.valueOf(request.getStatus()));
        job.setNotes(request.getNotes());

        job = jobRepository.save(job);
        return convertToResponse(job);
    }

    public JobDTO.JobResponse getJob(Long jobId, Long userId) {
        Job job = jobRepository.findByIdAndUserId(jobId, userId)
                .orElseThrow(() -> new RuntimeException("Job not found or unauthorized"));
        return convertToResponse(job);
    }

    public JobDTO.JobListResponse getAllJobs(Long userId, String status) {
        List<Job> jobs;
        if (status != null && !status.isEmpty()) {
            jobs = jobRepository.findByUserIdAndStatus(userId, Job.ApplicationStatus.valueOf(status));
        } else {
            jobs = jobRepository.findByUserIdOrderByCreatedAtDesc(userId);
        }

        List<JobDTO.JobResponse> responses = jobs.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return new JobDTO.JobListResponse(responses, responses.size());
    }

    public JobDTO.JobResponse updateJob(Long jobId, Long userId, JobDTO.UpdateJobRequest request) {
        Job job = jobRepository.findByIdAndUserId(jobId, userId)
                .orElseThrow(() -> new RuntimeException("Job not found or unauthorized"));

        if (request.getCompany() != null) job.setCompany(request.getCompany());
        if (request.getRoleName() != null) job.setRoleName(request.getRoleName());
        if (request.getJobUrl() != null) job.setJobUrl(request.getJobUrl());
        if (request.getJobDescription() != null) job.setJobDescription(request.getJobDescription());
        if (request.getJobType() != null) job.setJobType(Job.JobType.valueOf(request.getJobType()));
        if (request.getLocation() != null) job.setLocation(request.getLocation());
        if (request.getSalary() != null) job.setSalary(request.getSalary());
        if (request.getCompanySize() != null) job.setCompanySize(request.getCompanySize());
        if (request.getDeadline() != null) job.setDeadline(request.getDeadline());
        if (request.getStatus() != null) job.setStatus(Job.ApplicationStatus.valueOf(request.getStatus()));
        if (request.getNotes() != null) job.setNotes(request.getNotes());

        job = jobRepository.save(job);
        return convertToResponse(job);
    }

    public void deleteJob(Long jobId, Long userId) {
        Job job = jobRepository.findByIdAndUserId(jobId, userId)
                .orElseThrow(() -> new RuntimeException("Job not found or unauthorized"));
        jobRepository.delete(job);
    }

    public long getJobCount(Long userId) {
        return jobRepository.countByUserId(userId);
    }

    private JobDTO.JobResponse convertToResponse(Job job) {
        return new JobDTO.JobResponse(
                job.getId(),
                job.getCompany(),
                job.getRoleName(),
                job.getJobUrl(),
                job.getJobDescription(),
                job.getJobType() != null ? job.getJobType().toString() : null,
                job.getLocation(),
                job.getSalary(),
                job.getCompanySize(),
                job.getPostedDate(),
                job.getDeadline(),
                job.getStatus() != null ? job.getStatus().toString() : null,
                job.getNotes(),
                job.getCreatedAt() != null ? job.getCreatedAt().format(dateFormatter) : null,
                job.getUpdatedAt() != null ? job.getUpdatedAt().format(dateFormatter) : null
        );
    }
}