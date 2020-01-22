package ilapin.engine3d

interface Scene {

    val cameras: List<CameraComponent>

    fun getRenderingTargetCameras(textureName: String): List<CameraComponent>

    fun update()

    fun onScreenConfigUpdate(width: Int, height: Int)

    fun onCleared()
}