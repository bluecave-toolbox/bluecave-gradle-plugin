package io.bluecave.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Path

abstract class BlueCaveReportTask : DefaultTask() {

    @TaskAction
    fun run() {
        val root = project.rootProject.rootDir
        val projects = if (project.subprojects.isEmpty()) {
            listOf(project)
        } else {
            project.subprojects
        }

        val allSources = mutableListOf<Path>()
        val allBuild = mutableListOf<Path>()

        projects.forEach { p ->
            val sets = p.extensions.findByName("sourceSets") as? SourceSetContainer
            for (f: String in listOf("main", "test")) {
                sets?.getByName(f)?.allJava?.srcDirs?.forEach {
                    val rel = root.toPath().relativize(it.toPath())
                    if (!rel.startsWith("build/") && !rel.startsWith("target/")) {
                        allSources.add(rel)
                    }
                }
            }
        }

        allBuild.add(Path.of("build/classes/java/main"))

        if (allSources.isEmpty()) {
            throw RuntimeException("No source directories found")
        }

        if (allBuild.isEmpty()) {
            throw RuntimeException("No build directories found")
        }

        var command =
            "./bluecave -l java -s ${allSources.joinToString(" -s ") { it.toString() }} -x ${allBuild.joinToString(" -x ") { it.toString() }}"

        System.getenv("BLUECAVE_EXTRA_OPTS")?.let {
            command += " $it"
        }

        runCommand("curl -sL https://get.bluecave.io | bash")
        runCommand(command)
    }

    fun runCommand(command: String) {
        println("Running: $command")
        val process = ProcessBuilder("bash", "-c", command)
            .redirectErrorStream(true)
            .start()

        BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                println(line)
            }
        }

        val exit = process.waitFor()
        if (exit != 0) {
            throw RuntimeException("Command failed with exit code $exit")
        }
    }

}

class BlueCaveReportPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("bluecaveReport", BlueCaveReportTask::class.java) {
            project.group = "verification"
            project.description = "Run static analysis and report coverage to Blue Cave"
        }
    }
}
