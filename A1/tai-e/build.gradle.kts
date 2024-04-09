import org.gradle.api.tasks.JavaExec
plugins {
    id("java")
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("lib/tai-e-assignment.jar"))
    implementation(files("../../lib/dependencies.jar"))
    testImplementation("junit:junit:4.13")
}

application {
    mainClass.set("pascal.taie.Assignment")
}
tasks.named<JavaExec>("run") {
    args = listOf("-cp","src/test/resources/dataflow/livevar","-m","Assign") // 这里替换为你想传递的参数
}
tasks.compileJava { options.encoding = "UTF-8" }
tasks.compileTestJava { options.encoding = "UTF-8" }

tasks.test {
    useJUnit()
    maxHeapSize = "4G"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

val libDir = project.projectDir.parentFile.parentFile.resolve("lib")
libDir.listFiles()
    ?.map { it.name }
    ?.toList()
    ?.containsAll(listOf("dependencies.jar", "rt.jar"))
    ?.takeIf { it }
    ?: throw IllegalStateException("Could not find dependencies.jar or rt.jar in ${libDir.absolutePath}")
