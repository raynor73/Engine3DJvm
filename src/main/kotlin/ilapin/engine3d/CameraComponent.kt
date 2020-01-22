package ilapin.engine3d

import org.joml.Matrix4fc

abstract class CameraComponent(var targetTextureNames: List<String>?) : GameObjectComponent() {

    abstract fun getViewProjectionMatrix(): Matrix4fc?
}