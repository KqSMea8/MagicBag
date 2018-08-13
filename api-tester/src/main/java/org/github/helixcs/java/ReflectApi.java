package org.github.helixcs.java;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectApi {

    // use reflect ignore properties
    public static <T> T ignoreStringProperties(T t , String ...properties) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        T tNewInstance = JSONObject.parseObject(JSONObject.toJSONBytes(t),t.getClass());
        Field [] fields = tNewInstance.getClass().getDeclaredFields();
        for (Field field : fields){
            for (String property:properties){
                if(field.getName().equalsIgnoreCase(property)){
                    Class<?> fieldType = field.getType();
                    String setMethodName = "set"+property.substring(0,1).toUpperCase()+property.substring(1,property.length());
                    Method invokeMethod = tNewInstance.getClass().getDeclaredMethod(setMethodName, fieldType);
                    invokeMethod.setAccessible(true);
                    invokeMethod.invoke(tNewInstance,"");
                }
            }
        }

        return tNewInstance;
    }

    // use BeanUtils ignore properties
    public static <T> T ignoreProperties(T source , String ...ignoreProperties)
            throws IllegalAccessException, InstantiationException {
        Assert.notNull(source,"source is not null");
        T newInstance = (T)source.getClass().newInstance();
        BeanUtils.copyProperties(newInstance,source,ignoreProperties);
        return newInstance;
    }
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        org.github.helixcs.java.MockObject.Student studentA = new org.github.helixcs.java.MockObject.Student(11,"zhangsan","NanJing");
        ignoreProperties(studentA,"name","age");
    }
}
