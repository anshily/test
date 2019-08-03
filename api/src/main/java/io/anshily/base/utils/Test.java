package io.anshily.base.utils;


import org.apache.commons.lang.StringUtils;

import java.util.List;

public class Test {

	public static void main(String[] args) {
		//Test test = new Test();
		/*List list = new ArrayList();
		list.add("9bc8f637-dce3-4609-86c9-2592d23134f7");
		list.add("0677c997-ad4e-48b1-a8a5-e192853ea90c");
		list.add("9bc8f637-dce3-4609-86c9-2592d23134f3");
		String cc = test.getOracleSQLIn(list, 2, "cc");*/
		int i = 5%13;
		System.out.println("i==="+i);
	}
	@SuppressWarnings("unused")
	private String getOracleSQLIn(List<?> ids, int count, String field) {

	    count = Math.min(count, 1000);

	    int len = ids.size();

	    int size = len % count;

	    if (size == 0) {

	        size = len / count;

	    } else {

	        size = (len / count) + 1;

	    }

	    StringBuilder builder = new StringBuilder();

	    for (int i = 0; i < size; i++) {

	        int fromIndex = i * count;

	        int toIndex = Math.min(fromIndex + count, len);

	        //System.out.println(ids.subList(fromIndex, toIndex));

	        String productId = StringUtils.defaultIfEmpty(StringUtils.join(ids.subList(fromIndex, toIndex), "','"), "");

	        if (i != 0) {

	            builder.append(" or ");

	        }

	        builder.append(field).append(" in ('").append(productId).append("')");

	    }

	    

	    return StringUtils.defaultIfEmpty(builder.toString(), field + " in ('')");

	}
}
