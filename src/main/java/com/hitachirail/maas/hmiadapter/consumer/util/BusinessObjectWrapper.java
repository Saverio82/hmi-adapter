package com.hitachirail.maas.hmiadapter.consumer.util;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BusinessObjectWrapper<T> {
    private String type;
    private T BusinessObject;


}
