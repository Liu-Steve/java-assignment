package edu.whu.week7.aspect;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuperviseResult {

    private long callingCount;
    private long exceptionCount;
    private long sumTime;
    private Long fastestTime;
    private Long slowestTime;

    public SuperviseResult() {
        callingCount = 0;
        exceptionCount = 0;
        sumTime = 0;
        fastestTime = null;
        slowestTime = null;
    }

    public void endNormal(long time) {
        callingCount += 1;
        sumTime += time;
        // 首次执行
        if (fastestTime == null || slowestTime == null) {
            fastestTime = time;
            slowestTime = time;
        } else {
            fastestTime = Math.min(fastestTime, time);
            slowestTime = Math.max(slowestTime, time);
        }
    }

    public void endAbnormal() {
        callingCount += 1;
        exceptionCount += 1;
    }

    public SuperviseResultDto getResult(String name) {
        SuperviseResultDto dto = new SuperviseResultDto();
        dto.setApiName(name);
        dto.setCallingCount(callingCount);
        dto.setExceptionCount(exceptionCount);
        long avg = (callingCount - exceptionCount == 0) ? 0 : sumTime / (callingCount - exceptionCount);
        dto.setAverageTime(avg);
        dto.setFastestTime(fastestTime);
        dto.setSlowestTime(slowestTime);
        return dto;
    }

}
