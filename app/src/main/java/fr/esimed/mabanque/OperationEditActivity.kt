package fr.esimed.mabanque

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import fr.esimed.mabanque.data.*
import kotlinx.android.synthetic.main.activity_operation_add.*

import kotlinx.android.synthetic.main.activity_operation_edit.*
import kotlinx.android.synthetic.main.activity_operation_edit.toolbar
import kotlinx.android.synthetic.main.content_operation_add.*
import kotlinx.android.synthetic.main.content_operation_edit.*
import java.text.SimpleDateFormat
import java.util.*

class OperationEditActivity : AppCompatActivity() {

    var button_date: Button? = null
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    val myFormat = "dd/MM/yyyy" // mention the format you need
    val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)

    val db = BanqueDatabase.getDatabase(this)
    override fun onCreate(savedInstanceState: Bundle?) {

        var currentOperation = intent.getSerializableExtra("currentOperation") as Operation

        var currentDate = DateConverter().toDate(currentOperation.date)
        cal.time = currentDate


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation_edit)
        setSupportActionBar(toolbar)

        var currentDebit:Int

        if (currentOperation.debit)
            currentDebit = R.id.radBtnEditDebit
        else
            currentDebit = R.id.radBtnEditCredit

        spinnerOperationEditTiers.setSelection(db.tiersDAO().getTiers(currentOperation.tiers).id!!.toInt() - 1)
        spinnerOperationEditMoyenPaiement.setSelection(db.moyenPaiementDAO().getMoyenPaiement(currentOperation.moyenPaiement).id!!.toInt() - 1)

        editTextOperationEditSomme.setText(currentOperation.somme.toString())
        spinnerOperationEditMoyenPaiement.setSelection(currentOperation.moyenPaiement.toInt())
        spinnerOperationEditTiers.setSelection(currentOperation.tiers.toInt())
        radioGroupOperationEditDebit.check(currentDebit)
        //radioGroupOperationEditDebit.check()


        //CALENDRIER
        // get the references from layout file
        textview_date = this.txtViewOperationEditDateSelectionner
        button_date = this.btnOperationEditDate

        txtViewOperationEditDateSelectionner.text = SimpleDateFormat(myFormat, Locale.FRANCE).format(DateConverter().toDate(currentOperation.date)!!).toString()


        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        button_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@OperationEditActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })

        //EDIT D'UNE OPÉRATION
        btnEditOperationEdit.setOnClickListener(View.OnClickListener {

            if (editTextOperationEditSomme.text.isEmpty() || radioGroupOperationEditDebit.checkedRadioButtonId == -1 || txtViewOperationEditDateSelectionner.text == "--/--/----") {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }

            println(radioGroupOperationEditDebit.checkedRadioButtonId)

            var spinTiers = spinnerOperationEditTiers.selectedItemId
            var spinPaiement = spinnerOperationEditMoyenPaiement.selectedItemId
            var dateConverti = DateConverter().toLong(cal.getTime())
            var debit: Boolean
            var rapprochement: Boolean

            if (radioGroupOperationEditDebit.checkedRadioButtonId == R.id.radBtnEditDebit) debit = true else debit = false

            var editOperation = Operation(
                currentOperation.id,
                spinTiers,
                editTextOperationEditSomme.text.toString().toFloat(),
                spinPaiement,
                dateConverti!!,
                debit,
                false
            )
            db.operationDAO().updateOperation(editOperation)

            val intent = Intent(this, AccueilActivity::class.java)
            startActivity(intent)

        })

        btnEditOperationAnnuler.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AccueilActivity::class.java)
            startActivity(intent)
        })


        //REMPLISSAGE DROPDOWN LIST
        val spinnerPaiement: Spinner = spinnerOperationEditMoyenPaiement
        val paiementList = db.moyenPaiementDAO().getAllMoyenPaiement().toTypedArray()


        //DROPDOWNLIST DE PAIEMENT
        val adapterPaiement = ArrayAdapter(this, android.R.layout.simple_spinner_item, paiementList)

        adapterPaiement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPaiement.adapter = adapterPaiement

        imgBtnOperationEditPaiement.setOnClickListener(View.OnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.paiement_add)

            var btnAdd = dialog.findViewById<Button>(R.id.btnPaiementAddValider)
            var btnCancel = dialog.findViewById<Button>(R.id.btnPaiementAddAnnuler)

            btnCancel.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })

            //DIALOG AJOUT DE MOYEN DE PAIEMENT
            btnAdd.setOnClickListener(View.OnClickListener {

                var varEditTextAddPaiement =
                    dialog.findViewById<EditText>(R.id.editTextPaiementAdd).text

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
        val spinnerTiers: Spinner = spinnerOperationEditTiers
        val tiersList = db.tiersDAO().getAllTiers().toTypedArray()

        val adapterTiers = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiersList)

        adapterTiers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTiers.adapter = adapterTiers

        //DIALOG AJOUT DE TIERS
        imgBtnOperationEditTiers.setOnClickListener(View.OnClickListener {
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

}
