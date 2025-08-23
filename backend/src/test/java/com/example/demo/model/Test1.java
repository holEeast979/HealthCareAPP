package com.example.demo.model;

import com.example.demo.config.ChatUser;
import com.example.demo.pojo.Orders;
import com.example.demo.pojo.dto.CalendarRequestDto;
import com.example.demo.pojo.dto.CalendarResponseDto;
import com.example.demo.service.Impl.CalendarServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class Test1 {

    @Autowired SMSModel smsModel;
    @Autowired
    CalendarServiceImpl calendarService;
    @Autowired
    ChatUser chatUser;

    @Test
    public void test1(){
        Date date = new Date();
       date.setTime(date.getTime()+1000*60*60*24*3);
       System.out.println(date);
    }

    @Test
    public void test2(){
        List<CalendarResponseDto> calendarResponseDtos = calendarService.listAppointmentCalendar(new CalendarRequestDto(2025, 4, 1));
        System.out.println(calendarResponseDtos);
    }
}
