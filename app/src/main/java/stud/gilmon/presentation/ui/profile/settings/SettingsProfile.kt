package stud.gilmon.presentation.ui.profile.settings

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.presentation.components.ChangeEmailBottomSheet
import stud.gilmon.presentation.components.ChangePasswordBottomSheet
import stud.gilmon.presentation.components.ChangePhoneNumberBottomSheet
import stud.gilmon.presentation.components.ChooseFamilyStatusBottomSheet
import stud.gilmon.presentation.components.ChooseGenderBottomSheet
import stud.gilmon.presentation.components.CustomButton
import stud.gilmon.presentation.components.CustomTextField
import stud.gilmon.presentation.components.LabelText
import stud.gilmon.presentation.components.SelectButton
import stud.gilmon.presentation.theme.DatePickerGray
import stud.gilmon.presentation.theme.DatePickerLightGray
import stud.gilmon.presentation.ui.profile.TOP_BAR_HEIGHT
import stud.gilmon.presentation.ui.profile.TOP_NAVIGATION_BAR_HEICHT
import stud.gilmon.presentation.ui.profile.isScrolled
import timber.log.Timber
import java.time.LocalDate
import java.util.Locale


@Composable
fun SettingsProfile(
    darkTheme: Boolean,
    lazyListState: LazyListState,
    user: MutableState<UsersEntity>,
    toggleTheme: () -> Unit,
    viewModelFactory: ViewModelFactory,
    onClick: () -> Unit
) {
    val viewModel: SettingsViewModel = viewModel(factory = viewModelFactory)

    val topPadding by animateDpAsState(
        targetValue = if (lazyListState.isScrolled) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(durationMillis = 300)
    )
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = TOP_NAVIGATION_BAR_HEICHT,
                start = 15.dp,
                end = 15.dp,
                bottom = 75.dp
            )
            .navigationBarsPadding(),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Timber.tag("JC_TAG").d("screen")
        item {
            Divider(thickness = 1.dp, color = Color.White)
            PersonalData(
                viewModel = viewModel,
                user
            )
        }
        item {
            AccountSettings(
                viewModel = viewModel,
                user
            )
        }
        item {
            AdditionalSettings(darkTheme)
        }
        item {
            CustomButton(text = "switch theme", onClick = toggleTheme)
            CustomButton(text = "Log Out") {
                viewModel.setUser( onClick)
            }
        }
    }
}

@Composable
fun PersonalData(
    viewModel: SettingsViewModel,
    user: MutableState<UsersEntity>
) {
    val name = rememberSaveable { mutableStateOf(user.value.firstName) }
    val lastName = rememberSaveable { mutableStateOf(user.value.lastName) }
    val gender = rememberSaveable { mutableStateOf(user.value.gender) }
    //val dateOfBirth = rememberSaveable { mutableStateOf(user.age) }
    val familyStatus = rememberSaveable { mutableStateOf(user.value.familyStatus) }
    val aboutMe = rememberSaveable { mutableStateOf(user.value.aboutMe) }
    val showChooseGenderBottomSheet = rememberSaveable { mutableStateOf(false) }
    val showCooseFamilyStatusBottomSheet = rememberSaveable { mutableStateOf(false) }
    var date by remember {
        mutableStateOf(LocalDate.now())
    }
    val controller =  LocalSoftwareKeyboardController.current
    var focusedKey by remember { mutableStateOf(false) }
    val focusModifier = Modifier.onFocusChanged { //save updates after focus changes.
        if (it.isFocused) {
            focusedKey = true
        }
        if (!it.isFocused) {
            if (focusedKey) {
                val updUser = user.value.copy(
                    firstName = name.value,
                    lastName = lastName.value,
                    gender = gender.value,
                    age = date.toString(),
                    familyStatus = familyStatus.value,
                    aboutMe = aboutMe.value,
                )
                if(user.value !=updUser)
                {
                    user.value = updUser
                    viewModel.updateUserData(user.value)
                }
                focusedKey = false
            }
        }
    }
    val onDismiss = {
        viewModel.updateUserData(
            user.value.copy(
                firstName = name.value,
                lastName = lastName.value,
                gender = gender.value,
                age = date.toString(),
                familyStatus = familyStatus.value,
                aboutMe = aboutMe.value,)
        )
        controller?.hide()
    }
    val dateDialogState = rememberMaterialDialogState()
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(15.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LabelText(text = "Personal data")
        CustomTextField(
            modifier = Modifier.then(focusModifier),
            value = name.value, label = "Name"
        )
        {
            name.value = it
        }
        CustomTextField(
            modifier = Modifier.then(focusModifier),
            value = lastName.value, label = "Surname"
        )
        {
            lastName.value = it
        }
        CustomButton(text = date.toString()) {
            dateDialogState.show()
        }
        SelectButton(labelText = "Gender", text = gender.value)
        {
            showChooseGenderBottomSheet.value = !showChooseGenderBottomSheet.value
        }
        SelectButton(labelText = "Family status", text = familyStatus.value)
        {
            showCooseFamilyStatusBottomSheet.value = !showCooseFamilyStatusBottomSheet.value
        }
        CustomTextField(
            modifier = Modifier.then(focusModifier),
            value = aboutMe.value, label = "About me"
        ) {
            aboutMe.value = it
        }

    }

    MaterialDialog(
        dialogState = dateDialogState,
        autoDismiss = true,
        backgroundColor = DatePickerGray,
        buttons = {
            positiveButton(text = "Ok", textStyle = TextStyle(Color.LightGray))
            negativeButton(text = "Cancel", textStyle = TextStyle(Color.LightGray))
        },

        ) {
        this.datepicker(
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = DatePickerLightGray,
                dateActiveBackgroundColor = DatePickerLightGray,
                dateInactiveTextColor = Color.White,
                dateActiveTextColor = Color.LightGray,
                headerTextColor = Color.White,
                calendarHeaderTextColor = Color.White
            ),
            initialDate = date,
            locale = Locale.CANADA
        )
        {
            date = it
            viewModel.updateUserData(
                user.value.copy(
                    firstName = name.value,
                    lastName = lastName.value,
                    gender = gender.value,
                    age = date.toString(),
                    familyStatus = familyStatus.value,
                    aboutMe = aboutMe.value,
                )
            )
        }
    }

    ChooseGenderBottomSheet(showModalBottomSheet = showChooseGenderBottomSheet, option = gender) {
        showChooseGenderBottomSheet.value = false
       onDismiss()
    }
    ChooseFamilyStatusBottomSheet(
        showModalBottomSheet = showCooseFamilyStatusBottomSheet,
        option = familyStatus,
    ) {
        showCooseFamilyStatusBottomSheet.value = false
        onDismiss()
    }
}

