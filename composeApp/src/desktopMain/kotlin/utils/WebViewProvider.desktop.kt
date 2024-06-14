package utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposeWindow
import javax.swing.JEditorPane
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

@Composable
actual fun WebPageViewer(url: String) {
    SwingUtilities.invokeLater {
        val windows = ComposeWindow()

        windows.setSize(800, 600)
        windows.isVisible = true
        val editorPane = JEditorPane().apply {
        isEditable = true
        page = java.net.URL(url)
        }

        windows.add(JScrollPane(editorPane))
       editorPane.page
    }
}