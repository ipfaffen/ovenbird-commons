package com.ipfaffen.ovenbird.commons;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Isaias Pfaffenseller
 */
public class ReflectionUtil {

	/**
	 * Copies all declared fields non-static and non-final with getter and setter from base to receiver.
	 * 
	 * @param receiver - object that will receive the values. Must be the same type as base.
	 * @param base - object that will give the values. Must be the same type as receiver.
	 */
	public static void copyFields(Object receiver, Object base) {
		if(receiver.getClass() != base.getClass()) {
			return;
		}

		for(Field field: receiver.getClass().getDeclaredFields()) {
			if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			Method fieldGetter = getFieldGetter(base.getClass(), field.getName(), null);
			if(fieldGetter != null) {
				Object baseFieldValue = invoke(fieldGetter, base);
				Object receiverFieldValue = invoke(fieldGetter, receiver);

				if((baseFieldValue != null && !baseFieldValue.equals(receiverFieldValue)) || (receiverFieldValue != null && !receiverFieldValue.equals(baseFieldValue))) {
					callFieldSetter(receiver, field.getName(), field.getType(), baseFieldValue);
				}
			}
		}
	}

	/**
	 * Copies fields from base to receiver.
	 * 
	 * @param receiver - object that will receive the values. Must be the same type as base.
	 * @param base - object that will give the values. Must be the same type as receiver.
	 * @param fieldsNames - fields that will be copied.
	 */
	public static void copyFields(Object receiver, Object base, String... fieldsNames) {
		if(receiver.getClass() != base.getClass()) {
			return;
		}

		for(String fieldName: fieldsNames) {
			Field field = getDeclaredField(base.getClass(), fieldName);
			Method fieldGetter = getFieldGetter(base.getClass(), fieldName, null);

			Object baseFieldValue = invoke(fieldGetter, base);
			Object receiverFieldValue = invoke(fieldGetter, receiver);

			if((baseFieldValue != null && !baseFieldValue.equals(receiverFieldValue)) || (receiverFieldValue != null && !receiverFieldValue.equals(baseFieldValue))) {
				callFieldSetter(receiver, fieldName, field.getType(), baseFieldValue);
			}
		}
	}

