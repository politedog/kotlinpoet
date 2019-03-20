# Kotlin code generation with kotlinpoet

---

# Chris Koeberle
# Bottle Rocket 
### @kodi

---

# What is kotlinpoet?

A library from Square for writing Kotlin programs that have Kotlin source as output.

* Takes care of things you'd rather not think about, like import statements
* Requires you to learn a new syntax for writing about Kotlin

---

# Why kotlinpoet?

* IDE Plugins
* Create boilerplate based on data files
* Create dependent code automatically
* Skynet?

---

# Where kotlinpoet?

<https://github.com/square/kotlinpoet>

`compile 'com.squareup:kotlinpoet:1.1.0'`

Easy places to get started:

* [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
* [kscript](https://github.com/holgerbrandl/kscript)

---

# A kscript digression

* kscript is a wrapper around kotlinc's ability to run kotlin programs as a script
* It also supports creating a temporary wrapper to pull the program into IntelliJ IDEA
* This is a great way to just noodle around without a lot of setup
* It's also a great way to actually write a command line script!

---

# Hello, Hello World!

Let's start by looking at a program to write a Hello, World! program.

    !kotlin
    val greeterClass = TypeSpec.classBuilder("Greeter")
        .primaryConstructor(
            FunSpec.constructorBuilder()
                .addParameter("name", String::class)
                .build())
        .addProperty(
            PropertySpec.builder("name", String::class)
                .initializer("name")
                .build())
        .addFunction(FunSpec.builder("greet")
            .addStatement("println(%P)", "Hello, \$name")
            .build())
        .build()

---

# Hello, Hello World!

    !kotlin
    val file = FileSpec.builder("", "HelloWorld")
        .addType( greeterClass)
        .addFunction(FunSpec.builder("main")
            .addParameter("args", String::class, KModifier.VARARG)
            .addStatement("%N(args[0]).greet()", greeterClass)
            .build())
        .build()
    
    fun main(vararg args: String) {
        file.writeTo(System.out)
    }

---

---

# But why?

How is this different from just search and replace?

* Well, this is a weak example.
* The real value comes when we can use this WITH a data source

---

# A trivial example of a powerful concept

Here's a trivial class:

    !kotlin
    class Dependency() {
        var name: String = ""
        var color: String = ""
        var shape: String = ""
    }

Let's imagine that this is the representation of this dependency on the server, and it has a whole bunch of implementation in it. We want to just export a data transfer object with these properties.

---

# A trivial example of a powerful concept

    !kotlin
    fun writeClassInterface(kClass: KClass<out Any>) : String {
        val name = kClass.simpleName?:"anon"
        val dependencyClass = ClassName("", name)
        val classTypeBuilder = TypeSpec.classBuilder(dependencyClass)
        for(prop in kClass.memberProperties) {
            classTypeBuilder.addProperty(prop.name, prop.returnType.asTypeName().copy(true))
        }
        val file = FileSpec.builder("", name)
            .addType(classTypeBuilder.build())
            .build()
        return file.toString()
    }

---

# Sources

<https://bit.ly/cdk-kotlinpoet>
