package stud.gilmon.ui.main.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import stud.gilmon.ui.components.CustomButton
import stud.gilmon.ui.components.CustomText
import stud.gilmon.ui.components.CustomTextField
import stud.gilmon.ui.components.LabelText
import stud.gilmon.ui.components.SocialNetworkIcon
import stud.gilmon.ui.theme.OrangeOdnoklassniki
import stud.gilmon.ui.theme.YellowButton

@Preview
@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onBackground)
            .fillMaxSize()
            .padding( start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(  modifier = Modifier.fillMaxWidth()){
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()){
                LabelText(
                    text = "Authorization",
                    modifier = Modifier
                )
            }

            Box(contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxWidth())
            {
                CloseButton()
            }
        }
        CustomText(text = "in the discount service with free coupons\n " +
                "and promo codes with 100% discount")
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .size(1.dp)
        )
        CustomTextField(value = "", label = "email")

        Button(
            onClick = { },
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
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()) {
            SocialNetworkIcon(color = OrangeOdnoklassniki)
            SocialNetworkIcon(color = OrangeOdnoklassniki)
            SocialNetworkIcon(color = OrangeOdnoklassniki)
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()) {
            SocialNetworkIcon(color = OrangeOdnoklassniki)
            SocialNetworkIcon(color = OrangeOdnoklassniki)
        }
    }

}
@Composable
fun CloseButton(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .shadow(8.dp, shape = CircleShape)
            .size(36.dp)
            .clip(CircleShape)
            .clickable { }
            .background(Color.Gray)
    ) {
        Icon(
            Icons.Filled.Close, contentDescription = "Hide Authorization Sheet",
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.size(24.dp)
        )
    }
}

