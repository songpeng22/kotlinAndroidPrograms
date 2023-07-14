package com.example.kotlinaidl

import android.os.Parcel
import android.os.Parcelable

data class Field(val id:String?,val type:String?,val posX:Int,val posY:Int,val width:Int,val height:Int,val font:String?,val rotation:String?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),//TODO("id"),
        parcel.readString(),//TODO("type"),
        parcel.readInt(),//TODO("posX"),
        parcel.readInt(),//TODO("posY"),
        parcel.readInt(),//TODO("width"),
        parcel.readInt(),//TODO("height"),
        parcel.readString(),//TODO("font"),
        parcel.readString()//TODO("rotation")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeInt(posX)
        parcel.writeInt(posY)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeString(font)
        parcel.writeString(rotation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Field> {
        override fun createFromParcel(parcel: Parcel): Field {
            return Field(parcel)
        }

        override fun newArray(size: Int): Array<Field?> {
            return arrayOfNulls(size)
        }
    }
}

class Label(var fields:List<Field> = listOf<Field>()) : Parcelable {
    constructor(parcel: Parcel) : this() {
        fields = listOf<Field>()
        parcel.readTypedList(fields,Field.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(fields)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Label> {
        override fun createFromParcel(parcel: Parcel): Label {
            return Label(parcel)
        }

        override fun newArray(size: Int): Array<Label?> {
            return arrayOfNulls(size)
        }
    }
}

