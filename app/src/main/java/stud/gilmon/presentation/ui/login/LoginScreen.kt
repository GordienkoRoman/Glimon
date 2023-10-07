package stud.gilmon.presentation.ui.login

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import stud.gilmon.base.utils.launchAndCollectIn
import stud.gilmon.data.oauth.GithubAuthConfig
import stud.gilmon.data.oauth.MailAuthConfig
import stud.gilmon.presentation.components.CustomText
import stud.gilmon.presentation.components.CustomTextField
import stud.gilmon.presentation.components.LabelText
import stud.gilmon.presentation.components.SocialNetworkIcon
import stud.gilmon.presentation.theme.OrangeOdnoklassniki
import stud.gilmon.presentation.theme.YellowButton
import stud.gilmon.presentation.ui.main.Graph
import stud.gilmon.presentation.ui.profile.coupons.CouponsViewModel
import javax.inject.Scope

@Composable
fun LoginScreen(navController: NavHostController,viewModelFactory: ViewModelFactory,onClose:()-> Unit) {
    val scope = rememberCoroutineScope()
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    val viewModel: LoginViewModel = viewModel(factory = viewModelFactory)
    val getAuthResponse = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(), onResult = {
            if (it.data != null) {
                val dataIntent = it.data!!
                handleAuthResponseIntent(dataIntent, viewModel = viewModel)
            }
        })

    SideEffect {
        viewModel.openAuthPageFlow.launchAndCollectIn(lifecycleOwner.value) {
            getAuthResponse.launch(it)
        }
        viewModel.authSuccessFlow.launchAndCollectIn(lifecycleOwner.value) {
            //viewModel.setUser(viewModel.config.login)
            viewModel.loadUserInfo(viewModel.config.login)

            navController.navigate(Graph.PROFILE_GRAPH+"/"+viewModel.config.login)
            onClose()
        }
        viewModel.remoteUserGithubInfoFlow.launchAndCollectIn(lifecycleOwner.value){
            val a = it

        }
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                LabelText(
                    text = "Authorization",
                    modifier = Modifier
                )
            }

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxWidth()
            )
            {
                CloseButton(scope,onClose)
            }
        }
        CustomText(
            text = "in the discount service with free coupons\n " +
                    "and promo codes with 100% discount"
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .size(1.dp)
        )
        CustomTextField(value = "", label = "email")

        Button(
            onClick = {

                viewModel.openLoginPage()
                      },
            colors = ButtonDefaults.buttonColors(
                containerColor = YellowButton
            ),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()


            //modifier = Modifier.clip(RoundedCornerShape(1.dp))
        ) {

            Text(
                text = "Login",
                color = Color.Black,
                modifier = Modifier.padding(10.dp)
            )
        }
        CustomText(text = "Or by Social Media")
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SocialNetworkIcon(color = OrangeOdnoklassniki){
                viewModel.config = GithubAuthConfig
               viewModel.openLoginPage()
            }
            SocialNetworkIcon(color = OrangeOdnoklassniki)
            {
                viewModel.config = MailAuthConfig
                viewModel.openLoginPage()
            }
            SocialNetworkIcon(color = OrangeOdnoklassniki)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SocialNetworkIcon(color = OrangeOdnoklassniki)
            SocialNetworkIcon(color = OrangeOdnoklassniki)
        }
    }

}

fun handleAuthResponseIntent(intent: Intent, viewModel: LoginViewModel) {
    // пытаемся получить ошибку из ответа. null - если все ок
    val exception = AuthorizationException.fromIntent(intent)
    // пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
    val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
        ?.createTokenExchangeRequest()
    when {
        // авторизация завершались ошибкой
        exception != null -> viewModel.onAuthCodeFailed(exception)
        // авторизация прошла успешно, меняем код на токен
        tokenExchangeRequest != null ->
            viewModel.onAuthCodeReceived(tokenExchangeRequest)
    }
}

@Composable
fun CloseButton(scope: CoroutineScope,onClose: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .shadow(8.dp, shape = CircleShape)
            .size(36.dp)
            .clip(CircleShape)
            .clickable {
                onClose()
            }
            .background(Color.Gray)
    ) {
        Icon(
            Icons.Filled.Close, contentDescription = "Hide Authorization Sheet",
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.size(24.dp)
        )
    }
}

