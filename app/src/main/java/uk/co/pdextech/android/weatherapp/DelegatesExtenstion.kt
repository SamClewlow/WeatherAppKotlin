package uk.co.pdextech.android.weatherapp

import android.content.Context
import kotlin.reflect.KProperty

/**
 * Created by Pdex on 13/02/2018.
 */

class NotNullSingleValueVar<T> {

    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("$property.name not initialised")
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("${property.name} already set")
    }
}

object DelegatesExt {
    fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()
    fun <T> preference(context: Context, name: String, default: T): Preference<T> {
        return Preference(context, name, default)
    }
}

class Preference<T>(val context: Context, val name: String, val default: T) {

    val prefs by lazy { context.getSharedPreferences("default", Context.MODE_PRIVATE) }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreferece(name, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun <T> findPreferece(name: String, default: T): T {
        with(prefs) {
            val res: Any = when(default) {
                is Long -> getLong(name, default)
                is String -> getString(name, default)
                is Int -> getInt(name, default)
                is Boolean -> getBoolean(name, default)
                is Float -> getFloat(name, default)
                else -> throw IllegalArgumentException()
            }

            return res as T
        }
    }

    private fun <U> putPreference(name: String, value: U) {
        with(prefs.edit()) {
            when(value) {
                is Long -> putLong(name, value).apply()
                is String -> putString(name, value).apply()
                is Int -> putInt(name, value).apply()
                is Boolean ->putBoolean(name, value).apply()
                is Float -> putFloat(name, value).apply()
                else -> throw IllegalArgumentException()
            }
        }
    }
}