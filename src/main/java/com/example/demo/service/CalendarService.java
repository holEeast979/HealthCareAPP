package com.example.demo.service;

import com.example.demo.pojo.dto.CalendarRequestDto;
import com.example.demo.pojo.dto.CalendarResponseDto;

import java.util.List;

public interface CalendarService {
    public List<CalendarResponseDto> getCurrentCalendarList(Integer year, Integer month);

    public List<CalendarResponseDto> listAppointmentCalendar(CalendarRequestDto dto);

}
