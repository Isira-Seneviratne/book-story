package ua.acclorite.book_story.presentation.screens.settings.nested.appearance.components.theme_switcher

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ua.acclorite.book_story.R
import ua.acclorite.book_story.presentation.components.CategoryTitle
import ua.acclorite.book_story.presentation.data.MainEvent
import ua.acclorite.book_story.presentation.data.MainViewModel
import ua.acclorite.book_story.ui.Theme
import ua.acclorite.book_story.ui.isDark
import ua.acclorite.book_story.util.Constants

/**
 * Theme switcher.
 */
@Composable
fun AppearanceSettingsThemeSwitcher(
    mainViewModel: MainViewModel
) {
    val theme = mainViewModel.theme.collectAsState().value!!
    val darkTheme = mainViewModel.darkTheme.collectAsState().value!!

    val themes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) Constants.THEMES
    else Constants.THEMES.dropWhile { it.first == Theme.DYNAMIC }

    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        CategoryTitle(
            title = stringResource(id = R.string.app_theme_option)
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            Modifier
                .fillMaxWidth()
        ) {
            itemsIndexed(themes) { index, themeEntry ->
                if (index == 0)
                    Spacer(modifier = Modifier.width(18.dp))

                AppearanceSettingsThemeSwitcherItem(
                    theme = themeEntry,
                    darkTheme = darkTheme.isDark(),
                    selected = theme == themeEntry.first
                ) {
                    mainViewModel.onEvent(MainEvent.OnChangeTheme(themeEntry.first.toString()))
                }

                if (index != Theme.entries.lastIndex) {
                    Spacer(modifier = Modifier.width(8.dp))
                } else {
                    Spacer(modifier = Modifier.width(18.dp))
                }
            }
        }
    }
}