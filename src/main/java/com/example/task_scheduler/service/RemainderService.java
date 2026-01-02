package com.example.task_scheduler.service;

import com.example.task_scheduler.entity.Remainder;
import com.example.task_scheduler.repository.RemainderRepository;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RemainderService {

    private static final Logger log = LogManager.getLogger(RemainderService.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private RemainderRepository remainderRepository;

    @Scheduled(fixedDelay = 6000)
    @SchedulerLock(
            name = "ReminderService_sendReminders",
            lockAtLeastFor = "30s",
            lockAtMostFor = "1m"
    )
    public void sendRemainders() {

        List<Remainder> remainderList = getDueRemainder();
        log.info("Number of reminders found: {}", remainderList.size());

        remainderList.forEach(remainder -> {

            log.info("Sending reminder email to {}", remainder.getEmail());

            emailService.sendEmail(
                    remainder.getEmail(),
                    "Reminder",
                    remainder.getMessage()
            );

            markAsSent(remainder);
            log.info("Reminder marked as sent for {}", remainder.getEmail());
        });
    }

    public List<Remainder> getDueRemainder() {
        return remainderRepository
                .findByScheduledTimeLessThanEqualAndSentFalse(LocalDateTime.now());
    }
    private void markAsSent(Remainder reminder) {
        reminder.setSent(true);
        remainderRepository.save(reminder);
    }

}
