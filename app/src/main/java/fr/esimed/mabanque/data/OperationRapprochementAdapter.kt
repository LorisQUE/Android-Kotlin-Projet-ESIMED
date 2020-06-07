package fr.esimed.mabanque.data

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.mabanque.OperationEditActivity
import fr.esimed.mabanque.R
import java.text.SimpleDateFormat
import java.util.*

class OperationRapprochementAdapter(context: Context, operationDAO: OperationDAO, moyenPaiementDAO: MoyenPaiementDAO, tiersDAO: TiersDAO): RecyclerView.Adapter<OperationRapprochementViewHolder>(){

    private var context: Context = context
    private var operation:OperationDAO = operationDAO
    private var paiement:MoyenPaiementDAO = moyenPaiementDAO
    private var tiers:TiersDAO = tiersDAO

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationRapprochementViewHolder {
        return OperationRapprochementViewHolder(LayoutInflater.from(context).inflate(R.layout.content_operation_rapprochement_cell, parent, false))
    }

    override fun onBindViewHolder(holder: OperationRapprochementViewHolder, position: Int) {
            val db = BanqueDatabase.getDatabase(context)
            var currentOperation = operation.getOperation(position.toLong())

            //On change les données du viewHolder pour avoir celle des opérations
            holder.txtViewOperationRapprochementDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(DateConverter().toDate(currentOperation.date)!!).toString()
            holder.txtViewOperationRapprochementModePaiement.text = paiement.getNomMoyenPaiement(currentOperation.moyenPaiement)
            holder.txtViewOperationRapprochementTiers.text = tiers.getNomTiers(currentOperation.tiers)

            if (currentOperation.debit) {
                holder.txtViewOperationRapprochementDebit.text = "Débit"
                holder.txtViewOperationRapprochementSomme.text = "-" + currentOperation.somme.toString() + "€"
            }
            else {
                holder.txtViewOperationRapprochementDebit.text = "Crédit"
                holder.txtViewOperationRapprochementSomme.text = currentOperation.somme.toString() + "€"
            }
    }

    override fun getItemCount(): Int {
        return operation.getCountOperation()
    }

}