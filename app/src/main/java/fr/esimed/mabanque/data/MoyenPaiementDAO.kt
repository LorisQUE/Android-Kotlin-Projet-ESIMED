package fr.esimed.mabanque.data

import androidx.room.*

@Dao
interface MoyenPaiementDAO {
    @Query("SELECT * FROM MoyenPaiement")
    fun getAllMoyenPaiement() : List<MoyenPaiement>

    @Query("SELECT * FROM MoyenPaiement WHERE id = :id")
    fun getMoyenPaiement(id: Long) : MoyenPaiement

    @Query("SELECT nom FROM MoyenPaiement WHERE id = :id")
    fun getNomMoyenPaiement(id: Long) : String

    @Query("SELECT COUNT(id) FROM MoyenPaiement")
    fun getCountMoyenPaiement() : Int

    @Insert
    fun insertMoyenPaiement(moyenPaiement: MoyenPaiement):Long

    @Update
    fun updateMoyenPaiement(vararg moyenPaiement: MoyenPaiement)

    @Delete
    fun deleteMoyenPaiement(vararg moyenPaiement: MoyenPaiement)
}