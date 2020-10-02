package com.kietngo.ngaytabennhau.ui.fragment.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.FragmentHomeBinding
import com.kietngo.ngaytabennhau.repository.*
import com.kietngo.ngaytabennhau.repository.model.Quote
import com.kietngo.ngaytabennhau.ui.fragment.shareviewModel.ShareHomeQuoteViewModel
import timber.log.Timber

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private val shareViewModel : ShareHomeQuoteViewModel by activityViewModels()
    private val viewModel : HomeViewModel by activityViewModels(){
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(requireContext()) as T
            }
        }
    }

    lateinit var clickToUser1 : () -> Unit
    lateinit var clickToUser2 : () -> Unit

    //Default ID
    private var idInProfileDialogFragment = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.btnQuoteFragment.observe(viewLifecycleOwner, {btn ->
            binding.cardViewQuote.setOnClickListener{ btn.onClick() }
        })

        viewModel.btnDateFragment.observe(viewLifecycleOwner,{btn ->
            binding.cardViewDate.setOnClickListener{ btn.onClick() }
        })

        viewModel.btnSettingFragment.observe(viewLifecycleOwner, {btn ->
            binding.btnSetting.setOnClickListener { btn.onClick() }
        })

        viewModel.btnProfileDialogFragment.observe(viewLifecycleOwner, {btn->
            binding.avatarPerson1.setOnClickListener {
                btn.onClick()
                clickToUser1()
                idInProfileDialogFragment = ID_USER_1
            }

            binding.avatarPerson2.setOnClickListener {
                btn.onClick()
                clickToUser2()
                idInProfileDialogFragment = ID_USER_2
            }
        })

        viewModel.btnGetColor.observe(viewLifecycleOwner, {btn ->
            binding.btnFavorite.setOnClickListener { btn.onClick() }
        })


        viewModel.navigateDateFragment.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(it)
            Timber.d("Navigate Date Fragment")
        })

        viewModel.navigateQuoteFragment.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(it)
            Timber.d("Navigate Quote Fragment")
        })

        viewModel.navigateSettingFragment.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(it)
            Timber.d("Navigate Setting Fragment")
        })

        viewModel.navigateProfileDialogFragment.observe(viewLifecycleOwner, EventObserver{
                findNavController().navigate(it)
            Timber.d("Navigate Profile Dialog Fragment")
        })

        viewModel.navigateColorDialogFragment.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(it)
            Timber.d("navigate Color Dialog Fragment")
        })

        viewModel.user1.observe(viewLifecycleOwner, { user ->
            binding.avatarPerson1.setImageBitmap(user.user.avatar)
            binding.avatarPerson1.borderColor = Color.parseColor(user.user.borderColor)
            binding.textViewPerson1.text = " ${user.user.nickName} "
            setGenderWithUser(user.user.gender, binding.btnGender1)
            clickToUser1 = {
                user.onClick()
                Timber.d("Profile User1")
            }
        })

        viewModel.user2.observe(viewLifecycleOwner, {user ->
            binding.avatarPerson2.setImageBitmap(user.user.avatar)
            binding.avatarPerson2.borderColor = Color.parseColor(user.user.borderColor)
            binding.textViewPerson2.text = " ${user.user.nickName} "
            setGenderWithUser(user.user.gender, binding.btnGender2)
            clickToUser2 = {
                user.onClick()
                Timber.d("Profile User2")
            }
        })

        // color Heart dialog
        val colorToHeart = Observer<String>{
            binding.btnFavorite.setColorFilter(Color.parseColor(it))
        }

        viewModel.setColorHeartView.observe(viewLifecycleOwner, colorToHeart)

        val quote = Observer<Quote> {
            binding.textViewQuote.text = it.content
        }
        viewModel.quoteInHomeFragment.observe(viewLifecycleOwner, quote)

        //get id to the quote
        shareViewModel.numberIdQuote.observe(viewLifecycleOwner, {
            viewModel.randomQuoteWithInt(it)
        })

        //bo sung
        val topTitle = Observer<String> {
            binding.tvTopTitle.text = it
        }
        val bottomTitle = Observer<String> {
            binding.bottomTitle.text = it
        }
        val dayTogether = Observer<String> {
            binding.tvDayTogether.text = it
        }

        viewModel.topTitle.observe(viewLifecycleOwner,topTitle)
        viewModel.bottomTitle.observe(viewLifecycleOwner,bottomTitle)
        viewModel.dayTogether.observe(viewLifecycleOwner,dayTogether)
    }
    private fun setGenderWithUser(code: Int, btn: ImageButton){
        val imageMale = R.drawable.ic_male_black
        val imageFemale = R.drawable.ic_female_black
        val imageGay = R.drawable.ic_gay_black
        val imageLes = R.drawable.ic_les_black
        val imagePrivateGender = R.drawable.ic_color_white

        when(code){
            GENDER_MALE ->      btn.setImageResource(imageMale)
            GENDER_FEMALE ->    btn.setImageResource(imageFemale)
            GENDER_GAY ->       btn.setImageResource(imageGay)
            GENDER_LESS ->      btn.setImageResource(imageLes)
            else ->             btn.setImageResource(imagePrivateGender)
        }
    }
}