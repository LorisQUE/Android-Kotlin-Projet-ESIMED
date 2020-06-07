package fr.esimed.mabanque

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esimed.mabanque.data.BanqueDatabase
import fr.esimed.mabanque.data.OperationAdapter
import fr.esimed.mabanque.data.OperationSwipeController

import kotlinx.android.synthetic.main.activity_operation.*
import kotlinx.android.synthetic.main.content_accueil.*
import kotlinx.android.synthetic.main.content_operation.*

class OperationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)
        setSupportActionBar(toolbar)

        val db = BanqueDatabase.getDatabase(this)

        my_recycler_view_operation.layoutManager = LinearLayoutManager(this)
        my_recycler_view_operation.adapter = OperationAdapter(this, db.operationDAO(), db.moyenPaiementDAO(), db.tiersDAO())

        var operationSwipe = OperationSwipeController(db.operationDAO(),OperationAdapter(this, db.operationDAO(), db.moyenPaiementDAO(),db.tiersDAO()))
        val itemTouch = ItemTouchHelper(operationSwipe)

        itemTouch.attachToRecyclerView(my_recycler_view_operation)

        var debit = db.operationDAO().getSumOperationDebit()
        var credit = db.operationDAO().getSumOperationCredit()
        var res = credit-debit

        txtViewOperationContentCrédit.text = "Crédit: " + db.operationDAO().getSumOperationCredit() + "€"
        txtViewOperationContentDébit.text = "Débit: -" + db.operationDAO().getSumOperationDebit() + "€"

        if (res < 0)
            txtViewOperationContentSoldeModif.setTextColor(Color.parseColor("#FF0000"))
        else if (res > 0)
            txtViewOperationContentSoldeModif.setTextColor(Color.parseColor("#00FF00"))

        txtViewOperationContentSoldeModif.text = res.toString() + "€"
    }
}