@Composable
fun AccountSettings(
    viewModel: SettingsViewModel,
    user: MutableState<UsersEntity>
) {
    val showChangeEmailBottomSheet = rememberSaveable { mutableStateOf(false) }
    val mail = rememberSaveable { mutableStateOf(user.value.mail) }
    val showChangePhoneNumberBottomSheet = rememberSaveable { mutableStateOf(false) }
    val number = rememberSaveable { mutableStateOf(user.value.number) }
    val showChangePasswordBottomSheet = rememberSaveable { mutableStateOf(false) }
    val password = rememberSaveable { mutableStateOf(user.value.password) }
    val controller =  LocalSoftwareKeyboardController.current
    val onDismiss = {
        viewModel.updateUserData(
            user.value.copy(
                mail=mail.value,
                number= number.value,
                password = password.value,
            )
        )
       controller?.hide()
    }
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(15.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LabelText(text = "Label")
        Timber.tag("JC_TAG").d("account")
        SelectButton(labelText = "E-mail", text = mail.value, underline = true) {
            showChangeEmailBottomSheet.value = !showChangeEmailBottomSheet.value
        }
        SelectButton(labelText = "Phone number", text = number.value, underline = true)
        {
            showChangePhoneNumberBottomSheet.value = !showChangePhoneNumberBottomSheet.value
        }
        SelectButton(labelText = "Password", text = password.value, underline = true){
            showChangePasswordBottomSheet.value=!showChangePasswordBottomSheet.value
        }
        SelectButton(labelText = "label", text = "text", underline = true)

        Row(Modifier.padding(horizontal = 30.dp)) {
            Column(Modifier.weight(1f)) {
                Column() {
                    Text(
                        text = "label",
                        color = Color.LightGray,
                    )
                    Text(
                        text = "text",
                        color = Color.White,
                    )
                }
            }
            Switch(checked = true,
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = Color.Transparent,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = MaterialTheme.colorScheme.background,
                    checkedTrackColor = MaterialTheme.colorScheme.tertiary
                ),
                onCheckedChange = { TODO() })
        }
    }
    ChangeEmailBottomSheet(
        showModalBottomSheet = showChangeEmailBottomSheet,
        option = mail
    ) {
        showChangeEmailBottomSheet.value = false
        onDismiss()
    }
    ChangePhoneNumberBottomSheet(
        showModalBottomSheet = showChangePhoneNumberBottomSheet,
        option = number
    ) {
        showChangePhoneNumberBottomSheet.value = false
        onDismiss()
    }
    ChangePasswordBottomSheet(showModalBottomSheet = showChangePasswordBottomSheet, option = password) {
        showChangePasswordBottomSheet.value=false
        onDismiss()
    }
}

@Composable
fun AdditionalSettings(darkTheme: Boolean) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(15.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Timber.d("additional")
        LabelText(text = "Label")
        SelectButton(labelText = "DARK THEME", text = darkTheme.toString(), underline = true) {
            coroutineScope.launch {

            }
        }
        SelectButton(labelText = "DARK THEME", text = "text", underline = true)
    }
}


