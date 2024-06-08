import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

struct ContentView: View {
    init() {
        KoinHelperKt.doInitKoin()
    }

    var body: some View {
        ComposeView()
            .ignoresSafeArea(.all) // Compose has own keyboard handler
    }
}
