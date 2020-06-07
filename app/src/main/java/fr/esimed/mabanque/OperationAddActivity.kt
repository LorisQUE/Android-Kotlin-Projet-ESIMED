package fr.esimed.mabanque

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import fr.esimed.mabanque.data.*

import kotlinx.android.synthetic.main.activity_operation_add.*
import kotlinx.android.synthetic.main.content_operation.*
import kotlinx.android.synthetic.main.content_operation_add.*
import java.text.SimpleDateFormat
import java.util.*

class OperationAddActivity : AppCompatActivity() {

    var button_date: Button? = null
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    val myFormat = "dd/MM/yyyy" // mention the format you need
    val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
    val db = BanqueDatabase.getDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation_add)
        setSupportActionBar(toolbar)





        //CALENDRIER
        // get the references from layout file
        textview_date = this.txtViewOperationAddDateSelectionner
        button_date = this.btnOperationAddDate

        textview_date!!.text = "--/--/----"

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        button_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@OperationAddActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })




        //AJOUT D'UNE OPÉRATION
        btnAjoutOperationAjout.setOnClickListener(View.OnClickListener {

            if (editTextoperationAddSomme.text.isEmpty() || radioGroupOperationAddDebit.checkedRadioButtonId == -1 || txtViewOperationAddDateSelectionner.text == "--/--/----"){
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }

            var spinTiers = spinnerOperationAddTiers.selectedItemId
            var spinPaiement = spinnerOperationAddMoyenPaiement.selectedItemId
            var dateConverti = DateConverter().toLong(cal.getTime())
            var debit: Boolean
            var rapprochement: Boolean

            if (radioGroupOperationAddDebit.checkedRadioButtonId == R.id.radBtnAddDebit) debit = true else debit = false

            var addOperation = Operation(null,spinTiers,editTextoperationAddSomme.text.toString().toFloat(),spinPaiement,dateConverti!!, debit ,false)
            db.operationDAO().insertOperation(addOperation)

            val intent = Intent(this, AccueilActivity::class.java)
            startActivity(intent)

            //(my_recycler_view_operation.adapter as OperationAdapter).notifyDataSetChanged()

        })

        btnAjoutOperationAnnuler.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AccueilActivity::class.java)
            startActivity(intent)
        })


        //REMPLISSAGE DROPDOWN LIST
        val spinnerPaiement: Spinner = spinnerOperationAddMoyenPaiement
        val paiementList = db.moyenPaiementDAO().getAllMoyenPaiement().toTypedArray()





        //DROPDOWNLIST DE PAIEMENT
        val adapterPaiement = ArrayAdapter(this, android.R.layout.simple_spinner_item, paiementList)

        adapterPaiement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPaiement.adapter = adapterPaiement

        imgBtnOperationAddPaiement.setOnClickListener(View.OnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.paiement_add)

            var btnAdd = dialog.findViewById<Button>(R.id.btnPaiementAddValider)
            var btnCancel = dialog.findViewById<Button>(R.id.btnPaiementAddAnnuler)

            btnCancel.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })

            //DIALOG AJOUT DE MOYEN DE PAIEMENT
            btnAdd.setOnClickListener(View.OnClickListener {

                var varEditTextAddPaiement = dialog.findViewById<EditText>(R.id.editTextPaiementAdd).text

                if (varEditTextAddPaiement.toString().trim().isEmpty()) {
                    Toast.makeText(this, "Aucun texte saisi", Toast.LENGTH_LONG).show()
                    return@OnClickListener
                }
                var paiement = MoyenPaiement(null, varEditTextAddPaiement.toString())
                db.moyenPaiementDAO().insertMoyenPaiement(paiement)

                dialog.dismiss()
            })

            dialog.show()
        })




        //DROPDOWNLIST DE TIERS
        val spinnerTiers: Spinner = spinnerOperationAddTiers
        val tiersList = db.tiersDAO().getAllTiers().toTypedArray()

        val adapterTiers = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiersList)

        adapterTiers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTiers.adapter = adapterTiers

        //DIALOG AJOUT DE TIERS
        imgBtnOperationAddTiers.setOnClickListener(View.OnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.tiers_add)

            var btnAdd = dialog.findViewById<Button>(R.id.btnTiersAddValider)
            var btnCancel = dialog.findViewById<Button>(R.id.btnTiersAddAnnuler)

            btnCancel.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })

            btnAdd.setOnClickListener(View.OnClickListener {

                var varEditTextAddTiers = dialog.findViewById<EditText>(R.id.editTextTiersAdd).text

                if (varEditTextAddTiers.toString().trim().isEmpty()) {
                    Toast.makeText(this, "Aucun texte saisi", Toast.LENGTH_LONG).show()
                    return@OnClickListener
                }
                var tiers = Tiers(null, varEditTextAddTiers.toString())
                db.tiersDAO().insertTiers(tiers)

                dialog.dismiss()
            })

            dialog.show()
        })
    }

    private fun updateDateInView() {
        textview_date!!.text = sdf.format(cal.getTime())
    }


    override fun onResume() {
        super.onResume()
        txtViewOperationAddDateSelectionner
    }

}
