package com.kietngo.ngaytabennhau.ui.fragment.quote

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.databinding.FragmentQuoteBinding
import com.kietngo.ngaytabennhau.repository.EventObserver
import com.kietngo.ngaytabennhau.repository.model.Quote
import com.kietngo.ngaytabennhau.ui.fragment.home.HomeViewModel
import com.kietngo.ngaytabennhau.ui.fragment.shareviewModel.ShareHomeQuoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class QuoteFragment : Fragment() {
    private lateinit var binding : FragmentQuoteBinding
    private val shareViewModel : ShareHomeQuoteViewModel by activityViewModels()
    private val viewModel : QuoteViewModel by activityViewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.application?.let { QuoteViewModel(it) } as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuoteBinding.inflate(inflater, container, false)
        return binding.root
    }
    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
            Timber.d("back press Quote to Home")
        }

        // TODO: Show date today
        val currentDate : Date = Calendar.getInstance().time
        val dateString = SimpleDateFormat("dd/MM/yyyy").format(currentDate)
        binding.textViewDateCurrent.text = dateString

        // TODO: show Quote
        viewModel.randomQuote.observe(viewLifecycleOwner, { quote ->
            if(quote != null){
                binding.textViewQuote.text = quote.content
            }
        })

        //TODO: btn random quote
        viewModel.btnRandomQuote.observe(viewLifecycleOwner, {btn ->
            binding.btnRandomQuote.setOnClickListener {
                btn.onClick()
                Timber.d("random quote :)")
            }
        })

        // TODO: Load quote with id
        shareViewModel.numberIdQuote.observe(viewLifecycleOwner, {
            viewModel.randomQuoteWithInt(it)
        })

        //TODO: Post Id is random
        viewModel.idInShareViewModel.observe(viewLifecycleOwner,{
            shareViewModel.numberIdQuote.postValue(it)
        })


        //TODO: btn Shre quote
        viewModel.btnShareQuote.observe(viewLifecycleOwner,{ btn ->
            binding.btnShare.setOnClickListener {
                btn.onClick()
            }
        })

        viewModel.contentQuote.observe(viewLifecycleOwner, EventObserver{
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,it)
                type = "text/plan"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            Timber.d("go share")
        })


    }
}