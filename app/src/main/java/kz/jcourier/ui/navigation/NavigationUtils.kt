package kz.jcourier.ui.navigation

import android.os.Parcelable
import androidx.navigation.NavController

inline fun <reified T> List<*>.isListInstanceOf(): Boolean {
    this.filterIsInstance<T>().apply { return size == this.size }
}

fun NavController.navigate(route: String, vararg args: Pair<String, Any?>) {
    navigate(route)

    requireNotNull(currentBackStackEntry?.arguments).apply {
        args.forEach { (key: String, arg: Any?) ->
            when (arg) {
                is Parcelable -> putParcelable(key, arg)
                is String -> putString(key, arg)
                is Boolean -> putBoolean(key, arg)
                is Double -> putDouble(key, arg)
                is Long -> putLong(key, arg)
                is Int -> putInt(key, arg)
                is List<*> -> {
                    when {
                        arg.isListInstanceOf<Parcelable>() -> {
                            putParcelableArrayList(
                                key,
                                ArrayList(arg.filterIsInstance<Parcelable>())
                            )
                        }
                        arg.isListInstanceOf<String>() -> {
                            putStringArrayList(key, ArrayList(arg.filterIsInstance<String>()))
                        }
                        arg.isListInstanceOf<Boolean>() -> {
                            putBooleanArray(key, arg.filterIsInstance<Boolean>().toBooleanArray())
                        }
                        arg.isListInstanceOf<Double>() -> {
                            putDoubleArray(key, arg.filterIsInstance<Double>().toDoubleArray())
                        }
                        arg.isListInstanceOf<Long>() -> {
                            putLongArray(key, arg.filterIsInstance<Long>().toLongArray())
                        }
                        arg.isListInstanceOf<Int>() -> {
                            putIntArray(key, arg.filterIsInstance<Int>().toIntArray())
                        }
                    }
                }
            }
        }
    }
}
