package aren.kamalyan.data.repository;

import javax.inject.Inject;

import aren.kamalyan.data.db.FavoriteDBEntity;
import aren.kamalyan.data.db.FavoriteJavaDao;
import aren.kamalyan.data.mapper.MapperRepoUIEntityToDbEntity;
import aren.kamalyan.domain.entity.RepoUiEntity;
import aren.kamalyan.domain.repository.FavoriteJavaRepository;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavoriteJavaRepositoryImpl implements FavoriteJavaRepository {
    private final FavoriteJavaDao favoriteJavaDao;
    private final MapperRepoUIEntityToDbEntity toDbEntityMapper;

    @Inject
    public FavoriteJavaRepositoryImpl(FavoriteJavaDao favoriteJavaDao) {
        this.favoriteJavaDao = favoriteJavaDao;
        this.toDbEntityMapper = new MapperRepoUIEntityToDbEntity();
    }

    @Override
    public Completable addToFavorite(RepoUiEntity repo) {
        return Completable.fromAction(() -> {
            FavoriteDBEntity favoriteDbEntity = toDbEntityMapper.map(repo);
            favoriteJavaDao.insertOne(favoriteDbEntity);
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable removeFromFavorite(RepoUiEntity repo) {
        return Completable.fromAction(() -> favoriteJavaDao.deleteOneById(repo.getId()))
                .subscribeOn(Schedulers.io());
    }
}
