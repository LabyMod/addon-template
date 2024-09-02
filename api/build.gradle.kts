import net.labymod.labygradle.common.extension.LabyModAnnotationProcessorExtension.ReferenceType

dependencies {
    labyApi("api")
}

labyModAnnotationProcessor {
    referenceType = ReferenceType.INTERFACE
}