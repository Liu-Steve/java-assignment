package edu.whu.week7.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class SuperviseAspect {

    /**
     * API调用监控结果
     */
    final Map<String, SuperviseResult> superviseTable = Collections.synchronizedMap(new HashMap<>());

    @Around("within(edu.whu.week7.controller.*)")
    public Object aroundDao(ProceedingJoinPoint jp) throws Throwable {
        String methodSig = jp.getSignature().toString();
        if (!superviseTable.containsKey(methodSig)) {
            superviseTable.put(methodSig, new SuperviseResult());
        }
        // 执行
        long t1 = Calendar.getInstance().getTimeInMillis();
        Object retVal = jp.proceed();
        long t2 = Calendar.getInstance().getTimeInMillis();
        // 记录正常执行
        superviseTable.get(methodSig).endNormal(t2 - t1);
        return retVal;
    }

    @AfterThrowing(pointcut = "within(edu.whu.week7.controller.*)", throwing = "ex")
    public void afterException(JoinPoint jp, Exception ex) {
        String methodSig = jp.getSignature().toString();
        // 记录不正常执行
        superviseTable.get(methodSig).endAbnormal();
    }

    public List<SuperviseResultDto> getSuperviseResult() {
        List<SuperviseResultDto> list = new ArrayList<>();
        for (String key : superviseTable.keySet()) {
            SuperviseResult data = superviseTable.get(key);
            list.add(data.getResult(key));
        }
        return list;
    }

}
