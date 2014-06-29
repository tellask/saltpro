package test.json;

public class Foo {
	private int memberId;
	private String memberName;
	private float salary;
	private Foo2 foo2;
	
	public Foo2 getFoo2() {
		return foo2;
	}
	public void setFoo2(Foo2 foo2) {
		this.foo2 = foo2;
	}
	
	public Foo(int memberId, String memberName, float salary) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.salary = salary;
		
		this.foo2 = new Foo2(3, "aaa", "bbb", "ccc");
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	
}