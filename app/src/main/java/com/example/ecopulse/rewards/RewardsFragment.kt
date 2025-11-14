package com.example.ecopulse.rewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ecopulse.R

class RewardsFragment : Fragment() {

    private lateinit var rewardsContainer: LinearLayout

    data class RewardItem(
        val partner: String,
        val title: String,
        val expires: String,
        val points: Int,
        val imageUrl: String
    )

    private val rewardsList = listOf(
        RewardItem(
            partner = "5 Coffee",
            title = "Cafea gratuită",
            expires = "Valabil până la 31.12.2024",
            points = 500,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuChcFDNwyOwZsH9RS2O8dyg66htwFMMobOawvWgFIc2R9mNVUL9Te-eeoYHZ8uK51my5wJw5V5ckOF0M4tK-Xubz92ZciJV5fLBod22fnShMunhuZi0yCuXdSxZ3MU6q8TEglnmmmBnYqcgtjH_NH-z8ZEhsjHCB3qzRvacyHv75q_cJ9GDw3J2WBV0akmOAU3nt5p9ay8bbrPASS9X02mXn5CzDBDDeaMpSrd8BOICAsRZ0Dc24Vj7BrQ86vyqDxu-iqX1Wug-dRw"
        ),
        RewardItem(
            partner = "The Barber Shop",
            title = "-20% la Tuns",
            expires = "Valabil până la 15.11.2024",
            points = 1000,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCpPTCsONvDUAxX-9dIqUNGFEthmymQlz3c3_TzVGrGqVcIJ2gtQb1BtrOUkKotMPa7lPYzm67AqWBVqboMFMrsxpAZeRpCgfUJkZSf-7_C1uXgivJLAu1C8dYPWh1vtKUfAyCzV2-BlZmMiCz7o76f-6u_WkCUdQlw-BVSBbRbGmf_KHj3dljhCqCbSCo_0RYxFRtoQ3ZntKS5lpWN0vTpMsAiC1uIxDTd4dczpal-fsNCxP2mqe5qEp6TshY-JBJMQ_5yu6J2IbA"
        ),
        RewardItem(
            partner = "Bio Market",
            title = "Reducere 15% la coș",
            expires = "Valabil până la 30.11.2024",
            points = 750,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBy8sTHJeiWZv_gPokPA1a-SPQPzIUf89QpZygAUidtvSvWWPhCyiCK89Z0pYUTchjbjLP45hcHjcXMp6FDxCvac42JCPNkI-ffgUbVHoy995isvslWl1SDY5g9YsrYQr8mxkpJ7_CTCjXnRW8XGOg1PV2LrG9_WHqJFTV1H5XDbqc_33XFpQFZ9jQNCMQw7dLzGE6qyaWBrmqshJ0GPXQ5vjxUj5ng-ck68PayS0SBl-1me5Q1JHFxO9LUTr3ODDbUiFYQKWBAEEM"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rewards_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rewardsContainer = view.findViewById(R.id.rewards_container)

        val btnBack = view.findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        populateRewards()
    }

    private fun populateRewards() {
        rewardsContainer.removeAllViews()

        for (reward in rewardsList) {
            val itemView = layoutInflater.inflate(R.layout.item_reward, rewardsContainer, false)

            val tvPartner = itemView.findViewById<TextView>(R.id.tv_reward_partner)
            val tvTitle = itemView.findViewById<TextView>(R.id.tv_reward_title)
            val tvExpires = itemView.findViewById<TextView>(R.id.tv_reward_expires)
            val tvPoints = itemView.findViewById<TextView>(R.id.tv_reward_points)
            val img = itemView.findViewById<ImageView>(R.id.reward_image)

            tvPartner.text = reward.partner
            tvTitle.text = reward.title
            tvExpires.text = reward.expires
            tvPoints.text = "${reward.points} Puncte"

            // Dacă vrei să încarci imagini din URL:
            // Poți folosi Glide:
            // Glide.with(this).load(reward.imageUrl).into(img)

            rewardsContainer.addView(itemView)

            itemView.setOnClickListener {
                // TODO: Ce se întâmplă la click pe reward
            }
        }
    }
}
