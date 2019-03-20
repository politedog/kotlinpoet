import com.squareup.kotlinpoet.*

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
