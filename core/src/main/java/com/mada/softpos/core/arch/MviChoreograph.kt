package com.mada.softpos.core.arch

abstract class MviChoreograph<State : MviState> {
    abstract fun invalidate(state: State)
}