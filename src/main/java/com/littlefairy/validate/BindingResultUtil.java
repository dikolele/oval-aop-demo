package com.littlefairy.validate;

import com.littlefairy.exceptions.CommonCodeEnum;
import com.littlefairy.exceptions.ParamsIllegalException;
import net.sf.oval.ConstraintViolation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 在这里编写类的功能描述
 *
 * @author luweiliang
 * @created 2018/8/6
 */
public class BindingResultUtil {
    private BindingResultUtil() {
    }

    public static void checkBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            StringBuilder errorSB = new StringBuilder();
            for (FieldError fieldError : fieldErrorList) {
                errorSB.append("[").append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage())
                        .append("]");
            }
            throw new ParamsIllegalException(CommonCodeEnum.PARAM_ERROR.getCode(), errorSB.toString());
        }
    }

    public static <T> void doConstraintViolationCheck(Set<ConstraintViolation<T>> constraintViolationSet) {
        if (CollectionUtils.isNotEmpty(constraintViolationSet)) {
            StringBuilder errorSB = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolationSet) {
                errorSB.append("[").append(constraintViolation.getMessage()).append("]");
            }
            throw new ParamsIllegalException(CommonCodeEnum.PARAM_ERROR.getCode(), errorSB.toString());
        }
    }
}