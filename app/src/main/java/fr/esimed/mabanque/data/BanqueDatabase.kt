package fr.esimed.mabanque.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.text.SimpleDateFormat
import java.time.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Database(entities = [Operation::class, Tiers::class, MoyenPaiement::class], version = 2)
abstract class BanqueDatabase():RoomDatabase() {

    abstract fun operationDAO():OperationDAO
    abstract fun tiersDAO():TiersDAO
    abstract fun moyenPaiementDAO():MoyenPaiementDAO

    companion object {
        var INSTANCE: BanqueDatabase? = null
        fun getDatabase(context: Context): BanqueDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, BanqueDatabase::class.java, "banque.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }

    fun seed(){

        if (moyenPaiementDAO().getAllMoyenPaiement().isEmpty() && tiersDAO().getAllTiers().isEmpty()){

            tiersDAO().insertTiers(Tiers(0, "Carrefour"))
            tiersDAO().insertTiers(Tiers(1, "LIDL"))
            tiersDAO().insertTiers(Tiers(2, "Leclerc"))
            tiersDAO().insertTiers(Tiers(3, "FNAC"))

            moyenPaiementDAO().insertMoyenPaiement(MoyenPaiement(0, "Virement"))
            moyenPaiementDAO().insertMoyenPaiement(MoyenPaiement(1, "Espèce"))
            moyenPaiementDAO().insertMoyenPaiement(MoyenPaiement(2, "Chèque"))
        }

    }
}