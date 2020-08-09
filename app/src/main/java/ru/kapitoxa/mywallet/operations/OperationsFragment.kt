package ru.kapitoxa.mywallet.operations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.kapitoxa.mywallet.databinding.FragmentOperationsBinding

/**
 * A simple [Fragment] subclass.
 */
class OperationsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOperationsBinding.inflate(inflater)

        val viewModel = ViewModelProvider(this).get(OperationsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToOperationDetail.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController().navigate(
                        OperationsFragmentDirections.actionOperationsFragmentToDetailFragment())
                viewModel.onNavigatedToOperationDetail()
            }
        })

        return binding.root
    }

}