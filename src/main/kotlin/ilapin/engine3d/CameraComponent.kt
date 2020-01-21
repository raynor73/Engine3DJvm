package ilapin.engine3d

import org.joml.Matrix4fc

abstract class CameraComponent(
        var targetTextureNames: List<String>? = null
) : GameObjectComponent() {

    abstract fun getViewProjectionMatrix(): Matrix4fc?
}