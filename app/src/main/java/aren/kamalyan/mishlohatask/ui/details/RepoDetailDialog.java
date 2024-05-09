package aren.kamalyan.mishlohatask.ui.details;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import aren.kamalyan.domain.entity.RepoUiEntity;
import aren.kamalyan.mishlohatask.databinding.DialogRepositoryDetailBinding;
import coil.Coil;
import coil.request.ImageRequest;

public class RepoDetailDialog extends Dialog {
    private final static String TAG = "RepoDetailDialog";
    private DialogRepositoryDetailBinding binding;
    private final RepoDetailsViewModel viewModel;
    private final LifecycleOwner lifecycleOwner;

    public RepoDetailDialog(@NonNull Fragment fragment, RepoUiEntity repo) {
        super(fragment.requireContext());
        this.lifecycleOwner = fragment;
        viewModel = new ViewModelProvider(fragment).get(RepoDetailsViewModel.class);
        viewModel.setRepoData(repo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DialogRepositoryDetailBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(binding.getRoot());
        // Set the window attributes for full-screen
        Window window = getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        initObservers();
    }

    private void initObservers() {
        viewModel.repoData.observe(this.lifecycleOwner, repo -> {
            if (repo != null) {
                showRepoDetails(repo);
                setupListeners(repo);
            }
        });
    }

    private void showRepoDetails(RepoUiEntity repo) {
        Coil.imageLoader(getContext()).enqueue(new ImageRequest.Builder(getContext())
                .data(repo.getOwnerAvatarUrl())
//                .placeholder(R.drawable.ic_avatar_placeholder)
//                .error(R.drawable.ic_avatar_error)
                .target(binding.ivAvatar)
                .build());

        binding.tvUsername.setText(repo.getOwnerName());
        binding.tvName.setText(repo.getName());
        binding.tvDescription.setText(getDescriptionText(repo));
        binding.tvLanguage.setText("Language: " + getLanguageText(repo));
        binding.tvForksCount.setText("Forks: " + repo.getForksCount());
        binding.tvStartCount.setText("Stars: " + repo.getStarsCount());
        binding.tvCreatedDAte.setText("Created on: " + repo.getCreatedAt());
    }

    private void setupListeners(RepoUiEntity repo) {
        if (repo.getHtmlUrl() != null) {
            binding.btnGithubLink.setVisibility(View.VISIBLE);
            binding.btnGithubLink.setOnClickListener(v -> {
                Log.d(TAG, "Launching URL: " + repo.getHtmlUrl());
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(repo.getHtmlUrl())));
            });
        } else {
            binding.btnGithubLink.setVisibility(View.GONE);
        }

        binding.ivAvatar.setOnClickListener(v -> {
            if (repo.isFavorite()) {
                viewModel.removeFromFavorite(repo);
            } else {
                viewModel.addToFavorite(repo);
            }
        });
    }

    private String getDescriptionText(RepoUiEntity repo) {
        return (repo.getDescription().isEmpty()) ? "No description provided." : repo.getDescription();
    }

    private String getLanguageText(RepoUiEntity repo) {
        return (repo.getLanguage() == null || repo.getLanguage().isEmpty()) ? "Not specified" : repo.getLanguage();
    }
}
