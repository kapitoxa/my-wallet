package ru.kapitoxa.mywallet

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ExtendedFabOnScrollListener(private val fab: ExtendedFloatingActionButton)
    : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0 && fab.isExtended) {
            fab.shrink()
        }
        super.onScrolled(recyclerView, dx, dy)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE
                && !fab.isExtended
                && recyclerView.computeVerticalScrollOffset() == 0) {
            fab.extend()
        }
        super.onScrollStateChanged(recyclerView, newState)
    }
}