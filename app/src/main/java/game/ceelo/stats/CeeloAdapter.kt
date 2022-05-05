package game.ceelo.stats

import android.annotation.SuppressLint
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import game.ceelo.R
import game.ceelo.R.layout.game_row
import game.ceelo.domain.*
import game.ceelo.stats.CeeloAdapter.CeeloViewHolder

class CeeloAdapter(var games: List<List<List<Int>>>) : Adapter<CeeloViewHolder>() {


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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: CeeloViewHolder,
        position: Int
    ) {
        games[position].apply {
            holder.gameIdText.text = (position + 1).toString()
            holder.player_one_name_text.text = PLAYER_ONE_NAME
            holder.player_one_dices_throw_text.text = first().toString()
            holder.player_one_result_text.text = first()
                .compareThrows(secondPlayerThrow = second()).toString()
            holder.player_one_game_type_text.text = GAME_TYPE
            holder.player_two_name_text.text = PLAYER_TWO_NAME
            holder.player_two_dices_throw_text.text = second().toString()
            holder.player_two_result_text.text = second()
                .compareThrows(secondPlayerThrow = first()).toString()
            holder.player_two_game_type_text.text = GAME_TYPE
        }
    }

    override fun getItemCount(): Int = games.size


    class CeeloViewHolder(
        itemView: View,
        var gameIdText: TextView = itemView.findViewById(R.id.gameIdText),
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