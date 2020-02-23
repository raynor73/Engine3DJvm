package ilapin.engine3d

abstract class GameObjectComponent {

    var isEnabled = true

    open var gameObject: GameObject? = null

    open fun update() {}

    open fun deinit() {}
}
