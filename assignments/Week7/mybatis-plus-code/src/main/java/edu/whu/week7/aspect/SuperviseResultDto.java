package edu.whu.week7.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SuperviseResultDto {

    private String apiName;
    private long callingCount;
    private long exceptionCount;
    private long averageTime;
    private Long fastestTime;
    private Long slowestTime;

}
