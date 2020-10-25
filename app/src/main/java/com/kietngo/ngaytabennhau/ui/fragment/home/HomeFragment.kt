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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.FragmentHomeBinding
import com.kietngo.ngaytabennhau.repository.*
import com.kietngo.ngaytabennhau.repository.model.Quote
import com.kietngo.ngaytabennhau.ui.fragment.quote.QuoteViewModel
import com.kietngo.ngaytabennhau.ui.fragment.shareviewModel.ShareHomeQuoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private val shareViewModel : ShareHomeQuoteViewModel by activityViewModels()
    private val viewModel : HomeViewModel by activityViewModels(){
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.application?.let { HomeViewModel(it) } as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //TODO: Navigate to Quote Fragment
        viewModel.btnQuoteFragment.observe(viewLifecycleOwner, {btn ->
            binding.cardViewQuote.setOnClickListener{ btn.onClick() }
        })
        viewModel.navigateQuoteFragment.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(it)
            Timber.d("Navigate Quote Fragment")
        })

        //TODO: Navigate to Option Fragment
        viewModel.btnOptionDialog.observe(viewLifecycleOwner, {btn ->
            binding.cardViewDate.setOnClickListener {
                btn.onClick()
            }
        })

        viewModel.navigateOptionDialog.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(it)
        })

        //TODO: Navigate to Setting
        viewModel.btnSettingFragment.observe(viewLifecycleOwner, {btn ->
            binding.btnSetting.setOnClickListener { btn.onClick() }
        })
        
        viewModel.navigateSettingFragment.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(it)
            Timber.d("Navigate Setting Fragment")
        })


        //TODO: go to select color of Heart
        viewModel.btnGetColor.observe(viewLifecycleOwner, {btn ->
            binding.btnFavorite.setOnClickListener { btn.onClick() }
        })

        viewModel.navigateColorDialogFragment.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(it)
            Timber.d("navigate Color Dialog Fragment")
        })

        //TODO: Set love date
        viewModel.loveDate.observe(viewLifecycleOwner, {loveDate ->
            if(loveDate != null){
                val colorSet = Color.parseColor(loveDate.loveColor)
                binding.btnFavorite.setColorFilter(colorSet)

                binding.tvTopTitle.text = loveDate.topTitle
                binding.bottomTitle.text = loveDate.bottomTitle
            }

        })

        //TODO:  navigate to Profile Fragment
        viewModel.btnProfileDialogFragmentUser1.observe(viewLifecycleOwner, {btn ->
            binding.avatarPerson1.setOnClickListener {
                btn.onClick()
            }
        })

        viewModel.btnProfileDialogFragmentUser2.observe(viewLifecycleOwner, {btn ->
            binding.avatarPerson2.setOnClickListener {
                btn.onClick()
            }
        })

        viewModel.navigateProfileDialogFragment.observe(viewLifecycleOwner, EventObserver{
            val action = HomeFragmentDirections.actionHomeFragmentToProfileDialogFragment(it)
            findNavController().navigate(action)
            Timber.d("Navigate Profile Dialog Fragment")
        })


        // TODO: load user to view
        viewModel.user1.observe(viewLifecycleOwner, { user ->
           if(user != null){
                binding.avatarPerson1.setImageBitmap(user.avatar)
                binding.avatarPerson1.borderColor = Color.parseColor(user.borderColor)
                binding.textViewPerson1.text = " ${user.nickName} "
                setGenderWithUser(user.gender, binding.btnGender1)
            }
       })

        viewModel.user2.observe(viewLifecycleOwner, {user ->
            if(user != null) {
                binding.avatarPerson2.setImageBitmap(user.avatar)
                binding.avatarPerson2.borderColor = Color.parseColor(user.borderColor)
                binding.textViewPerson2.text = " ${user.nickName} "
                setGenderWithUser(user.gender, binding.btnGender2)
            }
          })

        //TODO: get id to the quote
        shareViewModel.numberIdQuote.observe(viewLifecycleOwner, {
            viewModel.randomQuoteWithInt(it)
        })

        //TODO: set quote
        viewModel.quoteInHomeFragment.observe(viewLifecycleOwner,{quote ->
            if (quote !=null){
                binding.textViewQuote.text = quote.content
            }
        })

//
//        //bo sung
//        val topTitle = Observer<String> {
//            binding.tvTopTitle.text = it
//        }
//        val bottomTitle = Observer<String> {
//            binding.bottomTitle.text = it
//        }
//        val dayTogether = Observer<String> {
//            binding.tvDayTogether.text = it
//        }
//
//        viewModel.topTitle.observe(viewLifecycleOwner,topTitle)
//        viewModel.bottomTitle.observe(viewLifecycleOwner,bottomTitle)
//        viewModel.dayTogether.observe(viewLifecycleOwner,dayTogether)


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