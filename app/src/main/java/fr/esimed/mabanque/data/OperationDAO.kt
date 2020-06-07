package fr.esimed.mabanque.data

import androidx.room.*

@Dao
interface OperationDAO {
    @Query("SELECT * FROM operation ORDER BY date")
    fun getAllOperation() : List<Operation>

    @Query("SELECT * FROM operation WHERE date > 01/01/2020")
    fun getOperationDuMois() : List<Operation>

    @Query("SELECT * FROM operation LIMIT 1 OFFSET :id")
    fun getOperation(id: Long) : Operation

    @Query("SELECT SUM(somme) FROM operation")
    fun getSumOperation() : Float

    @Query("SELECT SUM(somme) FROM operation WHERE rapprochement = 0")
    fun getSumOperationNonRapprocher() : Float

    @Query("SELECT SUM(somme) FROM operation WHERE debit = 1")
    fun getSumOperationDebit() : Float

    @Query("SELECT SUM(somme) FROM operation WHERE debit = 0")
    fun getSumOperationCredit() : Float

    @Query("SELECT SUM(somme) FROM operation WHERE date > 01/01/2020")
    fun getSumOperationMoisEnCours() : Float



    @Query("SELECT COUNT(id) FROM operation")
    fun getCountOperation() : Int

    @Insert
    fun insertOperation(operation:Operation):Long

    @Update
    fun updateOperation(vararg operation: Operation)

    @Delete
    fun deleteOperation(vararg operation: Operation)
}