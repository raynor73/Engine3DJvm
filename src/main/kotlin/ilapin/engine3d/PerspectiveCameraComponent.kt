package ilapin.engine3d

import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.joml.Vector3f

class PerspectiveCameraComponent(targetTextureNames: List<String>? = null): CameraComponent(targetTextureNames) {

    private val projectionMatrix = Matrix4f()
    private val viewProjectionMatrix = Matrix4f()
    private val lookAtDirection = Vector3f(0f, 0f, -1f)
    private val up = Vector3f(0f, 1f, 0f)

    var aspect: Float? = null
        set(value) {
            val config = this.config
            if (value != null && config != null) {
                projectionMatrix.identity().perspective(
                    Math.toRadians(config.fieldOfView.toDouble()).toFloat(),
                    value,
                    config.zNear,
                    config.zFar
                )
            } else {
                projectionMatrix.identity()
            }
            field = value
        }

    var config: Config? = null
        set(value) {
            val aspect = this.aspect
            if (value != null && aspect != null) {
                projectionMatrix.identity().perspective(
                    Math.toRadians(value.fieldOfView.toDouble()).toFloat(),
                    aspect,
                    value.zNear,
                    value.zFar
                )
            } else {
                projectionMatrix.identity()
            }
            field = value
        }

    init {
        projectionMatrix.identity()
    }

    override fun getViewProjectionMatrix(): Matrix4fc? {
        return if (config != null) {
            val transform = gameObject?.getComponent(TransformationComponent::class.java)
            val position = transform?.position ?: return null
            val rotation = transform.rotation
            lookAtDirection.set(0f, 0f, -1f)
            lookAtDirection.rotate(rotation)
            up.set(0f, 1f, 0f)
            up.rotate(rotation)
            projectionMatrix.lookAt(
                position.x(), position.y(), position.z(),
                position.x() + lookAtDirection.x, position.y() + lookAtDirection.y, position.z() + lookAtDirection.z,
                up.x, up.y, up.z,
                viewProjectionMatrix
            )
        } else {
            null
        }
    }

    data class Config(val fieldOfView: Float, val zNear: Float, val zFar: Float)
}