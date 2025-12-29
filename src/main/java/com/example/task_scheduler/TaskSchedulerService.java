package com.example.task_scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskSchedulerService {
    @Scheduled(fixedRate = 5000)
    public void fixedRateTask(){
        System.out.println("Running Fixed Rate Task "+ System.currentTimeMillis());
    }

    @Scheduled(fixedDelay = 5000)
    public void fixedDelayTask(){
        System.out.println("Running Fixed Delay Task "+ System.currentTimeMillis());
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void cornTask(){
        System.out.println("Running corn Tasks every hour");
    }
}
