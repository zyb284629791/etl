package com.john.etl;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-18 15:01
 */
public class DurationTest {

    @Test
    public void test(){
        LocalDateTime start = LocalDateTime.now();
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalDateTime end = LocalDateTime.now();
        Duration between = Duration.between(start, end);
        System.out.println(between.toMillis());
    }
}
