package com.job.tracker.repository;


import com.job.tracker.entity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {
    List<Referral> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Referral> findByIdAndUserId(Long id, Long userId);
    long countByUserId(Long userId);
}
