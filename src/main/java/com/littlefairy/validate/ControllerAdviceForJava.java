package com.littlefairy.validate;

import com.gsafety.gemp.common.result.Result;
import com.gsemergency.natural.util.exceptions.ParamsIllegalException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * 方法描述：参数校验
 * @author zhangqianle
 * @date 2020/4/13 17:16
 */
@Aspect
@Component
public class ControllerAdviceForJava implements Ordered {

    private final static Logger LOGGER = LoggerFactory.getLogger(ControllerAdviceForJava.class);

    /**
     * 路径
     */
    @Pointcut("execution(* com.gsemergency.natural..*.controller..*(..)))")
    private void anyMethod() {
        //DO NOTHING
    }

    @Around("anyMethod()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        StringBuilder methodSB = new StringBuilder();
        StringBuilder methodName = new StringBuilder();
        methodName.append(joinPoint.getTarget().getClass().getCanonicalName())
                .append(".").append(joinPoint.getSignature().getName()).append("()");
        Object retVal = null;
        String retValInString = null;
        BindingResult bindingResult = null;
        try {
            Object[] args = joinPoint.getArgs();
            methodSB.append(methodName);
            methodSB.append(", args:[");
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    Object obj = args[i];
                    if (obj instanceof BindingResult) {
                        bindingResult = (BindingResult) obj;
                    } else {
                        if (obj != null) {
                            if (obj instanceof BaseObject) {
                                methodSB.append((BaseObject) obj);
                            } else {
                                methodSB.append(obj);
                            }
                        } else {
                            methodSB.append("null");
                        }
                    }
                }
            }
            methodSB.append("]");
            LOGGER.info("Begin request,method:{}", methodSB.toString());
            if (bindingResult != null) {
                BindingResultUtil.checkBindingResult(bindingResult);
            }
            retVal = joinPoint.proceed();
            retValInString = retVal != null ? customToString(retVal) : "null";
        } catch (Exception e) {
                if (e instanceof ParamsIllegalException){
                    ParamsIllegalException xe = (ParamsIllegalException)e;
                    return Result.builder().status(xe.getCode()).msg(xe.getMess()).build();
                }
                return Result.builder().status(400).msg("参数错误"+e.getMessage()).build();

        } finally {
            long end = System.currentTimeMillis();
            long expendTime = end - start;
            StringBuilder returnValue = new StringBuilder();
            returnValue.append("End request").append(",method :").append(methodName).append(",return value:");
            if (retVal != null) {
                returnValue.append("[").append(retValInString).append("]");
            } else {
                returnValue.append("[null]");
            }
            returnValue.append(". consuming time: ").append(expendTime);
            LOGGER.info(returnValue.toString());
        }

        return retVal;
    }


    private String customToString(Object vo) {
        if (vo instanceof BaseObject) {
            return vo.toString();
        }

        return ToStringBuilder.reflectionToString(vo, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public int getOrder() {
        return 1;
    }



}