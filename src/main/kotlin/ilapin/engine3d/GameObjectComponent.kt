package ilapin.engine3d

abstract class GameObjectComponent {

    var isEnabled = true

    open var gameObject: GameObject? = null

    abstract fun copy(): GameObjectComponent

    open fun update() {}

    open fun deinit() {}

    companion object {

        private var copyPostfix = 0

        protected fun nextCopyPostfix(): Int {
            return copyPostfix++
        }
    }
}
