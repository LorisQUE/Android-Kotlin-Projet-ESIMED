package fr.esimed.mabanque.data

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.mabanque.OperationEditActivity
import fr.esimed.mabanque.R
import java.text.SimpleDateFormat
import java.util.*

class OperationAdapter(context: Context, operationDAO: OperationDAO, moyenPaiementDAO: MoyenPaiementDAO, tiersDAO: TiersDAO): RecyclerView.Adapter<OperationViewHolder>(){

    private var context:Context = context
    private var operation:OperationDAO = operationDAO
    private var paiement:MoyenPaiementDAO = moyenPaiementDAO
    private var tiers:TiersDAO = tiersDAO

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        return OperationViewHolder(LayoutInflater.from(context).inflate(R.layout.operation_cell, parent, false))
    }

    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
        val db = BanqueDatabase.getDatabase(context)
        var currentOperation = operation.getOperation(position.toLong())

        //On change les données du viewHolder pour avoir celle des opérations
        holder.txtViewOperationDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(DateConverter().toDate(currentOperation.date)!!).toString()
        holder.txtViewOperationModePaiement.text = paiement.getNomMoyenPaiement(currentOperation.moyenPaiement)
        holder.txtViewOperationTiers.text = tiers.getNomTiers(currentOperation.tiers)

        if (currentOperation.debit) {
            holder.txtViewOperationDebit.text = "Débit"
            holder.txtViewOperationSomme.text = "-" + currentOperation.somme.toString() + "€"
        }
        else {
            holder.txtViewOperationDebit.text = "Crédit"
            holder.txtViewOperationSomme.text = currentOperation.somme.toString() + "€"
        }
        if (currentOperation.rapprochement)
            holder.txtViewOperationRapprochement.text = "Rapproché"
        else
            holder.txtViewOperationRapprochement.text = "Non rapproché"


        holder.cardViewOperation.setOnClickListener(View.OnClickListener {

            var intent = Intent(context, OperationEditActivity::class.java)
            intent.putExtra("currentOperation", currentOperation)
            startActivity(context, intent, null)

        })
    }

    override fun getItemCount(): Int {
        return operation.getCountOperation()
    }

}