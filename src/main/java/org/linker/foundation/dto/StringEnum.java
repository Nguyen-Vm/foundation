package org.linker.foundation.dto;

import java.lang.reflect.Field;

public interface StringEnum {
	static <T extends Enum<T>> void changeNameTo(T enumT, String value) {
		try {
			Field fieldName = enumT.getClass().getSuperclass().getDeclaredField("name");
			fieldName.setAccessible(true);
			fieldName.set(enumT, value);
			fieldName.setAccessible(false);
		} catch (Exception e) {
		    e.printStackTrace();
			// LOG.error("%s %s", e.getMessage(), e);
		}
	}
}
