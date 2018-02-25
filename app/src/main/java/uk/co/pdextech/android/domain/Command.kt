package uk.co.pdextech.android.domain

/**
 * Created by Pdex on 12/02/2018.
 */

public interface Command<out T> {
    fun execute(): T
}