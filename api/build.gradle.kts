import net.labymod.labygradle.common.extension.LabyModAnnotationProcessorExtension.ReferenceType

dependencies {
    labyProcessor()
    labyApi("api")
}

labyModAnnotationProcessor {
    referenceType = ReferenceType.INTERFACE
}