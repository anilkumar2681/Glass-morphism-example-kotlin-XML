package com.team42.glassmorphism.utill_classes

import android.os.Parcel
import android.os.Parcelable
import android.view.AbsSavedState

class SavedState : AbsSavedState {

    var cornerRadius: Float = 0f
    var padding: Float = 0f
    var surfaceOpacity: Float = 0f
    var borderOpacity: Float = 0f
    var shadowOpacity: Float = 0f
    var borderWidth: Float = 0f

    constructor(superState: Parcelable) : super(superState)

    constructor(source: Parcel, loader: ClassLoader?) : super(source, loader) {
        cornerRadius = source.readFloat()
        padding = source.readFloat()
        surfaceOpacity = source.readFloat()
        borderOpacity = source.readFloat()
        shadowOpacity = source.readFloat()
        borderWidth = source.readFloat()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeFloat(cornerRadius)
        out.writeFloat(padding)
        out.writeFloat(surfaceOpacity)
        out.writeFloat(borderOpacity)
        out.writeFloat(shadowOpacity)
        out.writeFloat(borderWidth)
    }

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.ClassLoaderCreator<SavedState> =
            object : Parcelable.ClassLoaderCreator<SavedState> {
                override fun createFromParcel(source: Parcel, loader: ClassLoader): SavedState {
                    return SavedState(source, loader)
                }

                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source, null)
                }

                override fun newArray(size: Int): Array<SavedState> {
                    return newArray(size)
                }
            }
    }
}
