package ilapin.engine3d

open class GameObject(val name: String) {

    var isEnabled = true

    private var _parent: GameObject? = null
    private val _children = HashSet<GameObject>()
    private val components = HashSet<GameObjectComponent>()

    val parent: GameObject?
        get() = _parent

    val children: Set<GameObject>
        get() = _children

    fun addChild(child: GameObject) {
        _children += child
        child._parent = this
    }

    fun removeChild(child: GameObject) {
        child.deinit()
        _children -= child
        child._parent = null
    }

    fun detachChild(child: GameObject) {
        _children -= child
        child._parent = null
    }

    fun addComponent(component: GameObjectComponent) {
        components += component
        component.gameObject = this
    }

    fun removeComponent(component: GameObjectComponent) {
        component.deinit()
        components -= component
        component.gameObject = null
    }

    fun update() {
        if (isEnabled) {
            components.forEach { it.update() }
            _children.forEach { it.update() }
        }
    }

    fun copy(copyName: String? = null): GameObject {
        val copy = GameObject(copyName ?: name + nextCopyPostfix())

        _children.forEach { copy.addChild(it.copy()) }
        components.forEach { copy.addComponent(it.copy()) }

        return copy
    }

    fun deinit() {
        _children.forEach { it.deinit() }
        components.forEach { it.deinit() }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : GameObjectComponent> getComponent(clazz: Class<T>): T? {
        for (component in components) {
            if (component.javaClass == clazz) {
                return component as T
            }
        }

        return null
    }

    companion object {

        private var copyPostfix = 0

        private fun nextCopyPostfix(): Int {
            return copyPostfix++
        }
    }
}