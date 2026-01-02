package com.example.task_scheduler.repository;

import com.example.task_scheduler.entity.Remainder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RemainderRepository
        extends JpaRepository<Remainder, Long> {

    List<Remainder> findByScheduledTimeLessThanEqualAndSentFalse(
            LocalDateTime time
    );
}
