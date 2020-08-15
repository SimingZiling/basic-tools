package org.yiming.localtools.conversion;



import org.yiming.localtools.conversion.annotation.Alias;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * map转换
 */
public class MapUtil {

    /**
     * Map转String
     * @param mapParam map参数
     * @param keyConnector 键值对连接
     * @param paramConnector 参数连接
     * @return String信息
     */
    public static String mapToString(Map mapParam,String keyConnector,String paramConnector){
        StringBuilder stringBuilder = new StringBuilder();
        for (Object key:mapParam.keySet()){
            stringBuilder.append(key);
            stringBuilder.append(keyConnector);
            stringBuilder.append(mapParam.get(key));
            stringBuilder.append(paramConnector);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() -1);
        return stringBuilder.toString();
    }

    /**
     * Map转对象
     * @param clazz 类
     * @param values map值
     * @param <T> 对象
     * @return 对象
     */
    public static <T> T mapToObject(Class<T> clazz,Map<String, Object> values) throws IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {

        // 实例化对象
        T t =  clazz.getDeclaredConstructor().newInstance();
        // 获取属性
        for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(clazz).getPropertyDescriptors()){
            if(propertyDescriptor.getName().equals("class")){
                continue;
            }
            // 获取属性
            String propertyName = propertyDescriptor.getName().substring(0, 1).toUpperCase() + propertyDescriptor.getName().substring(1);
            Method method = propertyDescriptor.getReadMethod();
            // 获取方法注解
            if (method.isAnnotationPresent(Alias.class)) {
                Alias alias = method.getAnnotation(Alias.class);
                // 判断是否有别名
                if (alias.name().equals("")) {
                    //获取map的值
                    Object value = values.get(propertyDescriptor.getName());
                    Object[] args = new Object[1];
                    args[0] = value;
                    // 属性赋值
                    propertyDescriptor.getWriteMethod().invoke(t, args);
                } else {
                    //获取map的值
                    Object value = values.get(alias.name());
                    Object[] args = new Object[1];
                    args[0] = value;
                    propertyDescriptor.getWriteMethod().invoke(t, args);
                }
            }else
                // 获取属性注解
                if(clazz.getDeclaredField(propertyDescriptor.getName()).isAnnotationPresent(Alias.class)){
                    Alias alias = clazz.getDeclaredField(propertyDescriptor.getName()).getAnnotation(Alias.class);
                    // 判断是否有别名
                    if (alias.name().equals("")) {
                        //获取map的值
                        Object value = values.get(propertyDescriptor.getName());
                        Object[] args = new Object[1];
                        args[0] = value;
                        // 属性赋值
                        propertyDescriptor.getWriteMethod().invoke(t, args);
                    } else {
                        //获取map的值
                        Object value = values.get(alias.name());
                        Object[] args = new Object[1];
                        args[0] = value;
                        propertyDescriptor.getWriteMethod().invoke(t, args);
                    }
            }else
                //判断map的值与属性名称是否一致
                if(values.containsKey(propertyDescriptor.getName())){
                    //获取map的值
                    Object value = values.get(propertyDescriptor.getName());
                    Object[] args = new Object[1];
                    args[0] = value;
                    propertyDescriptor.getWriteMethod().invoke(t, args);
                }else
                    //判断map的值是否与属性名首字母大写是否一致（部分属性名大写，在数据库中小写）
                    if(values.containsKey(propertyName)){
                        Object value = values.get(propertyName);
                        Object[] args = new Object[1];
                        args[0] = value;
                        propertyDescriptor.getWriteMethod().invoke(t, args);
                    }
        }
        return t;
    }

    public static Map<String,Object> objectToMap(Object object) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(clazz).getPropertyDescriptors()){
            if(propertyDescriptor.getName().equals("class")){
                continue;
            }

            // 获取方法注解
            if(propertyDescriptor.getReadMethod().isAnnotationPresent(Alias.class)){
                Method method = propertyDescriptor.getReadMethod();
                Alias alias = method.getAnnotation(Alias.class);
                map.put(alias.name(),method.invoke(object));
            }else
                // 获取属性注解
                if (clazz.getDeclaredField(propertyDescriptor.getName()).isAnnotationPresent(Alias.class)){
                    Field field = clazz.getDeclaredField(propertyDescriptor.getName());
                    Alias alias = field.getAnnotation(Alias.class);
                    field.setAccessible(true);
                    map.put(alias.name(),field.get(object));
                }else
                    // 直接通过属性获取
                    {
                        Field field = clazz.getDeclaredField(propertyDescriptor.getName());
                        field.setAccessible(true);
                        map.put(propertyDescriptor.getName(),field.get(object));
            }
        }
//        System.out.println(clazz);
        return map;
    }

}