	/**
	 * Returns the declared field.
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Field getDeclaredField(Class<?> clazz, String fieldName) {
		try {
			return clazz.getDeclaredField(fieldName);
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Returns the method.
	 * 
	 * @param clazz
	 * @param methodName
	 * @param paramsTypes
	 * @return
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramsTypes) {
		try {
			return clazz.getMethod(methodName, paramsTypes);
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Returns the constructor.
	 * 
	 * @param clazz
	 * @param paramsTypes
	 * @return
	 */
	public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... paramsTypes) {
		try {
			return clazz.getConstructor(paramsTypes);
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Invoke the method.
	 * 
	 * @param method
	 * @param object
	 * @param params
	 * @return
	 */
	public static Object invoke(Method method, Object object, Object... params) {
		try {
			return method.invoke(object, params);
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Invoke the static method.
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static Object invokeStatic(Class<?> clazz, String methodName) {
		try {
			return clazz.getMethod(methodName).invoke(null);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the getter method of the field.
	 * 
	 * @param clazz
	 * @param fieldName
	 * @param paramType
	 * @return
	 */
	public static Method getFieldGetter(Class<?> clazz, String fieldName, Class<?> paramType) {
		try {
			String methodName = "get".concat(StringUtils.capitalize(fieldName));
			if(paramType != null) {
				return getMethod(clazz, methodName, paramType);
			}
			else {
				return getMethod(clazz, methodName);
			}
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Returns the setter method of the field.
	 * 
	 * @param clazz
	 * @param field
	 * @param paramType
	 * @return
	 */
	public static Method getFieldSetter(Class<?> clazz, String fieldName, Class<?> paramType) {
		try {
			String methodName = "set".concat(StringUtils.capitalize(fieldName));
			if(paramType != null) {
				return getMethod(clazz, methodName, paramType);
			}
			return null;
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Call object field getter method.
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Object callFieldGetter(Object object, String fieldName) {
		return callFieldGetter(object, fieldName, null, null);
	}

	/**
	 * Call object field getter method.
	 * 
	 * @param object
	 * @param fieldName
	 * @param paramType - getter method parameter type.
	 * @param param - getter method parameter.
	 * @return
	 */
	public static Object callFieldGetter(Object object, String fieldName, Class<?> paramType, Object param) {
		try {
			Method fieldGetter = getFieldGetter(object.getClass(), fieldName, paramType);
			if(paramType != null) {
				return invoke(fieldGetter, object, param);
			}
			else {
				return invoke(fieldGetter, object);
			}
		}
		catch(Exception e) {
			return null;
		}
	}

	/**
	 * Call object field setter method.
	 * 
	 * @param object
	 * @param field
	 * @param paramType - setter method parameter type.
	 * @param param - setter method parameter.
	 */
	public static void callFieldSetter(Object object, String field, Class<?> paramType, Object param) {
		try {
			if(paramType != null) {
				Method fieldSetter = getFieldSetter(object.getClass(), field, paramType);
				invoke(fieldSetter, object, param);
			}
		}
		catch(Exception e) {
			throw new RuntimeException("Invalid field setter method call.", e);
		}
	}

	/**
	 * Create a new instance.
	 * 
	 * @param constructor
	 * @param params
	 * @return
	 */
	public static Object newInstance(Constructor<?> constructor, Object... params) {
		try {
			return constructor.newInstance(params);
		}
		catch(Exception e) {
			throw new RuntimeException("Invalid new instance.", e);
		}
	}

	/**
	 * @param object
	 * @param field
	 * @param value
	 */
	public static void setFieldValue(Object object, Field field, Object value) {
		try {
			field.set(object, value);
		}
		catch(Exception e) {
			throw new RuntimeException("Cannot set field value.", e);
		}
	}
	
	/**
	 * @param object
	 * @param field
	 * @return
	 */
	public static Object getFieldValue(Object object, Field field) {
		try {
			return field.get(object);
		}
		catch(Exception e) {
			throw new RuntimeException("Cannot get field value.", e);
		}
	}
	
	/**
	 * Create a new instance calling empty constructor method.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Object newInstance(Class<?> clazz) {
		return newInstance(clazz, new Class[]{}, new Object[]{});
	}

	/**
	 * Create a new instance.
	 * 
	 * @param clazz
	 * @param paramType - constructor method parameter type.
	 * @param param - constructor method parameter.
	 * @return
	 */
	public static Object newInstance(Class<?> clazz, Class<?> paramType, Object param) {
		return newInstance(clazz, new Class[]{paramType}, new Object[]{param});
	}

	/**
	 * Create a new instance.
	 * 
	 * @param clazz
	 * @param paramsTypes - constructor method parameters type.
	 * @param params - constructor method parameters.
	 * @return
	 */
	public static Object newInstance(Class<?> clazz, Class<?>[] paramsTypes, Object[] params) {
		try {
			Constructor<?> constructor = clazz.getConstructor(paramsTypes);
			return constructor.newInstance(params);
		}
		catch(Exception e) {
			throw new RuntimeException("[Reflection] Invalid new instance.", e);
		}
	}

	/**
	 * Create a new instance calling empty constructor method.
	 * 
	 * @param clazzName - class name with package.
	 * @return
	 */
	public static Object newInstance(String clazzName) {
		return newInstance(clazzName, new Class[]{}, new Object[]{});
	}

	/**
	 * Create a new instance.
	 * 
	 * @param clazzName - class name with package.
	 * @param paramType - constructor method parameter type.
	 * @param param - constructor method parameter type.
	 * @return
	 */
	public static Object newInstance(String clazzName, Class<?> paramType, Object param) {
		return newInstance(clazzName, new Class[]{paramType}, new Object[]{param});
	}

	/**
	 * Create a new instance.
	 * 
	 * @param clazzName - class name with package.
	 * @param paramsTypes - constructor method parameter type.
	 * @param params - constructor method parameter type.
	 * @return
	 */
	public static Object newInstance(String clazzName, Class<?>[] paramsTypes, Object[] params) {
		try {
			return newInstance(Class.forName(clazzName), paramsTypes, params);
		}
		catch(Exception e) {
			throw new RuntimeException("Invalid new instance.", e);
		}
	}
}