package fr.esimed.mabanque

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import fr.esimed.mabanque.data.BanqueDatabase

import kotlinx.android.synthetic.main.activity_accueil.*
import kotlinx.android.synthetic.main.content_accueil.*
import kotlinx.android.synthetic.main.content_operation_add.*

class AccueilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        setSupportActionBar(toolbar)

        val db = BanqueDatabase.getDatabase(this)
        db.seed()

        var debit = db.operationDAO().getSumOperationDebit()
        var credit = db.operationDAO().getSumOperationCredit()
        var res = credit-debit

        if (res < 0)
            txtViewMenuSoldeModif.setTextColor(Color.parseColor("#FF0000"))
        else if (res > 0)
            txtViewMenuSoldeModif.setTextColor(Color.parseColor("#0B6623"))
        txtViewMenuSoldeModif.text =  res.toString() + "€"

        txtViewMenuDebitModif.text = debit.toString()
        txtViewMenuCreditModif.text = credit.toString()

        cardViewMenuAccesCompte.setOnClickListener( View.OnClickListener {

            val intent = Intent(this, OperationActivity::class.java)
            startActivity(intent)

        })

        cardViewMenuAjoutCompte.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, OperationAddActivity::class.java)
            startActivity(intent)
        })

        cardViewMenuAccesRapprochement.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, OperationRapprochementActivity::class.java)
            startActivity(intent)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

        val db = BanqueDatabase.getDatabase(this)
        db.seed()

        var debit = db.operationDAO().getSumOperationDebit()
        var credit = db.operationDAO().getSumOperationCredit()
        var res = credit-debit

        txtViewMenuDebitModif.text = debit.toString()
        txtViewMenuCreditModif.text = credit.toString()

        if (res < 0)
            txtViewMenuSoldeModif.setTextColor(Color.parseColor("#FF0000"))
        else if (res > 0)
            txtViewMenuSoldeModif.setTextColor(Color.parseColor("#0B6623"))
        txtViewMenuSoldeModif.text =  res.toString() + "€"
    }
}
