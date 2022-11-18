package com.pthw.appbase.di

import com.pthw.data.feature.article.repository_impl.ArticleRepositoryImpl
import com.pthw.data.feature.githubrepo.repository_impl.GithubRepositoryImpl
import com.pthw.data.feature.samplepost.repository_impl.PostRepositoryImpl
import com.pthw.data.feature.splashimage.repository_impl.SplashPhotoRepositoryImpl
import com.pthw.domain.feature.article.repository.ArticleRepository
import com.pthw.domain.feature.githubrepo.repository.GithubRepository
import com.pthw.domain.feature.samplepost.repository.PostRepository
import com.pthw.domain.feature.splashimage.repository.SplashPhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository

    @Binds
    abstract fun bindImageRepository(splashPhotoRepositoryImpl: SplashPhotoRepositoryImpl): SplashPhotoRepository

    @Binds
    abstract fun bindGithubRepository(githubRepositoryImpl: GithubRepositoryImpl): GithubRepository

    @Binds
    abstract fun bindSamplePostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository
}