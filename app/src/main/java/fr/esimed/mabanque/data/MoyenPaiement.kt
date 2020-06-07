package fr.esimed.mabanque.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class MoyenPaiement(@PrimaryKey(autoGenerate = true)
                         val id : Long? = null,
                         var nom : String){

    override fun equals(other: Any?): Boolean {
        val obj = other as Tiers
        return this.id == obj.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)

    }

    override fun toString(): String {
        return nom
    }

}