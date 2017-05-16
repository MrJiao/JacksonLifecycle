package com.jackson.presentelifecycle;


import com.jackson.fragmentactivitylifecycle.glide_lifecycle.FragmentLifecycleCallbacks;

/**
 * Created by jackson on 2017/5/14.
 */

public class FragmentPresenterLifecycle implements FragmentLifecycleCallbacks {

    private android.app.Fragment fragment;
    private android.support.v4.app.Fragment supportFragment;
    private final IPresenterCreator creator;
    private PresenterLoader loader;
    private SupportPresenterLoader supportLoader;

    public FragmentPresenterLifecycle(android.app.Fragment fragment, IPresenterCreator creator) {
        this.fragment = fragment;
        this.creator = creator;
    }

    public FragmentPresenterLifecycle(android.support.v4.app.Fragment supportFragment, IPresenterCreator creator) {
        this.supportFragment = supportFragment;
        this.creator = creator;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void onActivityCreated() {
        if(loader!=null)return;
        if (fragment != null ) {
            loader = (PresenterLoader) fragment.getLoaderManager().initLoader(0, null, new PresenterCallbacks<>(fragment.getActivity(), creator));
            return;
        } else if (supportFragment != null) {
            supportLoader = (SupportPresenterLoader) supportFragment.getLoaderManager().initLoader(0, null, new SupportPresenterCallbacks<>(supportFragment.getContext(), creator));
            return;
        }
    }

    @Override
    public void onStart() {
        final ILifeCyclePresenter presenter = getPresenter();
        if(fragment!=null){
            presenter.onAttachView(fragment);
        }else if(supportFragment !=null){
            presenter.onAttachView(supportFragment);
        }
        presenter.onInitFinished();
        creator.initPresenterFinished(presenter);
    }

    @Override
    public void onStop() {
        getPresenter().onStop();
    }

    @Override
    public void onDestroy() {
        loader = null;
    }

    private ILifeCyclePresenter getPresenter() {
        if (loader != null) {
            return loader.getPresenter();
        } else if (supportLoader != null) {
            return supportLoader.getPresenter();
        }
        return null;
    }


}