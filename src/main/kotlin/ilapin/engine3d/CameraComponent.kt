package ilapin.engine3d

import org.joml.Matrix4fc

abstract class CameraComponent : GameObjectComponent() {

    var targetTextureNames: List<String>? = null

    abstract fun getViewProjectionMatrix(): Matrix4fc?
}