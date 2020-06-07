package fr.esimed.mabanque.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*


@Entity(foreignKeys = [
    ForeignKey(
        entity = Tiers::class,
        parentColumns = ["id"],
        childColumns = ["tiers"]
    ),
    ForeignKey(
        entity =  MoyenPaiement::class,
        parentColumns = ["id"],
        childColumns = ["moyenPaiement"]
    )])

data class Operation (@PrimaryKey(autoGenerate = true)
                      val id : Long? = null,
                      var tiers: Long, //Clé étrangère
                      var somme: Float,
                      var moyenPaiement: Long, //Clé étrangère
                      var date: Long, //Timestamp
                      var debit: Boolean,
                      var rapprochement: Boolean):Serializable {


    override fun equals(other: Any?): Boolean {
        val obj = other as Operation
        return this.id == obj.id
    }


    override fun hashCode(): Int {
        return Objects.hash(id)

    }


    override fun toString(): String {
        return "Id : $id tiers: $tiers somme: $somme date: $date moyen de paiement: $moyenPaiement"
    }

}