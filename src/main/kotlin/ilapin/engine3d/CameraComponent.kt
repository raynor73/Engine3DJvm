package ilapin.engine3d

import org.joml.Matrix4fc

abstract class CameraComponent<T>(var targetTextureNames: List<String>?) : GameObjectComponent() {

    var aspect: Float? = null
    var config: T? = null

    abstract fun getViewProjectionMatrix(aspect: Float? = null, config: T? = null): Matrix4fc?
}