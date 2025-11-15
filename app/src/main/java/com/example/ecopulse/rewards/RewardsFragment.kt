package com.example.ecopulse.rewards

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.ecopulse.R

class RewardsFragment : Fragment() {

    private lateinit var foodContainer: LinearLayout
    private lateinit var servicesContainer: LinearLayout
    private lateinit var discountsContainer: LinearLayout

    data class RewardItem(
        val partner: String,
        val title: String,
        val description: String,
        val expires: String,
        val points: Int,
        val imageUrl: String
    )

    // -------------------------------------------------------
    // LISTE
    // -------------------------------------------------------

    private val foodRewards = listOf(
        RewardItem("Green Eat", "Salată detox",
            "O salată fresh, ideală pentru energie rapidă.", "Expiră în 7 zile", 300, "drawable://food_salad"),

        RewardItem("Smoothie Hub", "Smoothie fresh",
            "Smoothie natural din fructe proaspete.", "Expiră în 4 zile", 250, "drawable://food_smoothie"),

        RewardItem("Urban Grill", "Burger vegetarian",
            "Burger vegan cu chiflă integrală.", "Expiră în 10 zile", 450, "drawable://food_burger"),

        RewardItem("Italiano", "Pizza Margherita",
            "Pizza clasică, 100% ingrediente bio.", "Expiră în 3 zile", 600, "drawable://food_pizza"),

        RewardItem("Wrap&Go", "Wrap proteic",
            "Wrap cu pui, hummus și legume fresh.", "Expiră în 5 zile", 350, "drawable://food_wrap")
    )

    private val servicesRewards = listOf(
        RewardItem(
            "The Barber Shop", "Tuns + Styling",
            "Serviciu complet de grooming masculin.",
            "Expiră în 14 zile", 1000,
            "drawable://service_barber"
        ),
        RewardItem(
            "BodyFit", "Antrenament personal",
            "Sesiune 1-1 cu antrenor profesionist.",
            "Expiră în 5 zile", 800,
            "drawable://service_gym"
        ),
        RewardItem(
            "Relax Spa", "Masaj de relaxare",
            "Ședință de 40 minute pentru relaxare completă.",
            "Expiră în 12 zile", 1200,
            "drawable://service_spa"
        ),
        RewardItem(
            "CleanHouse", "Curățenie la domiciliu",
            "2 ore de curățenie profesională.",
            "Expiră în 8 zile", 500,
            "drawable://service_clean"
        ),
        RewardItem(
            "AutoWash", "Spălare auto completă",
            "Interior + exterior realizate profesionist.",
            "Expiră în 3 zile", 400,
            "drawable://service_carwash"
        )
    )


    private val discountRewards = listOf(
        RewardItem(
            "BookLand", "-30% la cărți",
            "Aplicabil la orice carte disponibilă.",
            "Expiră în 12 zile", 600,
            "drawable://discount_books"
        ),
        RewardItem(
            "FashionUp", "-20% la îmbrăcăminte",
            "Promoție specială doar în EcoPulse.",
            "Expiră în 7 zile", 900,
            "drawable://discount_clothes"
        ),
        RewardItem(
            "Bio Market", "-15% la alimente bio",
            "Reducere aplicată automat la casă.",
            "Expiră în 30 zile", 750,
            "drawable://discount_food"
        ),
        RewardItem(
            "MegaStore", "-25% la electrocasnice",
            "Reducere valabilă pentru produsele selectate.",
            "Expiră în 3 zile", 1500,
            "drawable://discount_home"
        ),
        RewardItem(
            "SportLife", "-10% la echipamente sportive",
            "Valabil pentru toate produsele sport.",
            "Expiră în 10 zile", 500,
            "drawable://discount_sport"
        )
    )


    // -------------------------------------------------------
    // LIFECYCLE
    // -------------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.rewards_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodContainer = view.findViewById(R.id.container_food)
        servicesContainer = view.findViewById(R.id.container_services)
        discountsContainer = view.findViewById(R.id.container_discounts)

        view.findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        addRewards(foodContainer, foodRewards)
        addRewards(servicesContainer, servicesRewards)
        addRewards(discountsContainer, discountRewards)
    }

    // -------------------------------------------------------
    // ADĂUGARE CARDURI
    // -------------------------------------------------------

    private fun addRewards(container: LinearLayout, list: List<RewardItem>) {
        container.removeAllViews()

        for (reward in list) {
            val item = layoutInflater.inflate(R.layout.item_reward, container, false)

            val tvPartner = item.findViewById<TextView>(R.id.tv_reward_partner)
            val tvTitle = item.findViewById<TextView>(R.id.tv_reward_title)
            val tvExpires = item.findViewById<TextView>(R.id.tv_reward_expires)
            val tvPoints = item.findViewById<TextView>(R.id.tv_reward_points)
            val img = item.findViewById<ImageView>(R.id.reward_image)

            tvPartner.text = reward.partner
            tvTitle.text = reward.title
            tvExpires.text = reward.expires
            tvPoints.text = "${reward.points} Puncte"

            if (reward.imageUrl.startsWith("drawable://")) {
                val name = reward.imageUrl.removePrefix("drawable://")
                val id = resources.getIdentifier(name, "drawable", requireContext().packageName)
                img.setImageResource(id)
            } else {
                Glide.with(this).load(reward.imageUrl).into(img)
            }

            item.setOnClickListener {
                val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.profile_pulse)
                item.startAnimation(anim)
                openDetailsScreen(reward)   // 🔥 AICI ESTE FIXUL
            }

            container.addView(item)
        }
    }

    // -------------------------------------------------------
    // POPUP DETALII - VARIANTA FINALĂ
    // -------------------------------------------------------

    private fun openDetailsScreen(reward: RewardItem) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.reward_details)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val img = dialog.findViewById<ImageView>(R.id.details_image)
        val title = dialog.findViewById<TextView>(R.id.details_title)
        val partner = dialog.findViewById<TextView>(R.id.details_partner)
        val expires = dialog.findViewById<TextView>(R.id.details_expires)
        val points = dialog.findViewById<TextView>(R.id.details_points)
        val desc = dialog.findViewById<TextView>(R.id.details_description)

        title.text = reward.title
        partner.text = reward.partner
        expires.text = reward.expires
        points.text = "${reward.points} puncte"
        desc.text = reward.description

        if (reward.imageUrl.startsWith("drawable://")) {
            val name = reward.imageUrl.removePrefix("drawable://")
            val id = resources.getIdentifier(name, "drawable", requireContext().packageName)
            img.setImageResource(id)
        } else {
            Glide.with(requireContext()).load(reward.imageUrl).into(img)
        }

        dialog.show()
    }
}
