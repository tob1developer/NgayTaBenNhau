package com.kietngo.ngaytabennhau.ui.fragment.quote

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
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.databinding.FragmentQuoteBinding
import com.kietngo.ngaytabennhau.repository.EventObserver
import com.kietngo.ngaytabennhau.repository.model.Quote
import com.kietngo.ngaytabennhau.ui.fragment.shareviewModel.ShareHomeQuoteViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class QuoteFragment : Fragment() {
    private lateinit var binding : FragmentQuoteBinding
    private val shareViewModel : ShareHomeQuoteViewModel by activityViewModels()
    private val viewModel : QuoteViewModel by viewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return QuoteViewModel(requireContext()) as T
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
            Timber.d("back press Quote to Home")
        }

        val currentDate : Date = Calendar.getInstance().time
        val dateString = SimpleDateFormat("dd/MM/yyyy").format(currentDate)
        binding.textViewDateCurrent.text = dateString

        //get id to share viewModel quote
        shareViewModel.numberIdQuote.observe(viewLifecycleOwner, {
            viewModel.randomQuoteWithInt(it)
        })

        val quote = Observer<Quote>{
            if(it != null)
                binding.textViewQuote.text = it.content
        }

        viewModel.randomQuote.observe(viewLifecycleOwner, quote)

        viewModel.btnRandomQuote.observe(viewLifecycleOwner, {btn ->
            binding.btnRandomQuote.setOnClickListener {
                btn.onClick()
                Timber.d("random quote :)")
            }
        })

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
        })

        viewModel.idInShareViewModel.observe(viewLifecycleOwner,{
            shareViewModel.numberIdQuote.postValue(it)
        })
    }
}