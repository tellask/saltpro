package test.json;

import marst.util.json.JsonConverter;


public class TestJsonConverter {
	public static void main(String[] args) {

//		printObj("5", Integer.class);
//		printObj("'abcd'", String.class);
//		printObj("5.6", Float.class);
//		printObj("5.082", Double.class);
//		printObj("true", Boolean.class);
		
//		String jsonStr = "[1,2,3,5]";
//		Object o = JsonConverter.json2Obj(jsonStr, int[].class);
//		Integer[] obj = (Integer[])o;
//		for (Integer i : obj) {
//			System.out.println(i);
//		}
		
//		//int[][] arr = new int[5][];
//		int[] a = new int[2];
//		a[0] = 5;
//		a[1] = 7;
//		Object arr = ClassReflector.newArray(int[].class, 5);
//		ClassReflector.setArrayElement(arr, 0, a);

		
		
		String jsonStr = "[[1,2],[3],[5]]";
		Object o = JsonConverter.json2Obj(jsonStr, int[][].class);
		int[][] obj = (int[][])o;
		for (int[] i : obj) {
			for (int k : i) {
				System.out.print(k + ",");
			}
			System.out.println();
		}
		
		
//		Foo[] obj = new Foo[3];
//		for (int i = 1; i < 4; i++) {
//			obj[i-1] = new Foo(2, "����", (float)189.52);
//		}
//		String str = JsonConverter.obj2Json(obj);
//		System.out.println(str);
	}
	
	public static void printObj(String jsonStr, Class cls) {
		Object obj = JsonConverter.json2Obj(jsonStr, cls);
		System.out.println(obj);
	}
}







