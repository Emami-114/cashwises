package ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import org.company.app.theme.cw_dark_whiteText
import ui.components.customModiefier.noRippleClickable
import utils.openUrl

@Composable
fun CustomHyperlinkView(
    modifier: Modifier = Modifier,
    fullText: String,
    linksText: List<String>,
    hyperLinks: List<String>,
    linkTextColor: Color = Color.Blue,
    color: Color = cw_dark_whiteText,
    linkTextFontWeight: FontWeight = FontWeight.Medium,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        addStyle(
            style = SpanStyle(fontSize = fontSize, color = color),
            start = 0,
            end = fullText.length
        )
        linksText.forEachIndexed { index, link ->
            val startIndex = fullText.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ), start = startIndex, end = endIndex
            )
            addStringAnnotation(
                tag = "URL", annotation = hyperLinks[index], start = startIndex, end = endIndex
            )
        }

    }
    ClickableText(modifier = Modifier, text = annotatedString, onClick = {
        annotatedString.getStringAnnotations("URL", it, it)
            .firstOrNull()?.let { stringAnnotation ->
                openUrl(stringAnnotation.item)
            }
    })
}