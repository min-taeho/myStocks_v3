package com.nuritech.stock.mystock.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 엔티티를 업데이트 할때 업데이트 항목 이외 항목의 값을 채워주는 클래스
 */
@Slf4j
@Component
public class PropertiesUtils {

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(target));
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            // null이 아닌 경우는 복사되지 않도록 ignorePorperties 파라미터 설정
            if (srcValue != null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];

        //System.out.println("empty name ="+ emptyNames);
        return emptyNames.toArray(result);
    }

}
