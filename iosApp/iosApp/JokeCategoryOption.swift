//
//  JokeCategoryOption.swift
//  iosApp
//
//  Created by Danail Alexiev on 21.03.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sdk

struct JokeCategoryOption: Hashable {
    
    static let ANY = JokeCategoryOption(displayName: NSLocalizedString("joke_category_any", comment: ""), category: nil)
    
    let displayName: String
    let category: JokeCategory?
    
}

extension JokeCategory {
    
    func toOption() -> JokeCategoryOption {
        return JokeCategoryOption(displayName: getDisplayName(), category: self)
    }
    
    private func getDisplayName() -> String {
        return NSLocalizedString("joke_category_\(self.name.lowercased())", comment: "")
    }
    
}
