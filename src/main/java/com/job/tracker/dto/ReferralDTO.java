package com.job.tracker.dto;

import java.util.List;

public class ReferralDTO {

    public static class CreateReferralRequest {
        private String referrerName;
        private String referrerEmail;
        private String referrerPhone;
        private String company;
        private String referrerLinkedinUrl;
        private String relationship;
        private String notes;

        public CreateReferralRequest() {
        }

        public CreateReferralRequest(String referrerName, String referrerEmail, String referrerPhone,
                                      String company, String referrerLinkedinUrl, String relationship, String notes) {
            this.referrerName = referrerName;
            this.referrerEmail = referrerEmail;
            this.referrerPhone = referrerPhone;
            this.company = company;
            this.referrerLinkedinUrl = referrerLinkedinUrl;
            this.relationship = relationship;
            this.notes = notes;
        }

        public String getReferrerName() { return referrerName; }
        public void setReferrerName(String referrerName) { this.referrerName = referrerName; }

        public String getReferrerEmail() { return referrerEmail; }
        public void setReferrerEmail(String referrerEmail) { this.referrerEmail = referrerEmail; }

        public String getReferrerPhone() { return referrerPhone; }
        public void setReferrerPhone(String referrerPhone) { this.referrerPhone = referrerPhone; }

        public String getCompany() { return company; }
        public void setCompany(String company) { this.company = company; }

        public String getReferrerLinkedinUrl() { return referrerLinkedinUrl; }
        public void setReferrerLinkedinUrl(String referrerLinkedinUrl) { this.referrerLinkedinUrl = referrerLinkedinUrl; }

        public String getRelationship() { return relationship; }
        public void setRelationship(String relationship) { this.relationship = relationship; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class UpdateReferralRequest {
        private String referrerName;
        private String referrerEmail;
        private String referrerPhone;
        private String company;
        private String referrerLinkedinUrl;
        private String relationship;
        private String notes;

        public UpdateReferralRequest() {
        }

        public UpdateReferralRequest(String referrerName, String referrerEmail, String referrerPhone,
                                      String company, String referrerLinkedinUrl, String relationship, String notes) {
            this.referrerName = referrerName;
            this.referrerEmail = referrerEmail;
            this.referrerPhone = referrerPhone;
            this.company = company;
            this.referrerLinkedinUrl = referrerLinkedinUrl;
            this.relationship = relationship;
            this.notes = notes;
        }

        public String getReferrerName() { return referrerName; }
        public void setReferrerName(String referrerName) { this.referrerName = referrerName; }

        public String getReferrerEmail() { return referrerEmail; }
        public void setReferrerEmail(String referrerEmail) { this.referrerEmail = referrerEmail; }

        public String getReferrerPhone() { return referrerPhone; }
        public void setReferrerPhone(String referrerPhone) { this.referrerPhone = referrerPhone; }

        public String getCompany() { return company; }
        public void setCompany(String company) { this.company = company; }

        public String getReferrerLinkedinUrl() { return referrerLinkedinUrl; }
        public void setReferrerLinkedinUrl(String referrerLinkedinUrl) { this.referrerLinkedinUrl = referrerLinkedinUrl; }

        public String getRelationship() { return relationship; }
        public void setRelationship(String relationship) { this.relationship = relationship; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class ReferralResponse {
        private Long id;
        private String referrerName;
        private String referrerEmail;
        private String referrerPhone;
        private String company;
        private String referrerLinkedinUrl;
        private String relationship;
        private String notes;
        private long referredJobCount;
        private String createdAt;
        private String updatedAt;

        public ReferralResponse() {
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getReferrerName() { return referrerName; }
        public void setReferrerName(String referrerName) { this.referrerName = referrerName; }

        public String getReferrerEmail() { return referrerEmail; }
        public void setReferrerEmail(String referrerEmail) { this.referrerEmail = referrerEmail; }

        public String getReferrerPhone() { return referrerPhone; }
        public void setReferrerPhone(String referrerPhone) { this.referrerPhone = referrerPhone; }

        public String getCompany() { return company; }
        public void setCompany(String company) { this.company = company; }

        public String getReferrerLinkedinUrl() { return referrerLinkedinUrl; }
        public void setReferrerLinkedinUrl(String referrerLinkedinUrl) { this.referrerLinkedinUrl = referrerLinkedinUrl; }

        public String getRelationship() { return relationship; }
        public void setRelationship(String relationship) { this.relationship = relationship; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }

        public long getReferredJobCount() { return referredJobCount; }
        public void setReferredJobCount(long referredJobCount) { this.referredJobCount = referredJobCount; }

        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

        public String getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
    }

    public static class ReferralListResponse {
        private List<ReferralResponse> data;
        private long totalCount;

        public ReferralListResponse() {
        }

        public List<ReferralResponse> getData() { return data; }
        public void setData(List<ReferralResponse> data) { this.data = data; }

        public long getTotalCount() { return totalCount; }
        public void setTotalCount(long totalCount) { this.totalCount = totalCount; }
    }

    public static class ReferralStats {
        private long totalReferrals;
        private long totalApplications;
        private long totalApplicationsViaReferral;
        private double referralRatePercent;

        public ReferralStats() {
        }

        public long getTotalReferrals() { return totalReferrals; }
        public void setTotalReferrals(long totalReferrals) { this.totalReferrals = totalReferrals; }

        public long getTotalApplications() { return totalApplications; }
        public void setTotalApplications(long totalApplications) { this.totalApplications = totalApplications; }

        public long getTotalApplicationsViaReferral() { return totalApplicationsViaReferral; }
        public void setTotalApplicationsViaReferral(long totalApplicationsViaReferral) { this.totalApplicationsViaReferral = totalApplicationsViaReferral; }

        public double getReferralRatePercent() { return referralRatePercent; }
        public void setReferralRatePercent(double referralRatePercent) { this.referralRatePercent = referralRatePercent; }
    }
}
