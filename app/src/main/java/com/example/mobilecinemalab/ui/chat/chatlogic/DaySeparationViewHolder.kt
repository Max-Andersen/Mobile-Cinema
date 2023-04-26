package com.example.mobilecinemalab.ui.chat.chatlogic

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.ui.chat.additionalmodels.ChatUIModel
import com.example.mobilecinemalab.databinding.DaySeparationBinding
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import com.example.mobilecinemalab.ui.chat.MonthsEnum
import java.util.*

class DaySeparationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding by viewBinding(DaySeparationBinding::bind)

    fun bind(data: ChatUIModel.DaySeparationModel) {

        val now: Instant = Clock.System.now()
        val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date

        if (today == data.daySeparation.date.date){
            binding.dateText.text = itemView.context.getText(R.string.today)
        } else{

            val date = String.format(
                itemView.context.getString(R.string.date_short),
                data.daySeparation.date.dayOfMonth.toString(),
                MonthsEnum.months[data.daySeparation.date.date.monthNumber]?.let {
                    itemView.context.getString(
                        it
                    )
                }
            )

            binding.dateText.text = date
        }


    }
}