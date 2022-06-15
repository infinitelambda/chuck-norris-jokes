import SwiftUI
import sdk

@main
struct iOSApp: App {
    
    private let viewModelFactory = ViewModelFactoryImpl()
    
	var body: some Scene {
		WindowGroup {
            ContentView(viewModelFactory: self.viewModelFactory)
		}
	}
}
