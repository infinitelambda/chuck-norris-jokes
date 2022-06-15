//
//  JokeClientExt.swift
//  iosApp
//
//  Created by Danail Alexiev on 18.03.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sdk

extension JokeClient {
    
    func getRandomJokeAsync(jokeCategory: JokeCategory? = nil) async throws -> Joke {
        return try await convertToAsync(kotlinCall: { completionHandler in
            self.getRandomJoke(category: jokeCategory, completionHandler: completionHandler)
        })
    }
    
    func getJokeCategoriesAsync() async throws -> [JokeCategory] {
        return try await convertToAsync(kotlinCall: { completionHandler in
            self.getJokeCategories(completionHandler: completionHandler)
        })
    }
    
}
