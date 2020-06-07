package fr.esimed.mabanque.data

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.mabanque.R
import org.w3c.dom.Text

class OperationViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    val cardViewOperation:CardView = row.findViewById(R.id.cardViewOperationCell)
    val txtViewOperationDate:TextView = row.findViewById(R.id.txtViewOperationCellDate)
    val txtViewOperationDebit:TextView = row.findViewById(R.id.txtViewOperationCellDebit)
    val txtViewOperationSomme:TextView = row.findViewById(R.id.txtViewOperationCellSomme)
    val txtViewOperationTiers:TextView = row.findViewById(R.id.txtViewOperationCellTiers)
    val txtViewOperationModePaiement:TextView = row.findViewById(R.id.txtViewOperationCellModePaie)
    val txtViewOperationRapprochement:TextView = row.findViewById(R.id.txtViewOperationCellRapprochement)
    //val txtViewOperationSolde:TextView = row.findViewById(R.id.txtViewOperationCellSolde)

}