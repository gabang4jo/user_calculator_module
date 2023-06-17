package com.cheongyak.alrimi.cheongyakalrimi.calculator.controller;

import com.cheongyak.alrimi.cheongyakalrimi.calculator.dto.CalculatorRequestDto;
import com.cheongyak.alrimi.cheongyakalrimi.calculator.dto.CalculatorResponseDto;
import com.cheongyak.alrimi.cheongyakalrimi.calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {
    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<CalculatorResponseDto> calculate(@RequestBody CalculatorRequestDto requestDto){
        CalculatorResponseDto responseDto = calculatorService.calculatePoints(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
