package ru.kapitoxa.mywallet.categorydetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import ru.kapitoxa.mywallet.R
import ru.kapitoxa.mywallet.databinding.FragmentCategoryDetailBinding


/**
 * A simple [Fragment] subclass.
 */
class CategoryDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentCategoryDetailBinding.inflate(inflater)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_category_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}