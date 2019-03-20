//DEPS com.squareup:kotlinpoet:1.1.0

import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties


class Dependency() {
    var name: String=""
    var color: String=""
    var shape: String=""
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

fun main(vararg args: String) {
    System.out.println(writeClassInterface(Dependency::class))
}
