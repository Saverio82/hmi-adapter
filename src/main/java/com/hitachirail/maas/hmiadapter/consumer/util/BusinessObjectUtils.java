package com.hitachirail.maas.hmiadapter.consumer.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Component
@Slf4j
public class BusinessObjectUtils {

    public Object convertFromLinkedHashMap(Class beanClass, LinkedHashMap linkedHashMap, Map<Class, Map<String, Function>> converterMap) {

        Object result = null;

        if (linkedHashMap != null) {
            Object currentInstance = BeanUtils.instantiateClass(beanClass);
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(currentInstance);

            PropertyDescriptor[] propertyDescriptors = wrapper.getPropertyDescriptors();
            for (PropertyDescriptor currentPropertyDescriptor : propertyDescriptors) {
                String propertyName = currentPropertyDescriptor.getName();
                Object parsedProperty = linkedHashMap.get(propertyName);
                if (parsedProperty != null) {
                    if (parsedProperty instanceof LinkedHashMap) {
                        beanClass.getFields();
                        wrapper.setPropertyValue(currentPropertyDescriptor.getName(), convertFromLinkedHashMap(null, (LinkedHashMap) parsedProperty, converterMap));
                    }
                    if (parsedProperty instanceof Collection) {
                        Type type = currentPropertyDescriptor.getReadMethod().getGenericReturnType();
                        if (type instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) type;
                            Class propertyClass =(Class) pt.getActualTypeArguments()[0];
                            Iterator items = ((Collection) parsedProperty).iterator();
                            List<Object> list = new ArrayList();
                            while (items != null && items.hasNext()) {
                                Object item = items.next();
                                if(item instanceof LinkedHashMap) {
                                    list.add(convertFromLinkedHashMap(propertyClass, (LinkedHashMap) item, converterMap));
                                }
                                else {
                                    if(propertyClass != LocalDate.class)
                                        list.add(item);
                                    else
                                        list.add(LocalDate.parse((String)item));
                                }

                            }
                            wrapper.setPropertyValue(currentPropertyDescriptor.getName(), list);
                        }
                    } else {
                        try {
                            if (currentPropertyDescriptor.getPropertyType() != LocalDate.class) {
                                wrapper.setPropertyValue(currentPropertyDescriptor.getName(), parsedProperty);
                            } else {
                                wrapper.setPropertyValue(currentPropertyDescriptor.getName(), LocalDate.parse((String) parsedProperty));
                            }
                        } catch (ConversionNotSupportedException ex) {

                            Object convertedValue = null;

                            if (converterMap != null) {
                                Map<String, Function> converterFromClass = converterMap.get(beanClass);
                                if (converterFromClass != null) {
                                    Function converter = converterFromClass.get(currentPropertyDescriptor.getName());
                                    convertedValue = converter.apply(parsedProperty);
                                }
                            }

                            if (convertedValue == null) {
                                throw new RuntimeException("Convertion Error", ex);
                            } else {
                                wrapper.setPropertyValue(currentPropertyDescriptor.getName(), convertedValue);
                            }
                        }
                    }
                }
            }

            result = currentInstance;
        }

        return result;
    }

}
