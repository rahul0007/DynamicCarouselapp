import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.dynamiccarouselapp.core.util.Dimens

@Composable
fun ListItemCard(
    title: String,
    subtitle: String,
    iconResId: Int
) {
    Row(
        modifier = Modifier
            .padding(Dimens.PaddingS)
            .fillMaxWidth()
            .background(Color(0xFFcde8e1), RoundedCornerShape(Dimens.BorderRadius))
            .padding(Dimens.PaddingM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier
                .size(Dimens.ImageSize)
                .clip(RoundedCornerShape(Dimens.BorderRadius)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(Dimens.PaddingL))

        Column {
            Text(
                text = title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = Dimens.TitleFontSize
            )
            Text(
                text = subtitle,
                color = Color.Gray,
                fontSize = Dimens.SubtitleFontSize
            )
        }
    }
}


