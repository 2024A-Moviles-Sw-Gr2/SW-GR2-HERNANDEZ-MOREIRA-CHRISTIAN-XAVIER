package com.example.deber02_farmaciamedicamento

import android.os.Parcel
import android.os.Parcelable

data class Medicamento(
    val idMedicamento: Int,
    var nombreMedicamento: String,
    var esRecetado: Boolean,
    var precioMedicamento: Double,
    var idFarmacia: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idMedicamento)
        parcel.writeString(nombreMedicamento)
        parcel.writeByte(if (esRecetado) 1 else 0)
        parcel.writeDouble(precioMedicamento)
        parcel.writeInt(idFarmacia)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Medicamento> {
        override fun createFromParcel(parcel: Parcel): Medicamento {
            return Medicamento(parcel)
        }

        override fun newArray(size: Int): Array<Medicamento?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Nombre medicamento: $nombreMedicamento Precio medicamento: $precioMedicamento"
    }
}