package aren.kamalyan.domain.usecase;

import javax.inject.Inject;

import aren.kamalyan.domain.entity.RepoUiEntity;
import aren.kamalyan.domain.repository.FavoriteJavaRepository;
import io.reactivex.rxjava3.core.Completable;

public class AddToFavoriteJavaUseCase {

    private final FavoriteJavaRepository repository;

    @Inject
    public AddToFavoriteJavaUseCase(FavoriteJavaRepository repository) {
        this.repository = repository;
    }

    public Completable invoke(RepoUiEntity repo) {
        return repository.addToFavorite(repo);
    }
}