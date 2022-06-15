//
//  ViewModelFactory.swift
//  iosApp
//
//  Created by Danail Alexiev on 18.03.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sdk

protocol ViewModelFactory {
    
    func getContentViewModel() -> ContentView.ViewModel
    
}

class ViewModelFactoryImpl : ViewModelFactory {
    
    private let jokeClient: JokeClient = JokeClientKt.JokeClient(block: {config in
        config.isLoggingEnabled = true
    })
        
    @MainActor func getContentViewModel() -> ContentView.ViewModel {
        return ContentView.ViewModel(jokeClient: self.jokeClient)
    }
}
