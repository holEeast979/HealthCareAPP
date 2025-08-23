package com.example.demo.controller.user;

import com.example.demo.pojo.dto.CalendarRequestDto;
import com.example.demo.pojo.dto.CalendarResponseDto;
import com.example.demo.result.Result;
import com.example.demo.service.CalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "万年历")
@Slf4j
@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    @PostMapping("/listcalendar")
    @ApiOperation("查询万年历")
    public @ResponseBody Result listCalendar(@RequestBody CalendarRequestDto calendarRequestDto) {
        List<CalendarResponseDto> calendarResponseDtos = calendarService.listAppointmentCalendar(calendarRequestDto);
        log.info("年、月、医院:{}", calendarRequestDto);
        return Result.success(calendarResponseDtos);
    }
}
