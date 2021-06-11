package com.github.mik629.android.fundamentals.ui.movieslist

import androidx.test.core.app.ActivityScenario
import com.github.mik629.android.fundamentals.screens.MovieDetailsScreen
import com.github.mik629.android.fundamentals.screens.MoviesListScreen
import com.github.mik629.android.fundamentals.ui.AppActivity
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test

class MoviesListTest : TestCase() {
    @Test
    fun testRecyclerViewItemLayout() {
        ActivityScenario.launch(AppActivity::class.java)
        MoviesListScreen {
            movieList {
                firstChild<MoviesListScreen.MovieItem> {
                    moviePoster {
                        isVisible()
                    }
                    minAge {
                        isVisible()
                    }
                    genres {
                        isVisible()
                    }
                    reviews {
                        isVisible()
                    }
                    movieTitle {
                        isVisible()
                    }
                    movieLength {
                        isVisible()
                    }
                }
            }
        }
    }

    @Test
    fun testOnRecyclerViewItemClick() {
        run {
            ActivityScenario.launch(AppActivity::class.java)
            step("Click on movie item to open details screen") {
                MoviesListScreen {
                    movieList {
                        firstChild<MoviesListScreen.MovieItem> {
                            click()
                        }
                    }
                }
            }

            step("Check layout of MovieDetails screen") {
                MovieDetailsScreen {
                    backgroundImg {
                        isVisible()
                    }
                    back {
                        isVisible()
                    }
                    age {
                        isVisible()
                    }
//                    movieTitle {
//                        isVisible()
//                    }
                    genre {
                        isVisible()
                    }
//                    reviews {
//                        isVisible()
//                    }
                    storylineTitle {
                        isVisible()
                    }
                    storyline {
                        isVisible()
                    }
                    cast {
                        isVisible()
                    }
                    actors {
                        isVisible()
                        firstChild<MovieDetailsScreen.ActorItem> {
                            avatar {
                                isVisible()
                            }
                            name {
                                isVisible()
                            }
                        }
                    }
                }
            }
        }
    }
}