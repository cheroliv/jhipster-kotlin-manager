package game.ceelo.stats

import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import game.ceelo.R
import game.ceelo.R.layout.game_row
import game.ceelo.domain.*
import game.ceelo.service.ICeeloService
import game.ceelo.service.local.inmemory.CeeloServiceInMemory
import game.ceelo.stats.CeeloAdapter.CeeloViewHolder

class CeeloAdapter : Adapter<CeeloViewHolder>() {
    private val ceeloService: ICeeloService = CeeloServiceInMemory(null)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CeeloViewHolder = CeeloViewHolder(
        from(parent.context).inflate(
            game_row,
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: CeeloViewHolder,
        position: Int
    ) {
        val currentItem = ceeloService.allGames()[position]
        holder.player_one_name_text.text = PLAYER_ONE_NAME
        holder.player_one_dices_throw_text.text = currentItem.first().toString()
        holder.player_one_result_text.text = currentItem.first()
            .compareThrows(secondPlayerThrow = currentItem.second()).toString()
        holder.player_one_game_type_text.text = GAME_TYPE
        holder.player_two_name_text.text = PLAYER_TWO_NAME
        holder.player_two_dices_throw_text.text = currentItem.second().toString()
        holder.player_two_result_text.text = currentItem.second()
            .compareThrows(secondPlayerThrow = currentItem.first()).toString()
        holder.player_two_game_type_text.text = GAME_TYPE
    }

    override fun getItemCount(): Int =
        ceeloService.allGames().size


    class CeeloViewHolder(
        itemView: View,
        var player_one_name_text: TextView = itemView.findViewById(R.id.player_one_name_text),
        var player_one_dices_throw_text: TextView = itemView.findViewById(R.id.player_one_dices_throw_text),
        var player_one_result_text: TextView = itemView.findViewById(R.id.player_one_result_text),
        var player_one_game_type_text: TextView = itemView.findViewById(R.id.player_one_game_type_text),
        var player_two_name_text: TextView = itemView.findViewById(R.id.player_two_name_text),
        var player_two_dices_throw_text: TextView = itemView.findViewById(R.id.player_two_dices_throw_text),
        var player_two_result_text: TextView = itemView.findViewById(R.id.player_two_result_text),
        var player_two_game_type_text: TextView = itemView.findViewById(R.id.player_two_game_type_text),
    ) : ViewHolder(itemView)
}