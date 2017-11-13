package com.fritz.reflection;

import java.lang.reflect.Modifier;
import java.util.Arrays;


public class Reflection {
	
	public static void main(String[] args) throws ClassNotFoundException {
		
		/*** 1) Let's get CLASS using Reflection ***/
		/* Class used for reflection */
		String reflectedClass = ConcreteClass.class.getSimpleName();
		System.out.println("Reflected CLASS of: " + reflectedClass);
		
		
		//Pattern 1 - Through static variable class (also used for primtive types and arrays)
		Class<?> concreteClass = ConcreteClass.class;
		
		//Pattern 2 - Using getClass() method of object java.lang.Class.forName(String fullyClassifiedClassName)
		concreteClass = new ConcreteClass(5).getClass();
		
		//Pattern 3 - Using forName (used most time in frameworks as JUnit, Spring dependency injection, TomCat etc..
		try {
			// Returning a Class object associated with the class or interface provided in String name.
			concreteClass = Class.forName("com.fritz.reflection.ConcreteClass");
			
			// Above method is used most of the times in frameworks like JUnit
			// Spring dependency injection, Tomcat web container
			// Eclipse auto completion of method names, hibernate, Struts2 etc.
			// because ConcreteClass is not available at compile time	
		} catch (ClassNotFoundException e) { e.printStackTrace(); }
		
		// in getCanonicalName, null will be returned if concreteClass were an anonymous class
		System.out.println("The name of reflected CLASS is: " + concreteClass.getCanonicalName() + "\n");
		
		
		// Reflect primitive types, wrapper classes and arrays
		Class<?> booleanClass		= boolean.class;			// boolean
		Class<?> cDouble 			= Double.TYPE;				// double
		Class<?> cDoubleArray		= Class.forName("[D");		// double[]
		Class<?> twoDStringArray 	= String[][].class;			// java.lang.String[][]
		
		System.out.println("Reflected a " + booleanClass.getCanonicalName());
		System.out.println("Reflected a " + cDouble.getCanonicalName());
		System.out.println("Reflected a " + cDoubleArray.getCanonicalName());
		System.out.println("Reflected a " + twoDStringArray.getCanonicalName() + "\n");		
		
		
		
		/*** 2) Let's get SUPERCLASS using Reflection ***
		 *   If this Class represents either the Object class, an interface, a primitive type, or void, 
		 *   then null is returned
		 */
		Class<?> superClassOfConcreteClass = Class.forName("com.fritz.reflection.ConcreteClass").getSuperclass();
		
		System.out.println("The SuperClass of " + ConcreteClass.class + " is: " + superClassOfConcreteClass + "\n");
		/*
		System.out.println(Object.class.getSuperclass());		// will print "null"
		System.out.println(String[][].class.getSuperclass()); 	// will print "class java.lang.Object"
		*/

		
		
		/*** 3) Let's get PUBLIC MEMBER CLASSES ***
		 * 		getClasses() -> Returns an array of Class objects with:
		 * 							- public classes
		 * 							- public interfaces
		 * 							- enums
		 * 						declared by the class object, or inherited members from Superclasses 
		 *		if the returned array has length 0, means Class object has no public members or interfaces
		 *		or if this Class object represents a primitive type, an array class, or a void.
		 */
		Class<?>[] publicMembersOfConcreteClass = concreteClass.getClasses();
		
		if (publicMembersOfConcreteClass.length != 0) {
			System.out.println("Public classes: ");
			System.out.println(Arrays.toString(publicMembersOfConcreteClass) + "\n");
		}
		else System.out.println(ConcreteClass.class + "class does not has public members, interfaces, or their members represents primitive type, an array class ora a void" + "\n");
		

		
		/*** 4) Let's get DECLARED CLASSES ***
		 * 		getDeclaredClasses() -> Returns an array of Class objects with:
		 * 									- explicit classes defined in ConcreteClass
		 * 									- inner classes defined in ConcreteClass
		 * 									- explicit interfaces defined in ConcreteClass
		 * 								in this Class object.
		 * 		It will return PRIVATE AND PROTECTED inner classes of ConcreteClass (only) !
		 * 		The returned array does not include classes declared in inherited classes and interfaces.
		 */
		Class<?>[] explicitClassesOfConcreteClass = Class.forName("com.fritz.reflection.ConcreteClass").getDeclaredClasses();
		System.out.println("Declared explicit Classes in " + reflectedClass + " classes " + 
							"PRIVATE and PROTECTED classes or inner classes: "
							);
		System.out.println(Arrays.toString(explicitClassesOfConcreteClass) + "\n");
		
		
		
		/*** 5) Let's get the DECLARING CLASS ***
		 * 		getDeclaringClass() -> Returns the Class object representing the class in which it was declared.
		 */
		
		// com.fritz.reflection.ConcreteClass$ConcreteClassDefaultClass is a known inner class we inspect using getDeclaredClasses
		// Lets return class from inner class ConcreteClassDefaultClass belongs
		Class<?> innerClass = Class.forName("com.fritz.reflection.ConcreteClass$ConcreteClassDefaultClass");
		
		System.out.println("Inner Class " + ConcreteClass.ConcreteClassDefaultClass.class.getSimpleName() + " belongs to: " + "\n" +
							innerClass.getDeclaringClass().getCanonicalName());
		System.out.println("Alternative way: " + "\n" + innerClass.getEnclosingClass().getCanonicalName() + "\n");
		
		
		
		/*** 6) Let's get the Package name ***
		 *		getPackage() -> Returns the package for the "this" class. 		
		 */
		String pkg = Class.forName("com.fritz.reflection.BaseInterface").getPackage().getName();
		System.out.println("This reflected class: " + reflectedClass + " belongs to " + pkg + " package " + "\n");
		
		
		
		/*** 7) Let's get the CLASS MODIFIERS ***
		 * 		getModifiers() ->	Returns the int representation of the class modifiers.
		 * 							However, we can use 
		 * 								java.lang.reflect.Modifier.toString() to make the 
		 * 								conversion to a String format as appears in the source code.
		 */
		String modifierClass = Modifier.toString(concreteClass.getModifiers());
		System.out.println("The reflected class " + reflectedClass + " is : " + modifierClass);
		
		String modifierClass2 = Modifier.toString(Class.forName("com.fritz.reflection.BaseInterface").getModifiers());
		System.out.println("The reflected class implements: " + modifierClass2 + "\n");
		
		
		
		
		
		
		
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
 