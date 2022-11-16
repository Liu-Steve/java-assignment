package edu.whu.week8.aspect;

import lombok.Data;

@Data
public class SuperviseResultDto {

    private String apiName;
    private long callingCount;
    private long exceptionCount;
    private long averageTime;
    private Long fastestTime;
    private Long slowestTime;

}
