package uz.nt.uzumproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling
@Configuration
@EnableAsync
public class ScheduleJob {
    @Async
    @Scheduled(fixedRate = 1000)
    public void print() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println(new SimpleDateFormat("dd.MM.yyy HH:mm:ss").format(new Date()) + " -> " + Thread.currentThread().getName());
    }
    @Scheduled(cron = "0 0 8 20-27 * SUN")
    public void report() {

    }

}







//exel faylga yozish