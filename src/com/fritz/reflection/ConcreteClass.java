package com.fritz.reflection;

public class ConcreteClass extends BaseClass implements BaseInterface {
	
	/* Class attributes */
	public int publicInt;
	private String privateString = "This is a private String from ConcreteClass";
	protected boolean protectedBoolean;
	Object defaultObject;
	
	
	/* Set */
	public ConcreteClass(int i) {
		this.publicInt = i;
	}
	
	
	/* Methods */
	
	/* This is a class that implements BaseInterface so Overriding method1 and method2 is needed */
	@Override
	public void method1() {
		System.out.println("This is Method 1 from ConcreteClass class!");
	}
	
	@Override
	public int method2(String str) {
		System.out.println("This is Method 2 from ConcreteClass class!");
		
		return 0;
	}
	
	/* This is a class that extends BaseClass also so we can @Override the methods */
	public int method4() {
		System.out.println("This is Method 4 from ConcreteClass! (overriden from BaseClass)");
		
		return 4;	// it's Overridden
	}

	public int method5() {
		System.out.println("This is Method 4 from ConcreteClass! (overriden from BaseClass)");
		
		return 5;	// it's Overridden
	}
	
	
	/* inner classes */
	public class	ConcreteClassPublicClass {}
	private class	ConcreteClassPrivateClass {}
	protected class ConcreteClassProtectedClass {}
	class ConcreteClassDefaultClass {}
	
	/* enum members */
	enum ConcreteClassDefaultEnum {}
	public enum ConcreteClassPublicEnum {}
	
	/* interface members */
	public interface ConcreteClassPublicInterface {}
}
