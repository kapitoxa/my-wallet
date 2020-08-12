package ru.kapitoxa.mywallet.categorydetail

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import ru.kapitoxa.mywallet.R
import ru.kapitoxa.mywallet.database.CategoryType
import ru.kapitoxa.mywallet.database.WalletDatabase
import ru.kapitoxa.mywallet.databinding.FragmentCategoryDetailBinding


/**
 * A simple [Fragment] subclass.
 */
class CategoryDetailFragment : Fragment() {
    private lateinit var viewModel: CategoryDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentCategoryDetailBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val arguments = CategoryDetailFragmentArgs.fromBundle(requireArguments())
        val category = arguments.category
        val dataSource = WalletDatabase.getInstance(application).walletDatabaseDao
        val viewModelFactory = CategoryDetailViewModelFactory(category, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(CategoryDetailViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.titleText.text = getString(
                when (category.id) {
                    0L -> R.string.category_create_title
                    else -> R.string.category_edit_title
                }
        )

        viewModel.types.observe(viewLifecycleOwner, object : Observer<List<CategoryType>> {
            override fun onChanged(data: List<CategoryType>?) {
                data ?: return

                val chipGroup = binding.typeChipGroup
                val chipInflater = LayoutInflater.from(chipGroup.context)

                val children = data.map { type ->
                    val chip = chipInflater.inflate(R.layout.chip, chipGroup, false) as Chip
                    chip.text = type.name
                    chip.tag = type.id
                    chip.setOnCheckedChangeListener { button, isChecked ->
                        viewModel.onTypeChanged(button.tag as Long, isChecked)
                    }
                    chip
                }

                chipGroup.removeAllViews()

                for (chip in children) {
                    chipGroup.addView(chip)
                }

                val firstChild = chipGroup.getChildAt(0) as Chip
                firstChild.isChecked = true
            }
        })

        viewModel.showCategoryNameFieldError.observe(viewLifecycleOwner, Observer { show ->
            if (show) {
                binding.categoryNameLayout.error = getString(R.string.input_field_is_not_filled)
            } else {
                binding.categoryNameLayout.error = null
            }
        })

        viewModel.navigateToCategories.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController().navigate(CategoryDetailFragmentDirections
                        .actionCategoryDetailFragmentToCategoriesFragment())
                viewModel.onNavigatedToCategories()

                //Hide the keyboard
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.categoryName.windowToken, 0)
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_category_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_category) {
            viewModel.onSave()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}