package aren.kamalyan.mishlohatask.ui.details;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import aren.kamalyan.domain.delegate.ActionType;
import aren.kamalyan.domain.delegate.FavoriteAction;
import aren.kamalyan.domain.delegate.FavoriteActionDelegate;
import aren.kamalyan.domain.entity.RepoUiEntity;
import aren.kamalyan.domain.usecase.AddToFavoriteJavaUseCase;
import aren.kamalyan.domain.usecase.RemoveFromFavoriteJavaUseCase;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class RepoDetailsViewModel extends ViewModel {
    private final static String TAG = "RepoDetailsViewModel";
    private final AddToFavoriteJavaUseCase addToFavoriteUseCase;
    private final RemoveFromFavoriteJavaUseCase removeFromFavoriteUseCase;
    private final FavoriteActionDelegate favoriteActionDelegate;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<RepoUiEntity> _repoData = new MutableLiveData<>();
    public LiveData<RepoUiEntity> repoData = _repoData;

    @Inject
    public RepoDetailsViewModel(AddToFavoriteJavaUseCase addToFavoriteUseCase,
                                RemoveFromFavoriteJavaUseCase removeFromFavoriteUseCase,
                                FavoriteActionDelegate favoriteActionDelegate) {
        this.addToFavoriteUseCase = addToFavoriteUseCase;
        this.removeFromFavoriteUseCase = removeFromFavoriteUseCase;
        this.favoriteActionDelegate = favoriteActionDelegate;
    }

    public void setRepoData(RepoUiEntity repoData) {
        _repoData.setValue(repoData);
    }

    public void addToFavorite(RepoUiEntity currentRepo) {
        if (currentRepo == null) return;
        disposables.add(addToFavoriteUseCase.invoke(currentRepo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> handleFavoriteSuccess(currentRepo, true),
                        this::handleFavoriteError
                ));
    }

    public void removeFromFavorite(RepoUiEntity currentRepo) {
        if (currentRepo == null) return;
        disposables.add(removeFromFavoriteUseCase.invoke(currentRepo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> handleFavoriteSuccess(currentRepo, false),
                        this::handleFavoriteError
                ));
    }

    private void handleFavoriteSuccess(RepoUiEntity repo, boolean isFavorite) {
        RepoUiEntity updatedRepo = repo.withFavoriteStatus(isFavorite);
        _repoData.postValue(updatedRepo);
        favoriteActionDelegate.setFavoriteActionFromJava(new FavoriteAction(updatedRepo.getId(), isFavorite ? ActionType.ADD : ActionType.REMOVE));
    }

    private void handleFavoriteError(Throwable throwable) {
        Log.e(TAG, "Error processing favorite operation", throwable);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
