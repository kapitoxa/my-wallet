package ru.kapitoxa.mywallet.operationdetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import ru.kapitoxa.mywallet.R
import ru.kapitoxa.mywallet.database.CategoryWithType
import ru.kapitoxa.mywallet.database.Operation
import ru.kapitoxa.mywallet.database.WalletDatabase
import ru.kapitoxa.mywallet.databinding.FragmentOperationDetailBinding


/**
 * A simple [Fragment] subclass.
 */
class OperationDetailFragment : Fragment() {

    private lateinit var binding: FragmentOperationDetailBinding

    private lateinit var viewModel: OperationDetailViewModel

    private lateinit var operation: Operation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentOperationDetailBinding.inflate(inflater)
        operation = Operation()

        val application = requireActivity().application
        val dataSource = WalletDatabase.getInstance(application).walletDatabaseDao
        val viewModelFactory = OperationDetailViewModelFactory(operation, dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(OperationDetailViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupObservers()

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_opetation_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupObservers() {
        viewModel.categories.observe(viewLifecycleOwner, object : Observer<List<CategoryWithType>> {
            override fun onChanged(data: List<CategoryWithType>?) {
                data ?: return

                val chipGroup = binding.categoryChipGroup
                val chipInflater = LayoutInflater.from(chipGroup.context)

                val children = data.map {
                    val chip = chipInflater.inflate(R.layout.chip, chipGroup, false) as Chip
                    chip.text = it.category.name
                    chip.tag = it.category.id
                    chip
                }

                chipGroup.removeAllViews()

                for (chip in children) {
                    chipGroup.addView(chip)
                }

                val checkedChipIndex = when (operation.id) {
                    0L -> 0
                    else -> operation.categoryId.toInt() - 1
                }

                val checkedChip = chipGroup.getChildAt(checkedChipIndex) as Chip
                checkedChip.isChecked = true
            }
        })
    }
}