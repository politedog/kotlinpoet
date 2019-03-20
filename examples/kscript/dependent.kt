//DEPS com.squareup:kotlinpoet:1.1.0

import com.squareup.kotlinpoet.*

class Dependency() {
    var name: String
    var color: String
    var shape: String
}

val dependencyClass = ClassName("", Dependency::class.simpleName)
val file = FileSpec.builder("", "HelloWorld")
    .addType(TypeSpec.classBuilder("Greeter")
        .primaryConstructor(FunSpec.constructorBuilder()
            .addParameter("name", String::class)
            .build())
        .addProperty(PropertySpec.builder("name", String::class)
            .initializer("name")
            .build())
        .addFunction(FunSpec.builder("greet")
            .addStatement("println(%P)", "Hello, \$name")
            .build())
        .build())
    .addFunction(FunSpec.builder("main")
        .addParameter("args", String::class, VARARG)
        .addStatement("%T(args[0]).greet()", greeterClass)
        .build())
    .build()


fun main(vararg args: String) {
file.writeTo(System.out)
}
