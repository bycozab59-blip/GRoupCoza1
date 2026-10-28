package com.b2yblaster.groupcoza

object ExecutorService {

    init {
        System.loadLibrary("groupcoza")
    }

    external fun attach(): Boolean
    external fun execute(script: String): Boolean
    external fun detach()
}
