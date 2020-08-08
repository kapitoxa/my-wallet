package ru.kapitoxa.mywallet.operations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kapitoxa.mywallet.databinding.FragmentOperationsBinding

/**
 * A simple [Fragment] subclass.
 */
class OperationsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOperationsBinding.inflate(inflater)
        return binding.root
    }

}