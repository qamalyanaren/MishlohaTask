package aren.kamalyan.domain.usecase;

import javax.inject.Inject;

import aren.kamalyan.domain.entity.RepoUiEntity;
import aren.kamalyan.domain.repository.FavoriteJavaRepository;
import io.reactivex.rxjava3.core.Completable;

public class RemoveFromFavoriteJavaUseCase {

    private final FavoriteJavaRepository repository;

    @Inject
    public RemoveFromFavoriteJavaUseCase(FavoriteJavaRepository repository) {
        this.repository = repository;
    }

    public Completable invoke(RepoUiEntity repo) {
        return repository.removeFromFavorite(repo);
    }
}