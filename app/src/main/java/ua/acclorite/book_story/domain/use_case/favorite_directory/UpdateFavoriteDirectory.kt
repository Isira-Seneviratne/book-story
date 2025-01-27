package ua.acclorite.book_story.domain.use_case.favorite_directory

import ua.acclorite.book_story.domain.repository.BookRepository
import javax.inject.Inject

class UpdateFavoriteDirectory @Inject constructor(private val repository: BookRepository) {
    suspend fun execute(path: String) {
        return repository.updateFavoriteDirectory(path)
    }
}