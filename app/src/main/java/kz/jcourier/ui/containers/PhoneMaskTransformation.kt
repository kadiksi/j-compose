package kz.jcourier.ui.containers

import android.util.Log
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.*

// Define the mask pattern
private const val PHONE_MASK = "(###) ###-####"

class PhoneMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return maskFilter(text)
    }

    private fun maskFilter(text: AnnotatedString): TransformedText {

        // +7 012 345 67 89
        // 0123456789012345
        val trimmed = if (text.text.length >= 17) text.text.substring(0..16) else text.text
        var out = "+7 "
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 2) out += " "
            if (i == 5) out += " "
            if (i == 7) out += " "
        }

        val numberOffsetTranslator = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 3) {
                    Log.e("originalToTransformed", "offset 3")
                    return offset + 3
                }
                if (offset <= 6) {
                    Log.e("originalToTransformed", "offset 4")
                    return offset + 4
                }
                if (offset <= 8) {
                    Log.e("originalToTransformed", "offset 5")
                    return offset + 5
                }
                if (offset <= 17) {
                    Log.e("originalToTransformed", "offset 6 $offset")
                    return offset + 6
                }
                return 17
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset in 3..7) {
                    Log.e("transformedToOriginal", "offset 2-7")
                    return offset - 3
                }
                if (offset in 8..11) {
                    Log.e("transformedToOriginal", "offset 8-11")
                    return offset - 4
                }
                if (offset in 12..17) {
                    Log.e("transformedToOriginal", "offset 12-16 $offset")
                    return offset - 5
                }
                return 0
            }
        }
        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}