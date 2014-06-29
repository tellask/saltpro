package marst.util.json;

public class JsonProp {
	public static final int SIMPLE = 0;
	public static final int ARRAY = 1;
	public static final int OBJECT = 2;
	
	public JsonProp(String propName, int propType, String jsonPropValue) {
		this.propName = propName;
		this.propType = propType;
		this.jsonPropValue = jsonPropValue;
	}
	
	public String propName;
	public int propType;
	public String jsonPropValue;
}
