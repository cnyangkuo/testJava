package com.practice.base;

class Animal {
    public void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal {
    @Override
    public void eat() {
        System.out.println("Dog is eating");
    }
}

public class Test {
    public static void main(String[] args) {
        Animal animal = new Dog();  // 父类引用指向子类对象
        animal.eat();               // 动态链接，运行时调用 Dog.eat()
    }
}

/**
 *
 javap -c -v -p ./src/main/java/com/practice/Test.class
 Classfile /Users/hanson/data/project/study/testJava/src/main/java/com/practice/Test.class
 Last modified 2025-5-6; size 354 bytes
 MD5 checksum eca8dc2c7d04aa52f5aa7a45c7956319
 Compiled from "Test.java"
 public class com.practice.Test
 minor version: 0
 major version: 52
 flags: ACC_PUBLIC, ACC_SUPER
 Constant pool:
 #1 = Methodref          #6.#15         // java/lang/Object."<init>":()V
 #2 = Class              #16            // com/practice/Dog
 #3 = Methodref          #2.#15         // com/practice/Dog."<init>":()V
 #4 = Methodref          #17.#18        // com/practice/Animal.eat:()V
 #5 = Class              #19            // com/practice/Test
 #6 = Class              #20            // java/lang/Object
 #7 = Utf8               <init>
 #8 = Utf8               ()V
 #9 = Utf8               Code
 #10 = Utf8               LineNumberTable
 #11 = Utf8               main
 #12 = Utf8               ([Ljava/lang/String;)V
 #13 = Utf8               SourceFile
 #14 = Utf8               Test.java
 #15 = NameAndType        #7:#8          // "<init>":()V
 #16 = Utf8               com/practice/Dog
 #17 = Class              #21            // com/practice/Animal
 #18 = NameAndType        #22:#8         // eat:()V
 #19 = Utf8               com/practice/Test
 #20 = Utf8               java/lang/Object
 #21 = Utf8               com/practice/Animal
 #22 = Utf8               eat
 {
 public com.practice.Test();
 descriptor: ()V
 flags: ACC_PUBLIC
 Code:
 stack=1, locals=1, args_size=1
 0: aload_0
 1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 4: return
 LineNumberTable:
 line 16: 0

 public static void main(java.lang.String[]);
 descriptor: ([Ljava/lang/String;)V
 flags: ACC_PUBLIC, ACC_STATIC
 Code:
 stack=2, locals=2, args_size=1
 0: new           #2                  // class com/practice/Dog
 3: dup
 4: invokespecial #3                  // Method com/practice/Dog."<init>":()V
 7: astore_1
 8: aload_1
 9: invokevirtual #4                  // Method com/practice/Animal.eat:()V
 12: return
 LineNumberTable:
 line 18: 0
 line 19: 8
 line 20: 12
 }
 SourceFile: "Test.java"

 *
 */