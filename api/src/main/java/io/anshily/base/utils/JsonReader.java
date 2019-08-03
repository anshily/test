package io.anshily.base.utils;

import io.anshily.base.core.Entity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

/**
 * json解析类
 * 
 * @author Richard.Ma
 * 
 */
@SuppressWarnings(value = { "unchecked" })
public class JsonReader {

	public static <T> Object fromJson(String jsonAsString, Class<T> pojoClass) throws Exception {
		final String message = "json转化异常";
		try {
			if (Entity.class.isAssignableFrom(pojoClass)) {
				JSONObject item = new JSONObject(jsonAsString.toString());
				return readEntity(item, pojoClass);
			} else if (List.class.isAssignableFrom(pojoClass)) {
				JSONArray item = new JSONArray(jsonAsString.toString());
				return readList(item, null);
			} else if (Map.class.isAssignableFrom(pojoClass)) {
				JSONObject item = new JSONObject(jsonAsString.toString());
				return readMap(item, pojoClass);
			} else {
				JSONObject item = new JSONObject(jsonAsString.toString());
				Iterator<String> iter = item.keys();
				while (iter.hasNext()) {
					String key = iter.next();
					return item.get(key);
				}
			}
			return null;
		} catch (SecurityException e) {
			throw new Exception(message, e);
		} catch (IllegalArgumentException e) {
			throw new Exception(message, e);
		} catch (IllegalAccessException e) {
			throw new Exception(message, e);
		} catch (InstantiationException e) {
			throw new Exception(message, e);
		} catch (JSONException e) {
			throw new Exception(message, e);
		} catch (ClassNotFoundException e) {
			throw new Exception(message, e);
		} catch (NoSuchMethodException e) {
			throw new Exception(message, e);
		} catch (InvocationTargetException e) {
			throw new Exception(message, e);
		}
	}

	private static <T> Object readEntity(JSONObject item, Class<T> cls)
			throws IllegalAccessException, InstantiationException, JSONException, ClassNotFoundException,
			SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		Object obj = cls.newInstance();
		Field[] fields = cls.getDeclaredFields();
		Object value = null;
		if (fields == null || fields.length == 0) {
			return obj;
		}
		for (Field entityField : fields) {
			String fieldName = entityField.getName();
			if (!item.has(fieldName)) { // json中不存在该字段的值
				continue;
			}
			if (Entity.class.isAssignableFrom(entityField.getType())) {
				JSONObject items = item.getJSONObject(fieldName);
				value = readEntity(items, entityField.getType());
			} else if (List.class.isAssignableFrom(entityField.getType())) {
				JSONArray items = item.getJSONArray(fieldName);
				value = readList(items, entityField);
			} else if (Map.class.isAssignableFrom(entityField.getType())) {
				JSONObject items = item.getJSONObject(fieldName);
				value = readMap(items, entityField.getType());
			} else {
				value = item.get(fieldName);
			}
			String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			invokeValue(cls, obj, methodName, entityField, value);
		}
		return obj;
	}

	private static <T> Object readList(JSONArray array, Field field)
			throws IllegalAccessException, InstantiationException, JSONException, ClassNotFoundException,
			SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();
		Object value = null;
		Type arrayType = null;
		if (field != null) {
			if (field.getGenericType() instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) field.getGenericType();
				arrayType = pt.getActualTypeArguments()[0];
			}
		}
		for (int i = 0; i < array.length(); i++) {
			JSONObject items = array.getJSONObject(i);
			if (arrayType != null) {// 泛型
				value = readEntity(items, (Class<T>) arrayType);
			} else {
				value = readMap(items, HashMap.class);
			}
			list.add(value);
		}
		return list;
	}

	/**
	 * 暂时不支持map下实体对象解析（需要用到的时候再加上去）
	 * 
	 * @param <T>
	 * @param item
	 * @param cls
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws JSONException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	private static <T> Object readMap(JSONObject item, Class<T> cls)
			throws IllegalAccessException, InstantiationException, JSONException, ClassNotFoundException,
			SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		Map<String, Object> map = (HashMap<String, Object>) cls.newInstance();
		Iterator<String> iter = item.keys();
		while (iter.hasNext()) {
			String key = iter.next();
			map.put(key, item.get(key));
		}
		return map;
	}

	private static <T> void invokeValue(Class<T> cls, Object obj, String methodName, Field field, Object value)
			throws IllegalAccessException, InstantiationException, SecurityException, ClassNotFoundException,
			IllegalArgumentException, InvocationTargetException {
		if (value == null || value.toString().equals("null")) {
			return;
		}
		String fieldType = field.getType().getName();
		if ("String".equals(fieldType)) {
			fieldType = String.class.getName();
		} else if ("Date".equals(fieldType)) {
			fieldType = Date.class.getName();
		} else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
			fieldType = Integer.class.getName();
		} else if ("Long".equalsIgnoreCase(fieldType)) {
			fieldType = Long.class.getName();
		} else if ("Double".equalsIgnoreCase(fieldType)) {
			fieldType = Double.class.getName();
		} else if ("Boolean".equalsIgnoreCase(fieldType)) {
			fieldType = Boolean.class.getName();
		} else if ("Float".equalsIgnoreCase(fieldType)) {
			fieldType = Float.class.getName();
		} else if ("Timestamp".equalsIgnoreCase(fieldType) || "Time".equalsIgnoreCase(fieldType)) {
			fieldType = Timestamp.class.getName();
		}

		Class<?> claz = Class.forName(fieldType);
		Method m = null;
		try {
			m = cls.getMethod(methodName, new Class[] { claz });
			if (fieldType.equals(Integer.class.getName())) {
				m.invoke(obj, new Object[] { claz.cast(Integer.parseInt(value.toString())) });
			} else if (fieldType.equals(Long.class.getName())) {
				m.invoke(obj, new Object[] { claz.cast(Long.parseLong(value.toString())) });
			} else if (fieldType.equals(Double.class.getName())) {
				m.invoke(obj, new Object[] { claz.cast(Double.parseDouble(value.toString())) });
			} else if (fieldType.equals(Boolean.class.getName())) {
				m.invoke(obj, new Object[] { claz.cast(Boolean.parseBoolean(value.toString())) });
			} else if (fieldType.equals(Float.class.getName())) {
				m.invoke(obj, new Object[] { claz.cast(Float.parseFloat(value.toString())) });
			} else {
				m.invoke(obj, new Object[] { claz.cast(value) });
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return;
		}

	}
}
