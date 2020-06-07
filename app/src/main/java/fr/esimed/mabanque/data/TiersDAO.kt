package fr.esimed.mabanque.data

import androidx.room.*

@Dao
interface TiersDAO {
    @Query("SELECT * FROM tiers")
    fun getAllTiers() : List<Tiers>

    @Query("SELECT * FROM tiers WHERE id = :id")
    fun getTiers(id: Long) : Tiers

    @Query("SELECT nom FROM tiers WHERE id = :id")
    fun getNomTiers(id: Long) : String

    @Query("SELECT COUNT(id) FROM tiers")
    fun getCountTiers() : Int

    @Insert
    fun insertTiers(tiers:Tiers):Long

    @Update
    fun updateTiers(vararg tiers: Tiers)

    @Delete
    fun deleteTiers(vararg tiers: Tiers)
}