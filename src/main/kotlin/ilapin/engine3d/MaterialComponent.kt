package ilapin.engine3d

class MaterialComponent(
    val textureName: String,
    val isDoubleSided: Boolean = false,
    val isWireframe: Boolean = false,
    val isUnlit: Boolean = false
) : GameObjectComponent() {

    override fun copy(): GameObjectComponent {
        return MaterialComponent(textureName, isDoubleSided, isWireframe, isUnlit)
    }
}