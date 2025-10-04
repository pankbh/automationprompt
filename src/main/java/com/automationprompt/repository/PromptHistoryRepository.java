package com.automationprompt.repository;

import com.automationprompt.entity.PromptHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PromptHistoryRepository extends JpaRepository<PromptHistory, Long> {

    List<PromptHistory> findByAppTypeAndTestTypeOrderByCreatedAtDesc(String appType, String testType);

    List<PromptHistory> findByTemplateTypeOrderByCreatedAtDesc(String templateType);

    @Query("SELECT p FROM PromptHistory p WHERE p.createdAt >= :startDate ORDER BY p.createdAt DESC")
    List<PromptHistory> findRecentPrompts(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(p) FROM PromptHistory p WHERE p.createdAt >= :startDate")
    Long countPromptsGeneratedSince(@Param("startDate") LocalDateTime startDate);
}
