package com.fritz.reflection;

import java.lang.reflect.*;
import java.util.Arrays;
import java.lang.annotation.*;
import java.util.HashMap;
import java.util.Map;


public class Reflection {
	
	public static void main(String[] args) throws ClassNotFoundException {
		
		/*** 1) Get CLASS using Reflection ***/
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
		
		
		
		/*** 2) Get SUPERCLASS using Reflection ***
		 *   If this Class represents either the Object class, an interface, a primitive type, or void, 
		 *   then null is returned
		 */
		Class<?> superClassOfConcreteClass = Class.forName("com.fritz.reflection.ConcreteClass").getSuperclass();
		
		System.out.println("The SuperClass of " + ConcreteClass.class + " is: " + superClassOfConcreteClass + "\n");
		/*
		System.out.println(Object.class.getSuperclass());		// will print "null"
		System.out.println(String[][].class.getSuperclass()); 	// will print "class java.lang.Object"
		*/

		
		
		/*** 3) Get PUBLIC MEMBER CLASSES ***
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
		

		
		/*** 4) Get DECLARED CLASSES ***
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
		
		
		
		/*** 5) Get the DECLARING CLASS ***
		 * 		getDeclaringClass() -> Returns the Class object representing the class in which it was declared.
		 */
		
		// com.fritz.reflection.ConcreteClass$ConcreteClassDefaultClass is a known inner class we inspect using getDeclaredClasses
		// Lets return class from inner class ConcreteClassDefaultClass belongs
		Class<?> innerClass = Class.forName("com.fritz.reflection.ConcreteClass$ConcreteClassDefaultClass");
		
		System.out.println("Inner Class " + ConcreteClass.ConcreteClassDefaultClass.class.getSimpleName() + " belongs to: " + "\n" +
							innerClass.getDeclaringClass().getCanonicalName());
		System.out.println("Alternative way: " + "\n" + innerClass.getEnclosingClass().getCanonicalName() + "\n");
		
		
		
		/*** 6) Get the Package name ***
		 *		getPackage() -> Returns the package for the "this" class. 		
		 */
		String pkg = Class.forName("com.fritz.reflection.BaseInterface").getPackage().getName();
		System.out.println("This reflected class: " + reflectedClass + " belongs to " + pkg + " package " + "\n");
		
		
		
		/*** 7) Get the CLASS MODIFIERS ***
		 * 		getModifiers() ->	Returns the int representation of the class modifiers.
		 * 							However, we can use 
		 * 								java.lang.reflect.Modifier.toString() to make the 
		 * 								conversion to a String format as appears in the source code.
		 */
		String modifierClass = Modifier.toString(concreteClass.getModifiers());
		System.out.println("The reflected class " + reflectedClass + " is : " + modifierClass);
		
		String modifierClass2 = Modifier.toString(Class.forName("com.fritz.reflection.BaseInterface").getModifiers());
		System.out.println("The reflected class implements: " + modifierClass2 + "\n");
		
		
		
		/*** 8) Get TYPE PARAMETERS ***
		 * 		getTypeParameters() ->	Returns an array of TypeVariable if there are Type parameters associated with the class
		 * 								The parameters are returned in the same order as declared.							
		 */
		
		/*** A small test returning parameter types of an HashMap ***/
		TypeVariable<?>[] typeParameters = Class.forName("java.util.HashMap").getTypeParameters();
		System.out.println("The type arguments of an HashMap are");
		for (TypeVariable<?> t : typeParameters) {
			System.out.print(t.getName() + ",");
		}
		System.out.println("\n");
		/*
		 * 	java.lang.Object
		 *	java.util.AbstractMap<K,V>
		 *	java.util.HashMap<K,V>
		 *		-> Type Parameters:
		 *			K - the type of keys maintained by this map
		 *			V - the type of mapped values
		 */
		
		/* A small test returning parameter types of a Queue */
		TypeVariable<?>[] typeParameters2 = Class.forName("java.util.Queue").getTypeParameters();
		
		System.out.println("The type arguments of a Queue are");
		for (TypeVariable<?> t : typeParameters2) {
			System.out.print(t.getName());
		}
		System.out.println("\n");
		/*	java.util
		 *	Interface Queue<E>
		 * 		-> Type Parameters:
		 *			E - the type of elements held in this collection
		 *			All Superinterfaces: Collection<E>, Iterable<E>
		 */
		
		
		
		/*** 9) Get IMPLEMENTED INTERFACES ***
		 * 		getGenericInterfaces()	-> Returns an array with interfaces implemented in the class and generic type information.
		 * 		getInterfaces()			-> Returns an array with class representation of all implemented classes.	
		 */
		Type[] interfaces = Class.forName("java.util.HashMap").getGenericInterfaces();
		System.out.println("List of interfaces: ");
		System.out.println(Arrays.toString(interfaces));
		System.out.println(Arrays.toString(Class.forName("java.util.HashMap").getInterfaces()));
		
		
		
		/*** 10) Get PUBLIC METHODS ***
		 * 		 getMethods()	-> Returns an array with public methods of the Class including public methods of it's superclasses and super interfaces.
		 */
		Method[] publicMethods = Class.forName("com.fritz.reflection.ConcreteClass").getMethods();
		// this will print public methods not only from ConcreteClass but also from BaseClass, Object and BaseInterface interface
		System.out.println("\nList of all methods: ");
		for (Method m : publicMethods) {
			System.out.println(m);
		}
		
		
		/*** 11) Get ALL PUBLIC CONSTRUCTORS ***
		 * 		 getConstructors()	-> Returns a list with all public constructors in the class
		 */
		Constructor<?>[] publicConstructors = Class.forName("com.fritz.reflection.ConcreteClass").getConstructors();
		System.out.println("\nList of all constructors: ");
		for (Constructor<?> c : publicConstructors) {
			System.out.println(c);
		}
		
		
		/*** 12) Get ALL PUBLIC FIELDS ***
		 * 		 getFields()	-> Returns the array of public fields 
		 * 						   (includes fields of superclasses and super instances)
		 * 
		 */
		 Field[] publicFields = Class.forName("com.fritz.reflection.ConcreteClass").getFields();
		 System.out.println(Arrays.toString(publicFields));
		 

		 
		/*** 13) Get ALL ANNOTATIONS ***
		 * 		 getAnnotations()	->	Returns all possible annotations which can be used with a class, fields and methods
		 * 		 The type Annotation[] requires the import of java.lang.annotation
		 * 		 Remember to uncomment the @Deprecated annotation
		 */
		 Annotation[] annotations = Class.forName("com.fritz.reflection.ConcreteClass").getAnnotations();
		 System.out.println("\nList of all annotations: ");
		 for (Annotation annot : annotations) {
			 System.out.println(annot);
		 }
		 
		
		 
		 /*** 14) Get FIELD DECLARINg CLASS ***
		  *       getDeclaringClass()	-> Get the class declaring the field 
		  */
		 try {
			 System.out.println("\nThe class field of interfaceInt");
			 Field field = Class.forName("com.fritz.reflection.ConcreteClass").getField("interfaceInt");
			 Class<?> fieldClass = field.getDeclaringClass();
			 System.out.println(fieldClass.getCanonicalName());
		 }
		 catch (NoSuchFieldException | SecurityException e){
			 e.printStackTrace();
		 } 

		 
		 
		/*** 15) Get FIELD TYPE ***
		 *       getType()	-> Returns the Class object for the declared field type.
		 *       			   If field is a primitive type, it returns the wrapper class object.
		 */
		 
		 try {
			 Field field = Class.forName("com.fritz.reflection.ConcreteClass").getField("publicInt");
			 Class<?> fieldType = field.getType();
			 System.out.println("\nClass object of field type of publicInt ");
			 System.out.println(fieldType.getCanonicalName());
		 }
		 catch (NoSuchFieldException | SecurityException e){
			 e.printStackTrace();
		 }
	
		 
		 
		 /*** 16) Get/Set PUBLIC Field Value ***
		  * 	  If field is a primitive type	-> returns a Wrapper Class.
		  * 	  If field is static			-> is possible to pass Object as null in get()
		  * 	  If field is final				-> set throw java.lang.IllegalAccessException (should be treated on catch)
		  */	  
		 
		 try {
			 Field field = Class.forName("com.fritz.reflection.ConcreteClass").getField("publicInt");
			 ConcreteClass obj = new ConcreteClass(5);
			 System.out.println("\nOriginal value of publicInt: " + field.get(obj)); // prints 5	
			 field.setInt(obj,10);													 // setting the value 10 in the object
			 System.out.println("Modified value of publicInt: " + field.get(obj));	 // now will print 10
		 }
		 catch (IllegalAccessException | NoSuchFieldException e){
			 e.printStackTrace();
		 }
		 
		 
		 
		 /*** Get and Set the value of PRIVATE fields using Java Reflection ***
		  * 	java.lang.reflect.*;
		  * 	Private fields and methods can't be accessible from outside the class -> Is that so?
		  */
		 try {
			 /* Getting the "private" (sure) field in ConcreteClass */
			 Field privateField = Class.forName("com.fritz.reflection.ConcreteClass").getDeclaredField("nameOfPrivateVariable");
			 
			/* Disable Java access check for field modifiers */
			 privateField.setAccessible(true); 
			 
			 ConcreteClass c = new ConcreteClass(10);
			 System.out.println("\nOriginal value of nameOfPrivateVariable: " + privateField.get(c)); // prints 10	
			 
			 /* Modifying the "private" value */
			 privateField.set(c, 20);	// Now it's 20
			 
			 /* Printing updated "private" variable nameOfPrivateVariable */
			 System.out.println("Modified value of nameOfPrivateVariable: " + privateField.get(c)); // TA DA!
		 }
		 catch (NoSuchFieldException | IllegalAccessException e){
			 e.printStackTrace();
		 }
		 
		 
		 
		 /*** 17) Get PUBLIC METHOD ***
		  * 	  getMethod()	-> Returns a public method of a class. 
		  * 					   The name of method and his parameter types is needed.
		  * 					   When the method is not found in class, the method is looked at superclass.
		  */
		 try {
			 /* Getting a put method in an HashMap */
			 Method m = Class.forName("java.util.HashMap").getMethod("put", Object.class, Object.class);
			 System.out.println(Arrays.toString(m.getParameterTypes()));
			 
			/* Get method return type, return "class java.lang.Object", class reference for void */
			 System.out.println(m.getReturnType());
			 
			/* Get method modifiers */
			 System.out.println(Modifier.toString(m.getModifiers())); //prints "public"
		 }
		 catch (NoSuchMethodException e){
			 e.printStackTrace();
		 }
		 
		 
		 
		 /*** 18) Invoking PUBLIC METHODS ***
		  * 	  invoke()	-> Method used to invoke a method in a class
		  * 	  If the invoked method is static, it's possible to pass NULL as object argument.
		  */
		 /* Using an HashMap as a example */
		 try {
			 Method m = Class.forName("java.util.HashMap").getMethod("put", Object.class, Object.class);
			 Map<String, String> h = new HashMap<>();
			 m.invoke(h, "key", "value");
			 System.out.println(m);
		 }
		 catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
			 e.printStackTrace();
		 }
		 
		 
		 
