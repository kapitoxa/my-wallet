package ru.kapitoxa.mywallet.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.kapitoxa.mywallet.databinding.FragmentCategoriesBinding


/**
 * A simple [Fragment] subclass.
 */
class CategoriesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentCategoriesBinding.inflate(inflater)

        val viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToCategoryDetail.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController().navigate(
                        CategoriesFragmentDirections.actionCategoriesFragmentToCategoryDetailFragment())
                viewModel.onNavigatedToCategoryDetail()
            }
        })

        return binding.root
    }


}