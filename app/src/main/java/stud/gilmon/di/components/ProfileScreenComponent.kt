package stud.gilmon.di.components

import stud.gilmon.di.viewModelFactory.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent
import stud.gilmon.di.modules.ProfileViewModelModule

@Subcomponent(
    modules = [ProfileViewModelModule::class]
)
interface ProfileScreenComponent {

    fun getViewModelFactory(): ViewModelFactory
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance userId:String
        ) :ProfileScreenComponent
    }
}