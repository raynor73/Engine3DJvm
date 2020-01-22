package ilapin.engine3d

import ilapin.common.kotlin.safeLet
import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.joml.Vector3f

class PerspectiveCameraComponent(
        targetTextureNames: List<String>? = null
): CameraComponent<PerspectiveCameraComponent.Config>(targetTextureNames) {

    private val projectionMatrix = Matrix4f()
    private val viewProjectionMatrix = Matrix4f()

    private val lookAtDirection = Vector3f(0f, 0f, -1f)
    private val up = Vector3f(0f, 1f, 0f)

    override fun getViewProjectionMatrix(aspect: Float?, config: Config?): Matrix4fc? {
        return safeLet(aspect ?: this.aspect, config ?: this.config) { a, c ->
            projectionMatrix.identity().perspective(
                    Math.toRadians(c.fieldOfView.toDouble()).toFloat(),
                    a,
                    c.zNear,
                    c.zFar
            )

            val transform = gameObject?.getComponent(TransformationComponent::class.java)
            val position = transform?.position ?: return@safeLet null
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
        }
    }

    data class Config(val fieldOfView: Float, val zNear: Float, val zFar: Float)
}