package fr.esimed.mabanque.data

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.mabanque.R

class OperationRapprochementViewHolder(row: View) : RecyclerView.ViewHolder(row) {


    val cardViewOperationRapprochement: CardView = row.findViewById(R.id.cardViewOperationRapprochementCell)
    val txtViewOperationRapprochementDate: TextView = row.findViewById(R.id.txtViewOperationRapprochementCellDate)
    val txtViewOperationRapprochementDebit: TextView = row.findViewById(R.id.txtViewOperationRapprochementCellDebit)
    val txtViewOperationRapprochementSomme: TextView = row.findViewById(R.id.txtViewOperationRapprochementCellSomme)
    val txtViewOperationRapprochementTiers: TextView = row.findViewById(R.id.txtViewOperationRapprochementCellTiers)
    val txtViewOperationRapprochementModePaiement: TextView = row.findViewById(R.id.txtViewOperationRapprochementCellModePaie)

}