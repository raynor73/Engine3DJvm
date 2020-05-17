package ilapin.engine3d

open class GameObject(val name: String) {

    var isEnabled = true

    private var _parent: GameObject? = null
    private val _children = HashSet<GameObject>()
    private val components = HashMap<Class<out GameObjectComponent>, GameObjectComponent>()

    val parent: GameObject?
        get() = _parent

    val children: Set<GameObject>
        get() = _children

    fun addChild(child: GameObject) {
        if (_children.contains(child)) {
            error("Trying to add ${child.name} game object second time")
        }
        _children += child
        child._parent = this
    }

    fun removeChild(child: GameObject) {
        if (!_children.contains(child)) {
            error("${child.name} game object not found")
        }
        child.deinit()
        _children -= child
        child._parent = null
    }

    fun detachChild(child: GameObject) {
        if (!_children.contains(child)) {
            error("${child.name} game object not found")
        }
        _children -= child
        child._parent = null
    }

    fun addComponent(component: GameObjectComponent) {
        if (components.containsKey(component.javaClass)) {
            error("Already have ${component.javaClass.simpleName} component")
        }
        components[component.javaClass] = component
        component.gameObject = this
    }

    fun removeComponent(component: GameObjectComponent) {
        if (!components.containsKey(component.javaClass)) {
            error("Component ${component.javaClass.simpleName} not found")
        }

        component.deinit()
        components.remove(component.javaClass)
        component.gameObject = null
    }

    fun update() {
        if (isEnabled) {
            components.values.forEach { it.update() }
            _children.forEach { it.update() }
        }
    }

    fun copy(copyName: String? = null): GameObject {
        val copy = GameObject(copyName ?: name + nextCopyPostfix())

        _children.forEach { copy.addChild(it.copy()) }
        components.values.forEach { copy.addComponent(it.copy()) }

        return copy
    }

    fun deinit() {
        _children.forEach { it.deinit() }
        components.values.forEach { it.deinit() }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : GameObjectComponent> getComponent(clazz: Class<T>): T? {
        return components[clazz] as T?
    }

    companion object {

        private var copyPostfix = 0

        private fun nextCopyPostfix(): Int {
            return copyPostfix++
        }
    }
}