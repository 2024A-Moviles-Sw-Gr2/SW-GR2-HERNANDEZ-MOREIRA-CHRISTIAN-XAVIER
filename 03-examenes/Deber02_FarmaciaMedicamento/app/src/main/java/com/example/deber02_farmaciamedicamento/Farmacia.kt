package com.example.deber02_farmaciamedicamento

import android.os.Parcel
import android.os.Parcelable

data class Farmacia(
    val idFarmacia: Int,
    var nombreFarmacia: String,
    var direccionFarmacia: String,
    var numeroTotalMedicamentos: Int,
    var abierta24Horas: Boolean,
    var latitudFarmacia: Double,
    var longitudFarmacia: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idFarmacia)
        parcel.writeString(nombreFarmacia)
        parcel.writeString(direccionFarmacia)
        parcel.writeInt(numeroTotalMedicamentos)
        parcel.writeByte(if (abierta24Horas) 1 else 0)
        parcel.writeDouble(latitudFarmacia)
        parcel.writeDouble(longitudFarmacia)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Farmacia> {
        override fun createFromParcel(parcel: Parcel): Farmacia {
            return Farmacia(parcel)
        }

        override fun newArray(size: Int): Array<Farmacia?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Nombre farmacia: $nombreFarmacia, Número total de medicamentos: $numeroTotalMedicamentos, " +
                "Latitud: $latitudFarmacia, Longitud: $longitudFarmacia"
    }
}