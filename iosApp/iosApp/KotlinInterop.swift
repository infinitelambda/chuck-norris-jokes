//
//  KotlinInterop.swift
//  iosApp
//
//  Created by Danail Alexiev on 18.03.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sdk

typealias NativeCompletionHandler<Result> = (Result?, Error?) -> ()

typealias NativeCall<Result> = (@escaping NativeCompletionHandler<Result>) -> NativeCancellable

func convertToAsync<Result>(kotlinCall call: @escaping NativeCall<Result>) async throws -> Result {
    let cancellableActor: NativeCancellableActor = NativeCancellableActor()
    
    return try await withTaskCancellationHandler(
        operation: {
            try await withUnsafeThrowingContinuation { continuation in
                let completionHandler: NativeCompletionHandler<Result> = { result, error in
                    if (result != nil) {
                        continuation.resume(with: .success(result!))
                    } else {
                        continuation.resume(with: .failure(error!))
                    }
                }
                
                let cancellable = call(completionHandler)
                Task { await cancellableActor.setCancellable(cancellable: cancellable) }
            }
        },
        onCancel: {
            Task { await cancellableActor.cancel() }
        }
    )
    
}

private actor NativeCancellableActor {
    
    private var isCancelled: Bool = false
    private var cancellable: NativeCancellable?
    
    func setCancellable(cancellable: NativeCancellable) {
        if (isCancelled) {
            cancellable.cancel()
        }
        self.cancellable = cancellable
    }
    
    func cancel() {
        cancellable?.cancel()
        isCancelled = true
    }
    
}
