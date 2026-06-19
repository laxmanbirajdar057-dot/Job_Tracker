package com.job.tracker.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.job.tracker.dto.ReferralDTO;

@Entity
@Table(name = "referrals")
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(nullable = false)
    private String referrerName;

    private String referrerEmail;

    private String referrerPhone;

    private String company;

    private String referrerLinkedinUrl;

    private String status;

    @Enumerated(EnumType.STRING)
    private RelationshipType relationship;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Referral() {
    }

    public Referral(Long id, User user, String referrerName, String referrerEmail, String referrerPhone,
            String company, String referrerLinkedinUrl, RelationshipType relationship, String notes,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.referrerName = referrerName;
        this.referrerEmail = referrerEmail;
        this.referrerPhone = referrerPhone;
        this.company = company;
        this.referrerLinkedinUrl = referrerLinkedinUrl;
        this.relationship = relationship;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ================= GETTERS / SETTERS =================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public String getReferrerEmail() {
        return referrerEmail;
    }

    public void setReferrerEmail(String referrerEmail) {
        this.referrerEmail = referrerEmail;
    }

    public String getReferrerPhone() {
        return referrerPhone;
    }

    public void setReferrerPhone(String referrerPhone) {
        this.referrerPhone = referrerPhone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getReferrerLinkedinUrl() {
        return referrerLinkedinUrl;
    }

    public void setReferrerLinkedinUrl(String referrerLinkedinUrl) {
        this.referrerLinkedinUrl = referrerLinkedinUrl;
    }

    public RelationshipType getRelationship() {
        return relationship;
    }

    public void setRelationship(RelationshipType relationship) {
        this.relationship = relationship;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public enum RelationshipType {
        COLLEGE_ALUMNI, FORMER_COLLEAGUE, CURRENT_COLLEAGUE, FRIEND, FAMILY, LINKEDIN_CONNECTION, RECRUITER, OTHER
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReferrerContact() {
        return this.referrerEmail; // or referrerPhone if preferred
    }

    public String getReferrerRelation() {
        return relationship != null ? relationship.name() : null;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