		 /*** 19) Invoking PRIVATE METHODS ***
		  * 		As private fields, methods can be invoked() disabling Java access check for methods
		  */
		 try {
			 Method m3 = Class.forName("com.fritz.reflection.BaseClass").getDeclaredMethod("method3", null);
			 m3.setAccessible(true);
			 m3.invoke(null, null);
		 }
		 catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
			 e.printStackTrace();
		 }
		 
		 
		 
		 /*** 20) Get a PUBLIC Constructor ***
		  * 	  getConstructor();		-> Get constructors of class
		  * 	  getParameterTypes()	-> Get constructor parameters from "this" constructor
		  */
		 try {
			 /* Getting constructor from ConcreteClass class */
			 Constructor<?> constructor = Class.forName("com.fritz.reflection.ConcreteClass").getConstructor(int.class);
			 
			 /* Printing constructor parameters */
			 System.out.println(Arrays.toString(constructor.getParameterTypes()));
			 
			 
			 /* Getting constructor of an hashMap for example */
			 Constructor<?> hashMapConstructor = Class.forName("java.util.HashMap").getConstructor();
			 
			 /* Printing hashMap constructor parameters */
			 System.out.println(Arrays.toString(hashMapConstructor.getParameterTypes()));
		 }
		 catch (NoSuchMethodException e){
			 e.printStackTrace();
		 }

		 
		 
		 /*** 21) Instantiate an Object using Constructor ***
		  * 	  newInstance()		-> Can be used over the constructor object to instantiate a new instance for the class.
		  * 						   Using reflection the information about classes is not available in compile time
		  * 						   Assign it to Object and then use reflection to access fields and invoke its methods
		  */

		 /* */
		 try {
			 Constructor<?> constructor = Class.forName("com.fritz.reflection.ConcreteClass").getConstructor(int.class);
			 System.out.println(Arrays.toString(constructor.getParameterTypes()));
			 
			 Object myObj 		= constructor.newInstance(50);
			 Method myObjMethod = myObj.getClass().getMethod("method1", null);
			 myObjMethod.invoke(myObj, null);
		 }
		 catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException  e){
			 e.printStackTrace();
		 }
	}	
}
 