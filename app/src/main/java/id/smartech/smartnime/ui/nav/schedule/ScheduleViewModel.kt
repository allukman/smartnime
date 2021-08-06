package id.smartech.smartnime.ui.nav.schedule

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import id.smartech.smartnime.services.ApiServices
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class ScheduleViewModel: ViewModel(), CoroutineScope {

    private lateinit var services: ApiServices
    
    override val coroutineContext: CoroutineContext
        get() = TODO("Not yet implemented")
}