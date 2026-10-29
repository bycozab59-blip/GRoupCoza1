package com.b2yblaster.groupcoza

object ExecutorService {

    private var attached = false

    fun attach(): Boolean {
        attached = true
        return true
    }

    fun execute(script: String): Boolean {
        if (!attached) return false
        ScriptStore.pending = script
        return true
    }

    fun detach() {
        attached = false
    }
}

object ScriptStore {
    var pending: String = ""
}
