package ui.account.imprint

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.imprint
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import org.company.app.theme.cw_dark_green
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.stringResource
import ui.AppConstants
import ui.components.CustomTopAppBar

@Composable
fun ImprintView(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val richTextState = rememberRichTextState()
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        richTextState.setHtml(
            """<p><strong style="font-size: 24px;">Impressum </strong></p><p><br></p><p>Max Mustermann </p><p>Musterladen (Einzelunternehmer) </p><p>Musterstraße 111 </p><p>Gebäude 44 </p><p>90210 Musterstadt </p><p><br></p><p><strong style="font-size: 24px;">Kontakt</strong> </p><p><br></p><p>Telefon: +49 (0) 123 44 55 66 </p><p>Telefax: +49 (0) 123 44 55 99 </p><p>E-Mail: info@cwcash.de </p><p><br></p><p><strong style="font-size: 24px;">Umsatzsteuer-ID </strong></p><p><br></p><p>Umsatzsteuer-Identifikationsnummer gemäß § 27 a Umsatzsteuergesetz: </p><p>DE999999999</p><p><br></p><p><strong style="font-size: 24px;">EU-Streitschlichtung </strong></p><p><br></p><p>Die Europäische Kommission stellt eine Plattform zur Online-Streitbeilegung (OS) bereit: https://ec.europa.eu/consumers/odr/. </p><p>Unsere E-Mail-Adresse finden Sie oben im Impressum.</p><p><br></p><p><strong style="font-size: 24px;">Verbraucherstreitbeilegung/Universalschlichtungsstelle </strong></p><p><br></p><p>Wir sind nicht bereit oder verpflichtet, an Streitbeilegungsverfahren vor einer Verbraucherschlichtungsstelle teilzunehmen. </p><p><br></p><p><strong style="font-size: 24px;">Zentrale Kontaktstelle nach dem Digital Services Act - DSA (Verordnung (EU) 2022/265) </strong></p><p><br></p><p>Unsere zentrale Kontaktstelle für Nutzer und Behörden nach Art. 11, 12 DSA erreichen Sie wie folgt: </p><p><br></p><p>E-Mail: [E-Mail-Adresse der Kontaktstelle] </p><p><br></p><p>Die für den Kontakt zur Verfügung stehenden Sprachen sind: Deutsch, Englisch</p>"""
        )
    }
    Scaffold(
        topBar = {
            CustomTopAppBar(
                modifier = modifier,
                title = stringResource(Res.string.imprint),
                backButtonAction = {
                    navController.popBackStack()
                })
        }, containerColor = Color.Transparent
    ) { innerPadding ->
        Column(modifier = Modifier.verticalScroll(state = scrollState)) {
            RichText(
                state = richTextState,
                modifier = Modifier.padding(innerPadding).padding(10.dp),
                color = cw_dark_whiteText
            )
        }
    }
}