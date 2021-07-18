package com.example.calculator

class Stack<T>(vararg items: T) {
    private val elements = items.toMutableList()
    fun isEmpty() = elements.isEmpty()
    fun push(element: T) = elements.add(element)
    fun peek(): T? = elements.lastOrNull()
    fun pop(): T? {
        val item = elements.lastOrNull()
        if (!isEmpty())
            elements.removeAt(elements.size - 1)
        return item
    }

    fun size() = elements.size

    override fun toString(): String = elements.toString()
}

fun <T> Stack<T>.push(elements: Collection<T>) = elements.forEach {this.push(it)}