package com.kmecpp.jflame;

import com.kmecpp.jflame.value.JsonArray;
import com.kmecpp.jflame.value.JsonBoolean;
import com.kmecpp.jflame.value.JsonNull;
import com.kmecpp.jflame.value.JsonNumber;
import com.kmecpp.jflame.value.JsonObject;
import com.kmecpp.jflame.value.JsonString;

public enum JsonType {

	OBJECT(JsonObject.class),
	ARRAY(JsonArray.class),

	BOOLEAN(JsonBoolean.class),
	NUMBER(JsonNumber.class),
	STRING(JsonString.class),

	NULL(JsonNull.class);

	private Class<? extends JsonValue> jsonType;

	private JsonType(Class<? extends JsonValue> jsonType) {
		this.jsonType = jsonType;
	}

	public Class<? extends JsonValue> getJsonType() {
		return jsonType;
	}

}
