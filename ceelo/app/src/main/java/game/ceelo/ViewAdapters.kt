package game.ceelo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import game.ceelo.CeeloAdapter.CeeloViewHolder
import game.ceelo.Constant.GAME_TYPE
import game.ceelo.Constant.PLAYER_ONE_NAME
import game.ceelo.Constant.PLAYER_TWO_NAME
import game.ceelo.Hand.compareHands
import game.ceelo.Game.firstPlayer
import game.ceelo.Game.secondPlayer

class CeeloAdapter(
    var games: List<List<List<Int>>>
) : Adapter<CeeloViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CeeloViewHolder = CeeloViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.simple_game_row,
            parent,
            false
        )
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: CeeloViewHolder,
        position: Int
    ) = games[position].run {
        holder.gameIdText.text = (position + 1).toString()
        holder.player_one_name_text.text = PLAYER_ONE_NAME
        holder.player_one_dices_throw_text.text = firstPlayer().toString()
        holder.player_one_result_text.text = firstPlayer().compareHands(secondPlayer()).toString()
        holder.player_one_game_type_text.text = GAME_TYPE
        holder.player_two_name_text.text = PLAYER_TWO_NAME
        holder.player_two_dices_throw_text.text = secondPlayer().toString()
        holder.player_two_result_text.text = secondPlayer().compareHands(firstPlayer()).toString()
        holder.player_two_game_type_text.text = GAME_TYPE
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