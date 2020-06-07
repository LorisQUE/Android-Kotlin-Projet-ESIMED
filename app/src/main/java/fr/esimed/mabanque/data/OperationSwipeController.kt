package fr.esimed.mabanque.data

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class OperationSwipeController(operationDAO: OperationDAO, operationAdapter: OperationAdapter):ItemTouchHelper.Callback() {

    private var operationDAO = operationDAO
    private var operationAdapter = operationAdapter

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.END)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var currentOperation = operationDAO.getOperation(viewHolder.adapterPosition.toLong())
        operationDAO.deleteOperation(currentOperation)

    }
}