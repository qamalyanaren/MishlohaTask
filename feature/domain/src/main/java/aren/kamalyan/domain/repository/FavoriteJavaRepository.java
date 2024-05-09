package aren.kamalyan.domain.repository;

import aren.kamalyan.domain.entity.RepoUiEntity;
import io.reactivex.rxjava3.core.Completable;

public interface FavoriteJavaRepository {
    Completable addToFavorite(RepoUiEntity repo);
    Completable removeFromFavorite(RepoUiEntity repo);
}
