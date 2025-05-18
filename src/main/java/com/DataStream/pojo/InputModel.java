package com.DataStream.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-18 星期日 14:11:34
 */
@Data
@Builder
public class InputModel {
    private String username;
    private int age;
}
