package com.example.imgurapiapp.common

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.imgurapiapp.StringsConstant.Companion.SERVER_DATE_FORMAT
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

fun Window.getSoftInputMode(): Int {
    return attributes.softInputMode
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun String.sindri(){

}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View?.preventDoubleClick() {
    if (this != null) {
        this.isEnabled = false
        this.postDelayed(Runnable { this.isEnabled = true }, 1000)
    }
}

fun List<String>.getString(): String {
    if (this.isEmpty()) {
        return ""
    }

    val stringBuilder = StringBuilder()
    this.sortedBy { it.toDouble() }.forEach {
        stringBuilder.append(it)
        stringBuilder.append(",")
    }
    return stringBuilder.toString().removeSuffix(",")
}

fun String.indexesOf(substr: String, ignoreCase: Boolean = true) : List<Int> =
    (if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr))
        .findAll(this).map { it.range.first }.toList()

fun compressInputImage(inputImageData: Uri?, context: Context): Bitmap {
    //TODO deprecated
    var dpBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, inputImageData)
    try {
        //TODO deprecated
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, inputImageData)
        if (bitmap.width > 2048 && bitmap.height > 2048) {
            dpBitmap = Bitmap.createScaledBitmap(bitmap, 1024, 1280, true)
        } else if (bitmap.width > 2048 && bitmap.height < 2048) {
            dpBitmap = Bitmap.createScaledBitmap(bitmap, 1920, 1200, true)
        } else if (bitmap.width < 2048 && bitmap.height > 2048) {
            dpBitmap = Bitmap.createScaledBitmap(bitmap, 1024, 1280, true)
        } else if (bitmap.width < 2048 && bitmap.height < 2048) {
            dpBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)
        }
    } catch (e: Exception) {
        Log.d("profilePicture", "error while compressing " + e.localizedMessage, e)
    }
    return dpBitmap
}

inline fun EditText.observeTextChange(crossinline body: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            body(p0.toString())
        }
    })
}

inline fun EditText.observerAfterTextChange(crossinline body: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            body(p0.toString())
        }
    })
}

fun String.isEmail(): Boolean {
        if (this.contains("@"))
            return true
    return false
}

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard(view ?: View(activity))
}

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showKeyBoard(context: Context?, editText: EditText?) {
    if (context != null && editText != null) {
        editText.requestFocus()
      //  editText.isFocusable = true
        val im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.showSoftInput(editText, 0)
        if (editText.length()>0) {
            editText.setSelection(editText.length())
        }
    }
}



@Throws(IllegalAccessException::class, InstantiationException::class)
inline fun <reified T> newFragmentInstance(extras: Bundle.() -> Unit = {}): T? {

    return (T::class.java.newInstance() as Fragment).apply {
        arguments = Bundle().apply { extras() }
    } as T

}

@Throws(IllegalAccessException::class, InstantiationException::class)
inline fun <reified T : DialogFragment> AppCompatActivity.showDialogFragment(extras: Bundle.() -> Unit = {}): T? {

    return newFragmentInstance<T>(extras)?.apply {
        show(supportFragmentManager, T::class.java.simpleName)
    }
}

@Throws(IllegalAccessException::class, InstantiationException::class)
inline fun <reified T> Fragment.showDialogFragment(
    fromActivity: Boolean = true,
    extras: Bundle.() -> Unit = {}
): T? {
    val instance = newFragmentInstance<T>(extras)
    (instance as DialogFragment).show(
        if (fromActivity) {
            requireActivity().supportFragmentManager
        } else {
            childFragmentManager
        },
        T::class.java.simpleName
    )
    return instance
}

fun DialogFragment.dismissWithDelay(
    sleepDuration: Long = 300
) {
    lifecycleScope.launch {
        delay(sleepDuration)
        dismissAllowingStateLoss()
    }
}

fun Disposable.addToDisposable(disposer: CompositeDisposable) {
    disposer.add(this)

}

fun Context.getAppVersion(): String {
    var pInfo: PackageInfo? = null
    try {
        pInfo = this.packageManager.getPackageInfo(this.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
    }
    return pInfo?.versionName ?: ""
}

fun <E> ArrayList<E>.removeWithCondition(condition: (E) -> Boolean) {
    val each = iterator()
    while (each.hasNext()) {
        if (condition(each.next())) {
            each.remove()
        }
    }
}

fun String.toDate(): Date? {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(this)
}
