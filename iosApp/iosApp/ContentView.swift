import SwiftUI
import sdk

struct ContentView: View {
    
    @ObservedObject private var viewModel: ViewModel
    
    init(viewModelFactory: ViewModelFactory) {
        self.viewModel = viewModelFactory.getContentViewModel()
    }

	var body: some View {
        NavigationView {
            ZStack {
                VStack {
                    HStack {
                        Text("Category")
                        Spacer()
                        Picker(selection: $viewModel.selectedJokeCategory, label: Text("")) {
                            ForEach(viewModel.jokeCategories, id: \.self) { option in
                                Text(option.displayName).tag(option)
                            }
                        }.onChange(of: viewModel.selectedJokeCategory, perform: {category in
                            Task.init {
                                await viewModel.refreshJoke()
                            }
                        })
                    }
                    Text(viewModel.joke?.value ?? "")
                    Spacer()
                }
                
                if (viewModel.isLoading) {
                    ProgressView()
                }
            }
            .navigationTitle("Chuck Norris Jokes")
            .toolbar {
                ToolbarItem(placement: .bottomBar) {
                    Button("New joke") {
                        Task.init {
                            await viewModel.refreshJoke()
                        }
                    }
                }
            }
            .progressViewStyle(.circular)
            .padding()
            .onAppear {
                    Task.init {
                        await viewModel.loadData()
                    }
                }
        }
	}
}

extension ContentView {
    
    @MainActor
    class ViewModel: ObservableObject {
        
        private let jokeClient: JokeClient
        
        @Published var isLoading: Bool = true
        
        @Published var jokeCategories: [JokeCategoryOption] = []
        @Published var selectedJokeCategory: JokeCategoryOption = JokeCategoryOption.ANY
        
        @Published var joke: Joke? = nil
        
        init(jokeClient: JokeClient) {
            self.jokeClient = jokeClient
        }
        
        func loadData() async {
            self.isLoading = true
            do {
                var options = try await self.jokeClient.getJokeCategoriesAsync().map({ category in
                    category.toOption()
                })
                options.insert(JokeCategoryOption.ANY, at: 0)
                self.jokeCategories = options
                self.joke = try await self.jokeClient.getRandomJokeAsync(jokeCategory: nil)
            } catch {
                // ignore for now
            }
            
            self.isLoading = false
        }
    
        
        func refreshJoke() async {
            self.isLoading = true
            do {
                self.joke = try await self.jokeClient.getRandomJokeAsync(jokeCategory: selectedJokeCategory.category)
            } catch {
                // ignore for now
            }
            self.isLoading = false
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    
    class NativeCancellableMock: NativeCancellable {
        
        func cancel() {
            // do nothing
        }
        
    }
    
    class JokeClientMock: JokeClient {
        
        func getJokeCategories(completionHandler: @escaping ([JokeCategory]?, Error?) -> Void) -> NativeCancellable {
            completionHandler(
                [
                    JokeCategory.animal,
                    JokeCategory.career,
                    JokeCategory.dev
                ],
                nil
            );
            
            return NativeCancellableMock();
        }
        
        func getRandomJoke(category: JokeCategory?, completionHandler: @escaping (Joke?, Error?) -> Void) -> NativeCancellable {
        
            completionHandler(
                Joke(
                    iconUrl: "https://someurl.com/image",
                    id: "123",
                    url: "https://someurl.com",
                    value: "This is a preview joke",
                    categories: []
                ),
                nil);
            
            return NativeCancellableMock();
        }
        
        
        func findJoke(query: String, completionHandler: @escaping (JokeSearchResult?, Error?) -> Void) ->
            NativeCancellable {
                return NativeCancellableMock();
        }
        
    }
    
    class ViewModelFactoryMock: ViewModelFactory {
        
        @MainActor func getContentViewModel() -> ContentView.ViewModel {
            return ContentView.ViewModel(jokeClient: JokeClientMock());
        }
        
    }
    
	static var previews: some View {
        ContentView(viewModelFactory: ViewModelFactoryMock());
	}
}
