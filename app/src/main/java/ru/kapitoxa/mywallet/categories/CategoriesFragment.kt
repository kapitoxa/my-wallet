package ru.kapitoxa.mywallet.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.kapitoxa.mywallet.ExtendedFabOnScrollListener
import ru.kapitoxa.mywallet.database.WalletDatabase
import ru.kapitoxa.mywallet.databinding.FragmentCategoriesBinding


/**
 * A simple [Fragment] subclass.
 */
class CategoriesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentCategoriesBinding.inflate(inflater)
        val application = requireActivity().application
        val dataSource = WalletDatabase.getInstance(application).walletDatabaseDao
        val viewModelFactory = CategoriesViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)
                .get(CategoriesViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = CategoriesAdapter(CategoriesListener {
            viewModel.onCategoryClicked(it)
        })
        binding.categoryList.adapter = adapter
        binding.categoryList.addOnScrollListener(ExtendedFabOnScrollListener(binding.fab))

        viewModel.categories.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToCategoryDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                        CategoriesFragmentDirections
                                .actionCategoriesFragmentToCategoryDetailFragment(it))
                viewModel.onNavigatedToCategoryDetail()
            }
        })

        return binding.root
    }
}