package com.valinor.game.world.entity

import com.valinor.game.task.impl.TickableTask

fun Entity.event(task: TickableTask.() -> Unit) {
    repeatingTask {
        task.invoke(it)
    }
}
