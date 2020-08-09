package ru.kapitoxa.mywallet.operationdetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import ru.kapitoxa.mywallet.R
import ru.kapitoxa.mywallet.databinding.FragmentOperationDetailBinding


/**
 * A simple [Fragment] subclass.
 */
class OperationDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOperationDetailBinding.inflate(inflater)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_opetation_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}