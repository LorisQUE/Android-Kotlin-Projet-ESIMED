package fr.esimed.mabanque

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esimed.mabanque.data.BanqueDatabase
import fr.esimed.mabanque.data.OperationAdapter
import fr.esimed.mabanque.data.OperationRapprochementAdapter
import fr.esimed.mabanque.data.OperationSwipeController

import kotlinx.android.synthetic.main.activity_operation_rapprochement.*
import kotlinx.android.synthetic.main.content_operation.*
import kotlinx.android.synthetic.main.content_operation_rapprochement.*

class OperationRapprochementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation_rapprochement)
        setSupportActionBar(toolbar)

        val db = BanqueDatabase.getDatabase(this)

        my_recycler_view_operation_a_rapproche.layoutManager = LinearLayoutManager(this)
        my_recycler_view_operation_a_rapproche.adapter = OperationRapprochementAdapter(this, db.operationDAO(), db.moyenPaiementDAO(), db.tiersDAO())
    }

}
