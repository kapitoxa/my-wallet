package ru.kapitoxa.mywallet.operations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.kapitoxa.mywallet.ExtendedFabOnScrollListener
import ru.kapitoxa.mywallet.database.WalletDatabase
import ru.kapitoxa.mywallet.databinding.FragmentOperationsBinding

/**
 * A simple [Fragment] subclass.
 */
class OperationsFragment : Fragment() {

    private lateinit var viewModel: OperationsViewModel

    private lateinit var adapter: OperationsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOperationsBinding.inflate(inflater)

        val application = requireActivity().application
        val database = WalletDatabase.getInstance(application).walletDatabaseDao
        val viewModelFactory = OperationsViewModelFactory(database)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(OperationsViewModel::class.java)

        adapter = OperationsAdapter(OperationsListener {
            viewModel.onOperationClicked(it)
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.operationsList.adapter = adapter
        binding.operationsList.addOnScrollListener(ExtendedFabOnScrollListener(binding.fab))

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.navigateToOperationDetail.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(
                        OperationsFragmentDirections.actionOperationsFragmentToDetailFragment(it))
                viewModel.onNavigatedToOperationDetail()
            }
        })

        viewModel.operations.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
    }
}
