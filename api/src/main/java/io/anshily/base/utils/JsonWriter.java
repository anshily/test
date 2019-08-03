package io.anshily.base.utils;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class JsonWriter {

	private static ObjectMapper om = new ObjectMapper();
	private static JsonFactory jf = new JsonFactory();

	public static <T> Object fromJson(String jsonAsString, Class<T> pojoClass) {
		try {
			return om.readValue(jsonAsString, pojoClass);
		} catch (JsonParseException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public static <T> Object fromJson(FileReader fr, Class<T> pojoClass) {
		try {
			return om.readValue(fr, pojoClass);
		} catch (JsonParseException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public static String toJson(Object pojo, boolean prettyPrint) {
		try {
			
			//String start = System.nanoTime();
			StringWriter sw = new StringWriter();
			JsonGenerator jg = jf.createJsonGenerator(sw);
			
			if (prettyPrint) {
				jg.useDefaultPrettyPrinter();
			}
			om.writeValue(jg, pojo);
			// return sw.toString().replaceAll("\r\n", "");
			//System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," + (System.nanoTime() - start));
			return sw.toString();
		} catch (JsonParseException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public static void toJson(Object pojo, FileWriter fw, boolean prettyPrint) {
		try {
			JsonGenerator jg = jf.createJsonGenerator(fw);
			if (prettyPrint) {
				jg.useDefaultPrettyPrinter();
			}
			om.writeValue(jg, pojo);
		} catch (JsonParseException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}
