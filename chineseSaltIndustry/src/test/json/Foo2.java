package test.json;

import java.util.*;

public class Foo2 {
	private List<String> list = new ArrayList<String>();
	private int size;
	
	public Foo2(int size, String... strings) {
		this.size = size;
		for (String s : strings) {
			list.add(s);
		}
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
}
