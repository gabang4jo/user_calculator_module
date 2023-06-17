package com.cheongyak.alrimi.cheongyakalrimi.calculator.service;

import com.cheongyak.alrimi.cheongyakalrimi.calculator.dto.CalculatorRequestDto;
import com.cheongyak.alrimi.cheongyakalrimi.calculator.dto.CalculatorResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public CalculatorResponseDto calculatePoints(CalculatorRequestDto requestDto) {
        int noHouseholdPoints = calculateNoHouseholdPoints(requestDto.getNoHouseholdPeriod());
        int dependentsPoints = calculateDependentsPoints(requestDto.getDependents());
        int savingAccountPoints = calculateSavingAccountPoints(requestDto.getSavingAccountPeriod());
        boolean firstPriority = calculateFirstPriority(noHouseholdPoints, dependentsPoints, savingAccountPoints);
        boolean secondPriority = calculateSecondPriority(noHouseholdPoints, dependentsPoints, savingAccountPoints);
        int totalPoints = noHouseholdPoints + dependentsPoints + savingAccountPoints;

        CalculatorResponseDto responseDto = new CalculatorResponseDto();
        responseDto.setFirstPriority(firstPriority);
        responseDto.setSecondPriority(secondPriority);
        responseDto.setTotalPoints(totalPoints);

        return responseDto;
    }

    private int calculateNoHouseholdPoints(int noHouseholdPeriod) {

        int points = 0;

        // 가점 계산 로직 작성

        return points;
    }

    private int calculateDependentsPoints(int dependents) {

        int points = 0;

        // 가점 계산 로직 작성

        return points;
    }

    private int calculateSavingAccountPoints(int savingAccountPeriod) {

        int points = 0;

        // 가점 계산 로직 작성

        return points;
    }

    private boolean calculateFirstPriority(int noHouseholdPoints, int dependentsPoints, int savingAccountPoints) {

        boolean isGranted = false;

        // 계산 로직 작성

        return isGranted;
    }

    private boolean calculateSecondPriority(int noHouseholdPoints, int dependentsPoints, int savingAccountPoints) {

        boolean isGranted = false;

        // 계산 로직 작성

        return isGranted;
    }
}
