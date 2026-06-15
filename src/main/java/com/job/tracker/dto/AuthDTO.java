package com.job.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequest {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String targetRoles;
        private String targetLocations;

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getTargetRoles() {
            return targetRoles;
        }

        public String getTargetLocations() {
            return targetLocations;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private String token;
        private String tokenType;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProfile {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private String targetRoles;
        private String targetLocations;
    }
}