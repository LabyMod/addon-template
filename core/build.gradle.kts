import net.labymod.labygradle.common.extension.LabyModAnnotationProcessorExtension.ReferenceType

dependencies {
    api(project(":api"))
}

labyModAnnotationProcessor {
    referenceType = ReferenceType.DEFAULT
}