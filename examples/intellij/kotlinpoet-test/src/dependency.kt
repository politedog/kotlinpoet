import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties


class Dependency() {
    var name: String = ""
    var color: String = ""
    var shape: String = ""
}

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
/*    .addType(
        TypeSpec.classBuilder("Greeter")
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
        .build())
    .addFunction(FunSpec.builder("main")
        .addParameter("args", String::class, KModifier.VARARG)
        .addStatement("%T(args[0]).greet()", greeterClass)
        .build())
    .build()*/

fun main(vararg args: String) {
    System.out.println(writeClassInterface(Dependency::class))
}
